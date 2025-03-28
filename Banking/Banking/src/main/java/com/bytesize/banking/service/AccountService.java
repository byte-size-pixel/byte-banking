package com.bytesize.banking.service;

import com.bytesize.banking.repository.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    AccountRepo accountRepo;

}
