package com.indivie.cms.controller;

import com.indivie.cms.constant.ApiUrl;
import com.indivie.cms.dto.request.SearchRequest;
import com.indivie.cms.dto.request.UpdateOutletRequest;
import com.indivie.cms.dto.response.CommonResponse;
import com.indivie.cms.dto.response.OutletResponse;
import com.indivie.cms.dto.response.PagingResponse;
import com.indivie.cms.entity.Outlet;
import com.indivie.cms.service.OutletService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiUrl.OUTLET_API)
public class OutletController {
    private final OutletService outletService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<List<OutletResponse>>> getAllOutlet(
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
        Page<OutletResponse> outletResponses = outletService.getAll(request);

        PagingResponse pagingResponse = PagingResponse.builder()
                .totalPages(outletResponses.getTotalPages())
                .totalElement(outletResponses.getTotalElements())
                .page(outletResponses.getPageable().getPageNumber() + 1)
                .size(outletResponses.getPageable().getPageSize())
                .hasNext(outletResponses.hasNext())
                .hasPrevious(outletResponses.hasPrevious())
                .build();

        CommonResponse<List<OutletResponse>> response = CommonResponse.<List<OutletResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully get all data")
                .data(outletResponses.getContent())
                .paging(pagingResponse)
                .build();

        return ResponseEntity.ok(response);
    }
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<OutletResponse>> updateOutlet(@RequestBody UpdateOutletRequest request) {
        OutletResponse outletResponse = outletService.update(request);

        CommonResponse<OutletResponse> response = CommonResponse.<OutletResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully updated data")
                .data(outletResponse)
                .build();

        return ResponseEntity.ok(response);
    }
}
