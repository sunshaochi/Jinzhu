package com.beyonditsm.financial.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by wangbin on 15/11/19.
 */
public class SpUtils {

    private static final String SP_NAME="sp_name";
    private static final String FINANCIAL_SP="financial_sp";
    private final static String ISFIRST = "jinzhu_first";

    private static final String COOKIE="cookie";

    private static final String ROLE_NAME="role_name    ";//登录权限
    private static final String TOKEN="mytoken";//融云token
    private static final String USERID="userid";//用户id

    private static final String NEW_MESSAGE="new_message";//接收新消息
    private static final String IGNORE_MESSAGE="ignore_message";//消息免打扰

    public SpUtils(){

    }

    public static SharedPreferences getSp(Context context){
        return context.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE);
    }

    public static SharedPreferences getFinancialSp(Context context){
        return context.getSharedPreferences(FINANCIAL_SP,Context.MODE_PRIVATE);
    }

    /**
     * 是否第一次进入app
     *
     * @param context
     * @param isFirst
     */
    public static void setIsFirst(Context context, boolean isFirst) {
        getFinancialSp(context).edit().putBoolean(ISFIRST, isFirst).commit();
    }

    public static boolean getIsFirst(Context context) {
        return getFinancialSp(context).getBoolean(ISFIRST, true);
    }

    /**
     * 消息提醒
     *
     * @param context
     * @param isFirst
     */
    public static void setMsg(Context context, boolean isFirst) {
        getFinancialSp(context).edit().putBoolean(NEW_MESSAGE, isFirst).commit();
    }

    public static boolean getMsg(Context context) {
        return getFinancialSp(context).getBoolean(NEW_MESSAGE, true);
    }

    /**
     * 免打扰
     *
     * @param context
     * @param isFirst
     */
    public static void setSleep(Context context, boolean isFirst) {
        getFinancialSp(context).edit().putBoolean(IGNORE_MESSAGE, isFirst).commit();
    }

    public static boolean getSleep(Context context) {
        return getFinancialSp(context).getBoolean(IGNORE_MESSAGE, false);
    }

    /**
     * 保存cookie
     * @param context
     * @param cookie
     */
    public static void setCooike(Context context,String cookie){
        getSp(context).edit().putString(COOKIE,cookie).commit();
    }

    /**
     * 获取cookie
     * @param context
     * @return
     */
    public static String getCookie(Context context){
        return getSp(context).getString(COOKIE, "");
    }

    public static void setRoleName(Context context,String role_name){
        getSp(context).edit().putString(ROLE_NAME,role_name).commit();
    }

    public static String getRoleName(Context context){
        return getSp(context).getString(ROLE_NAME,"");
    }


    public static void setToken(Context context,String token){
        getSp(context).edit().putString(TOKEN,token).commit();
    }

    public static String getToken(Context context){
        return getSp(context).getString(TOKEN,"");
    }

    public static void setUserId(Context context,String token){
        getSp(context).edit().putString(USERID,token).commit();
    }

    public static String getUserId(Context context){
        return getSp(context).getString(USERID,"");
    }

    /**
     * 清除
     * @param context
     */
    public static void clearSp(Context context){
        getSp(context).edit().clear().commit();
    }

}
