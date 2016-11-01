package com.beyonditsm.financial.entity;

import java.util.List;

/**
 * Created by xuleyuan on 2016/11/1.
 */

public class AreaPanelEntity {

    private List<String> firChars;
    /**
     * pName : 安徽
     * firChar : A
     * citys : ["合肥","亳州","马鞍山","宿州","安庆","六安","淮南","阜阳","宣城","铜陵","巢湖","蚌埠","滁州","芜湖","池州","淮北","黄山"]
     */

    private List<PrvnsBean> prvns;
    private List<String> allCitys;
    private List<String> hotCitys;

    public List<String> getFirChars() {
        return firChars;
    }

    public void setFirChars(List<String> firChars) {
        this.firChars = firChars;
    }

    public List<PrvnsBean> getPrvns() {
        return prvns;
    }

    public void setPrvns(List<PrvnsBean> prvns) {
        this.prvns = prvns;
    }

    public List<String> getAllCitys() {
        return allCitys;
    }

    public void setAllCitys(List<String> allCitys) {
        this.allCitys = allCitys;
    }

    public List<String> getHotCitys() {
        return hotCitys;
    }

    public void setHotCitys(List<String> hotCitys) {
        this.hotCitys = hotCitys;
    }

    public static class PrvnsBean {
        private String pName;
        private String firChar;
        private List<String> citys;

        public String getPName() {
            return pName;
        }

        public void setPName(String pName) {
            this.pName = pName;
        }

        public String getFirChar() {
            return firChar;
        }

        public void setFirChar(String firChar) {
            this.firChar = firChar;
        }

        public List<String> getCitys() {
            return citys;
        }

        public void setCitys(List<String> citys) {
            this.citys = citys;
        }
    }
}
