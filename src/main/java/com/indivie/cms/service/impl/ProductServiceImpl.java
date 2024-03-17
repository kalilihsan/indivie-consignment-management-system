package com.indivie.cms.service.impl;

import com.indivie.cms.dto.request.*;
import com.indivie.cms.dto.response.ProductResponse;
import com.indivie.cms.entity.NetCost;
import com.indivie.cms.entity.NetPrice;
import com.indivie.cms.entity.Product;
import com.indivie.cms.repository.ProductRepository;
import com.indivie.cms.service.NetCostService;
import com.indivie.cms.service.NetPriceService;
import com.indivie.cms.service.ProductService;
import com.indivie.cms.service.ProductSupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductSupplierService supplierService;
    private final NetCostService netCostService;
    private final NetPriceService netPriceService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ProductResponse create(NewProductRequest request) {
        Product product = Product.builder()
                .name(request.getName())
                .productSupplier(supplierService.getById(request.getSupplierId()))
                .netCost(request.getNetCost())
                .netPrice(request.getNetPrice())
                .build();

        productRepository.saveAndFlush(product);
        netCostService.create(NetCost.builder()
                        .product(product)
                        .netCost(request.getNetCost())
                        .dateIssued(new Date())
                        .build());
        netPriceService.create(NetPrice.builder()
                .product(product)
                .netPrice(request.getNetPrice())
                .dateIssued(new Date())
                .build());

        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .supplier(product.getProductSupplier().getId())
                .NetCost(product.getNetCost())
                .NetPrice(product.getNetPrice())
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Page<ProductResponse> getAll(SearchProductRequest request) {
        if (request.getPage() <= 0) request.setPage(1);

        Pageable pageable = PageRequest.of((request.getPage() - 1), request.getSize());

        Page<Product> products = productRepository.findAll(pageable);


        if (request.getSupplierId() != null) {
            if (productRepository.findAllBySupplierId(request.getSupplierId()).isPresent()) {
                List<Product> productList = productRepository.findAllBySupplierId(request.getSupplierId()).get();
                products = new PageImpl<>(productList, pageable, productList.size());
            }
        }

        return products.map(product -> {
            return ProductResponse.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .supplier(product.getProductSupplier().getId())
                    .NetCost(product.getNetCost())
                    .NetPrice(product.getNetPrice())
                    .build();
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Product getById(String id) {
        return productRepository.findById(id).orElseThrow(() -> new  ResponseStatusException(HttpStatus.NOT_FOUND,"Not found"));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ProductResponse update(UpdateProductCostPriceRequest request) {
        Product product = getById(request.getProductId());

        if (request.getNetPrice() != null) {
            product.setNetPrice(request.getNetPrice());

            netPriceService.create(NetPrice.builder()
                    .product(product)
                    .dateIssued(new Date())
                    .netPrice(product.getNetPrice())
                    .build());
        }

        if (request.getNetCost() != null) {
            product.setNetCost(request.getNetCost());

            netCostService.create(NetCost.builder()
                    .product(product)
                    .dateIssued(new Date())
                    .netCost(product.getNetCost())
                    .build());
        }

        productRepository.saveAndFlush(product);

        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .supplier(product.getProductSupplier().getId())
                .NetCost(product.getNetCost())
                .NetPrice(product.getNetPrice())
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(String id) {
        Product product = getById(id);
        productRepository.delete(product);
    }
}
