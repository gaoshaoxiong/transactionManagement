package com.gsx.transaction.util.pagehelper;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
@Builder
public class PageInfoRet implements Serializable {

    private int total;

    private List rows;

    public PageInfoRet(int total, List list) {
        this.total = total;
        this.rows = list;
    }
}