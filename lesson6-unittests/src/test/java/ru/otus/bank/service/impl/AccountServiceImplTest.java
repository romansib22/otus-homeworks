package ru.otus.bank.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.bank.dao.AccountDao;
import ru.otus.bank.entity.Account;
import ru.otus.bank.entity.Agreement;
import ru.otus.bank.service.exception.AccountException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {
    @Mock
    AccountDao accountDao;

    @InjectMocks
    AccountServiceImpl accountServiceImpl;

    @Test
    public void testTransfer() {
        Account sourceAccount = new Account();
        sourceAccount.setAmount(new BigDecimal(100));

        Account destinationAccount = new Account();
        destinationAccount.setAmount(new BigDecimal(10));

        when(accountDao.findById(eq(1L))).thenReturn(Optional.of(sourceAccount));
        when(accountDao.findById(eq(2L))).thenReturn(Optional.of(destinationAccount));

        accountServiceImpl.makeTransfer(1L, 2L, new BigDecimal(10));

        assertEquals(new BigDecimal(90), sourceAccount.getAmount());
        assertEquals(new BigDecimal(20), destinationAccount.getAmount());
    }

    @Test
    public void testSourceNotFound() {
        when(accountDao.findById(any())).thenReturn(Optional.empty());

        AccountException result = assertThrows(AccountException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                accountServiceImpl.makeTransfer(1L, 2L, new BigDecimal(10));
            }
        });
        assertEquals("No source account", result.getLocalizedMessage());
    }


    @Test
    public void testTransferWithVerify() {
        Account sourceAccount = new Account();
        sourceAccount.setAmount(new BigDecimal(100));
        sourceAccount.setId(1L);

        Account destinationAccount = new Account();
        destinationAccount.setAmount(new BigDecimal(10));
        destinationAccount.setId(2L);

        when(accountDao.findById(eq(1L))).thenReturn(Optional.of(sourceAccount));
        when(accountDao.findById(eq(2L))).thenReturn(Optional.of(destinationAccount));

        ArgumentMatcher<Account> sourceMatcher =
                argument -> argument.getId().equals(1L) && argument.getAmount().equals(new BigDecimal(90));

        ArgumentMatcher<Account> destinationMatcher =
                argument -> argument.getId().equals(2L) && argument.getAmount().equals(new BigDecimal(20));

        accountServiceImpl.makeTransfer(1L, 2L, new BigDecimal(10));

        verify(accountDao).save(argThat(sourceMatcher));
        verify(accountDao).save(argThat(destinationMatcher));
    }

    @Test
    void testChargeTrue() {
        Account account = new Account();
        account.setAmount(new BigDecimal(100));

        when(accountDao.findById(eq(1L))).thenReturn(Optional.of(account));

        boolean result = accountServiceImpl.charge(1L, new BigDecimal(20));
        assertTrue(result);
    }

    @Test
    void testChargeAccountNotFound() {
        Account account = new Account();
        account.setAmount(new BigDecimal(100));

        when(accountDao.findById(any())).thenReturn(Optional.empty());
        AccountException result = assertThrows(AccountException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                accountServiceImpl.charge(1L, new BigDecimal(20));
            }
        });
        assertEquals("No source account", result.getLocalizedMessage());
    }

    @Test
    public void testAddAccountWithVerify() {
        Agreement agreement = new Agreement();
        agreement.setId(1L);

        ArgumentMatcher<Account> matcher =
                argument -> argument.getAgreementId().equals(1L) &&
                        argument.getNumber().equals("12345") &&
                        argument.getType() == 1 &&
                        argument.getAmount().equals(new BigDecimal(100));
        accountServiceImpl.addAccount(agreement, "12345", 1, new BigDecimal(100));
        verify(accountDao).save(argThat(matcher));
    }

    @Test
    public void testGetAccountsByAgreement() {
        Agreement agreement = new Agreement();
        agreement.setId(1L);

        Account account1 = new Account();
        account1.setAgreementId(1L);
        account1.setType(0);

        Account account2 = new Account();
        account2.setAgreementId(1L);
        account2.setType(1);

        when(accountDao.findByAgreementId(1L)).thenReturn(List.of(account1, account2));

        List<Account> result = accountServiceImpl.getAccounts(agreement);
        assertEquals(2, result.size());
        assertEquals(0, result.get(0).getType());
        assertEquals(1, result.get(1).getType());
    }

}
