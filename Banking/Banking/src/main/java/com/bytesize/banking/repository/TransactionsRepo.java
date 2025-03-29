package com.bytesize.banking.repository;

import com.bytesize.banking.entity.Transactions;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TransactionsRepo extends MongoRepository<Transactions, ObjectId> {

}
