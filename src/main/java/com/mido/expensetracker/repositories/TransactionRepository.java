package com.mido.expensetracker.repositories;

import java.util.List;

import com.mido.expensetracker.entity.Transaction;
import com.mido.expensetracker.exception.EtBadRequestException;
import com.mido.expensetracker.exception.EtResourceNotFoundException;

public interface TransactionRepository {

	List<Transaction> findAll(Integer userId, Integer categoryId);

    Transaction findById(Integer userId, Integer categoryId, Integer transactionId) throws EtResourceNotFoundException;

    Integer create(Integer userId, Integer categoryId, Double amount, String note, Long transactionDate) throws EtBadRequestException;

    void update(Integer userId, Integer categoryId, Integer transactionId, Transaction transaction) throws EtBadRequestException;

    void removeById(Integer userId, Integer categoryId, Integer transactionId) throws EtResourceNotFoundException;
}
