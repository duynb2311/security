package com.duy.nb.spring.security.dto;

import java.math.BigDecimal;

public class TransactionDto {
    private String transactionId;
    private String inAccount;
    private String outAccount;
    private String value;

    public TransactionDto() {
    }

    public TransactionDto(String transactionId, String inAccount, String outAccount, String value) {
        this.transactionId = transactionId;
        this.inAccount = inAccount;
        this.outAccount = outAccount;
        this.value = value;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getInAccount() {
        return inAccount;
    }

    public void setInAccount(String inAccount) {
        this.inAccount = inAccount;
    }

    public String getOutAccount() {
        return outAccount;
    }

    public void setOutAccount(String outAccount) {
        this.outAccount = outAccount;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
