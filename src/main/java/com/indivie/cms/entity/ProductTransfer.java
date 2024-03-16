package com.indivie.cms.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.indivie.cms.constant.TransferType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "m_product_transfer")
public class ProductTransfer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "transfer_date")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date transferDate;
    @Column(name = "sender")
    private String sender;
    @Column(name = "receiver")
    private String receiver;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @Column(name = "qty")
    private Integer qty;
    @Column(name = "transfer_type")
    @Enumerated(EnumType.STRING)
    private TransferType type;
}
