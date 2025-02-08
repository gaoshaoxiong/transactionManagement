package com.gsx.transaction.service;

import com.gsx.transaction.entity.ReviseRecord;
import com.gsx.transaction.entity.Transaction;
import com.gsx.transaction.exception.ServiceException;
import com.gsx.transaction.util.pagehelper.PageInfoRet;

import java.util.List;

public interface TransactionService {

    Transaction addTransaction(Transaction transaction) throws ServiceException;

    Transaction modifyTransaction(String orderNo, Transaction transaction);

    String deleteTransaction(String orderNo);

    PageInfoRet listAllTransactions(String orderNo, Integer pageSize, Integer pageNumber);

    List<ReviseRecord> getReviseRecord(String orderNo);

    void clearAll();
}