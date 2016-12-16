package com.beyonditsm.financial.http;

import android.text.TextUtils;

import com.beyonditsm.financial.MyApplication;
import com.beyonditsm.financial.util.SpUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户接口
 * Created by wangbin on 15/11/23.
 */
public class UserManager extends RequestManager {


    /**
     * 计算月供
     *
     * @param callBack 回调
     */
    public void getMonthPay(String repaymentMoney, String rate, String month, CallBack callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("repaymentMoney", repaymentMoney);
        params.put("month", month);
        params.put("rate", rate);
        doPost(IFinancialUrl.MONTH_PAY_URL, params, callBack);
    }

    /**
     * 查询热门产品列表
     *
     * @param
     * @param callBack 回调
     */
    public void findHotProductList(String page,String rows, CallBack callBack) {

        String cityName;
//        Map<String, String> params = new HashMap<>();
//        params.put("page", hp.getPage() + "");
//        params.put("rows", hp.getRows() + "");
        if (!TextUtils.isEmpty(SpUtils.getCity(MyApplication.getInstance().getApplicationContext()))) {
           cityName = SpUtils.getCity(MyApplication.getInstance().getApplicationContext());
//            params.put("cityName", SpUtils.getCity(MyApplication.getInstance().getApplicationContext()));
        } else {
//            params.put("cityName", "全国");
        cityName="全国";
        }

        doGet(IFinancialUrl.FIND_HOT_PRODUCT_LIST+"?cityName="+cityName+"&page="+page+"&rows="+rows , callBack);
    }


    /**
     * 查看当前登陆人的信息
     *
     * @param callBack 回调
     */
    public void findUserLoginInfo(CallBack callBack) {
        Map<String, String> params = new HashMap<String, String>();
//        List<NameValuePair> params = new ArrayList<>();
        doPost(IFinancialUrl.USER_LOGIN_URL, params, callBack);
    }

    public void findOrderDealHistory(String orderId, CallBack callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("orderId", orderId);
        doPost(IFinancialUrl.FIND_ORDER_DEAL_HISTORY, params, callBack);
    }

    //    public void findOrderDetailById(String productId, CallBack callBack) {
//        Map<String, String> params = new HashMap<>();
//        params.put("productId", productId);
//        doPost(IFinancialUrl.FIND_ORDER_DETAIL, params, callBack);
//    }
    //通过产品id获取产品详情
    public void findOrderDetailById(String productId, CallBack callBack) {
//    Map<String, String> params = new HashMap<>();
//    params.put("productId", productId);
        doGet(IFinancialUrl.FIND_ORDER_DETAIL + "?productId=" + productId, callBack);
    }

    /**
     * 更改订单状态
     *
     * @param orderId  订单id
     * @param callBack 回调
     */
    public void updateOrder(String orderId, CallBack callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("orderId", orderId);
        doPost(IFinancialUrl.UPDATE_ORDER, params, callBack);
    }

    /**
     * 取消订单
     *
     * @param orderId  订单id
     * @param callBack 回调
     */
    public void cancelOrder(String orderId, CallBack callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("orderId", orderId);
        doPost(IFinancialUrl.CANCEL_ORDER, params, callBack);
    }

}


