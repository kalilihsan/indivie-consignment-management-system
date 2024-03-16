package com.indivie.cms.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "t_product_supply_order")
public class ProductSupplyOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "order_date")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date orderDate;

    @ManyToOne
    @JoinColumn(name = "receiving_outlet_id")
    private Outlet outlet;

    @OneToMany(mappedBy = "productSupplyOrder")
    @JsonManagedReference
    private List<ProductSupplyOrderDetail> orderDetails;
}
