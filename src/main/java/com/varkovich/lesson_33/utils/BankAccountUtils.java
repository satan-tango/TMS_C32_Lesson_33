package com.varkovich.lesson_33.utils;

import com.varkovich.lesson_33.model.BankAccount;

import java.util.List;

public class BankAccountUtils {

    public static BankAccount getBankAccountFromExistingBankAccountsByAccountNumber(int accountNumber, List<BankAccount> existingBankAccounts) {
        for (BankAccount existingBankAccount : existingBankAccounts) {
            if (existingBankAccount.getAccountNumber() == accountNumber) {
                return existingBankAccount;
            }
        }

        return null;
    }
}
