package com.indivie.cms.service;

import com.indivie.cms.entity.ProductSupplyOrderDetail;

import java.util.List;

public interface OrderDetailService {
    List<ProductSupplyOrderDetail> createBulk(List<ProductSupplyOrderDetail> orderDetails);
}
