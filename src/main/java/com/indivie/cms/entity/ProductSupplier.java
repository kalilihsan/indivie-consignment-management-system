package com.indivie.cms.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "m_supplier")
public class ProductSupplier {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "name", unique = true)
    private String name;
    @Column(name = "mobile_phone_no")
    private String mobilePhoneNo;
    @Column(name = "address")
    private String address;
}
