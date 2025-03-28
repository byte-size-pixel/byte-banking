package com.bytesize.banking.repository;

import com.bytesize.banking.entity.Account;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AccountRepo extends MongoRepository<Account, ObjectId> {

}
