package com.springboot.bankingapp.domain.entities.requests;

import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TransferRequest {
    private String sourceIban;
    private String targetIban;
    private BigDecimal amount;
}