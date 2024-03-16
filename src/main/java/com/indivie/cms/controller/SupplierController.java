package com.indivie.cms.controller;

import com.indivie.cms.constant.ApiUrl;
import com.indivie.cms.dto.request.NewSupplierRequest;
import com.indivie.cms.dto.request.SearchRequest;
import com.indivie.cms.dto.request.UpdateSupplierRequest;
import com.indivie.cms.dto.response.CommonResponse;
import com.indivie.cms.dto.response.PagingResponse;
import com.indivie.cms.dto.response.SupplierResponse;
import com.indivie.cms.service.ProductSupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiUrl.SUPPLIER_API)
public class SupplierController {
    private final ProductSupplierService supplierService;
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<SupplierResponse>> createNewSupplier(@RequestBody NewSupplierRequest request) {
        SupplierResponse supplierResponse = supplierService.create(request);
        CommonResponse<SupplierResponse> response = CommonResponse.<SupplierResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Successfully saved")
                .data(supplierResponse)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<List<SupplierResponse>>> getAll(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(name = "direction", defaultValue = "asc") String direction
    ) {
        SearchRequest request = SearchRequest.builder()
                .size(size)
                .page(page)
                .direction(direction)
                .sortBy(sortBy)
                .build();
        Page<SupplierResponse> supplierResponses = supplierService.getAll(request);

        PagingResponse pagingResponse = PagingResponse.builder()
                .totalPages(supplierResponses.getTotalPages())
                .totalElement(supplierResponses.getTotalElements())
                .page(supplierResponses.getPageable().getPageNumber() + 1)
                .size(supplierResponses.getPageable().getPageSize())
                .hasNext(supplierResponses.hasNext())
                .hasPrevious(supplierResponses.hasPrevious())
                .build();

        CommonResponse<List<SupplierResponse>> response = CommonResponse.<List<SupplierResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully get all data")
                .data(supplierResponses.getContent())
                .paging(pagingResponse)
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<SupplierResponse>> updateSupplier(@RequestBody UpdateSupplierRequest request) {
        SupplierResponse supplierResponse = supplierService.update(request);
        CommonResponse<SupplierResponse> response = CommonResponse.<SupplierResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully updated data")
                .data(supplierResponse)
                .build();
        return ResponseEntity.ok(response);
    }
}
