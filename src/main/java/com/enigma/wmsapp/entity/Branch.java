package com.enigma.wmsapp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "m_branch")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String branchId;

    @Column(name = "branch_code", unique = true, nullable = false, length = 20)
    private String branchCode;

    @Column(name = "branch_name", nullable = false, length = 30)
    private String branchName;

    @Column(name = "address", length = 90)
    private String address;

    @Column(name = "phone_number",unique = true, nullable = false, length = 20)
    private String phoneNumber;
    @OneToOne
    @JoinColumn (name = "user_credential_id")
    private UserCredential userCredential;
}
