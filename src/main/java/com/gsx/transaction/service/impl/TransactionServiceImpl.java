package com.gsx.transaction.service.impl;

import ch.qos.logback.core.util.StringUtil;
import com.gsx.transaction.entity.ReviseRecord;
import com.gsx.transaction.entity.Transaction;
import com.gsx.transaction.exception.ServiceException;
import com.gsx.transaction.repository.ReviseRecordRepository;
import com.gsx.transaction.repository.TransactionRepository;
import com.gsx.transaction.service.TransactionService;
import com.gsx.transaction.util.pagehelper.PageInfoRet;
import com.gsx.transaction.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ReviseRecordRepository reviseRecordRepository;

    @Override
    public Transaction addTransaction(Transaction transaction) throws ServiceException{
        if (StringUtil.isNullOrEmpty(transaction.getOrderNo())) {
            transaction.setOrderNo(UuidUtil.generate());
        } else {
            if(!UuidUtil.verify(transaction.getOrderNo())) {
                throw new ServiceException("Invalid transactions orderNo format!");
            } else if (transactionRepository.getTransactionByOrderNo(transaction.getOrderNo()) != null) {
                throw new ServiceException("Duplicate transaction!");
            }
        }
        transaction.setIsDel(0);
        transaction.setIsRevise(0);
        transaction.setCreateTime(LocalDateTime.now());
        transaction.setUpdateTime(LocalDateTime.now());
        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction modifyTransaction(String orderNo, Transaction transaction) {
        if (transactionRepository.getTransactionByOrderNo(transaction.getOrderNo()) == null) {
            throw new ServiceException("non-exist transactions orderNo!");
        }
        transaction.setUpdateTime(LocalDateTime.now());
        transaction.setIsRevise(1);
        transaction.setIsDel(0);
        // save revise record
        ReviseRecord reviseRecord = ReviseRecord.builder()
                .orderNo(transaction.getOrderNo())
                .amount(transaction.getAmount())
                .remark(transaction.getRemark())
                .time(transaction.getUpdateTime())
                .build();
        reviseRecordRepository.save(reviseRecord);
        return transactionRepository.modify(transaction);
    }

    @Override
    public String deleteTransaction(String orderNo) {
        Transaction transaction = transactionRepository.getTransactionByOrderNo(orderNo);
        if (transaction == null) {
            throw new ServiceException("non-exist transactions orderNo!");
        }
        transaction.setUpdateTime(LocalDateTime.now());
        transaction.setIsDel(1);
        transactionRepository.modify(transaction);
        return orderNo;
    }

    @Override
    public PageInfoRet listAllTransactions(String orderNo, Integer pageSize, Integer pageNumber) {
        return transactionRepository.findAllByPage(orderNo, pageSize, pageNumber);
    }

    @Override
    public List<ReviseRecord> getReviseRecord(String orderNo) {
        return reviseRecordRepository.findByOrderNo(orderNo);
    }

    @Override
    public void clearAll() {
        transactionRepository.clear();
    }
}