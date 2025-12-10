package com.springboot.bankingapp.controllers;

import com.springboot.bankingapp.domain.entities.UserEntity;
import com.springboot.bankingapp.domain.dto.UserDto;
import com.springboot.bankingapp.mappers.Mapper;
import com.springboot.bankingapp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final Mapper<UserEntity, UserDto> userMapper;

    @PostMapping(path = "/u/create")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        UserEntity userEntity = userMapper.mapFrom(userDto);
        try {
            UserEntity savedUserEntity = userService.createUser(userEntity);
            UserDto returnedUserDto = userMapper.mapTo(savedUserEntity);
            return new ResponseEntity<>(returnedUserDto, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping(path = "/u/get/id/{id_val}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id_val") Long userId){
        Optional<UserEntity> foundUser = userService.getUserById(userId);
        return foundUser.map(userEntity -> {
            UserDto userDto = userMapper.mapTo(userEntity);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/u/get/email/{email_val}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable("email_val") String userEmail) {
        Optional<UserEntity> foundUser = userService.getUserByEmail(userEmail);
        return foundUser.map(userEntity -> {
            UserDto userDto = userMapper.mapTo(userEntity);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/u/get/list")
    public ResponseEntity<List<UserDto>> getUsersList() {
        List<UserEntity> users = userService.getUsersList();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<UserDto> gotUsers = users.stream().map(userMapper::mapTo).collect(Collectors.toList());
        return new ResponseEntity<>(gotUsers, HttpStatus.OK);
    }

    @PutMapping(path = "/u/update/id/{id_val}")
    public ResponseEntity<UserDto> fullUpdateUserById(
            @PathVariable("id_val") Long userId,
            @RequestBody UserDto userDto) {
        if(userService.isIdExists(userId)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        userDto.setId(userId);
        UserEntity userEntity = userMapper.mapFrom(userDto);
        UserEntity updatedUserEntity = userService.fullUpdateUserById(userId, userEntity);
        UserDto updatedUserDto = userMapper.mapTo(updatedUserEntity);
        return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);
    }

    @PatchMapping(path = "/u/patch/id/{id_val}")
    public ResponseEntity<UserDto> partialUpdateUserById(
            @PathVariable("id_val") Long userId,
            @RequestBody UserDto userDto) {
        if(userService.isIdExists(userId)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        UserEntity userEntity = userMapper.mapFrom(userDto);
        UserEntity updatedUserEntity = userService.partialUpdateUserById(userId, userEntity);
        UserDto updatedUserDto = userMapper.mapTo(updatedUserEntity);
        return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);
    }

    @PutMapping(path = "/u/update/email/{email_val}")
    public ResponseEntity<UserDto> fullUpdateUserByEmail(
            @PathVariable("email_val") String userEmail,
            @RequestBody UserDto userDto) {
        if (!userService.isEmailExists(userEmail)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userDto.setEmail(userEmail);
        UserEntity userEntity = userMapper.mapFrom(userDto);
        UserEntity updatedUserEntity = userService.fullUpdateUserByEmail(userEmail, userEntity);
        UserDto updatedUserDto = userMapper.mapTo(updatedUserEntity);
        return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);
    }

    @PatchMapping(path = "/u/patch/email/{email_val}")
    public ResponseEntity<UserDto> partialUpdateUserByEmail(
            @PathVariable("email_val") String userEmail,
            @RequestBody UserDto userDto) {
        if (!userService.isEmailExists(userEmail)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        UserEntity userEntity = userMapper.mapFrom(userDto);
        UserEntity updatedUserEntity = userService.partialUpdateUserByEmail(userEmail, userEntity);
        UserDto updatedUserDto = userMapper.mapTo(updatedUserEntity);
        return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);
    }

    @DeleteMapping(path = "/u/delete/id/{id_val}")
    public ResponseEntity<Void> deleteUserById(@PathVariable("id_val") Long userId) {
        if (userService.isIdExists(userId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userService.deleteUserById(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/u/delete/email/{email_val}")
    public ResponseEntity deleteUserByEmail(@PathVariable("email_val") String email) {
        userService.deleteUserByEmail(email);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
