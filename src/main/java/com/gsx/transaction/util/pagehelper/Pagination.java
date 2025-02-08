package com.gsx.transaction.util.pagehelper;

import java.util.List;

public class Pagination<T> {

    private List<T> dataList;
    private int pageSize;
    private int pageNumber;

    public Pagination(List<T> t, int pageSize, int pageNumber) {
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
        this.dataList = t;
    }

    public List<T> getPage() {
        if (pageNumber < 1 ) {
            throw new IllegalArgumentException("Invalid page number");
        }
        int fromIndex = (pageNumber - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, dataList.size());
        return dataList.subList(fromIndex, toIndex);
    }

}