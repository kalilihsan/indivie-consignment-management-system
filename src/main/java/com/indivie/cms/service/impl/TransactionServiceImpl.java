package com.indivie.cms.service.impl;

import com.indivie.cms.dto.request.SearchTransactionRequest;
import com.indivie.cms.dto.request.TransactionRequest;
import com.indivie.cms.dto.request.TransactionalProductTransferRequest;
import com.indivie.cms.dto.request.UpdateOutletProductRequest;
import com.indivie.cms.dto.response.TransactionDetailResponse;
import com.indivie.cms.dto.response.TransactionResponse;
import com.indivie.cms.entity.*;
import com.indivie.cms.repository.OutletTransactionRepository;
import com.indivie.cms.service.*;
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
public class TransactionServiceImpl implements TransactionService {
    private final OutletTransactionRepository outletTransactionRepository;
    private final TransactionDetailService transactionDetailService;
    private final OutletService outletService;
    private final ProductService productService;
    private final OutletProductService outletProductService;
    private final ProductTransferService productTransferService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TransactionResponse create(TransactionRequest request) {
        Outlet outlet = outletService.getById(request.getOutletId());

        OutletTransaction transaction = OutletTransaction.builder()
                .transDate(new Date())
                .outlet(outlet)
                .build();
        outletTransactionRepository.saveAndFlush(transaction);

        List<OutletTransactionDetail> transactionDetails = request.getDetailRequests().stream().map(transactionDetailRequest -> {
            Product product = productService.getById(transactionDetailRequest.getProductId());

            productTransferService.createTransactionTransfer(TransactionalProductTransferRequest.builder()
                            .outletId(request.getOutletId())
                            .productId(transactionDetailRequest.getProductId())
                            .qty(transactionDetailRequest.getQty())
                    .build());

            return OutletTransactionDetail.builder()
                    .outletTransaction(transaction)
                    .product(product)
                    .netPrice(product.getNetPrice())
                    .qty(transactionDetailRequest.getQty())
                    .build();
        }).toList();

        transactionDetailService.createBulk(transactionDetails);
        transaction.setTransactionDetails(transactionDetails);

        List<TransactionDetailResponse> detailResponses = transactionDetails.stream().map(outletTransactionDetail ->
                TransactionDetailResponse.builder()
                        .id(outletTransactionDetail.getId())
                        .productId(outletTransactionDetail.getProduct().getId())
                        .netPrice(outletTransactionDetail.getNetPrice())
                        .qty(outletTransactionDetail.getQty())
                        .build()
        ).toList();

        return TransactionResponse.builder()
                .id(transaction.getId())
                .transDate(transaction.getTransDate())
                .outletId(transaction.getOutlet().getId())
                .transactionDetails(detailResponses)
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Page<TransactionResponse> getAll(SearchTransactionRequest request) {
        if (request.getPage() <= 0) request.setPage(1);

        Sort sort = Sort.by(Sort.Direction.fromString(request.getDirection()), request.getSortBy());
        Pageable pageable = PageRequest.of((request.getPage() - 1), request.getSize(), sort);

        Page<OutletTransaction> transactionPage = outletTransactionRepository.findAll(pageable);

        String outletId = "%%";
        String productId = "%%";

        if (request.getOutletId() != null) {
            outletId = "%" + request.getOutletId() + "%";
        }
        if (request.getProductId() != null) {
            productId = "%" + request.getProductId() + "%";
        }

        if (outletTransactionRepository.findAllFiltered(outletId, productId).isPresent()) {
            List<OutletTransaction> outletTransactions = outletTransactionRepository.findAllFiltered(outletId, productId).get();
            transactionPage = new PageImpl<>(outletTransactions, pageable, outletTransactions.size());
        }

        return transactionPage.map(outletTransaction -> {
            List<TransactionDetailResponse> transactionDetailResponses = outletTransaction.getTransactionDetails().stream().map(
                    outletTransactionDetail -> TransactionDetailResponse.builder()
                            .id(outletTransactionDetail.getId())
                            .productId(outletTransactionDetail.getProduct().getId())
                            .netPrice(outletTransactionDetail.getNetPrice())
                            .qty(outletTransactionDetail.getQty())
                            .build()
            ).toList();

            return TransactionResponse.builder()
                    .id(outletTransaction.getId())
                    .transDate(outletTransaction.getTransDate())
                    .outletId(outletTransaction.getOutlet().getId())
                    .transactionDetails(transactionDetailResponses)
                    .build();
        });
    }
}
