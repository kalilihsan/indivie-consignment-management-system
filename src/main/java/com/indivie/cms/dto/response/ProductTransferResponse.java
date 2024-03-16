package com.indivie.cms.dto.response;

import com.indivie.cms.constant.TransferType;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductTransferResponse {
    private String id;
    private Date transferDate;
    private String productId;
    private String sender;
    private String receiver;
    private Integer qty;
    private TransferType type;
}
