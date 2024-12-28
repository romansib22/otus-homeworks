package ru.otus.java.pro.spring.app.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.java.pro.spring.app.dtos.ExecuteTransferDtoRq;
import ru.otus.java.pro.spring.app.entities.Account;
import ru.otus.java.pro.spring.app.entities.Transfer;
import ru.otus.java.pro.spring.app.exceptions_handling.BusinessLogicException;
import ru.otus.java.pro.spring.app.exceptions_handling.ValidationException;
import ru.otus.java.pro.spring.app.exceptions_handling.ValidationFieldError;
import ru.otus.java.pro.spring.app.repositories.TransfersRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransfersService {
    private final TransfersRepository transfersRepository;
    private final AccountsService accountsService;

    public Optional<Transfer> getTransferById(String id, String clientId) {
        return transfersRepository.findByIdAndClientId(id, clientId);
    }

    public List<Transfer> getAllTransfers(String clientId) {
        return transfersRepository.findAllByClientIdOrTargetClientId(clientId, clientId);
    }

    public void execute(String clientId, ExecuteTransferDtoRq executeTransferDtoRq) {
        validateExecuteTransferDtoRq(executeTransferDtoRq);
        processTransfer(clientId, executeTransferDtoRq);
    }

    private void processTransfer(String clientId, ExecuteTransferDtoRq executeTransferDtoRq) {
        Account srcAccount = accountsService.getAccountByNumberAndClientId(executeTransferDtoRq.sourceAccount(), clientId).orElseThrow(() -> new BusinessLogicException("Счёт с таким номером не найден", "ACCOUNT_NOT_FOUND"));
        if (srcAccount.getBalance() < executeTransferDtoRq.amount()) {
            throw new BusinessLogicException("Недостаточно средств для перевода","NOT_ENOUGH_MONEY");
        }
        if (srcAccount.isLocked())
            throw new BusinessLogicException("Счёт отправителя заблокирован","SOURCE_ACCOUNT_LOCKED");
        Account dstAccount = accountsService.getAccountByNumberAndClientId(executeTransferDtoRq.targetAccount(), executeTransferDtoRq.targetClientId()).orElseThrow(() -> new BusinessLogicException("Счёт получателя не найден", "TARGET_ACCOUNT_NOT_FOUND"));
        if (dstAccount.isLocked())
            throw new BusinessLogicException("Счёт получателя заблокирован","TARGET_ACCOUNT_LOCKED");
        srcAccount.setBalance(srcAccount.getBalance() - executeTransferDtoRq.amount());
        dstAccount.setBalance(dstAccount.getBalance() + executeTransferDtoRq.amount());
        transfersRepository.save(new Transfer(UUID.randomUUID().toString(), clientId, dstAccount.getClientId(), srcAccount.getNumber(), dstAccount.getNumber(), executeTransferDtoRq.message(), executeTransferDtoRq.amount()));
        accountsService.save(srcAccount);
        accountsService.save(dstAccount);
    }

    private void validateExecuteTransferDtoRq(ExecuteTransferDtoRq executeTransferDtoRq) {
        List<ValidationFieldError> errors = new ArrayList<>();
        if (executeTransferDtoRq.sourceAccount().length() != 12) {
            errors.add(new ValidationFieldError("sourceAccount", "Длина поля счет отправителя должна составлять 12 символов"));
        }
        if (executeTransferDtoRq.targetAccount().length() != 12) {
            errors.add(new ValidationFieldError("targetAccount", "Длина поля счет получателя должна составлять 12 символов"));
        }
        if (executeTransferDtoRq.amount() <= 0) {
            errors.add(new ValidationFieldError("amount", "Сумма перевода должна быть больше 0"));
        }
        if (!errors.isEmpty()) {
            throw new ValidationException("EXECUTE_TRANSFER_VALIDATION_ERROR", "Проблемы заполнения полей перевода", errors);
        }
    }
}
