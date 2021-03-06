package com.beyonditsm.financial.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by wangbin on 15/11/19.
 */
public class SpUtils {

    private static final String SP_NAME = "sp_name";
    private static final String FINANCIAL_SP = "financial_sp";
    private final static String ISFIRST = "jinzhu_first";

    private static final String COOKIE = "cookie";

    private static final String ROLE_NAME = "role_name";//登录权限
    private static final String TOKEN = "mytoken";//融云token
    private static final String USERID = "userid";//用户id

    private static final String NEW_MESSAGE = "new_message";//接收新消息
    private static final String IGNORE_MESSAGE = "ignore_message";//消息免打扰

    private static final String ORDERID = "orderId";
    private static final String SP_ORDERNAME = "sp_ordername";

    private static final String ISCOMMIT = "iscommit";
    private static final String ISCOMMITORDERID = "iscommitorderid";
    private static final String SP_ISCOMMIT = "sp_iscommit";
    private static final String ISUPGRADE = "is_upgrade";
    private static final String ISRECEIVE = "is_receive";
    private static final String GETPERMISSION = "get_permission";
    private static final String CITY = "user_city"; //定位城市
    private static  final String PHONENUMBER="phoneNum";
    private static final String PROFILEID="profileId";//个人信息id
    private static final String ADDRESSID="addressId";//用户地址id
    private static final String USERNAME="addressId";//用户地址id

    public SpUtils() {

    }

