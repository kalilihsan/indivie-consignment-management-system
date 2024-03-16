package com.indivie.cms.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionalProductTransferRequest {
    private String outletId;
    private String productId;
    private Integer qty;
}
