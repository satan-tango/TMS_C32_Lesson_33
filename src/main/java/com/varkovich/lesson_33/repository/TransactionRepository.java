package com.varkovich.lesson_33.repository;

import com.varkovich.lesson_33.model.Transaction;
import com.varkovich.lesson_33.utils.SQLQuery;
import lombok.extern.log4j.Log4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

@Log4j
public class TransactionRepository {

    public static void recordTransaction(Transaction transaction, Connection connection) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.LOG_NEW_TRANSACTION);
            preparedStatement.setInt(1, transaction.getFromAccount());
            preparedStatement.setInt(2, transaction.getToAccount());
            preparedStatement.setDouble(3, transaction.getAmount());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(transaction.getTimeStamp()));

            preparedStatement.executeUpdate();
            log.info("New transaction added to the database");
        } catch (SQLException e) {
            log.error("Failed to record transaction", e);
        }
    }
}
