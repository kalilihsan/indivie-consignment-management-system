package com.indivie.cms.controller;

import com.indivie.cms.constant.ApiUrl;
import com.indivie.cms.dto.request.OrderRequest;
import com.indivie.cms.dto.request.SearchOrderRequest;
import com.indivie.cms.dto.response.CommonResponse;
import com.indivie.cms.dto.response.OrderResponse;
import com.indivie.cms.dto.response.PagingResponse;
import com.indivie.cms.dto.response.TransactionResponse;
import com.indivie.cms.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiUrl.SUPPLY_ORDER_API)
public class OrderController {
    private final OrderService orderService;
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<OrderResponse>> createOrder(@RequestBody OrderRequest request) {
        OrderResponse orderResponse = orderService.create(request);
        CommonResponse<OrderResponse> response = CommonResponse.<OrderResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Successfully saved data")
                .data(orderResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<List<OrderResponse>>> getAllOrder(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(name = "direction", defaultValue = "asc") String direction,
            @RequestParam(name = "outletId", required = false) String outletId,
            @RequestParam(name = "productId", required = false) String productId,
            @RequestParam(name = "supplierId", required = false) String supplierId
    ) {
        SearchOrderRequest request = SearchOrderRequest.builder()
                .size(size)
                .page(page)
                .direction(direction)
                .sortBy(sortBy)
                .outletId(outletId)
                .productId(productId)
                .supplierId(supplierId)
                .build();
        Page<OrderResponse> orderResponses = orderService.getAll(request);

        PagingResponse pagingResponse = PagingResponse.builder()
                .totalPages(orderResponses.getTotalPages())
                .totalElement(orderResponses.getTotalElements())
                .page(orderResponses.getPageable().getPageNumber() + 1)
                .size(orderResponses.getPageable().getPageSize())
                .hasNext(orderResponses.hasNext())
                .hasPrevious(orderResponses.hasPrevious())
                .build();

        CommonResponse<List<OrderResponse>> response = CommonResponse.<List<OrderResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully get all data")
                .data(orderResponses.getContent())
                .paging(pagingResponse)
                .build();

        return ResponseEntity.ok(response);
    }
}
