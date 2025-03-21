package com.modulo.pix.models;

import com.modulo.pix.enums.KeyType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_pix_keys")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PixKeyModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Enumerated(EnumType.STRING)
    private KeyType keyType;

    private String keyValue;

    private int status;

    @DateTimeFormat
    private LocalDateTime disabledAt;


    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private AccountModel account;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
