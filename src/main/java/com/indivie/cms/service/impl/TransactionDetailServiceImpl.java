package com.indivie.cms.service.impl;

import com.indivie.cms.entity.OutletTransactionDetail;
import com.indivie.cms.repository.OutletTransactionDetailRepository;
import com.indivie.cms.service.TransactionDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionDetailServiceImpl implements TransactionDetailService {
    private final OutletTransactionDetailRepository outletTransactionDetailRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<OutletTransactionDetail> createBulk(List<OutletTransactionDetail> transactionDetails) {
        return outletTransactionDetailRepository.saveAllAndFlush(transactionDetails);
    }
}
