package com.indivie.cms.dto.response;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponse {
    private String id;
    private Date transDate;
    private String outletId;
    private List<TransactionDetailResponse> transactionDetails;
}
