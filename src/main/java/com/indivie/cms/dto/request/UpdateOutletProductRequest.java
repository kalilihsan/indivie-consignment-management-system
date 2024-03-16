package com.indivie.cms.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateOutletProductRequest {
    private String id;
    private Integer qty;
}
