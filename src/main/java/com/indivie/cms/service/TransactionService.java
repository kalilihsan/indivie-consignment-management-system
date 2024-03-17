package com.indivie.cms.service;

import com.indivie.cms.dto.request.TransactionRequest;
import com.indivie.cms.dto.response.TransactionResponse;
import org.springframework.data.domain.Page;

public interface TransactionService {
    TransactionResponse create(TransactionRequest request);
    Page<TransactionResponse> getAll();
}
