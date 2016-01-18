package com.beyonditsm.financial.http;

import com.beyonditsm.financial.entity.OrderBean;
import com.beyonditsm.financial.util.GsonUtils;
import com.beyonditsm.financial.util.ParamsUtil;

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
     * 提交抵扣券兑现订单
     * @param orderBean 订单实体
     * @param fundPassword 资金密码
     * @param callBack
     */
    public void submitDeductionTOrder(OrderBean orderBean,String fundPassword,CallBack callBack){
        List<NameValuePair> queryParams= ParamsUtil.getInstance().BeanToForm(GsonUtils.bean2Json(orderBean));
        queryParams.add(new BasicNameValuePair("fundPassword",fundPassword));
        doPost(IFinancialUrl.SUBMIT_DEDUCTION_ORDER,queryParams,callBack);
    }

    public void submitCashTOrder(OrderBean orderBean,String fundPassword,CallBack callBack){
        List<NameValuePair> queryParams= ParamsUtil.getInstance().BeanToForm(GsonUtils.bean2Json(orderBean));
        queryParams.add(new BasicNameValuePair("fundPassword",fundPassword));
        doPost(IFinancialUrl.SUBMIT_Cash_ORDER,queryParams,callBack);
    }
}
