package com.gsx.transaction.repository;

import ch.qos.logback.core.util.StringUtil;
import com.gsx.transaction.entity.Transaction;
import com.gsx.transaction.util.pagehelper.PageInfoRet;
import com.gsx.transaction.util.pagehelper.Pagination;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class TransactionRepository {

    private final Map<String, Transaction> transactions = new ConcurrentHashMap<>();

    public Transaction save(Transaction transaction) {
        transactions.put(transaction.getOrderNo(), transaction);
        return transaction;
    }

    public Transaction getTransactionByOrderNo(String orderNo) {
        return transactions.get(orderNo);
    }

    public Transaction modify(Transaction transaction) {
        transactions.put(transaction.getOrderNo(), transaction);
        return transaction;
    }

    public void delete(String orderNo) {
        transactions.remove(orderNo);
    }

    public PageInfoRet findAllByPage(String orderNo, Integer pageSize, Integer pageNumber) {
        List<Transaction> transactionList = new ArrayList<>();
        int total = 0;
        if (StringUtil.notNullNorEmpty(orderNo)) {
            Transaction transaction = transactions.get(orderNo);
            if (transaction != null && transaction.getIsDel() == 0) {
                transactionList.add(transaction);
                total = 1;
            }
        } else {
            List<Transaction> aliveList = transactions.values().stream().filter(v -> v.getIsDel() == 0).collect(Collectors.toList());
            Pagination<Transaction> pagination = new Pagination(aliveList,pageSize, pageNumber);
            transactionList = pagination.getPage();
            total = aliveList.size();
        }
        return PageInfoRet.builder().rows(transactionList).total(total).build();
    }

    public void clear() {
        transactions.clear();
    }
}