package com.indivie.cms.entity;

import com.indivie.cms.constant.OutletType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "m_outlet")
public class Outlet {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "address")
    private String address;
    @Column(name = "mobile_phone_no")
    private String mobilePhoneNo;
    @Column(name = "outlet_type")
    @Enumerated(EnumType.STRING)
    private OutletType type;
    @OneToOne
    @JoinColumn(name = "user_account_id", unique = true)
    private UserAccount userAccount;
}
