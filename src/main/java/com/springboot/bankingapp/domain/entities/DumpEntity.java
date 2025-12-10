package com.springboot.bankingapp.domain.entities;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Table(name = "dump_table")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class DumpEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String email;

    private BigDecimal balanceRON;

    private BigDecimal balanceEUR;

    private BigDecimal balanceUSD;

}