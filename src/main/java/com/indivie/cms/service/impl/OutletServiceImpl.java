package com.indivie.cms.service.impl;

import com.indivie.cms.constant.OutletType;
import com.indivie.cms.dto.request.NewOutletRequest;
import com.indivie.cms.dto.request.SearchRequest;
import com.indivie.cms.dto.request.UpdateOutletRequest;
import com.indivie.cms.dto.response.OutletResponse;
import com.indivie.cms.entity.Outlet;
import com.indivie.cms.repository.OutletRepository;
import com.indivie.cms.service.OutletService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class OutletServiceImpl implements OutletService {
    private final OutletRepository outletRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public OutletResponse create(Outlet newOutlet) {
        Outlet outlet = outletRepository.saveAndFlush(newOutlet);

        return OutletResponse.builder()
                .id(outlet.getId())
                .name(outlet.getName())
                .address(outlet.getAddress())
                .mobilePhoneNo(outlet.getMobilePhoneNo())
                .build();
    }

    @Override
    public Page<OutletResponse> getAll(SearchRequest request) {
        if (request.getPage() <= 0) request.setPage(1);

        Sort sort = Sort.by(Sort.Direction.fromString(request.getDirection()), request.getSortBy());
        Pageable pageable = PageRequest.of((request.getPage() - 1), request.getSize(), sort);

        return outletRepository.findAll(pageable).map(outlet -> {
            return OutletResponse.builder()
                    .id(outlet.getId())
                    .name(outlet.getName())
                    .address(outlet.getAddress())
                    .mobilePhoneNo(outlet.getMobilePhoneNo())
                    .build();
        });
    }

    @Override
    public Outlet getById(String id) {
        return outletRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Not found"));
    }

    @Override
    public OutletResponse update(UpdateOutletRequest request) {
        Outlet outlet = getById(request.getId());
        if (request.getName() != null) {
            outlet.setName(request.getName());
        }
        if (request.getAddress() != null) {
            outlet.setName(request.getAddress());
        }
        if (request.getMobilePhoneNo() != null) {
            outlet.setName(request.getMobilePhoneNo());
        }
        outletRepository.saveAndFlush(outlet);

        return OutletResponse.builder()
                .id(outlet.getId())
                .name(outlet.getName())
                .address(outlet.getAddress())
                .mobilePhoneNo(outlet.getMobilePhoneNo())
                .build();
    }
}
