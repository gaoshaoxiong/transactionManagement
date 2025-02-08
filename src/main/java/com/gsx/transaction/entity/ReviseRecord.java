package com.gsx.transaction.entity;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class ReviseRecord {

    private static final long serialVersionUID = 1L;

    /**
     * transaction orderNo
     */
    private String orderNo;

    /**
     * transaction amount
     */
    private BigDecimal amount;

    /**
     * transaction time
     */
    private LocalDateTime time;

    /**
     * transaction revise remark
     */
    private String remark;
}