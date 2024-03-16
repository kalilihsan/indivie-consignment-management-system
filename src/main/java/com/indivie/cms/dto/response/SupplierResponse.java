package com.indivie.cms.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SupplierResponse {
    private String id;
    private String name;
    private String address;
    private String mobilePhoneNo;
}
