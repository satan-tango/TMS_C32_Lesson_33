package com.varkovich.lesson_33;

import com.varkovich.lesson_33.controller.BankAccountController;
import com.varkovich.lesson_33.controller.impl.BankAccountControllerImpl;

import com.varkovich.lesson_33.utils.Constants;
import lombok.extern.log4j.Log4j;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Log4j
public class ApplicationRunner {

    public static void main(String[] args) {
        PropertyConfigurator.configure(Constants.LOG_PROPERTY_FILE_PATH);
        BankAccountControllerImpl bankAccountController = new BankAccountControllerImpl();
        bankAccountController.startApplication();
    }
}
