package com.duy.nb.spring.security.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "transaction_history")
public class TransactionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "transaction_ID")
    private String transactionId;
    @Column(name = "account")
    private String account;
    @Column(name = "in_debt")
    private BigDecimal inDebt;
    @Column(name = "have")
    private BigDecimal have;
    @Column(name = "time")
    private Date time;

    public TransactionHistory() {
    }

    public TransactionHistory(String transactionId, String account, BigDecimal inDebt, BigDecimal have, Date time) {
        this.transactionId = transactionId;
        this.account = account;
        this.inDebt = inDebt;
        this.have = have;
        this.time = time;
    }

    public TransactionHistory(Long id, String transactionId, String account, BigDecimal inDebt, BigDecimal have, Date time) {
        this.id = id;
        this.transactionId = transactionId;
        this.account = account;
        this.inDebt = inDebt;
        this.have = have;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public BigDecimal getInDebt() {
        return inDebt;
    }

    public void setInDebt(BigDecimal inDebt) {
        this.inDebt = inDebt;
    }

    public BigDecimal getHave() {
        return have;
    }

    public void setHave(BigDecimal have) {
        this.have = have;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
