package com.varkovich.lesson_33.controller.impl;

import com.varkovich.lesson_33.controller.BankAccountController;
import com.varkovich.lesson_33.model.BankAccount;
import com.varkovich.lesson_33.repository.BankAccountRepository;
import com.varkovich.lesson_33.validation.BankAccountValidator;
import lombok.extern.log4j.Log4j;

import java.util.Scanner;

@Log4j
public class BankAccountControllerImpl implements BankAccountController {

    private final Scanner scanner = new Scanner(System.in);


    @Override
    public void viewBalance() {
        int accountNumber = -1;
        boolean flag = true;

        while (flag) {
            System.out.print("Enter account number: ");
            accountNumber = scanner.nextInt();

            log.info("Account number has been entered: " + accountNumber);

            if (!BankAccountValidator.isValidateBankAccountNumber(accountNumber)) {
                System.out.println("Invalid account number, it must be 4 digits, please try again.");

                log.info("Account number has failed validation: " + accountNumber);

                continue;
            }

            BankAccount bankAccount = BankAccountRepository.getBalanceByAccountNumber(accountNumber);
            if (bankAccount == null) {
                System.out.println("There is no such bank account, please try again.");

                log.info("There is no such account number in system: " + accountNumber);

                continue;
            }

            log.info("Bank account with account number: " + bankAccount.getAccountNumber() + " has balance: " + bankAccount.getBalance());

            System.out.println("Bank account with account number: " + bankAccount.getAccountNumber() + " has balance: " + bankAccount.getBalance() + "\n\n");
            flag = false;
        }
    }

    @Override
    public void createAccount() {
        int accountNumber = -1;
        double accountBalance = -1;
        boolean flag = true;
        while (flag) {
            System.out.print("Enter account number: ");
            accountNumber = scanner.nextInt();

            log.info("Account number has been entered: " + accountNumber);

            if (!BankAccountValidator.isValidateBankAccountNumber(accountNumber)) {
                System.out.println("Invalid account number, it must be 4 digits, please try again.");

                log.info("Account number has failed validation: " + accountNumber);

                continue;
            }
            System.out.print("Enter account balance: ");
            accountBalance = scanner.nextDouble();

            log.info("Account balance has been entered: " + accountBalance);

            if (!BankAccountValidator.isValidateBankAccountBalance(accountBalance)) {
                System.out.println("Invalid account balance, it must be a positive number and less then 9999, please try again.");

                log.info("Account balance has failed validation: " + accountBalance);
            }

            flag = false;
        }

        BankAccount bankAccount = new BankAccount(accountNumber, accountBalance);
        BankAccountRepository.createBankAccount(bankAccount);

        log.info("Bank account has been created successfully: " + bankAccount.getAccountNumber() + " with account balance: " + bankAccount.getBalance());

        System.out.println("Account created successfully.\n\n");
    }

    @Override
    public void transferMoney() {
        int fromAccountNumber = -1;
        int toAccountNumber = -1;
        double amount = -1;
        boolean flag = true;
        while (flag) {
            System.out.print("Enter FROM account number: ");
            fromAccountNumber = scanner.nextInt();

            log.info("From account number has been entered: " + fromAccountNumber);

            System.out.print("Enter TO account number: ");
            toAccountNumber = scanner.nextInt();

            log.info("To account number has been entered: " + toAccountNumber);

            System.out.print("Enter amount: ");
            amount = scanner.nextDouble();

            log.info("Amount has been entered: " + amount);

            if (!BankAccountValidator.isValidateBankAccountsFroTransfer(fromAccountNumber, toAccountNumber, amount)) {
                System.out.println("Try again.");

                log.info("Validation fro transfer has failed.");

                continue;
            }

            flag = false;
        }
        BankAccountRepository.transferMoney(fromAccountNumber, toAccountNumber, amount);

        log.info("Bank account transfer has been successfully.");
    }

    @Override
    public void startApplication() {
        boolean flag = true;
        int option;
        while (flag) {
            System.out.println("1 -> create account");
            System.out.println("2 -> view balance");
            System.out.println("3 -> transfer money");
            System.out.println("4 -> exit");
            System.out.print("Enter your choice: ");
            option = scanner.nextInt();

            switch (option) {
                case 1 -> {
                    log.info("Create account option has been entered.");
                    createAccount();
                }
                case 2 -> {
                    log.info("View account option has been entered.");
                    viewBalance();
                }
                case 3 -> {
                    log.info("Transfer money option has been entered.");
                    transferMoney();
                }
                case 4 -> {
                    log.info("Exit option has been entered.");
                    flag = false;
                }

                default -> {
                    log.info("Invalid option has been entered.");
                    System.out.println("Invalid option, please try again.\n\n");
                }
            }
        }
    }
}
