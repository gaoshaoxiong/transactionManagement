package com.gsx.transaction.entity;

import com.gsx.transaction.annotation.ValidEnum;
import com.gsx.transaction.enmus.TransactionType;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Transaction {

    private static final long serialVersionUID = 1L;

    /**
     * transaction orderNo
     */
    private String orderNo;


    /**
     * transaction type (DEPOSIT /  WITHDRAW / TRANSFER)
     */
    @ValidEnum(enumClass = TransactionType.class, message = "Invalid transaction type enum value")
    private String type;

    /**
     * transaction subject
     */
    @NotBlank(message = "transaction subject is not blank")
    private String subject;

    /**
     * transaction object
     */
    @NotBlank(message = "transaction object is not blank")
    private String object;

    /**
     * transaction amount
     */
    @DecimalMin(value = "0.01", message = "amount must be greater than 0.01")
    @DecimalMax(value = "1000000.00", message = "amount cannot exceed 1000000.00")
    private BigDecimal amount;

    /**
     * transaction create time
     */
    private LocalDateTime createTime;

    /**
     * transaction update time
     */
    private LocalDateTime updateTime;

    /**
     * transaction remark
     */
    private String remark;

    /**
     * is delete? (0:no  1:yes)
     */
    private Integer isDel;

    /**
     * is revise? (0:no  1:yes)
     */
    private Integer isRevise;
}