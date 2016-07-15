package com.beyonditsm.financial;

import com.beyonditsm.financial.entity.FriendBean;
import com.beyonditsm.financial.entity.MessageBean;

/**
 * 全局常量
 * Created by wangbin on 15/11/14.
 */
public class ConstantValue {

    /**
     * &#x6b63;&#x5f0f;&#x4e0a;&#x7ebf;&#x540e;&#xff0c;&#x5173;&#x95ed;log&#xff0c;&#x6539;&#x4e3a;false
     */
    public final static boolean IS_SHOW_DEBUG = true;

    public static int STEP = 0;

    public static String ENCODING = "UTF-8";

    public static String ORDER = "order";

    public static int CREDIT_MONEY = 5;//金额
    public static int CREDIT_MONTH = 6;//周期

    /**
     * 数据库版本
     */
    public final static int VERSION = 3;
    public final static Class<?>[] MODELS = {MessageBean.class, FriendBean.class};
    public final static String DB_NAME = "jinzhu_.db";
}
