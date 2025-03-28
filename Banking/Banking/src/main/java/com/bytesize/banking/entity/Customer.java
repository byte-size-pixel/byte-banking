package com.bytesize.banking.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Document(collection = "customers")
@Data
public class Customer {

    @Id
    @Indexed(unique = true)
    @Generated
    private long customerId;

    private String customerName;

    private String userName;

    private String password;

    @DBRef
    private List<Account> acccount;


}
