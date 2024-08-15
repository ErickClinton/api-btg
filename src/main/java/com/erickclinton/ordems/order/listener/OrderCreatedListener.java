package com.erickclinton.ordems.order.listener;

import com.erickclinton.ordems.config.RabbitMqConfig;
import com.erickclinton.ordems.order.OrderService;
import com.erickclinton.ordems.order.dto.OrderCreatedEventDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedListener {

    private final Logger logger = LoggerFactory.getLogger(OrderCreatedListener.class);

    private final OrderService orderService;

    public OrderCreatedListener(OrderService orderService) {
        this.orderService = orderService;
    }

    @RabbitListener(queues = RabbitMqConfig.ORDER_CREATED_QUEUE)
    public void listen(Message<OrderCreatedEventDto> message) {
        logger.info("message: {}",message);

        orderService.save(message.getPayload());
    }
}
