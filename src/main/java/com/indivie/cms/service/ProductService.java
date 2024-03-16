package com.indivie.cms.service;

import com.indivie.cms.dto.request.*;
import com.indivie.cms.dto.response.ProductResponse;
import com.indivie.cms.entity.Product;
import org.springframework.data.domain.Page;

public interface ProductService {
    ProductResponse create(NewProductRequest request);
    Page<ProductResponse> getAll(SearchProductRequest request);
    Product getById(String id);
    ProductResponse update(UpdateProductCostPriceRequest request);
    void deleteById(String id);
}
