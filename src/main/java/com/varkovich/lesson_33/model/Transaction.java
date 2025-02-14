package com.varkovich.lesson_33.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;

public class Transaction {

    private int fromAccount;

    private int toAccount;

    private double amount;

    private LocalDateTime timeStamp = LocalDateTime.now();

    public Transaction(int fromAccount, int toAccount, double amount) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
    }


    public int getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(int fromAccount) {
        this.fromAccount = fromAccount;
    }

    public int getToAccount() {
        return toAccount;
    }

    public void setToAccount(int toAccount) {
        this.toAccount = toAccount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }
}
