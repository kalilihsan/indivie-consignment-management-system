package com.indivie.cms.service;

import com.indivie.cms.dto.request.ProductTransferRequest;
import com.indivie.cms.dto.request.SearchProductTransferRequest;
import com.indivie.cms.dto.request.TransactionalProductTransferRequest;
import com.indivie.cms.dto.response.ProductTransferResponse;
import org.springframework.data.domain.Page;

public interface ProductTransferService {
    ProductTransferResponse create(ProductTransferRequest request);
    ProductTransferResponse createOrderTransfer(TransactionalProductTransferRequest request);
    ProductTransferResponse createTransactionTransfer(TransactionalProductTransferRequest request);
    Page<ProductTransferResponse> getAll(SearchProductTransferRequest request);
}
