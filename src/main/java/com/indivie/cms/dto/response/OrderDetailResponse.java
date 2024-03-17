package com.indivie.cms.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailResponse {
    private String id;
    private String orderId;
    private String productId;
    private Long netCost;
    private Integer qty;
}
