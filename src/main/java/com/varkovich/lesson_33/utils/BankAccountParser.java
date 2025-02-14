package com.varkovich.lesson_33.utils;

import com.varkovich.lesson_33.model.BankAccount;
import lombok.extern.log4j.Log4j;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Log4j
public class BankAccountParser {

    public static List<BankAccount> parse(ResultSet rs) {
        List<BankAccount> bankAccounts = new ArrayList<>();

        try {
            while (rs.next()) {
                BankAccount bankAccount = new BankAccount();
                bankAccount.setAccountNumber(rs.getInt("account_number"));
                bankAccount.setBalance(rs.getDouble("account_balance"));
                bankAccounts.add(bankAccount);
            }
        } catch (SQLException e) {
            log.error("Result set parse error", e);
        }

        log.info("Bank accounts parsed");
        return bankAccounts;
    }
}
