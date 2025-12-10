package com.springboot.bankingapp.domain.dto;

import com.springboot.bankingapp.domain.entities.AccountEntity;
import com.springboot.bankingapp.domain.entities.types.RoleType;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private Long id;

    private String name;

    private String email;

    private String password;

    private String phone;

    private LocalDate dateOfBirth;

    private RoleType roleType;

    private Boolean enabled;

}
