package com.enigma.wmsapp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "t_bill_detail")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class BillDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn (name = "bill_id")
    private Bill bill;

    @ManyToOne
    @JoinColumn(name = "product_price_id")
    private ProductPrice productPrice;

    private Integer quantity;

}
