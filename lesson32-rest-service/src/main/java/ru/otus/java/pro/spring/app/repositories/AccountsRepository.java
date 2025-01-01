package ru.otus.java.pro.spring.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.java.pro.spring.app.entities.Account;

import java.util.List;
import java.util.Optional;

public interface AccountsRepository extends JpaRepository<Account, String> {
    List<Account> findAllByClientId(String clientId);
    Optional<Account> findByIdAndClientId(String id, String clientId);
    Optional<Account> findByNumberAndClientId(String number, String clientId);
    Optional<Account> findByNumber(String number);
}
