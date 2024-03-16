package com.indivie.cms.dto.response;

import com.indivie.cms.constant.OutletType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OutletResponse {
    private String id;
    private String name;
    private String address;
    private String mobilePhoneNo;
    private OutletType type;
}
