package com.beyonditsm.financial.http;

import com.beyonditsm.financial.entity.CreditManager;
import com.beyonditsm.financial.entity.FindProductListEntity;
import com.beyonditsm.financial.util.GsonUtils;
import com.lidroid.xutils.util.LogUtils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 信贷经理人接口调用
 * Created by wangbin on 15/11/23.
 */
public class MangManger extends RequestManager {
    /**
     * 通过产品表相关参数查询产品信息
     *
     * @param fpe
     * @param callBack
     */
    public void findProductList(FindProductListEntity fpe,String creditMoney,String creditTime ,CallBack callBack) {
        List<NameValuePair> queryParams = new ArrayList<NameValuePair>();
        if(creditMoney!=null)
            queryParams.add(new BasicNameValuePair("creditMoney",creditMoney ));
        if(creditTime!=null)
            queryParams.add(new BasicNameValuePair("creditTime",creditTime ));
        queryParams.add(new BasicNameValuePair("page", fpe.getPage() + ""));
        queryParams.add(new BasicNameValuePair("rows", fpe.getRows() + ""));
        queryParams.add(new BasicNameValuePair("status", "PUBLISHED"));
//        doPost(IFinancialUrl.FIND_PRODUCT_LIST_URL, queryParams, callBack);
        doPost(IFinancialUrl.FIND_PRODUCT_LIST_URL+"?creditMoney="+creditMoney+"&creditTime="+creditTime+"&orderByOfType="+""+"&page="+fpe.getPage()+"&rows="+fpe.getRows(),queryParams,callBack);
    }

    /**
     * 获取抢单列表
     *
     * @param callBack
     */
    public void getGrabOeder(int page, int rows, final CallBack callBack) {
        List<NameValuePair> queryParams = new ArrayList<NameValuePair>();
        queryParams.add(new BasicNameValuePair("page", page + ""));
        queryParams.add(new BasicNameValuePair("rows", rows + ""));
        doPost(IFinancialUrl.GRAB_ORDER_URL, queryParams, callBack);
    }

    /**
     * 抢单
     *
     * @param callBack
     */
    public void GrabOrder(String orderId, final CallBack callBack) {
        LogUtils.i(orderId);
        List<NameValuePair> queryParams = new ArrayList<>();
        queryParams.add(new BasicNameValuePair("orderId", orderId));
        doPost(IFinancialUrl.ORDER_GRAB_URL, queryParams, callBack);
    }

    /**
     * 查找已抢订单
     *
     * @param page
     * @param rows
     * @param callBack
     */
    public void findHasOrder(int page, int rows, CallBack callBack) {
        List<NameValuePair> queryParams = new ArrayList<NameValuePair>();
        queryParams.add(new BasicNameValuePair("page", page + ""));
        queryParams.add(new BasicNameValuePair("rows", rows + ""));
        doPost(IFinancialUrl.CM_ORDER_URL, queryParams, callBack);
    }

    /**
     * 查看订单详情
     *
     * @param orderId  订单id
     * @param callBack
     */
    public void checkOrderDetail(String orderId, CallBack callBack) {
        LogUtils.i(orderId);
        List<NameValuePair> queryParams = new ArrayList<NameValuePair>();
        queryParams.add(new BasicNameValuePair("orderId", orderId));
        doPost(IFinancialUrl.ORDER_DETAIL_URL, queryParams, callBack);
    }
    /**
     * 提交已抢订单
     *
     * @param orderId 订单id
     * @param callBack
     */
    public void commitOrder(String orderId ,CallBack callBack) {
        List<NameValuePair> queryParams = new ArrayList<NameValuePair>();
        queryParams.add(new BasicNameValuePair("orderId", orderId));
        doPost(IFinancialUrl.COMMIT_ORDER_URL, queryParams, callBack);
    }

    public void findCreditManagerDetail(CallBack callBack) {
        doPost(IFinancialUrl.FIND_CREDIT_MANAGER_DETAIL, null, callBack);
    }

    /**
     * 查看当前信贷经理登陆详情
     */
    public void currentCreditManagerDetail(CallBack callBack) {
        doPost(IFinancialUrl.CURR_CREDIT_MANGER_DETAIL, null, callBack);
    }

    /**
     * 修改信贷经理信息
     *
     * @param callBack
     */
    public void modifyCreditManager(CreditManager cm, CallBack callBack) {
        List<NameValuePair> queryParams = new ArrayList<NameValuePair>();
//        CreditManager cm = cme.getCreditManager();
        String json = GsonUtils.bean2Json(cm);
        try {
            JSONObject obj = new JSONObject(json);
            if((obj.toString()).indexOf("createTime")!=-1){
                obj.remove("createTime");
            }
            if((obj.toString()).indexOf("createPersonId")!=-1){
                obj.remove("createPerson");
            }
            if((obj.toString()).indexOf("modifyTime")!=-1){
                obj.remove("modifyTime");
            }
            if((obj.toString()).indexOf("modifyPersonId")!=-1){
                obj.remove("modifyPersonId");
            }
            Iterator<String> it = obj.keys();
            while (it.hasNext()) {
                String keys = it.next();
                queryParams.add(new BasicNameValuePair(keys, String.valueOf(obj.get(keys))));
                LogUtils.i(keys+":"+String.valueOf(obj.get(keys))+"\n");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        doPost(IFinancialUrl.UPDATE_CREDIT_DATAS, queryParams, callBack);
    }

//    /**
//     * 查找已抢订单
//     *
//     * @param productId 产品id
//     * @param callBack
//     */
//    public void checkProductDetail(String productId, CallBack callBack) {
//        List<NameValuePair> queryParams = new ArrayList<NameValuePair>();
//        queryParams.add(new BasicNameValuePair("productId", productId));
//        doPost(IFinancialUrl.PRODUCT_DETAIL_URL, queryParams, callBack);
//    }

    /**
     * 要求客户补件
     *
     * @param orderId 订单id
     * @param callBack
     */
    public void orderbj(String orderId,String remark, CallBack callBack) {
        List<NameValuePair> queryParams = new ArrayList<NameValuePair>();
        queryParams.add(new BasicNameValuePair("orderId", orderId));
        queryParams.add(new BasicNameValuePair("remark", remark));
        doPost(IFinancialUrl.BJ_ORDER_URL, queryParams, callBack);
    }

    /**
     * 查找已提交订单
     *
     * @param page
     * @param rows
     * @param callBack
     */
    public void findCommitOrder(String orderSts ,int page, int rows, CallBack callBack) {
        List<NameValuePair> queryParams = new ArrayList<NameValuePair>();
        queryParams.add(new BasicNameValuePair("orderSts",orderSts));
        queryParams.add(new BasicNameValuePair("page", page + ""));
        queryParams.add(new BasicNameValuePair("rows", rows + ""));
        doPost(IFinancialUrl.CM_COMMIT_ORDER_URL, queryParams, callBack);
    }

    /**
     * 获取附件图片
     * @param accountId
     * @param orderNo
     * @param callBack
     */
    public void getFujianPic(String accountId,String orderNo,CallBack callBack){
        List<NameValuePair> queryParams = new ArrayList<NameValuePair>();
        doGet(IFinancialUrl.GET_SUBMIT_FUJIAN_URL + "?accountId="+accountId+"&orderNo="+orderNo, queryParams, callBack);

    }
}
