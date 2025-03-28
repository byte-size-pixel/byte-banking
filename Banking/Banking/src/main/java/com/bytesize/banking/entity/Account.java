package com.bytesize.banking.entity;

import lombok.Data;
import lombok.Generated;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

@Document(collection = "account")
@Data
public class Account {

    @Id
    @Generated
    private long accountId;

    private BigDecimal balance;

    @DBRef
    private List<Transactions> transactions;

    @Transient
    private Collection<? extends GrantedAuthority> authorities;

}
