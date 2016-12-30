package com.beyonditsm.financial.util;

/**
 * Created by bitch-1 on 2016/12/28.
 */

public class ProductStatuUtil {

    /**急速贷产品类型
     * @param statu
     * @return
     */
    public static String getProStatu(String statu){
        String text=null;
        if(statu.equals("1")){
            text="月";
            return text;
        }else if(statu.equals("2")){
            text="周";
            return text;
        }else if(statu.equals("3")){
            text="日";
            return text;
        }

        return text;
    }

    /**
     * 急速贷利率类型 1为日利率，2为月利率
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
