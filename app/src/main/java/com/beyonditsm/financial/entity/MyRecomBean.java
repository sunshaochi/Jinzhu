package com.beyonditsm.financial.entity;

import java.util.List;

/**
 * Created by Yang on 2015/11/30 0030.
 */
public class MyRecomBean {

    /**
     * total : 2
     * rows : [{"userName":"15800701713","mobile":"13092324819"}]
     */

    private int total;
    /**
     * userName : 15800701713
     * mobile : 13092324819
     */

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

    public static class RowsEntity {
        private String userName;
        private String mobile;

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getUserName() {
            return userName;
        }

        public String getMobile() {
            return mobile;
        }
    }
}
