package com.springboot.bankingapp.controllers;

import com.springboot.bankingapp.domain.dto.BillDto;
import com.springboot.bankingapp.domain.entities.BillEntity;
import com.springboot.bankingapp.mappers.Mapper;
import com.springboot.bankingapp.services.BillService;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bills")
@RequiredArgsConstructor
public class BillController {

    private final BillService billService;
    private final Mapper<BillEntity, BillDto> billMapper;

    @GetMapping(path = "/b/email/{email_val}")
    public ResponseEntity<List<BillDto>> getAccountsByUserEmail(@PathVariable("email_val") String userEmail) {
        List<BillEntity> userBills = billService.getBillsListByUserEmail(userEmail);
        if (userBills.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<BillDto> billDtos = userBills.stream().map(billMapper::mapTo).collect(Collectors.toList());
        return new ResponseEntity<>(billDtos, HttpStatus.OK);
    }
}
