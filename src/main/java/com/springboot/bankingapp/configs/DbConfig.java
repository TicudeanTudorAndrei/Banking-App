package com.springboot.bankingapp.configs;

import com.springboot.bankingapp.domain.entities.AccountEntity;
import com.springboot.bankingapp.domain.entities.UserEntity;
import com.springboot.bankingapp.domain.entities.types.AccountType;
import com.springboot.bankingapp.domain.entities.types.CurrencyType;
import com.springboot.bankingapp.repositories.AccountRepository;
import com.springboot.bankingapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DbConfig {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    @Bean
    @Order(1)
    CommandLineRunner userAndAccountCommandLineRunner() {
        return args -> {
            UserEntity userEntity1 = new UserEntity(
                    "Alice Green",
                    "alice.green@example.com",
                    "$2a$12$CUH/9EZTXWJmLSCT54G4YepEim58HHb0hj21OhddFTwqkWjkSwkGa",//pass1
                    "0712345678",
                    LocalDate.of(1995, Month.MARCH, 15),
                    true
            );

            UserEntity userEntity2 = new UserEntity(
                    "Bob Smith",
                    "bob.smith@example.com",
                    "$2a$12$2otwk25Vjc8npUvdBPRpSOPjfmV3hdvxGwpB8qNhwCNVqYAz1UJTu",//pass2
                    "0723456789",
                    LocalDate.of(1998, Month.JULY, 22),
                    true
            );

            UserEntity userEntity3 = new UserEntity(
                    "Charlie Johnson",
                    "charlie.johnson@example.com",
                    "$2a$12$xxU3XWrAJHq8HqKI8GVM6e9sx57kAGa8ogBizazv0.HCJhsLSoq1q",//pass3
                    "0734567890",
                    LocalDate.of(1997, Month.NOVEMBER, 5),
                    true
            );

            UserEntity userComp1 = new UserEntity(
                    "Acme Corp",
                    "acme@example.com",
                    "$2a$12$mIG6iyR2Dfjfxamb.jAwVOwcxBQ4oB/aX84kRJCjOzApsbBon8LsS",//pass4
                    "3000",
                    null,
                    true
            );

            UserEntity userComp2 = new UserEntity(
                     "Globex Inc",
                    "globex@example.com",
                    "$2a$12$czz.8rATJJQVLjp8j6Npf.E2haBYfLDuTSimr2ZME7g.xkPNTvPEe",//pass5
                    "4000",
                    null,
                    true
            );

            userRepository.saveAll(List.of(userEntity1, userEntity2, userEntity3, userComp1, userComp2));

            UserEntity savedUser1 = userRepository.findByEmail("alice.green@example.com").orElseThrow();

            AccountEntity accountEntity1 = new AccountEntity(
                    savedUser1,
                    "RO49ABC1B310075938400005578",
                    CurrencyType.RON,
                    BigDecimal.valueOf(1000),
                    AccountType.DEBIT
            );

            AccountEntity accountEntity2 = new AccountEntity(
                    savedUser1,
                    "RO49VFR1B310075779511341256",
                    CurrencyType.EUR,
                    BigDecimal.valueOf(150),
                    AccountType.CREDIT
            );

            UserEntity savedUser2 = userRepository.findByEmail("bob.smith@example.com").orElseThrow();

            AccountEntity accountEntity3 = new AccountEntity(
                    savedUser2,
                    "RO49XKD1F314375237645673412",
                    CurrencyType.EUR,
                    BigDecimal.valueOf(200),
                    AccountType.DEBIT
            );

            UserEntity savedUser3 = userRepository.findByEmail("charlie.johnson@example.com").orElseThrow();

            AccountEntity accountEntity4 = new AccountEntity(
                    savedUser3,
                    "RO49PQR1J764275938477654387",
                    CurrencyType.USD,
                    BigDecimal.valueOf(150),
                    AccountType.DEBIT
            );

            UserEntity savedComp1 = userRepository.findByEmail("acme@example.com").orElseThrow();

            AccountEntity accountComp1 = new AccountEntity(
                    savedComp1,
                    "RO12ACMR",
                    CurrencyType.RON,
                    BigDecimal.valueOf(0),
                    AccountType.COMPANY
            );

            UserEntity savedComp2 = userRepository.findByEmail("globex@example.com").orElseThrow();

            AccountEntity accountComp2 = new AccountEntity(
                    savedComp2,
                    "RO45VDF",
                    CurrencyType.RON,
                    BigDecimal.valueOf(0),
                    AccountType.COMPANY
            );

            savedUser1.addAccount(accountEntity1);
            savedUser1.addAccount(accountEntity2);
            savedUser2.addAccount(accountEntity3);
            savedUser3.addAccount(accountEntity4);
            savedComp1.addAccount(accountComp1);
            savedComp2.addAccount(accountComp2);

            accountRepository.saveAll(List.of(accountEntity1, accountEntity2, accountEntity3, accountEntity4, accountComp1, accountComp2));
        };
    }
}