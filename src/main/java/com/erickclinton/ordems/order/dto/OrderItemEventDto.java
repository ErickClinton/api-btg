package com.erickclinton.ordems.order.dto;

import java.math.BigDecimal;

public record OrderItemEventDto(String produto, Integer quantidade, BigDecimal preco) {
}
