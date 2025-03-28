package com.bytesize.banking.repository;

import com.bytesize.banking.entity.Transactions;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransactionsRepo extends MongoRepository<Transactions, ObjectId> {
}
