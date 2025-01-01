package ru.otus.java.pro.spring.app.dtos;

import java.util.List;

public record AccountsPageDto(List<AccountDto> entries) {
}