package com.indivie.cms.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchTransactionRequest {
    private Integer page;
    private Integer size;
    private String sortBy;
    private String direction;
    private String outletId;
    private String productId;
}
