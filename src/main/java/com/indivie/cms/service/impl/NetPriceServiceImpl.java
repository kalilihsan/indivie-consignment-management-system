package com.indivie.cms.service.impl;

import com.indivie.cms.entity.NetPrice;
import com.indivie.cms.repository.NetPriceRepository;
import com.indivie.cms.service.NetPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NetPriceServiceImpl implements NetPriceService {
    private final NetPriceRepository netPriceRepository;
    @Override
    public NetPrice create(NetPrice netPrice) {
        netPriceRepository.saveAndFlush(netPrice);
        return netPrice;
    }
}
