package com.springboot.bankingapp.repositories;

import com.springboot.bankingapp.domain.entities.AccountEntity;
import com.springboot.bankingapp.domain.entities.BillEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BillRepository extends JpaRepository<BillEntity, Long> {

    List<BillEntity> findByUserEmail(String userEmail);
}
