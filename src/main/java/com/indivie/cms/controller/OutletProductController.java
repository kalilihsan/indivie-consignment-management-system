package com.indivie.cms.controller;

import com.indivie.cms.constant.ApiUrl;
import com.indivie.cms.dto.request.SearchOutletProductRequest;
import com.indivie.cms.dto.response.CommonResponse;
import com.indivie.cms.dto.response.OutletProductResponse;
import com.indivie.cms.dto.response.PagingResponse;
import com.indivie.cms.service.OutletProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiUrl.OUTLET_PRODUCTS_API)
public class OutletProductController {
    private final OutletProductService outletProductService;
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<List<OutletProductResponse>>> getAllOutletProduct(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "supplierId", required = false) String supplierId,
            @RequestParam(name = "outletId", required = false) String outletId
    ) {
        SearchOutletProductRequest request = SearchOutletProductRequest.builder()
                .size(size)
                .page(page)
                .supplierId(supplierId)
                .outletId(outletId)
                .build();

        Page<OutletProductResponse> outletProductResponses = outletProductService.getAll(request);

        PagingResponse pagingResponse = PagingResponse.builder()
                .totalPages(outletProductResponses.getTotalPages())
                .totalElement(outletProductResponses.getTotalElements())
                .page(outletProductResponses.getPageable().getPageNumber() + 1)
                .size(outletProductResponses.getPageable().getPageSize())
                .hasNext(outletProductResponses.hasNext())
                .hasPrevious(outletProductResponses.hasPrevious())
                .build();

        CommonResponse<List<OutletProductResponse>> response = CommonResponse.<List<OutletProductResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully get all data")
                .data(outletProductResponses.getContent())
                .paging(pagingResponse)
                .build();

        return ResponseEntity.ok(response);
    }
}
