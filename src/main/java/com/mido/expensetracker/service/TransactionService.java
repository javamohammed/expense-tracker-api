package com.mido.expensetracker.service;

import java.util.List;

import com.mido.expensetracker.entity.Transaction;
import com.mido.expensetracker.exception.EtBadRequestException;
import com.mido.expensetracker.exception.EtResourceNotFoundException;

public interface  TransactionService {

	 List<Transaction> fetchAllTransactions(Integer userId, Integer categoryId);

    Transaction fetchTransactionById(Integer userId, Integer categoryId, Integer transactionId) throws EtResourceNotFoundException;

    Transaction addTransaction(Integer userId, Integer categoryId, Double amount, String note, Long transactionDate) throws EtBadRequestException;

    void updateTransaction(Integer userId, Integer categoryId, Integer transactionId, Transaction transaction) throws EtBadRequestException;

    void removeTransaction(Integer userId, Integer categoryId, Integer transactionId) throws EtResourceNotFoundException;
}
