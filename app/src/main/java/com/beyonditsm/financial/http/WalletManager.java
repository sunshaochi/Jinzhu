package com.beyonditsm.financial.http;

import com.beyonditsm.financial.entity.AddBankCardEntity;
import com.beyonditsm.financial.entity.OrderBean;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 钱包manager
 * Created by wangbin on 16/1/18.
 */
public class WalletManager extends RequestManager{

    /**
     * 提交抵扣券兑现订单
     * @param orderBean 订单实体
     * @param fundPassword 资金密码
     * @param callBack
     */
    public void submitDeductionTOrder(OrderBean orderBean,String fundPassword,CallBack callBack){
//        Map<String,String> params=new HashMap<String,String>();
//        params.put("bankCardNo", orderBean.getBankCardNo());
//        params.put("bankName", orderBean.getBankName());
//        params.put("cashOutAmount", orderBean.getCashOutAmount()+"");
//        params.put("userName", orderBean.getUserName());
//        params.put("orderId", orderBean.getOrderId());
//        params.put("fundPassword", fundPassword);
        List<NameValuePair> queryParams = new ArrayList<NameValuePair>();
        queryParams.add(new BasicNameValuePair("bankCardNo",orderBean.getBankCardNo()));
        queryParams.add(new BasicNameValuePair("bankName",orderBean.getBankName()));
        queryParams.add(new BasicNameValuePair("cashOutAmount",orderBean.getCashOutAmount()+""));
        queryParams.add(new BasicNameValuePair("userName",orderBean.getUserName()));
        queryParams.add(new BasicNameValuePair("orderId",orderBean.getOrderId()));
        queryParams.add(new BasicNameValuePair("fundPassword",fundPassword));
        queryParams.add(new BasicNameValuePair("depositBank",orderBean.getDepositBank()));
        doPost(IFinancialUrl.SUBMIT_DEDUCTION_ORDER,queryParams,callBack);
    }
    /**
     * 提交现金券兑现订单
     * @param orderBean 订单实体
     * @param fundPassword 资金密码
     * @param callBack
     */
    public void submitCashTOrder(OrderBean orderBean,String fundPassword,CallBack callBack) {
        List<NameValuePair> queryParams = new ArrayList<NameValuePair>();
        queryParams.add(new BasicNameValuePair("bankCardNo",orderBean.getBankCardNo()));
        queryParams.add(new BasicNameValuePair("bankName",orderBean.getBankName()));
        queryParams.add(new BasicNameValuePair("cashOutAmount",orderBean.getCashOutAmount()+""));
        queryParams.add(new BasicNameValuePair("userName",orderBean.getUserName()));
        queryParams.add(new BasicNameValuePair("fundPassword", fundPassword));
        queryParams.add(new BasicNameValuePair("depositBank",orderBean.getDepositBank()));
        doPost(IFinancialUrl.SUBMIT_Cash_ORDER, queryParams, callBack);
    }
    /** 设置资金密码
     * @param userPassword 用户登录密码
     * @param fundPassword 资金密码
     * @param callBack
     */
    public void setFunPwd(String userPassword,String fundPassword,CallBack callBack) {
        List<NameValuePair> queryParams = new ArrayList<NameValuePair>();
        queryParams.add(new BasicNameValuePair("userPassword", userPassword));
        queryParams.add(new BasicNameValuePair("fundPassword", fundPassword));
        doPost(IFinancialUrl.SET_PWD_URL, queryParams, callBack);
    }
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
    /*订单明细列表*/
    public void findOrderList(int page,int rows,CallBack callBack){
        List<NameValuePair> queryParams = new ArrayList<NameValuePair>();
        queryParams.add(new BasicNameValuePair("page", page + ""));
        queryParams.add(new BasicNameValuePair("rows", rows + ""));
        doPost(IFinancialUrl.ORDER_LIST, queryParams, callBack);
    }

    /**
     * 通过username查询订单编号集合
     * @param callBack
     */
    public void findOrderNoListByUserName(CallBack callBack){
        Map<String,String> params=new HashMap<String,String>();
        doPost(IFinancialUrl.ORDER_NO_LIST,params,callBack);
    }

    /**
     * 通过订单编号查看总利息和可抵扣利息
     * @param orderNo
     * @param callBack
     */
    public void findInterestByOrderNo(String orderNo,CallBack callBack){
        List<NameValuePair> queryParams=new ArrayList<NameValuePair>();
        queryParams.add(new BasicNameValuePair("orderNo",orderNo));
        doPost(IFinancialUrl.LIXI,queryParams,callBack);
    }

    /**
     * 查询用户已绑定的银行卡
     * @param callBack
     */
    public void findBankCard(CallBack callBack){
        List<NameValuePair> queryParams=new ArrayList<NameValuePair>();
        doPost(IFinancialUrl.QUERY_BANK_CARD,queryParams,callBack);
    }

    /**
     * 添加银行卡
     * @param abce
     * @param callBack
     */
    public void addBankCard(AddBankCardEntity abce,CallBack callBack){
        List<NameValuePair> queryParams=new ArrayList<NameValuePair>();
        queryParams.add(new BasicNameValuePair("cardNo",abce.getCardNo()));
        queryParams.add(new BasicNameValuePair("bankName",abce.getBankName()));
        queryParams.add(new BasicNameValuePair("branchBankName",abce.getBranchBankName()));
        queryParams.add(new BasicNameValuePair("fundPassword",abce.getFundPassword()));
        queryParams.add(new BasicNameValuePair("accountName",abce.getAccountName()));
        doPost(IFinancialUrl.ADD_BANK_CARD, queryParams, callBack);
    }

    /**
     * 修改银行卡状态
     * @param cardNo
     * @param status
     * @param callBack
     */
    public void modifyBankCard(String cardNo,int status,CallBack callBack){
        List<NameValuePair> queryParams=new ArrayList<NameValuePair>();
        queryParams.add(new BasicNameValuePair("cardNo",cardNo));
        queryParams.add(new BasicNameValuePair("status",status+""));
        doPost(IFinancialUrl.MODIFY_BANK_CARD_STATUS,queryParams,callBack);
    }

    public void getBank(CallBack callBack){
        List<NameValuePair> queryParams=new ArrayList<NameValuePair>();
        doPost(IFinancialUrl.QUERY_BANK,queryParams,callBack);
    }
}
