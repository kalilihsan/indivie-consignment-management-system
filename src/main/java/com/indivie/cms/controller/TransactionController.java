package com.indivie.cms.controller;

import com.indivie.cms.constant.ApiUrl;
import com.indivie.cms.dto.request.SearchTransactionRequest;
import com.indivie.cms.dto.request.TransactionRequest;
import com.indivie.cms.dto.response.CommonResponse;
import com.indivie.cms.dto.response.PagingResponse;
import com.indivie.cms.dto.response.TransactionResponse;
import com.indivie.cms.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiUrl.OUTLET_TRANSACTION_API)
public class TransactionController {
    private final TransactionService transactionService;
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<TransactionResponse>> createTransaction(@RequestBody TransactionRequest request) {
        TransactionResponse transactionResponse = transactionService.create(request);
        CommonResponse<TransactionResponse> response = CommonResponse.<TransactionResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Successfully saved data")
                .data(transactionResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<List<TransactionResponse>>> getAll(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(name = "direction", defaultValue = "asc") String direction,
            @RequestParam(name = "outletId", required = false) String outletId,
            @RequestParam(name = "productId", required = false) String productId
    ) {
        SearchTransactionRequest request = SearchTransactionRequest.builder()
                .size(size)
                .page(page)
                .direction(direction)
                .sortBy(sortBy)
                .outletId(outletId)
                .productId(productId)
                .build();
        Page<TransactionResponse> transactionResponses = transactionService.getAll(request);

        PagingResponse pagingResponse = PagingResponse.builder()
                .totalPages(transactionResponses.getTotalPages())
                .totalElement(transactionResponses.getTotalElements())
                .page(transactionResponses.getPageable().getPageNumber() + 1)
                .size(transactionResponses.getPageable().getPageSize())
                .hasNext(transactionResponses.hasNext())
                .hasPrevious(transactionResponses.hasPrevious())
                .build();

        CommonResponse<List<TransactionResponse>> response = CommonResponse.<List<TransactionResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully get all data")
                .data(transactionResponses.getContent())
                .paging(pagingResponse)
                .build();

        return ResponseEntity.ok(response);
    }
}
