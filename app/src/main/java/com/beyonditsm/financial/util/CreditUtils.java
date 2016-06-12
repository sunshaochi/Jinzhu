package com.beyonditsm.financial.util;

/**
 * 贷款
 * Created by wangbin on 15/12/2.
 */
public class CreditUtils {
    /**
     *
     * @param total 贷款总金额
     * @param monthRate 月利率
     * @return 利息
     */

    public static String calculateCredit(double total,double monthRate,double creditTime){
        java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");
        String credit= df.format(total * monthRate * creditTime);
        return credit;
    }

    /**
     * 月供
     * @param total
     * @param monthRate
     * @param creditTime
     * @return
     */
    public static String getPayMents(double total,double monthRate,double creditTime){
        java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");
        double payMents= (total+total * monthRate * creditTime)/creditTime;
        return df.format(payMents);
    }
}
