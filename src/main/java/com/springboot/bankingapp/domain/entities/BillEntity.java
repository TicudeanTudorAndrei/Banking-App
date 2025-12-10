package com.springboot.bankingapp.domain.entities;

import com.springboot.bankingapp.domain.entities.types.BillType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name = "bill_table")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class BillEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String sourceAccountIban;

    private String targetAccountIban;

    @Enumerated(EnumType.STRING)
    private BillType billType;

    private String userEmail;

    private BigDecimal amount;

    private LocalDateTime creationDateTime;

}
