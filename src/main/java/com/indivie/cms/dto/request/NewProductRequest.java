package com.indivie.cms.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewProductRequest {
    private String name;
    private String supplierId;
    private Long netCost;
    private Long netPrice;
}
