package com.indivie.cms.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateProductCostPriceRequest {
    private String productId;
    private Long netPrice;
    private Long netCost;
}
