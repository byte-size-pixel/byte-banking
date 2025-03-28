package com.bytesize.banking.entity;

import lombok.Generated;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Document(collection = "transactions")
public class Transactions {

    @Id
    @Generated
    private long id;

    private BigDecimal amount;

    private String type;

    private LocalDateTime timeStamp;

    @DBRef
    private Account account;
}
