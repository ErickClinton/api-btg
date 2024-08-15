package com.erickclinton.ordems.order.repository;

import com.erickclinton.ordems.order.entities.OrderEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<OrderEntity, Long> {
}
