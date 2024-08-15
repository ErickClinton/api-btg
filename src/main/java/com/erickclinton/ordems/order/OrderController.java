package com.erickclinton.ordems.order;


import com.erickclinton.ordems.order.dto.ApiResponse;
import com.erickclinton.ordems.order.dto.OrderResponse;
import com.erickclinton.ordems.order.dto.PaginationResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/customers/{customerId}/orders")
    public ResponseEntity<ApiResponse<OrderResponse>> listOrders(@PathVariable("customerId") Long customerId,
                                                                 @RequestParam(name = "page",defaultValue = "0") Integer page,
                                                                 @RequestParam(name = "pageSize",defaultValue = "10") Integer pageSize){

        var pageResponse = this.orderService.findAllByCustomerId(customerId, PageRequest.of(page,pageSize));
        return ResponseEntity.ok(new ApiResponse<>(
                pageResponse.getContent(),
                PaginationResponse.fromPage(pageResponse)
        ));
    }
}
