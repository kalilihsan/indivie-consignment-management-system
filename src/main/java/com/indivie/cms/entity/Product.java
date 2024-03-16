package com.indivie.cms.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "m_product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "name")
    private String name;
    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private ProductSupplier productSupplier;
    @Column(name = "net_cost")
    private Long netCost;
    @Column(name = "net_price")
    private Long netPrice;
}
