package com.varkovich.lesson_33.utils;

public interface SQLQuery {

    String CREATE_BANK_ACCOUNT = "insert into bank_accounts (account_number, account_balance) values(?,?) ";
    String GET_ALL_BANK_ACCOUNTS = "select * from bank_accounts";
    String GET_BANK_ACCOUNT_BY_ACCOUNT_NUMBER = "select * from bank_accounts where account_number =?";
    String UPDATE_BALANCE = "update bank_accounts set account_balance =? where account_number =?";
    String LOG_NEW_TRANSACTION = "insert into transactions (from_account, to_account, amount, time_stamp) values(?,?,?,?)";
}
