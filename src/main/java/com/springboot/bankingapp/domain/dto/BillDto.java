package com.springboot.bankingapp.domain.dto;

import com.springboot.bankingapp.domain.entities.types.BillType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillDto {
    private Long id;

    private String sourceAccountIban;

    private String targetAccountIban;

    private String userEmail;

    private BillType billType;

    private BigDecimal amount;

    private LocalDateTime creationDateTime;
}
