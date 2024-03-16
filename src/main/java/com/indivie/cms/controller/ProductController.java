package com.indivie.cms.controller;

import com.indivie.cms.constant.ApiUrl;
import com.indivie.cms.dto.request.NewProductRequest;
import com.indivie.cms.dto.request.SearchProductRequest;
import com.indivie.cms.dto.request.UpdateProductCostPriceRequest;
import com.indivie.cms.dto.response.CommonResponse;
import com.indivie.cms.dto.response.PagingResponse;
import com.indivie.cms.dto.response.ProductResponse;
import com.indivie.cms.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiUrl.PRODUCT_API)
public class ProductController {
    private final ProductService productService;
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<ProductResponse>> createNewProduct(@RequestBody NewProductRequest request) {
        ProductResponse productResponse = productService.create(request);
        CommonResponse<ProductResponse> response = CommonResponse.<ProductResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Successfully saved data")
                .data(productResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<List<ProductResponse>>> getAll(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(name = "direction", defaultValue = "asc") String direction,
            @RequestParam(name = "supplierId", required = false) String supplierId
    ) {
        SearchProductRequest request = SearchProductRequest.builder()
                .size(size)
                .page(page)
                .direction(direction)
                .sortBy(sortBy)
                .supplierId(supplierId)
                .build();
        Page<ProductResponse> productResponses = productService.getAll(request);

        PagingResponse pagingResponse = PagingResponse.builder()
                .totalPages(productResponses.getTotalPages())
                .totalElement(productResponses.getTotalElements())
                .page(productResponses.getPageable().getPageNumber() + 1)
                .size(productResponses.getPageable().getPageSize())
                .hasNext(productResponses.hasNext())
                .hasPrevious(productResponses.hasPrevious())
                .build();

        CommonResponse<List<ProductResponse>> response = CommonResponse.<List<ProductResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully get all data")
                .data(productResponses.getContent())
                .paging(pagingResponse)
                .build();

        return ResponseEntity.ok(response);
    }
    @PutMapping(path = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<ProductResponse>> updateCostPriceProduct(
            @PathVariable(name = "id") String id,
            @RequestParam(name = "cost", required = false) Long netCost,
            @RequestParam(name = "price", required = false) Long netPrice
    ) {
        UpdateProductCostPriceRequest request = UpdateProductCostPriceRequest.builder()
                .productId(id)
                .netCost(netCost)
                .netPrice(netPrice)
                .build();
        ProductResponse productResponse = productService.update(request);

        CommonResponse<ProductResponse> response = CommonResponse.<ProductResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully update data")
                .data(productResponse)
                .build();

        return ResponseEntity.ok(response);
    }
}
