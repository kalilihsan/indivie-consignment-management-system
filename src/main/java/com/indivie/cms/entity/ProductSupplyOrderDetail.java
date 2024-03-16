package com.indivie.cms.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "t_product_supply_order_detail")
public class ProductSupplyOrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "product_supply_order_id", nullable = false)
    @JsonBackReference
    private ProductSupplyOrder productSupplyOrder;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "net_cost")
    private Long netCost;

    @Column(name = "qty")
    private Integer qty;
}
