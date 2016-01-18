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
    /*现金券收支明细*/
    public void findCashHistory(int page,int rows,CallBack callBack){
        List<NameValuePair> queryParams = new ArrayList<NameValuePair>();
        queryParams.add(new BasicNameValuePair("page", page + ""));
        queryParams.add(new BasicNameValuePair("rows", rows + ""));
        doPost(IFinancialUrl.CASH_HISTORY, queryParams, callBack);
    }
    /*抵扣券收支明细*/
    public void findDeductionHistory(int page,int rows,CallBack callBack){
        List<NameValuePair> queryParams = new ArrayList<NameValuePair>();
        queryParams.add(new BasicNameValuePair("page", page + ""));
        queryParams.add(new BasicNameValuePair("rows", rows + ""));
        doPost(IFinancialUrl.DEDUCTION_HISTORY, queryParams, callBack);
    }
    /*订单明细（现金券）*/
    public void findCashOrderDetail(String cashTOrderId,CallBack callBack){
        List<NameValuePair> queryParams = new ArrayList<>();
        queryParams.add(new BasicNameValuePair("cashTOrderId", cashTOrderId));
        doPost(IFinancialUrl.CASH_ORDER_DETAIL,queryParams,callBack);
    }
    /*订单明细（抵扣券）*/
    public void findDeductionOrderDetail(String deductionTOrderId,CallBack callBack){
        List<NameValuePair> queryParams = new ArrayList<>();
        queryParams.add(new BasicNameValuePair("deductionTOrderId", deductionTOrderId));
        doPost(IFinancialUrl.DEDUCTION_ORDER_DETAIL,queryParams,callBack);
    }
}
