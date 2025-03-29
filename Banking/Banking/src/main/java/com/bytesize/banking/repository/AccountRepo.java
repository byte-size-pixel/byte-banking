package com.bytesize.banking.repository;

import com.bytesize.banking.entity.Account;
import com.bytesize.banking.entity.Transactions;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AccountRepo extends MongoRepository<Account, ObjectId> {

    BigDecimal findBalanceByAccountId(long accountId);
    Transactions findTransactionsByAccountId(long accountId);
    Optional<Account> findByAccountId(long accountId);

}
