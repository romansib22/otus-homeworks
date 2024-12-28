package ru.otus.java.pro.spring.app.dtos;

public record AccountDto(String id, String accountNo, String clientId, Integer balance, boolean locked) {
}

