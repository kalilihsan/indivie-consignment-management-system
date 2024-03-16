package com.indivie.cms.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
    private String id;
    private String name;
    private String supplier;
    private Long NetCost;
    private Long NetPrice;
}
