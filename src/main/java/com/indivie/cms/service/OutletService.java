package com.indivie.cms.service;

import com.indivie.cms.dto.request.NewOutletRequest;
import com.indivie.cms.dto.request.SearchRequest;
import com.indivie.cms.dto.request.UpdateOutletRequest;
import com.indivie.cms.dto.response.OutletResponse;
import com.indivie.cms.entity.Outlet;
import org.springframework.data.domain.Page;

public interface OutletService {
    OutletResponse create(NewOutletRequest request);
    OutletResponse createMainStorage(NewOutletRequest request);
    Page<OutletResponse> getAll(SearchRequest request);
    OutletResponse update(UpdateOutletRequest request);
    Outlet getById(String id);
}
