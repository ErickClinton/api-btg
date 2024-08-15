package com.erickclinton.ordems.order;

import com.erickclinton.ordems.order.dto.OrderCreatedEventDto;
import com.erickclinton.ordems.order.dto.OrderResponse;
import com.erickclinton.ordems.order.entities.OrderEntity;
import com.erickclinton.ordems.order.entities.OrderItemEntity;
import com.erickclinton.ordems.order.repository.OrderRepository;
import org.bson.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final MongoTemplate mongoTemplate;

    public OrderService(OrderRepository orderRepository,MongoTemplate mongoTemplate) {
        this.orderRepository = orderRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public void save(OrderCreatedEventDto orderCreatedEventDto) {
        var entity = new OrderEntity(orderCreatedEventDto,getOrderItems(orderCreatedEventDto), getTotal(orderCreatedEventDto));
        orderRepository.save(entity);
    }

    public Page<OrderResponse> findAllByCustomerId(Long customerId, PageRequest pageRequest){
        var orders = this.orderRepository.findAllByCustomerId(customerId,pageRequest);

        return orders.map(OrderResponse::fromEntity);
    }

    public BigDecimal findTotalOnOrdersByCustomerId(Long customerId){
        var aggregations = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("customerId").is(customerId)),
                group().sum("total").as("total")
        );

        var response = mongoTemplate.aggregate(aggregations, "orders", Document.class);

        return new BigDecimal(response.getUniqueMappedResult().get("total").toString());
    }

    private static List<OrderItemEntity> getOrderItems(OrderCreatedEventDto orderCreatedEventDto){
        return orderCreatedEventDto.itens().stream()
                .map(item -> new OrderItemEntity(item.produto(),item.quantidade(),item.preco()))
                .toList();
    }

    private BigDecimal getTotal(OrderCreatedEventDto orderCreatedEventDto){
        return orderCreatedEventDto.itens().stream()
                .map(item -> item.preco().multiply(BigDecimal.valueOf(item.quantidade())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }
}
