package com.bytesize.banking.serviceimpl;

import com.bytesize.banking.entity.Account;
import com.bytesize.banking.entity.Transactions;
import com.bytesize.banking.exceptions.NotFoundException;
import com.bytesize.banking.repository.AccountRepo;
import com.bytesize.banking.repository.TransactionsRepo;
import com.bytesize.banking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepo accountRepo;

    private final TransactionsRepo transactionsRepo;

    private final MongoTemplate mongoTemplate;

    @Autowired
    public AccountServiceImpl(AccountRepo accountRepoI, TransactionsRepo transactionsRepoI, MongoTemplate mongoTemplateI)
    {
        this.accountRepo = accountRepoI;
        this.transactionsRepo = transactionsRepoI;
        this.mongoTemplate = mongoTemplateI;
    }


    @Override
    public BigDecimal getAccountBalance(long accountId) {
        return accountRepo.findBalanceByAccountId(accountId);
    }

    @Override
    public Account getTransactionsByAccountId(long accountId) {

        LookupOperation lookupOperation = LookupOperation.newLookup()
                .from("transactions")
                .localField("transactionId")
                .foreignField("accountId")
                .as("userTransactions");

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("transactionId").is(accountId)), lookupOperation
        );
        return mongoTemplate.aggregate(aggregation,"account", Account.class).getUniqueMappedResult();
    }

    @Override
    public Account findAccountByAccountId(long accountId) {
        Optional<Account> account = accountRepo.findByAccountId(accountId);
        if(account.isEmpty())
        {
            throw new NotFoundException("Account not found!");
        }
        return account.get();
    }

    @Override
    public void registerAccount(Account account) {
        accountRepo.save(account);
    }

    @Override
    public void deposit(Account account, BigDecimal amount) {
        account.setBalance(account.getBalance().add(amount));
        accountRepo.save(account);

        Transactions transactions = new Transactions(
                account.getAccountId(),
                amount,
                "Deposit",
                LocalDateTime.now()
        );

        transactionsRepo.save(transactions);
    }

    @Override
    public void withdraw(Account account, BigDecimal amount) {
        if(account.getBalance().compareTo(amount) < 0)
        {
            throw new RuntimeException("Insufficient funds!");
        }
        account.setBalance(account.getBalance().subtract(amount));
        accountRepo.save(account);

        Transactions transactions = new Transactions(
                account.getAccountId(),
                amount,
                "Withdrawal",
                LocalDateTime.now()
        );

        transactionsRepo.save(transactions);
    }

    public void transferAmount(Account fromAccount, long accountId, BigDecimal amount){

        if(fromAccount.getBalance().compareTo(amount) < 0){
            throw new NotFoundException("Insufficient funds");
        }
        Account toAccount = accountRepo.findByAccountId(accountId)
                .orElseThrow(()-> new NotFoundException("Recipient Account not found!"));

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        accountRepo.save(fromAccount);

        toAccount.setBalance(toAccount.getBalance().add(amount));
        accountRepo.save(toAccount);

        Transactions senderTransactions = new Transactions(
                fromAccount.getAccountId(),
                amount,
                "Transfer to " + accountId,
                LocalDateTime.now()
        );

        transactionsRepo.save(senderTransactions);

        Transactions recieverTransactions = new Transactions(
                toAccount.getAccountId(),
                amount,
                "Transfer from " + accountId,
                LocalDateTime.now()
        );

        transactionsRepo.save(recieverTransactions);
    }
}
