package com.springboot.bankingapp.controllers;

import com.springboot.bankingapp.domain.dto.AccountDto;
import com.springboot.bankingapp.domain.entities.AccountEntity;
import com.springboot.bankingapp.domain.entities.requests.TransferRequest;
import com.springboot.bankingapp.mappers.Mapper;
import com.springboot.bankingapp.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final Mapper<AccountEntity, AccountDto> accountMapper;

    @PostMapping(path = "/a/create")
    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto accountDto) {
        AccountEntity accountEntity = accountMapper.mapFrom(accountDto);
        AccountEntity savedAccountEntity = accountService.createAccount(accountEntity);
        AccountDto returnedAccountDto = accountMapper.mapTo(savedAccountEntity);
        return new ResponseEntity<>(returnedAccountDto, HttpStatus.CREATED);
    }

    @GetMapping(path = "/a/get/id/{id_val}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable("id_val") Long accountId) {
        Optional<AccountEntity> foundAccount = accountService.getAccountById(accountId);
        return foundAccount.map(accountEntity -> {
            AccountDto accountDto = accountMapper.mapTo(accountEntity);
            return new ResponseEntity<>(accountDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/a/list")
    public ResponseEntity<List<AccountDto>> getAccountsList() {
        List<AccountEntity> accounts = accountService.getAccountsList();
        List<AccountDto> gotAccounts = accounts.stream().map(accountMapper::mapTo).collect(Collectors.toList());
        return new ResponseEntity<>(gotAccounts, HttpStatus.OK);
    }

    @GetMapping("/a/users/accounts/id/{id_val}")
    public ResponseEntity<List<AccountDto>> getAccountsListByUserId(@PathVariable("id_val") Long userId) {
        List<AccountEntity> userAccounts = accountService.getAccountsListByUserId(userId);
        if (userAccounts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<AccountDto> userAccountDtos = userAccounts.stream().map(accountMapper::mapTo).collect(Collectors.toList());
        return new ResponseEntity<>(userAccountDtos, HttpStatus.OK);
    }

    @GetMapping("/a/users/accounts/email/{email_val}")
    public ResponseEntity<List<AccountDto>> getAccountsByUserEmail(@PathVariable("email_val") String userEmail) {
        List<AccountEntity> userAccounts = accountService.getAccountsListByUserEmail(userEmail);
        if (userAccounts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<AccountDto> userAccountDtos = userAccounts.stream().map(accountMapper::mapTo).collect(Collectors.toList());
        return new ResponseEntity<>(userAccountDtos, HttpStatus.OK);
    }

    @PutMapping(path = "/a/update/id/{id_val}")
    public ResponseEntity<AccountDto> fullUpdateAccountById(
            @PathVariable("id_val") Long accountId,
            @RequestBody AccountDto accountDto) {

        if (!accountService.isIdExists(accountId)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        accountDto.setId(accountId);
        AccountEntity accountEntity = accountMapper.mapFrom(accountDto);
        AccountEntity updatedAccountEntity = accountService.fullUpdateAccountById(accountId, accountEntity);
        AccountDto updatedAccountDto = accountMapper.mapTo(updatedAccountEntity);
        return new ResponseEntity<>(updatedAccountDto, HttpStatus.OK);
    }

    @PatchMapping(path = "/a/patch/id/{id_val}")
    public ResponseEntity<AccountDto> partialUpdateAccountById(
            @PathVariable("id_val") Long accountId,
            @RequestBody AccountDto accountDto) {

        if (!accountService.isIdExists(accountId)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        AccountEntity accountEntity = accountMapper.mapFrom(accountDto);
        AccountEntity updatedAccountEntity = accountService.partialUpdateAccountById(accountId, accountEntity);
        AccountDto updatedAccountDto = accountMapper.mapTo(updatedAccountEntity);
        return new ResponseEntity<>(updatedAccountDto, HttpStatus.OK);
    }

    @DeleteMapping(path = "/a/delete/id/{id_val}")
    public ResponseEntity deleteAccountById(@PathVariable("id_val") Long accountId) {

        if (!accountService.isIdExists(accountId)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        accountService.deleteAccountById(accountId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(path = "/a/update/iban/{iban_val}")
    public ResponseEntity<AccountDto> fullUpdateAccountByIban(
            @PathVariable("iban_val") String accountIban,
            @RequestBody AccountDto accountDto) {

        if (!accountService.isIbanExists(accountIban)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        accountDto.setIban(accountIban);
        AccountEntity accountEntity = accountMapper.mapFrom(accountDto);
        AccountEntity updatedAccountEntity = accountService.fullUpdateAccountByIban(accountIban, accountEntity);
        AccountDto updatedAccountDto = accountMapper.mapTo(updatedAccountEntity);
        return new ResponseEntity<>(updatedAccountDto, HttpStatus.OK);
    }

    @PatchMapping(path = "/a/patch/iban/{iban_val}")
    public ResponseEntity<AccountDto> partialUpdateAccountByIban(
            @PathVariable("iban_val") String accountIban,
            @RequestBody AccountDto accountDto) {

        if (!accountService.isIbanExists(accountIban)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        AccountEntity accountEntity = accountMapper.mapFrom(accountDto);
        AccountEntity updatedAccountEntity = accountService.partialUpdateAccountByIban(accountIban, accountEntity);
        AccountDto updatedAccountDto = accountMapper.mapTo(updatedAccountEntity);
        return new ResponseEntity<>(updatedAccountDto, HttpStatus.OK);
    }

    @DeleteMapping(path = "/a/delete/iban/{iban_val}")
    public ResponseEntity deleteAccountByIban(@PathVariable("iban_val") String accountIban) {

        if (!accountService.isIbanExists(accountIban)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        accountService.deleteAccountByIban(accountIban);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/a/transfer")
    public ResponseEntity<List<AccountDto>> transferMoney(@RequestBody TransferRequest transferRequest) {
        try {
            accountService.transferMoney(transferRequest.getSourceIban(),
                    transferRequest.getTargetIban(), transferRequest.getAmount());

            List<AccountEntity> updatedAccounts = accountService.getTransferAccountsByIban(
                    new String[]{transferRequest.getSourceIban(), transferRequest.getTargetIban()});

            List<AccountDto> updatedAccountDtos = updatedAccounts.stream().map(accountMapper::mapTo)
                            .collect(Collectors.toList());
            return new ResponseEntity<>(updatedAccountDtos, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}