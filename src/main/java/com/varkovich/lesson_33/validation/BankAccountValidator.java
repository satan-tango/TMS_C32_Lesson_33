package com.varkovich.lesson_33.validation;

import com.varkovich.lesson_33.model.BankAccount;
import com.varkovich.lesson_33.repository.BankAccountRepository;
import com.varkovich.lesson_33.utils.BankAccountUtils;

import java.util.List;

public class BankAccountValidator {

    public static boolean doesBankAccountExistByBankAccount(BankAccount bankAccount, List<BankAccount> existingBankAccounts) {

        if (existingBankAccounts == null || existingBankAccounts.isEmpty()) {
            return false;
        }

        for (BankAccount existingBankAccount : existingBankAccounts) {
            if (bankAccount.getAccountNumber() == existingBankAccount.getAccountNumber()) {
                return true;
            }
        }

        return false;
    }

    public static boolean doesBankAccountExistByAccountNumber(int accountNumber, List<BankAccount> existingBankAccounts) {
        if (existingBankAccounts == null || existingBankAccounts.isEmpty()) {
            System.out.println("Bank account does not exist");
            return false;
        }

        BankAccount bankAccount = BankAccountUtils.getBankAccountFromExistingBankAccountsByAccountNumber(accountNumber, existingBankAccounts);
        if (bankAccount != null) {
            return true;
        }
        System.out.println("Bank account does not exist");
        return false;
    }

    public static boolean isValidateBankAccountsFroTransfer(int fromBankAccount, int toBankAccount, double amount) {
        List<BankAccount> existingBankAccounts = BankAccountRepository.getAllBankAccounts();

        if (amount <= 0) {
            System.out.println("Amount must be greater than 0");
            //TODO logs
            return false;
        }

        if (existingBankAccounts == null || existingBankAccounts.isEmpty()) {
            System.out.println("There are no existing bank accounts");
            //TODO logs
            return false;
        }

        if (!isValidateBankAccountNumber(fromBankAccount)) {
            //TODO logs
            return false;
        }

        if (!isValidateBankAccountNumber(toBankAccount)) {
            //TODO logs
            return false;
        }

        if (!doesBankAccountExistByAccountNumber(fromBankAccount, existingBankAccounts)) {
            //TODO logs
            return false;
        }

        if (!doesBankAccountExistByAccountNumber(toBankAccount, existingBankAccounts)) {
            //TODO logs
            return false;
        }

        BankAccount bankAccount = BankAccountUtils.getBankAccountFromExistingBankAccountsByAccountNumber(fromBankAccount, existingBankAccounts);

        if (bankAccount.getBalance() < amount) {
            System.out.println("Insufficient funds");
            //TODO logs
            return false;
        }


        return true;
    }

    public static boolean isValidateBankAccountNumber(int bankAccountNumber) {
        String strBankAccountNumber = String.valueOf(bankAccountNumber);

        if (strBankAccountNumber.length() != 4) {
            System.out.println("Invalid bank account number");
            return false;
        }

        char[] charBankAccountNumber = strBankAccountNumber.toCharArray();

        for (int i = 0; i < charBankAccountNumber.length; i++) {
            if (!Character.isDigit(charBankAccountNumber[i])) {
                System.out.println("Invalid bank account number");
                return false;
            }
        }

        return true;
    }

    public static boolean isValidateBankAccountBalance(double bankAccountBalance) {
        if (bankAccountBalance < 0) {
            return false;
        }

        if (bankAccountBalance > 99999) {
            return false;
        }

        return true;
    }
}
