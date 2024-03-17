package com.indivie.cms.service.impl;

import com.indivie.cms.entity.ProductSupplyOrderDetail;
import com.indivie.cms.repository.ProductSupplyOrderDetailRepository;
import com.indivie.cms.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {
    private final ProductSupplyOrderDetailRepository repository;

    @Override
    public List<ProductSupplyOrderDetail> createBulk(List<ProductSupplyOrderDetail> orderDetails) {
        return repository.saveAllAndFlush(orderDetails);
    }
}
