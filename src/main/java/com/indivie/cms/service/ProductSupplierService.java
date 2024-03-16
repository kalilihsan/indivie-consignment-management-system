package com.indivie.cms.service;

import com.indivie.cms.dto.request.NewSupplierRequest;
import com.indivie.cms.dto.request.SearchRequest;
import com.indivie.cms.dto.request.UpdateSupplierRequest;
import com.indivie.cms.dto.response.SupplierResponse;
import com.indivie.cms.entity.ProductSupplier;
import org.springframework.data.domain.Page;

public interface ProductSupplierService {
    SupplierResponse create(NewSupplierRequest request);
    Page<SupplierResponse> getAll(SearchRequest request);
    ProductSupplier getById(String id);
    SupplierResponse update(UpdateSupplierRequest request);
    void deleteById(String id);
}
