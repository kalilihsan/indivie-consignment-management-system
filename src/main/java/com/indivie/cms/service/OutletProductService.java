package com.indivie.cms.service;

import com.indivie.cms.dto.request.SearchOutletProductRequest;
import com.indivie.cms.dto.request.UpdateOutletProductRequest;
import com.indivie.cms.dto.response.OutletProductResponse;
import com.indivie.cms.entity.OutletProduct;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface OutletProductService {
    OutletProduct getOrSaveOutletProduct(String outletId, String productId);
    OutletProduct getById(String id);
    OutletProductResponse update(UpdateOutletProductRequest request);
    Page<OutletProductResponse> getAll(SearchOutletProductRequest request);
}
