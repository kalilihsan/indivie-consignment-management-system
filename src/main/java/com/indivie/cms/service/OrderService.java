package com.indivie.cms.service;

import com.indivie.cms.dto.request.OrderDetailRequest;
import com.indivie.cms.dto.request.OrderRequest;
import com.indivie.cms.dto.request.SearchOrderRequest;
import com.indivie.cms.dto.response.OrderDetailResponse;
import com.indivie.cms.dto.response.OrderResponse;
import org.springframework.data.domain.Page;

public interface OrderService {
    OrderResponse create(OrderRequest request);
    Page<OrderResponse> getAll(SearchOrderRequest request);
}
