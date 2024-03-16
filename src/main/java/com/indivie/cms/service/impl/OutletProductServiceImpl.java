package com.indivie.cms.service.impl;

import com.indivie.cms.dto.request.SearchOutletProductRequest;
import com.indivie.cms.dto.request.UpdateOutletProductRequest;
import com.indivie.cms.dto.response.OutletProductResponse;
import com.indivie.cms.entity.OutletProduct;
import com.indivie.cms.repository.OutletProductRepository;
import com.indivie.cms.service.OutletProductService;
import com.indivie.cms.service.OutletService;
import com.indivie.cms.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OutletProductServiceImpl implements OutletProductService {
    private final OutletProductRepository outletProductRepository;
    private final ProductService productService;
    private final OutletService outletService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public OutletProduct getOrSaveOutletProduct(String outletId, String productId) {
        if (outletProductRepository.findOutletProductByProductIdAndOutletId(productId,outletId).isPresent()) {
            return outletProductRepository.findOutletProductByProductIdAndOutletId(productId,outletId).get();
        } else {
            OutletProduct outletProduct = outletProductRepository
                    .saveAndFlush(OutletProduct.builder()
                            .product(productService.getById(productId))
                            .outlet(outletService.getById(outletId))
                            .qty(0)
                            .build());
            return outletProduct;
        }
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public OutletProduct getById(String id) {
        return outletProductRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Not found"));
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public OutletProductResponse update(UpdateOutletProductRequest request) {
        OutletProduct outletProduct = getById(request.getId());
        if (request.getQty() != null) {
            outletProduct.setQty(request.getQty());
        }
        outletProductRepository.saveAndFlush(outletProduct);
        return OutletProductResponse.builder()
                .id(outletProduct.getId())
                .productId(outletProduct.getProduct().getId())
                .outletId(outletProduct.getOutlet().getId())
                .netPrice(outletProduct.getProduct().getNetPrice())
                .build();
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public Page<OutletProductResponse> getAll(SearchOutletProductRequest request) {
        if (request.getPage() <= 0) request.setPage(1);

        Sort sort = Sort.by(Sort.Direction.fromString(request.getDirection()), request.getSortBy());
        Pageable pageable = PageRequest.of((request.getPage() - 1), request.getSize());

        Page<OutletProduct> outletProductPage = outletProductRepository.findAll(pageable);

        String supplierId = "%%";
        String outletId = "%%";
        if (request.getOutletId() != null) {
            outletId = "%" + request.getOutletId() + "%";
        }
        if (request.getSupplierId() != null) {
            supplierId = "%" + request.getSupplierId() + "%";
        }
        if (outletProductRepository.findListOutletProductByProductIdAndOutletId(supplierId, outletId).isPresent()) {
            List<OutletProduct> outletProducts = outletProductRepository
                    .findListOutletProductByProductIdAndOutletId(
                            supplierId, outletId).get();

            outletProductPage = new PageImpl<>(outletProducts, pageable, outletProducts.size());
        }

        return outletProductPage.map(outletProduct -> {
            return OutletProductResponse.builder()
                    .id(outletProduct.getId())
                    .productId(outletProduct.getProduct().getId())
                    .outletId(outletProduct.getOutlet().getId())
                    .netPrice(outletProduct.getProduct().getNetPrice())
                    .qty(outletProduct.getQty())
                    .build();
        });
    }
}
