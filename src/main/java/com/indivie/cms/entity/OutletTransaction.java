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
@Table(name = "t_outlet_transaction")
public class OutletTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "transaction_date")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date transDate;
    @ManyToOne
    @JoinColumn(name = "outlet_id")
    private Outlet outlet;
    @OneToMany(mappedBy = "outletTransaction")
    @JsonManagedReference
    private List<OutletTransactionDetail> transactionDetails;
}
