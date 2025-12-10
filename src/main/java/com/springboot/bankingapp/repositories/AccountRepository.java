package com.springboot.bankingapp.repositories;

import com.springboot.bankingapp.domain.entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    List<AccountEntity> findByUserId(Long userId);
    @Query("SELECT accountEntity FROM AccountEntity accountEntity WHERE accountEntity.iban = ?1")
    Optional<AccountEntity> findByIban(String iban);

    List<AccountEntity> findByIbanIn(String[] ibans);

    List<AccountEntity> findByUserEmail(String userEmail);
}
