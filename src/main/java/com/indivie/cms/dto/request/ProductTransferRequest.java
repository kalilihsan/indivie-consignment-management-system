package com.indivie.cms.dto.request;

import com.indivie.cms.constant.TransferType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductTransferRequest {
    private String senderOutletId;
    private String receiverOutletId;
    private String productId;
    private Integer qty;
    private TransferType type;
}
