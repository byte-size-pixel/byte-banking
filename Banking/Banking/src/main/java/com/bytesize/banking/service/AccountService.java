package com.bytesize.banking.service;

import com.bytesize.banking.entity.Account;
import com.bytesize.banking.entity.Transactions;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    BigDecimal getAccountBalance(long accountId);
    Account getTransactionsByAccountId(long accountId);
    Account findAccountByAccountId(long accountId);
    void registerAccount(Account account);
    void deposit(Account account, BigDecimal amount);
    void withdraw(Account account, BigDecimal amount);

}
