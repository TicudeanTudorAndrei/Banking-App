package com.springboot.bankingapp.services;

import com.springboot.bankingapp.domain.entities.AccountEntity;
import com.springboot.bankingapp.domain.entities.BillEntity;
import com.springboot.bankingapp.repositories.BillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BillService {

    private final BillRepository billRepository;

    public List<BillEntity> getBillsListByUserEmail(String userEmail) {
        return billRepository.findByUserEmail(userEmail);
    }
}
