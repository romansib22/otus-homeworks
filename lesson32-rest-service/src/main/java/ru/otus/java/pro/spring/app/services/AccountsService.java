package ru.otus.java.pro.spring.app.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.java.pro.spring.app.entities.Account;
import ru.otus.java.pro.spring.app.repositories.AccountsRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AccountsService {
    private final AccountsRepository repository;

    public List<Account> getAllClientAccounts(String clientId) {
        return repository.findAllByClientId(clientId);
    }

    public Optional<Account> getAccountById(String accountId, String clientId) {
        return repository.findByIdAndClientId(accountId, clientId);
    }

    public Optional<Account> getAccountByNumberAndClientId(String number, String clientId) {
        return repository.findByNumberAndClientId(number, clientId);
    }

    public void save(Account a) {
        repository.save(a);
    }
}
