package com.bytesize.banking.service;

import com.bytesize.banking.entity.Account;
import com.bytesize.banking.entity.Customer;

import java.util.List;

public interface CustomerService {

    Customer findCustomerByUserName(String userName);
    Customer findCustomerByCustomerName(String customerName);
    void registerCustomer(String customerName, String userName, String password, Account account);
    boolean updateCustomerAccounts(long customerId, List<Account> accounts);
    boolean updateCredential(String userName, String newThing, boolean isUserName);
    List<Account> getCustomerAccountsByCustomerId(long customerId);
}
