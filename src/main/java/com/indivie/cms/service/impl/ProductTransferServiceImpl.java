package com.indivie.cms.service.impl;

import com.indivie.cms.constant.TransferType;
import com.indivie.cms.dto.request.ProductTransferRequest;
import com.indivie.cms.dto.request.SearchProductTransferRequest;
import com.indivie.cms.dto.request.TransactionalProductTransferRequest;
import com.indivie.cms.dto.request.UpdateOutletProductRequest;
import com.indivie.cms.dto.response.ProductTransferResponse;
import com.indivie.cms.entity.OutletProduct;
import com.indivie.cms.entity.ProductTransfer;
import com.indivie.cms.repository.ProductTransferRepository;
import com.indivie.cms.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductTransferServiceImpl implements ProductTransferService {
    private final ProductTransferRepository productTransferRepository;
    private final ProductService productService;
    private final OutletService outletService;
    private final OutletProductService outletProductService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ProductTransferResponse create(ProductTransferRequest request) {
        OutletProduct sentProduct = outletProductService.getOrSaveOutletProduct(
                request.getSenderOutletId(), request.getProductId()
        );

        if (sentProduct.getQty() < request.getQty()) {
            throw new RuntimeException("Insufficient stock from sender");
        }

        UpdateOutletProductRequest sentUpdateRequest = UpdateOutletProductRequest
                .builder()
                .id(sentProduct.getId())
                .qty(sentProduct.getQty() - request.getQty())
                .build();

        OutletProduct receivedProduct = outletProductService.getOrSaveOutletProduct(
                request.getReceiverOutletId(), request.getProductId()
        );

        UpdateOutletProductRequest receivedUpdateRequest = UpdateOutletProductRequest
                .builder()
                .id(receivedProduct.getId())
                .qty(receivedProduct.getQty() + request.getQty())
                .build();

        outletProductService.update(sentUpdateRequest);
        outletProductService.update(receivedUpdateRequest);

        ProductTransfer productTransfer = productTransferRepository.saveAndFlush(ProductTransfer
                        .builder()
                        .transferDate(new Date())
                        .product(productService.getById(request.getProductId()))
                        .sender(outletService.getById(request.getSenderOutletId()).getName())
                        .receiver(outletService.getById(request.getReceiverOutletId()).getName())
                        .qty(request.getQty())
                        .build());

        return ProductTransferResponse.builder()
                .id(productTransfer.getId())
                .transferDate(new Date())
                .productId(request.getProductId())
                .sender(outletService.getById(request.getSenderOutletId()).getName())
                .receiver(outletService.getById(request.getReceiverOutletId()).getName())
                .qty(request.getQty())
                .type(request.getType())
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ProductTransferResponse createOrderTransfer(TransactionalProductTransferRequest request) {
        OutletProduct outletProduct = outletProductService.getOrSaveOutletProduct(request.getOutletId(), request.getProductId());

        outletProductService.update(UpdateOutletProductRequest
                        .builder()
                        .id(outletProduct.getId())
                        .qty(outletProduct.getQty() + request.getQty())
                        .build());

        ProductTransfer productTransfer = productTransferRepository.saveAndFlush(ProductTransfer.builder()
                        .transferDate(new Date())
                        .product(productService.getById(request.getProductId()))
                        .sender(productService
                                .getById(request.getProductId())
                                .getProductSupplier()
                                .getName()
                        )
                        .receiver(outletService.getById(request.getOutletId()).getName())
                        .qty(request.getQty())
                        .type(TransferType.SUPPLY)
                .build()
        );

        return ProductTransferResponse.builder()
                .id(productTransfer.getId())
                .transferDate(new Date())
                .productId(request.getProductId())
                .sender(productTransfer.getSender())
                .receiver(productTransfer.getReceiver())
                .qty(productTransfer.getQty())
                .type(TransferType.SUPPLY)
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ProductTransferResponse createTransactionTransfer(TransactionalProductTransferRequest request) {
        OutletProduct outletProduct = outletProductService.getOrSaveOutletProduct(request.getOutletId(), request.getProductId());

        if (outletProduct.getQty() < request.getQty()) {
            throw new RuntimeException("Insufficient stock from sender");
        }

        outletProductService.update(UpdateOutletProductRequest.builder()
                        .id(outletProduct.getId())
                        .qty(outletProduct.getQty() - request.getQty())
                .build()
        );

        ProductTransfer productTransfer = ProductTransfer.builder()
                .transferDate(new Date())
                .product(productService.getById(request.getProductId()))
                .sender(outletService.getById(request.getOutletId()).getName())
                .receiver("Retail customer")
                .qty(request.getQty())
                .type(TransferType.SALES)
                .build();

        return ProductTransferResponse.builder()
                .id(productTransfer.getId())
                .transferDate(new Date())
                .productId(request.getProductId())
                .sender(productTransfer.getSender())
                .receiver(productTransfer.getReceiver())
                .qty(productTransfer.getQty())
                .type(TransferType.SALES)
                .build();
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    @Override
    public Page<ProductTransferResponse> getAll(SearchProductTransferRequest request) {
        if (request.getPage() <= 0) request.setPage(1);

        Sort sort = Sort.by(Sort.Direction.fromString(request.getDirection()), request.getSortBy());
        Pageable pageable = PageRequest.of((request.getPage() - 1), request.getSize());

        Page<ProductTransfer> productTransferPage = productTransferRepository.findAll(pageable);

        String sender = "%%";
        String receiver = "%%";
        String supplierId = "%%";
        String productId = "%%";

        if (request.getOutletId() != null) {
            sender = "%" + outletService.getById(request.getOutletId()).getName() + "%";
            receiver = "%" + outletService.getById(request.getOutletId()).getName() + "%";
        }
        if (request.getProductId() != null) {
            productId = "%" + request.getProductId() + "%";
        }
        if (request.getSupplierId() != null) {
            supplierId = "%" + request.getSupplierId() + "%";
        }

        if (productTransferRepository.getFilteredProductTransfers(sender, receiver, productId, supplierId).isPresent()) {
            List<ProductTransfer> productTransfers = productTransferRepository.getFilteredProductTransfers(
                    sender, receiver, productId, supplierId).get();
            productTransferPage = new PageImpl<>(productTransfers, pageable, productTransfers.size());
        }

        return productTransferPage.map(productTransfer -> ProductTransferResponse.builder()
                .id(productTransfer.getId())
                .transferDate(productTransfer.getTransferDate())
                .productId(productTransfer.getProduct().getId())
                .sender(productTransfer.getSender())
                .receiver(productTransfer.getReceiver())
                .qty(productTransfer.getQty())
                .type(productTransfer.getType())
                .build());
    }
}
