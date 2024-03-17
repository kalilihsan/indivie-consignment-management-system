package com.indivie.cms.dto.response;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    private String id;
    private Date orderDate;
    private String outletId;
    private List<OrderDetailResponse> orderDetails;
}
