package com.gsx.transaction;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gsx.transaction.controller.TransactionController;
import com.gsx.transaction.entity.Transaction;
import com.gsx.transaction.exception.ServiceException;
import com.gsx.transaction.repository.TransactionRepository;
import com.gsx.transaction.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;


@SpringBootTest
public class TransactionControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private ObjectMapper objectMapper;

    private Transaction testTransaction;

    @Autowired
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        // *** 重点：standaloneSetup不能监听到GlobalExceptionHandle(全局异常处理器)，需要用webAppContextSetup打通上下文 ***
        // mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        // create test transaction
        testTransaction = new Transaction();
        testTransaction.setOrderNo("aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee");
        testTransaction.setType("DEPOSIT");
        testTransaction.setSubject("A");
        testTransaction.setObject("B");
        testTransaction.setAmount(BigDecimal.valueOf(100));
    }

    @Test
    void addTransaction_expectSuccess() throws Exception {
        mockMvc.perform(post("/transaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testTransaction)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderNo").exists());
        clear();
    }

    @Test
    void addTransaction_expectFail_duplicateOrderNo() throws Exception {
        mockMvc.perform(post("/transaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testTransaction)))
                .andExpect(status().isOk());
        mockMvc.perform(post("/transaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testTransaction)))
                .andExpect(status().is5xxServerError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ServiceException))
                .andExpect(result -> assertEquals("Duplicate transaction!", result.getResolvedException().getMessage()));
        clear();
    }

    @Test
    void addTransaction_expectFail_badRequest_enumInvalid() throws Exception {
        testTransaction.setType("Invalid enum value");
        mockMvc.perform(post("/transaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testTransaction)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains("Invalid transaction type enum value")));
    }

    @Test
    void addTransaction_expectFail_badRequest_amountInvalid() throws Exception {
        testTransaction.setAmount(new BigDecimal(-1));
        mockMvc.perform(post("/transaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testTransaction)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains("amount must be greater than 0.01")));
    }

    @Test
    void addTransaction_expectFail_badRequest_nonBlank() throws Exception {
        testTransaction.setSubject("");
        mockMvc.perform(post("/transaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testTransaction)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains("transaction subject is not blank")));
    }

    @Test
    void modifyTransaction_expectSuccess() throws Exception {
        mockMvc.perform(post("/transaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testTransaction)))
                .andExpect(status().isOk());
        testTransaction.setAmount(new BigDecimal(666));
        mockMvc.perform(put("/transaction/" + testTransaction.getOrderNo())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testTransaction)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(666));
        clear();
    }

    @Test
    void modifyTransaction_expectFail_notExist() throws Exception {
        mockMvc.perform(put("/transaction/" + testTransaction.getOrderNo())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(testTransaction)))
                    .andExpect(status().isInternalServerError())
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ServiceException))
                    .andExpect(result -> assertEquals("non-exist transactions orderNo!", result.getResolvedException().getMessage()));
    }

    @Test
    void deleteTransaction_expectSuccess() throws Exception {
        mockMvc.perform(post("/transaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testTransaction)))
                .andExpect(status().isOk());
        mockMvc.perform(delete("/transaction/" + testTransaction.getOrderNo())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteTransaction_expectFail_notExist() throws Exception {
        mockMvc.perform(delete("/transaction/" + testTransaction.getOrderNo())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ServiceException))
                .andExpect(result -> assertEquals("non-exist transactions orderNo!", result.getResolvedException().getMessage()));
    }

    @Test
    void queryTransaction_expectSuccess() throws Exception {
        testTransaction.setOrderNo("");
        for (int i=0; i<18; i++) {
            mockMvc.perform(post("/transaction")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(testTransaction)))
                    .andExpect(status().isOk());
        }
        mockMvc.perform(get("/transaction/list?rows=10&page=2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").value(18));
        clear();
    }

    private void clear() {
        transactionService.clearAll();
    }
}
