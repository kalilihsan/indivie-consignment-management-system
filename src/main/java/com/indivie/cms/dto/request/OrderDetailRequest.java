package com.indivie.cms.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailRequest {
    private String orderId;
    private String productId;
    private Long netCost;
    private Integer qty;
}
