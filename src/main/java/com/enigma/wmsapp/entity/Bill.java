package com.enigma.wmsapp.entity;

import com.enigma.wmsapp.constant.ETransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "t_bill")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "receipt_number", unique = true, nullable = false, length = 30)
    private String receiptNumber;

    @Column(name = "trans_date", nullable = false)
    private LocalDateTime transDate;

    @Column(name = "transaction_type", nullable = false)
    private ETransactionType transactionType;

    @OneToMany(mappedBy = "bill", cascade = CascadeType.PERSIST)
    private List<BillDetail> billDetail;
}
