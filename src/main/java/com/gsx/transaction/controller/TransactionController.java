package com.gsx.transaction.controller;

import com.gsx.transaction.entity.ReviseRecord;
import com.gsx.transaction.entity.Transaction;
import com.gsx.transaction.exception.ServiceException;
import com.gsx.transaction.service.TransactionService;
import com.gsx.transaction.util.pagehelper.PageInfoRet;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
@Tag(name = "Transaction Management", description = "Transaction management APIs")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @Operation(summary = "add a new transaction")
    @PostMapping
    public ResponseEntity<Transaction> addTransaction(@Validated @RequestBody Transaction transaction) throws ServiceException {
        return ResponseEntity.ok(transactionService.addTransaction(transaction));
    }

    @Operation(summary = "Modify an existing transaction")
    @PutMapping("/{orderNo}")
    public ResponseEntity<Transaction> modifyTransaction(@PathVariable String orderNo, @RequestBody Transaction transaction) {
        return ResponseEntity.ok(transactionService.modifyTransaction(orderNo, transaction));
    }

    @Operation(summary = "Delete a transaction by orderNo")
    @DeleteMapping("/{orderNo}")
    public ResponseEntity<String> deleteTransaction(@PathVariable String orderNo) {
        return ResponseEntity.ok(transactionService.deleteTransaction(orderNo));
    }

    @Operation(summary = "query transactions list by conditions")
    @GetMapping("/list")
    public ResponseEntity<PageInfoRet> listAllTransactions(@RequestParam(required = false) String orderNo,
                                                           @RequestParam(defaultValue = "10") Integer rows,
                                                           @RequestParam(defaultValue = "1") Integer page) {
        return ResponseEntity.ok(transactionService.listAllTransactions(orderNo, rows, page));
    }

    @Operation(summary = "query transactions revise record")
    @GetMapping("/revise/record")
    public ResponseEntity<List<ReviseRecord>> getReviseRecord(@RequestParam String orderNo) {
        return ResponseEntity.ok(transactionService.getReviseRecord(orderNo));
    }
}