    public static SharedPreferences getSp(Context context) {
        return context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPreferences getOrdeIdSp(Context context) {
        return context.getSharedPreferences(SP_ORDERNAME, Context.MODE_PRIVATE);
    }

    public static SharedPreferences getFinancialSp(Context context) {
        return context.getSharedPreferences(FINANCIAL_SP, Context.MODE_PRIVATE);
    }
    public static SharedPreferences getCitySp(Context context) {
        return context.getSharedPreferences(CITY, Context.MODE_PRIVATE);
    }
    public static SharedPreferences getIsCommitSp(Context context) {
        return context.getSharedPreferences(SP_ISCOMMIT, Context.MODE_PRIVATE);
    }

    public static SharedPreferences getIsUpgradeSp(Context context) {
        return context.getSharedPreferences(ISUPGRADE, Context.MODE_PRIVATE);
    }

    public static SharedPreferences getIsReceiveSp(Context context) {
        return context.getSharedPreferences(ISRECEIVE, Context.MODE_PRIVATE);
    }

    public static SharedPreferences getPermissionSp(Context context) {
        return context.getSharedPreferences(GETPERMISSION, Context.MODE_PRIVATE);
    }

    public static SharedPreferences getRoleNameSp(Context context) {
        return context.getSharedPreferences(ROLE_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPreferences getPhoneNumSp(Context context){
        return context.getSharedPreferences(PHONENUMBER,Context.MODE_PRIVATE);
    }
    public static SharedPreferences getProfileId(Context context){
        return context.getSharedPreferences(PROFILEID,Context.MODE_PRIVATE);
    }
    public static SharedPreferences getAddressId(Context context){
        return context.getSharedPreferences(ADDRESSID,Context.MODE_PRIVATE);
    }
    public static SharedPreferences getUserName(Context context){
        return context.getSharedPreferences(USERNAME,Context.MODE_PRIVATE);

    }
    /**
     * 定位的城市名称
     * @param context
     * @param city
     */
    public static void setCity(Context context, String city) {
        getCitySp(context).edit().putString(CITY, city).commit();
    }

    public static String getCity(Context context) {
        return getCitySp(context).getString(CITY, "");
    }

    /**
     * 定位的城市名称
     * @param context
     * @param city
     */
    public static void setUsername(Context context, String city) {
        getUserName(context).edit().putString(USERNAME, city).commit();
    }

    public static String getUsername(Context context) {
        return getUserName(context).getString(USERNAME, "");
    }



    /**
     * 获取手机号码
     * @param context
     * @param phone
     */
    public static void setPhonenumber(Context context,String phone){
        getPhoneNumSp(context).edit().putString(PHONENUMBER,phone).commit();
    }
    public static String getPhonenumber(Context context){
        return getPhoneNumSp(context).getString(PHONENUMBER,"");
    }

    /**
     * 获取返回用户id
     * @param context
     * @param phone
     */
    public static void setProfileid(Context context,String phone){
        getProfileId(context).edit().putString(PROFILEID,phone).commit();
    }
    public static String getProfileid(Context context){
        return getPhoneNumSp(context).getString(PROFILEID,"");
    }
    /**
     * 获取用户地址id
     * @param context
     * @param phone
     */
    public static void setAddressid(Context context,String phone){
        getAddressId(context).edit().putString(ADDRESSID,phone).commit();
    }
    public static String getAddressid(Context context){
        return getAddressId(context).getString(ADDRESSID,"");
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
     *
     * @param context
     * @param cookie
     */
    public static void setCooike(Context context, String cookie) {
        getSp(context).edit().putString(COOKIE, cookie).commit();
    }

    /**
     * 获取cookie
     *
     * @param context
     * @return
     */
    public static String getCookie(Context context) {
        return getSp(context).getString(COOKIE, "");
    }

    public static void setRoleName(Context context, String role_name) {
        getSp(context).edit().putString(ROLE_NAME, role_name).commit();
    }

    public static String getRoleName(Context context) {
        return getSp(context).getString(ROLE_NAME, "");
    }


    public static void setToken(Context context, String token) {
        getSp(context).edit().putString(TOKEN, token).commit();
    }

    public static String getToken(Context context) {
        return getSp(context).getString(TOKEN, "");
    }

    public static void setUserId(Context context, String token) {
        getSp(context).edit().putString(USERID, token).commit();
    }

    public static String getUserId(Context context) {
        return getSp(context).getString(USERID, "");
    }

    /**
     * 清除
     *
     * @param context
     */
    public static void clearSp(Context context) {
        getSp(context).edit().clear().commit();
    }

    public static void clearRoleName(Context context) {
        getRoleNameSp(context).edit().clear().commit();
    }

    public static void clearUserName(Context context){
        getUserName(context).edit().clear().commit();
    }

    /**
     * 保存orderId
     *
     * @param context
     * @param orderId
     */
    public static void setOrderId(Context context, String orderId) {
        getOrdeIdSp(context).edit().putString(ORDERID, orderId).commit();
    }

    /**
     * 获取orderId
     *
     * @param context
     * @return
     */
    public static String getOrderId(Context context) {
        return getOrdeIdSp(context).getString(ORDERID, "");
    }

    public static void clearOrderId(Context context) {
        getOrdeIdSp(context).edit().clear().commit();
    }

    /**
     * 设置是否点击提交
     *
     * @param context
     * @param isCommit
     */
    public static void setIsCommit(Context context, boolean isCommit, String orderId) {
        getIsCommitSp(context).edit().putBoolean(ISCOMMIT, isCommit).commit();
        getIsCommitSp(context).edit().putString(ISCOMMITORDERID, orderId).commit();
    }

    public static boolean getIsCommit(Context context) {
        return getIsCommitSp(context).getBoolean(ISCOMMIT, true);
    }

    public static String getIsCommitOrderId(Context context) {
        return getIsCommitSp(context).getString(ISCOMMITORDERID, "");
    }

    public static void clearIsCommit(Context context) {
        getIsCommitSp(context).edit().clear().commit();
    }

    //保存是否升级代言人
    public static void setIsUpgrade(Context context, String isUpgrade) {
        getIsUpgradeSp(context).edit().putString(ISUPGRADE, isUpgrade).commit();
    }

    public static String getIsUpgrade(Context context) {
        return getIsUpgradeSp(context).getString(ISUPGRADE, "");
    }

    public static void clearIsUpgrade(Context context) {
        getIsUpgradeSp(context).edit().clear().commit();
    }

    //保存领取奖励的推送
    public static void setReceiveReward(Context context, String isReceive) {
        getIsReceiveSp(context).edit().putString(ISRECEIVE, isReceive).commit();
    }

    public static String getReceiveReward(Context context) {
        return getIsReceiveSp(context).getString(ISRECEIVE, "");
    }

    public static void clearReceiveReward(Context context) {
        getIsReceiveSp(context).edit().clear().commit();
    }

    public static void setISpermission(Context context, boolean isGetPermission) {
        getPermissionSp(context).edit().putBoolean(GETPERMISSION, isGetPermission).commit();
    }

    public static boolean getIsPermission(Context context) {
        return getPermissionSp(context).getBoolean(GETPERMISSION, false);
    }
}
