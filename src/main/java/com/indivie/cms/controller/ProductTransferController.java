package com.indivie.cms.controller;

import com.indivie.cms.constant.ApiUrl;
import com.indivie.cms.dto.request.ProductTransferRequest;
import com.indivie.cms.dto.request.SearchProductTransferRequest;
import com.indivie.cms.dto.response.CommonResponse;
import com.indivie.cms.dto.response.PagingResponse;
import com.indivie.cms.dto.response.ProductTransferResponse;
import com.indivie.cms.service.ProductTransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiUrl.PRODUCT_TRANSFER_API)
public class ProductTransferController {
    private final ProductTransferService productTransferService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<ProductTransferResponse>> createProductTransfer(@RequestBody ProductTransferRequest request) {
        ProductTransferResponse productTransferResponse = productTransferService.create(request);

        CommonResponse<ProductTransferResponse> response = CommonResponse.<ProductTransferResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Successfully saved data")
                .data(productTransferResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<List<ProductTransferResponse>>> getAllProductTransfer(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "transferDate") String sortBy,
            @RequestParam(name = "direction", defaultValue = "asc") String direction,
            @RequestParam(name = "outletId", required = false) String outletId,
            @RequestParam(name = "supplierId", required = false) String supplierId,
            @RequestParam(name = "productId", required = false) String productId
    ) {
        SearchProductTransferRequest request = SearchProductTransferRequest.builder()
                .size(size)
                .page(page)
                .direction(direction)
                .sortBy(sortBy)
                .supplierId(supplierId)
                .productId(productId)
                .outletId(outletId)
                .build();
        Page<ProductTransferResponse> productTransferResponses = productTransferService.getAll(request);

        PagingResponse pagingResponse = PagingResponse.builder()
                .totalPages(productTransferResponses.getTotalPages())
                .totalElement(productTransferResponses.getTotalElements())
                .page(productTransferResponses.getPageable().getPageNumber() + 1)
                .size(productTransferResponses.getPageable().getPageSize())
                .hasNext(productTransferResponses.hasNext())
                .hasPrevious(productTransferResponses.hasPrevious())
                .build();

        CommonResponse<List<ProductTransferResponse>> response = CommonResponse.<List<ProductTransferResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully get all data")
                .data(productTransferResponses.getContent())
                .paging(pagingResponse)
                .build();

        return ResponseEntity.ok(response);
    }
}
