package com.springboot.bankingapp.domain.entities;

import com.springboot.bankingapp.domain.entities.types.AccountType;
import com.springboot.bankingapp.domain.entities.types.CurrencyType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Table(name = "account_table")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(unique = true)
    private String iban;

    @Enumerated(EnumType.STRING)
    private CurrencyType currency;

    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private AccountType type;

    public AccountEntity(UserEntity user, String iban, CurrencyType currency, BigDecimal balance, AccountType type) {
        this.user = user;
        this.iban = iban;
        this.currency = currency;
        this.balance = balance;
        this.type = type;
    }
}