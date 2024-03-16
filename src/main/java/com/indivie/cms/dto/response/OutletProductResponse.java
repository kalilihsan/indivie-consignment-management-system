package com.indivie.cms.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OutletProductResponse {
    private String id;
    private String productId;
    private String outletId;
    private Long netPrice;
    private Integer qty;
}
