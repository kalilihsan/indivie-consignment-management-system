package com.indivie.cms.service.impl;

import com.indivie.cms.entity.NetCost;
import com.indivie.cms.repository.NetCostRepository;
import com.indivie.cms.service.NetCostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NetCostServiceImpl implements NetCostService {
    private final NetCostRepository netCostRepository;
    @Override
    public NetCost create(NetCost netCost) {
        netCostRepository.saveAndFlush(netCost);
        return netCost;
    }
}
