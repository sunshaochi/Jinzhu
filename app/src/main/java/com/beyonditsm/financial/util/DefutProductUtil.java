package com.beyonditsm.financial.util;

/**贷款期限类型(1.年2.月3.周4.日5.期数)
 * 大额贷和大额贷的贷款详情里面的产品类型，月率类型
 * Created by bitch-1 on 2016/12/29.
 */

public class DefutProductUtil {

    /**大额贷类型
     * @param statu
     * @return
     */
    public static String getProStatu(String statu){
        String text=null;
        if(statu.equals("1")){
            text="年";
            return text;
        }else if(statu.equals("2")){
            text="个月";
            return text;
        }else if(statu.equals("3")){
            text="周";
            return text;
        } else if(statu.equals("4")){
            text="日";
            return text;
        }else if(statu.equals("5")) {
            text="期数";
            return text;
        }
        return text;
    }

    /**
     * 利率类型 1为日利率，2为月利率
     * @param statu
     * @return
     */
    public static String getRoxstatu(String statu){
        String text=null;
        if(statu.equals("1")){
            text="日";
            return text;
        }else if(statu.equals("2")){
            text="月";
            return text;
        }
        return text;
    }
}
