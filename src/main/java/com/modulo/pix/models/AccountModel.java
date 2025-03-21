package com.modulo.pix.models;

import com.modulo.pix.enums.AccountType;
import com.modulo.pix.enums.ClientType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountModel {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    private String agencyNumber;
    private String accountNumber;
    private String accountHolderFirstName;
    private String accountHolderLastName;

    @Enumerated(EnumType.STRING)
    private ClientType clientType;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PixKeyModel> pixKeys = new ArrayList<>();

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
