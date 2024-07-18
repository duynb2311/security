package com.duy.nb.spring.security.dto;


public class CreateHistoryResponseDto {

    private String id;
    private String transactionId;
    private String account;
    private String inDebt;
    private String have;
    private String time;

    public CreateHistoryResponseDto() {
    }

    public CreateHistoryResponseDto(String id, String transactionId, String account, String inDebt, String have, String time) {
        this.id = id;
        this.transactionId = transactionId;
        this.account = account;
        this.inDebt = inDebt;
        this.have = have;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getInDebt() {
        return inDebt;
    }

    public void setInDebt(String inDebt) {
        this.inDebt = inDebt;
    }

    public String getHave() {
        return have;
    }

    public void setHave(String have) {
        this.have = have;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
