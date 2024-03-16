package com.indivie.cms.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchProductTransferRequest {
    private Integer page;
    private Integer size;
    private String sortBy;
    private String direction;
    private String supplierId;
    private String productId;
    private String outletId;
}
