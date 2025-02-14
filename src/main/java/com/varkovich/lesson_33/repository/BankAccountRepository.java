package com.varkovich.lesson_33.repository;

import com.varkovich.lesson_33.model.BankAccount;
import com.varkovich.lesson_33.model.Transaction;
import com.varkovich.lesson_33.service.DatabaseService;
import com.varkovich.lesson_33.utils.BankAccountParser;
import com.varkovich.lesson_33.utils.BankAccountUtils;
import com.varkovich.lesson_33.utils.SQLQuery;
import com.varkovich.lesson_33.validation.BankAccountValidator;
import lombok.extern.log4j.Log4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Log4j
public class BankAccountRepository {

    public static void createBankAccount(BankAccount bankAccount) {
        Connection conn = DatabaseService.getConnection();
        try {
            List<BankAccount> bankAccounts = getAllBankAccounts();
            if (BankAccountValidator.doesBankAccountExistByBankAccount(bankAccount, bankAccounts)) {
                log.info("Failed to create bank account because bank account already exists.");
                System.out.println("Bank account already exist");
                return;
            }

            PreparedStatement preparedStatement = conn.prepareStatement(SQLQuery.CREATE_BANK_ACCOUNT);
            preparedStatement.setInt(1, bankAccount.getAccountNumber());
            preparedStatement.setDouble(2, bankAccount.getBalance());

            preparedStatement.executeUpdate();
            log.info("Successfully created bank account");
        } catch (SQLException e) {
            log.error("Failed while creating bank account" + e.getMessage());
            //TODO logs
        }
    }

    public static BankAccount getBalanceByAccountNumber(int accountNumber) {
        Connection conn = DatabaseService.getConnection();

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(SQLQuery.GET_BANK_ACCOUNT_BY_ACCOUNT_NUMBER);
            preparedStatement.setInt(1, accountNumber);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<BankAccount> bankAccounts = BankAccountParser.parse(resultSet);

            if (!bankAccounts.isEmpty()) {
                log.info("Bank account  with account number: " + accountNumber + " has been found");
                return bankAccounts.get(0);
            }
        } catch (SQLException e) {
            log.error("Failed while getting bank account balance" + e.getMessage());
        }

        log.info("Bank account with account number " + accountNumber + " not found");
        return null;
    }

    public static void transferMoney(int fromAccountNumber, int toAccountNumber, double amount) {
        List<BankAccount> bankAccounts = getAllBankAccounts();
        Connection conn = DatabaseService.getConnection();
        BankAccount fromBankAccount = BankAccountUtils.getBankAccountFromExistingBankAccountsByAccountNumber(fromAccountNumber, bankAccounts);
        BankAccount toBankAccount = BankAccountUtils.getBankAccountFromExistingBankAccountsByAccountNumber(toAccountNumber, bankAccounts);
        try {
            conn.setAutoCommit(false);

            fromBankAccount.setBalance(fromBankAccount.getBalance() - amount);
            toBankAccount.setBalance(toBankAccount.getBalance() + amount);

            updateBalance(fromAccountNumber, fromBankAccount.getBalance(), conn);
            log.info("Bank account balance updated: " + fromBankAccount.getBalance());
            updateBalance(toAccountNumber, toBankAccount.getBalance(), conn);
            log.info("Bank account balance updated: " + toBankAccount.getBalance());

            Transaction transaction = new Transaction(fromBankAccount.getAccountNumber(), toBankAccount.getAccountNumber(), amount);
            TransactionRepository.recordTransaction(transaction, conn);
            log.info("Transaction has been recorded successfully");

            conn.commit();

            log.info("Successfully transferred from " + fromBankAccount.getAccountNumber() + " to " + toBankAccount.getAccountNumber() + ". Amount: " + amount);
            System.out.println("Transfer successful.\n\n");
        } catch (SQLException e) {
            log.error("Transfer failed: " + e.getMessage());
            try {
                conn.rollback();
                log.info("Transaction rolled back successfully");
            } catch (SQLException ex) {
                log.error("Transaction rolled back exception: " + ex.getMessage());
                throw new RuntimeException(ex);
            }
        } finally {
            try {
                conn.setAutoCommit(false);
            } catch (SQLException e) {
                log.error("Transaction set auto commit failed: " + e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

    public static void updateBalance(int accountNumber, double balance, Connection connection) {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.UPDATE_BALANCE);
            preparedStatement.setDouble(1, balance);
            preparedStatement.setInt(2, accountNumber);


            preparedStatement.executeUpdate();
            log.info("Account number balance: " + accountNumber + " has been updated successfully");

        } catch (SQLException e) {
            log.error("Error while updating balance: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static List<BankAccount> getAllBankAccounts() {
        Connection conn = DatabaseService.getConnection();

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(SQLQuery.GET_ALL_BANK_ACCOUNTS);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<BankAccount> bankAccounts = BankAccountParser.parse(resultSet);

            log.info("Bank account found");
            return bankAccounts;
        } catch (SQLException e) {
            log.error("Error while getting bank accounts: " + e.getMessage());

            return null;
        }
    }
}
