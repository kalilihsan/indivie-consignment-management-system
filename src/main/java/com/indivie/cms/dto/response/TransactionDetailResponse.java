package com.indivie.cms.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDetailResponse {
    private String id;
    private String productId;
    private Long netPrice;
    private Integer qty;
}
