package com.indivie.cms.dto.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {
    private String outletId;
    private List<OrderDetailRequest> detailRequests;
}
