package com.springboot.bankingapp.utils;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class IbanGenerator {
    private Random random = new Random();
    private String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private String DIGITS = "0123456789";

    public String generateRandomString() {
        StringBuilder result = new StringBuilder();

        result.append("RO");

        result.append(generateRandomNumber());
        result.append(generateRandomNumber());

        result.append(generateRandomLetter());
        result.append(generateRandomLetter());
        result.append(generateRandomLetter());

        for (int i = 0; i < 20; i++) {
            if (random.nextBoolean()) {
                result.append(generateRandomNumber());
            } else {
                result.append(generateRandomLetter());
            }
        }
        return result.toString();
    }
    private char generateRandomLetter() {
        return LETTERS.charAt(random.nextInt(LETTERS.length()));
    }
    private char generateRandomNumber() {
        return DIGITS.charAt(random.nextInt(DIGITS.length()));
    }
}
