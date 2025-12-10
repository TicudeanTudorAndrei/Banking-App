package com.springboot.bankingapp.services;

import com.springboot.bankingapp.domain.entities.UserEntity;
import com.springboot.bankingapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    public List<UserEntity> getUsersList() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }
    public UserEntity createUser(UserEntity userEntity) {
        Optional<UserEntity> userOptional = userRepository.findByEmail(userEntity.getEmail());
        if(userOptional.isPresent()){
            System.out.println("There is already a registered user with the email: " + userEntity.getEmail());
        }else{
            return userRepository.save(userEntity);
        }
        return null;
    }

    public Optional<UserEntity> getUserById(Long searchedId) {
        return userRepository.findById(searchedId);
    }

    public Optional<UserEntity> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean isIdExists(Long userId) {
        return !userRepository.existsById(userId);
    }

    public UserEntity fullUpdateUserById(Long userId, UserEntity userEntity) {
        userEntity.setId(userId);
        return userRepository.findById(userId).map(existingUser -> {
            existingUser.setName(userEntity.getName());
            existingUser.setEmail(userEntity.getEmail());
            existingUser.setPassword(userEntity.getPassword());
            existingUser.setPhone(userEntity.getPhone());
            existingUser.setDateOfBirth(userEntity.getDateOfBirth());
            existingUser.setEnabled(userEntity.getEnabled());
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new RuntimeException("User does not exist"));
    }

    public UserEntity partialUpdateUserById(Long userId, UserEntity userEntity) {
        userEntity.setId(userId);
        return userRepository.findById(userId).map(existingUser -> {
            Optional.ofNullable(userEntity.getName()).ifPresent(existingUser::setName);
            Optional.ofNullable(userEntity.getEmail()).ifPresent(existingUser::setEmail);
            Optional.ofNullable(userEntity.getPassword()).ifPresent(existingUser::setPassword);
            Optional.ofNullable(userEntity.getPhone()).ifPresent(existingUser::setPhone);
            Optional.ofNullable(userEntity.getDateOfBirth()).ifPresent(existingUser::setDateOfBirth);
            Optional.ofNullable(userEntity.getEnabled()).ifPresent(existingUser::setEnabled);
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new RuntimeException("User does not exist"));
    }

    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }

    public void deleteUserByEmail(String email) {
        userRepository.findByEmail(email).ifPresent(userRepository::delete);
    }

    public boolean isEmailExists(String userEmail) {
        return userRepository.findByEmail(userEmail).isPresent();
    }

    public UserEntity fullUpdateUserByEmail(String userEmail, UserEntity userEntity) {
        userEntity.setEmail(userEmail);
        return userRepository.findByEmail(userEmail).map(existingUser -> {
            existingUser.setName(userEntity.getName());
            existingUser.setPassword(userEntity.getPassword());
            existingUser.setPhone(userEntity.getPhone());
            existingUser.setDateOfBirth(userEntity.getDateOfBirth());
            existingUser.setEnabled(userEntity.getEnabled());
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new RuntimeException("User does not exist"));
    }

    public UserEntity partialUpdateUserByEmail(String userEmail, UserEntity userEntity) {
        userEntity.setEmail(userEmail);
        return userRepository.findByEmail(userEmail).map(existingUser -> {
            Optional.ofNullable(userEntity.getName()).ifPresent(existingUser::setName);
            Optional.ofNullable(userEntity.getPassword()).ifPresent(existingUser::setPassword);
            Optional.ofNullable(userEntity.getPhone()).ifPresent(existingUser::setPhone);
            Optional.ofNullable(userEntity.getDateOfBirth()).ifPresent(existingUser::setDateOfBirth);
            Optional.ofNullable(userEntity.getEnabled()).ifPresent(existingUser::setEnabled);
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new RuntimeException("User does not exist"));
    }
}
