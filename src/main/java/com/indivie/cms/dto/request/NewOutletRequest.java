package com.indivie.cms.dto.request;

import com.indivie.cms.entity.UserAccount;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewOutletRequest {
    private String name;
    private String address;
    private String mobilePhoneNo;
    private UserAccount userAccount;
}
