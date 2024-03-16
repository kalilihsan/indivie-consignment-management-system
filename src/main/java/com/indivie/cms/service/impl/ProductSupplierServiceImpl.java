package com.indivie.cms.service.impl;

import com.indivie.cms.dto.request.NewSupplierRequest;
import com.indivie.cms.dto.request.SearchRequest;
import com.indivie.cms.dto.request.UpdateSupplierRequest;
import com.indivie.cms.dto.response.SupplierResponse;
import com.indivie.cms.entity.ProductSupplier;
import com.indivie.cms.repository.ProductSupplierRepository;
import com.indivie.cms.service.ProductSupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductSupplierServiceImpl implements ProductSupplierService {
    private final ProductSupplierRepository supplierRepository;
    @Transactional(rollbackFor = Exception.class)
    @Override
    public SupplierResponse create(NewSupplierRequest request) {
        ProductSupplier supplier = ProductSupplier.builder()
                .name(request.getName())
                .address(request.getAddress())
                .mobilePhoneNo(request.getMobilePhoneNo())
                .build();

        supplierRepository.saveAndFlush(supplier);

        return SupplierResponse.builder()
                .id(supplier.getId())
                .name(supplier.getName())
                .address(supplier.getAddress())
                .mobilePhoneNo(supplier.getMobilePhoneNo())
                .build();
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Page<SupplierResponse> getAll(SearchRequest request) {
        if (request.getPage() <= 0) request.setPage(1);

        Sort sort = Sort.by(Sort.Direction.fromString(request.getDirection()), request.getSortBy());
        Pageable pageable = PageRequest.of((request.getPage() - 1), request.getSize());

        return supplierRepository.findAll(pageable).map(productSupplier -> {
            return SupplierResponse.builder()
                    .id(productSupplier.getId())
                    .name(productSupplier.getName())
                    .address(productSupplier.getAddress())
                    .mobilePhoneNo(productSupplier.getMobilePhoneNo())
                    .build();});
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ProductSupplier getById(String id) {
        return supplierRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public SupplierResponse update(UpdateSupplierRequest request) {
        ProductSupplier supplier = getById(request.getId());
        supplier.setName(request.getName());
        supplier.setAddress(request.getAddress());
        supplier.setMobilePhoneNo(request.getMobilePhoneNo());
        supplierRepository.saveAndFlush(supplier);
        return SupplierResponse.builder()
                .id(supplier.getId())
                .name(supplier.getName())
                .address(supplier.getAddress())
                .mobilePhoneNo(supplier.getMobilePhoneNo())
                .build();
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(String id) {
        ProductSupplier supplier = getById(id);
        supplierRepository.delete(supplier);
    }
}
