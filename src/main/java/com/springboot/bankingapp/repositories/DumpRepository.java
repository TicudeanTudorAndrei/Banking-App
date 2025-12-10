package com.springboot.bankingapp.repositories;

import com.springboot.bankingapp.domain.entities.DumpEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface DumpRepository extends JpaRepository<DumpEntity, Long> {

    Optional<DumpEntity> findByEmail(String email);
}
