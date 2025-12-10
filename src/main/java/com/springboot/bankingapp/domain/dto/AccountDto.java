package com.springboot.bankingapp.domain.dto;

import com.springboot.bankingapp.domain.entities.types.AccountType;
import lombok.*;

import java.math.BigDecimal;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDto {

    private Long id;

    private UserDto user;

    private String iban;

    private String currency;

    private BigDecimal balance;

    private AccountType type;

}
