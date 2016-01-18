package com.beyonditsm.financial.entity;

import java.util.List;

/**
 * 收支明细实体类
 * Created by Administrator on 2016/1/18.
 */
public class BalanceEntity {
    private int total;
    private List<RowsEntity> rows;
    public void setTotal(int total) {
        this.total = total;
    }
    public void setRows(List<RowsEntity> rows) {
        this.rows = rows;
    }
    public int getTotal() {
        return total;
    }
    public List<RowsEntity> getRows() {
        return rows;
    }
    public static class RowsEntity{

    }
}
