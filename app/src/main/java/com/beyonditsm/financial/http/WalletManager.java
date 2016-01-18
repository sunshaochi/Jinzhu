package com.beyonditsm.financial.http;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * 钱包manager
 * Created by wangbin on 16/1/18.
 */
public class WalletManager extends RequestManager{

    /**
     * 设置资金密码
     * @param userPassword 用户登录密码
     * @param fundPassword 资金密码
     * @param callBack
     */
    public void setFunPwd(String userPassword,String fundPassword,CallBack callBack){
        List<NameValuePair> queryParams = new ArrayList<NameValuePair>();
        queryParams.add(new BasicNameValuePair("userPassword", userPassword));
        queryParams.add(new BasicNameValuePair("fundPassword", fundPassword ));
        doPost(IFinancialUrl.SET_PWD_URL, queryParams, callBack);
    }
}
