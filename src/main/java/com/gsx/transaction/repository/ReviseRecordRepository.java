package com.gsx.transaction.repository;

import com.gsx.transaction.entity.ReviseRecord;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ReviseRecordRepository {

    private final Map<String, List<ReviseRecord>> reviseRecords = new ConcurrentHashMap<>();

    public void save(ReviseRecord reviseRecord) {
        if (reviseRecords.containsKey(reviseRecord.getOrderNo())) {
            reviseRecords.get(reviseRecord.getOrderNo()).add(reviseRecord);
        } else {
            List<ReviseRecord> recordList = new ArrayList<>();
            recordList.add(reviseRecord);
            reviseRecords.put(reviseRecord.getOrderNo(), recordList);
        }
    }

    public List<ReviseRecord> findByOrderNo(String orderNo) {
        return reviseRecords.get(orderNo);
    }
}