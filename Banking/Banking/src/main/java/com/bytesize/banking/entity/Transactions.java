package com.bytesize.banking.entity;

import lombok.Data;
import lombok.Generated;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Document(collection = "transactions")
@Data
public class Transactions {

    @Id
    @Generated
    private long transactionId;

    private long accountID;

    private BigDecimal amount;

    private String type;

    private LocalDateTime timeStamp;

    public Transactions() {
    }

    public Transactions(long accountID, BigDecimal amount, String type, LocalDateTime timeStamp) {
        this.accountID = accountID;
        this.amount = amount;
        this.type = type;
        this.timeStamp = timeStamp;
    }
}
