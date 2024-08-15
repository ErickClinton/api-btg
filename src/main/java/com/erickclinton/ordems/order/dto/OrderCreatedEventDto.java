package com.erickclinton.ordems.order.dto;

import java.util.List;

public record OrderCreatedEventDto(Long codigoPedido, Long codigoCliente, List<OrderItemEventDto> itens) {
}
