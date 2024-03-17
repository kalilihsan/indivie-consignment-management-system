package com.indivie.cms.service;

import com.indivie.cms.entity.OutletTransactionDetail;

import java.util.List;

public interface TransactionDetailService {
    List<OutletTransactionDetail> createBulk(List<OutletTransactionDetail> transactionDetails);
}
