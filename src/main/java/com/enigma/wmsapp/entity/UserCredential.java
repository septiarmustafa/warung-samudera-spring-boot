package com.enigma.wmsapp.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "m_user_credential")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UserCredential {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
