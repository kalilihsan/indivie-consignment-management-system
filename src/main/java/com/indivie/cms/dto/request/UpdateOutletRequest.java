package com.indivie.cms.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateOutletRequest {
    private String id;
    private String name;
    private String address;
    private String mobilePhoneNo;
}
