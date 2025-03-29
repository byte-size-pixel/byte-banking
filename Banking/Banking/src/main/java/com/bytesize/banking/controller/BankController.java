package com.bytesize.banking.controller;

import com.bytesize.banking.service.AccountService;
import com.bytesize.banking.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class BankController {

    private final CustomerService customerService;
    private final AccountService accountService;

    @Autowired
    public BankController(CustomerService customerServiceI, AccountService accountServiceI) {
        this.customerService = customerServiceI;
        this.accountService = accountServiceI;
    }


}
