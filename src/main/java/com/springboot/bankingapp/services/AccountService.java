package com.springboot.bankingapp.services;

import com.springboot.bankingapp.domain.entities.AccountEntity;
import com.springboot.bankingapp.domain.entities.BillEntity;
import com.springboot.bankingapp.domain.entities.DumpEntity;
import com.springboot.bankingapp.domain.entities.types.BillType;
import com.springboot.bankingapp.domain.entities.types.CurrencyType;
import com.springboot.bankingapp.repositories.AccountRepository;
import com.springboot.bankingapp.repositories.BillRepository;
import com.springboot.bankingapp.repositories.DumpRepository;
import com.springboot.bankingapp.utils.CurrencyConvertor;
import com.springboot.bankingapp.utils.IbanGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final BillRepository billRepository;
    private final DumpRepository dumpRepository;
    private final IbanGenerator ibanGenerator;

    public List<AccountEntity> getAccountsList() {
        return StreamSupport.stream(accountRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public List<AccountEntity> getAccountsListByUserId(Long userId) {
        return accountRepository.findByUserId(userId);
    }

    public List<AccountEntity> getAccountsListByUserEmail(String userEmail) {
        return accountRepository.findByUserEmail(userEmail);
    }
    public AccountEntity createAccount(AccountEntity accountEntity) {
        String randomIban = ibanGenerator.generateRandomString();
        Optional<AccountEntity> accountOptional = accountRepository.findByIban(randomIban);
        while (accountOptional.isPresent()) {
            randomIban = ibanGenerator.generateRandomString();
        }
        accountEntity.setIban(randomIban);
        accountEntity.setBalance(BigDecimal.valueOf(0.00));
        return accountRepository.save(accountEntity);
    }

    public Optional<AccountEntity> getAccountById(Long accountId) {
        return accountRepository.findById(accountId);
    }

    public boolean isIdExists(Long accountId) {
        return accountRepository.existsById(accountId);
    }

    public boolean isIbanExists(String iban) {
        return accountRepository.findByIban(iban).isPresent();
    }

    public AccountEntity fullUpdateAccountById(Long accountId, AccountEntity accountEntity) {
        accountEntity.setId(accountId);
        return accountRepository.findById(accountId).map(existingAccount -> {
            existingAccount.setCurrency(accountEntity.getCurrency());
            existingAccount.setIban(accountEntity.getIban());
            existingAccount.setBalance(accountEntity.getBalance());
            existingAccount.setType(accountEntity.getType());
            return accountRepository.save(existingAccount);
        }).orElseThrow(() -> new RuntimeException("Account does not exist"));
    }

    public AccountEntity partialUpdateAccountById(Long accountId, AccountEntity accountEntity) {
        accountEntity.setId(accountId);
        return accountRepository.findById(accountId).map(existingAccount -> {
            Optional.ofNullable(accountEntity.getCurrency()).ifPresent(existingAccount::setCurrency);
            Optional.ofNullable(accountEntity.getBalance()).ifPresent(existingAccount::setBalance);
            Optional.ofNullable(accountEntity.getIban()).ifPresent(existingAccount::setIban);
            Optional.ofNullable(accountEntity.getType()).ifPresent(existingAccount::setType);
            return accountRepository.save(existingAccount);
        }).orElseThrow(() -> new RuntimeException("Account does not exist"));
    }

    public AccountEntity fullUpdateAccountByIban(String accountIban, AccountEntity accountEntity) {
        accountEntity.setIban(accountIban);
        return accountRepository.findByIban(accountIban).map(existingAccount -> {
            existingAccount.setCurrency(accountEntity.getCurrency());
            existingAccount.setType(accountEntity.getType());
            existingAccount.setBalance(accountEntity.getBalance());
            return accountRepository.save(existingAccount);
        }).orElseThrow(() -> new RuntimeException("User does not exist"));
    }

    public AccountEntity partialUpdateAccountByIban(String accountIban, AccountEntity accountEntity) {
        accountEntity.setIban(accountIban);
        return accountRepository.findByIban(accountIban).map(existingAccount -> {
            Optional.ofNullable(accountEntity.getCurrency()).ifPresent(existingAccount::setCurrency);
            Optional.ofNullable(accountEntity.getBalance()).ifPresent(existingAccount::setBalance);
            Optional.ofNullable(accountEntity.getType()).ifPresent(existingAccount::setType);
            return accountRepository.save(existingAccount);
        }).orElseThrow(() -> new RuntimeException("Account does not exist"));
    }

    public void deleteAccountById(Long accountId) {
        accountRepository.findById(accountId).ifPresent(accountEntity -> {
            Optional<DumpEntity> dumpEntityOptional = dumpRepository.findByEmail(accountEntity.getUser().getEmail());
            if (dumpEntityOptional.isPresent()) {
                updateDumpEntity(accountEntity, dumpEntityOptional.get());
            } else {
                DumpEntity newDump = new DumpEntity();
                newDump.setEmail(accountEntity.getUser().getEmail());
                newDump.setBalanceRON(BigDecimal.valueOf(0.00));
                newDump.setBalanceEUR(BigDecimal.valueOf(0.00));
                newDump.setBalanceUSD(BigDecimal.valueOf(0.00));
                dumpRepository.save(newDump);
                updateDumpEntity(accountEntity, dumpRepository.findByEmail(accountEntity.getUser().getEmail()).get());
            }
            accountEntity.getUser().removeAccount(accountEntity);
            accountRepository.deleteById(accountId);
        });
    }

    public void deleteAccountByIban(String accountIban) {
        accountRepository.findByIban(accountIban).ifPresent(accountEntity -> {
            Optional<DumpEntity> dumpEntityOptional = dumpRepository.findByEmail(accountEntity.getUser().getEmail());
            if (dumpEntityOptional.isPresent()) {
                updateDumpEntity(accountEntity, dumpEntityOptional.get());
            } else {
                DumpEntity newDump = new DumpEntity();
                newDump.setEmail(accountEntity.getUser().getEmail());
                newDump.setBalanceRON(BigDecimal.valueOf(0.00));
                newDump.setBalanceEUR(BigDecimal.valueOf(0.00));
                newDump.setBalanceUSD(BigDecimal.valueOf(0.00));
                dumpRepository.save(newDump);
                updateDumpEntity(accountEntity, dumpRepository.findByEmail(accountEntity.getUser().getEmail()).get());
            }
            accountEntity.getUser().removeAccount(accountEntity); // Remove account association without deleting the user
            accountRepository.findByIban(accountIban).ifPresent(accountRepository::delete);
        });
    }

    private void updateDumpEntity(AccountEntity accountEntity, DumpEntity dumpEntity) {
        BigDecimal amount = accountEntity.getBalance();
        String currency = accountEntity.getCurrency().toString();

        switch (currency) {
            case "RON":
                dumpEntity.setBalanceRON(dumpEntity.getBalanceRON().add(amount));
                break;
            case "EUR":
                dumpEntity.setBalanceEUR(dumpEntity.getBalanceEUR().add(amount));
                break;
            case "USD":
                dumpEntity.setBalanceUSD(dumpEntity.getBalanceUSD().add(amount));
                break;
            default:
                throw new IllegalArgumentException("Unsupported currency: " + currency);
        }

        dumpRepository.save(dumpEntity);
    }

    public void transferMoney(String sourceIban, String targetIban, BigDecimal amount) {
        AccountEntity sourceAccount = accountRepository.findByIban(sourceIban)
                .orElseThrow(() -> new IllegalArgumentException("Source account not found"));

        AccountEntity targetAccount = accountRepository.findByIban(targetIban)
                .orElseThrow(() -> new IllegalArgumentException("Target account not found"));

        CurrencyType sourceCurrency = sourceAccount.getCurrency();
        CurrencyType targetCurrency = targetAccount.getCurrency();

        BigDecimal convRate = CurrencyConvertor.getConversionRate(sourceCurrency, targetCurrency);

        List<AccountEntity> accountsList = getAccountsListByUserEmail(sourceAccount.getUser().getEmail());

        boolean roundUpAccountFound = false;
        AccountEntity roundUpAccountEntity = null;

        for (AccountEntity account : accountsList) {
            if ("ROUND_UP".equals(account.getType().toString())) {
                roundUpAccountFound = true;
                roundUpAccountEntity = account;
                break;
            }
        }

        Double givenAmount = amount.doubleValue();
        Double trueAmount = 0.00;
        if (roundUpAccountFound == false) {
            trueAmount = givenAmount;
        } else {
            trueAmount =  Math.ceil(givenAmount);
        }

        BigDecimal trueAmountBigDecimal = BigDecimal.valueOf(trueAmount);

        if (sourceAccount.getBalance().compareTo(trueAmountBigDecimal) < 0) {
            throw new IllegalArgumentException("Insufficient balance in the source account");
        }

        if (roundUpAccountFound == true) {
            BigDecimal convRate2 = CurrencyConvertor.getConversionRate(sourceCurrency, roundUpAccountEntity.getCurrency());
            roundUpAccountEntity.setBalance(roundUpAccountEntity.getBalance().add(BigDecimal.valueOf(trueAmount-givenAmount).multiply(convRate2)));
        }

        sourceAccount.setBalance(sourceAccount.getBalance().subtract(trueAmountBigDecimal));
        targetAccount.setBalance(targetAccount.getBalance().add(amount.multiply(convRate)));

        accountRepository.save(sourceAccount);
        accountRepository.save(targetAccount);

        BillEntity sentBill = BillEntity.builder()
                .sourceAccountIban(sourceIban)
                .targetAccountIban(targetIban)
                .billType(BillType.SENT)
                .userEmail(sourceAccount.getUser().getEmail())
                .amount(amount)
                .creationDateTime(LocalDateTime.now())
                .build();

        billRepository.save(sentBill);

        String receiverEmail = targetAccount.getUser().getEmail();

        BillEntity receivedBill = BillEntity.builder()
                .sourceAccountIban(sourceIban)
                .targetAccountIban(targetIban)
                .billType(BillType.RECEIVED)
                .userEmail(receiverEmail)
                .amount(amount.multiply(convRate))
                .creationDateTime(LocalDateTime.now())
                .build();

        billRepository.save(receivedBill);
    }

    public List<AccountEntity> getTransferAccountsByIban(String[] ibans) {
        return accountRepository.findByIbanIn(ibans);
    }
}
