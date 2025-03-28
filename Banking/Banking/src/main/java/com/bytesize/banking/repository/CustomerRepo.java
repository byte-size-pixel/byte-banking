package com.bytesize.banking.repository;

import com.bytesize.banking.entity.Customer;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CustomerRepo extends MongoRepository<Customer, ObjectId> {

    Optional<Customer> findByUserName(String userName);
    Optional<Customer> findByCustomerName(String customerName);
    Optional<Customer> findByCustomerId(long customerId);
}
