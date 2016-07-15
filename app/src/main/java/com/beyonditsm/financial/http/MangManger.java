package com.beyonditsm.financial.http;

import com.beyonditsm.financial.entity.CreditManager;
import com.beyonditsm.financial.entity.FindProductListEntity;
import com.beyonditsm.financial.util.GsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 信贷经理人接口调用
 * Created by wangbin on 15/11/23.
 */
public class MangManger extends RequestManager {
    /**
     * 通过产品表相关参数查询产品信息
     *
     * @param fpe  查询产品列表实体类
     * @param callBack  回调
     */
    public void findProductList(FindProductListEntity fpe,String creditMoney,String creditTime ,CallBack callBack) {

        doGet(IFinancialUrl.FIND_PRODUCT_LIST_URL + "?creditMoney=" + creditMoney + "&creditTime=" + creditTime + "&orderByOfType=" + "" + "&page=" + fpe.getPage() + "&rows=" + fpe.getRows(), callBack);
    }

    /**
     * 获取抢单列表
     *
     * @param callBack  回调
     */
    public void getGrabOeder(int page, int rows, final CallBack callBack) {
        Map<String,String> params= new HashMap<>();
        params.put("page", page + "");
        params.put("rows", rows + "");
        doPost(IFinancialUrl.GRAB_ORDER_URL, params, callBack);
    }

    /**
     * 抢单
     *
     * @param callBack  回调
     */
    public void GrabOrder(String orderId, final CallBack callBack) {
        Map<String,String> params= new HashMap<>();
        params.put("orderId", orderId);
        doPost(IFinancialUrl.ORDER_GRAB_URL, params, callBack);
    }

    /**
     * 查找已抢订单
     *
     * @param page 页数
     * @param rows 页面记录数
     * @param callBack  回调
     */
    public void findHasOrder(int page, int rows, CallBack callBack) {
        Map<String,String> params= new HashMap<>();
        params.put("page", page + "");
        params.put("rows", rows + "");
        doPost(IFinancialUrl.CM_ORDER_URL, params, callBack);
    }

    /**
     * 查看订单详情
     *
     * @param orderId  订单id
     * @param callBack  回调
     */
    public void checkOrderDetail(String orderId, CallBack callBack) {
        Map<String,String> params= new HashMap<>();
        params.put("orderId", orderId);
        doPost(IFinancialUrl.ORDER_DETAIL_URL, params, callBack);
    }
    /**
     * 提交已抢订单
     *
     * @param orderId 订单id
     * @param callBack  回调
     */
    public void commitOrder(String orderId ,CallBack callBack) {
        Map<String,String> params= new HashMap<>();
        params.put("orderId", orderId);
        doPost(IFinancialUrl.COMMIT_ORDER_URL, params, callBack);
    }


    /**
     * 查看当前信贷经理登陆详情
     */
    public void currentCreditManagerDetail(CallBack callBack) {
        Map<String,String> params= new HashMap<>();
        doPost(IFinancialUrl.CURR_CREDIT_MANGER_DETAIL, params, callBack);
    }

    /**
     * 修改信贷经理信息
     *
     * @param callBack  回调
     */
    public void modifyCreditManager(CreditManager cm, CallBack callBack) {
        Map<String,String> params= new HashMap<>();
//        CreditManager cm = cme.getCreditManager();
        String json = GsonUtils.bean2Json(cm);
        try {
            JSONObject obj = new JSONObject(json);
            if((obj.toString()).contains("createTime")){
                obj.remove("createTime");
            }
            if((obj.toString()).contains("createPersonId")){
                obj.remove("createPerson");
            }
            if((obj.toString()).contains("modifyTime")){
                obj.remove("modifyTime");
            }
            if((obj.toString()).contains("modifyPersonId")){
                obj.remove("modifyPersonId");
            }
            Iterator<String> it = obj.keys();
            while (it.hasNext()) {
                String keys = it.next();
                params.put(keys, String.valueOf(obj.get(keys)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        doPost(IFinancialUrl.UPDATE_CREDIT_DATAS, params, callBack);
    }

    /**
     * 要求客户补件
     *
     * @param orderId 订单id
     * @param callBack  回调
     */
    public void orderbj(String orderId,String remark, CallBack callBack) {
        Map<String,String> params= new HashMap<>();
        params.put("orderId", orderId);
        params.put("remark", remark);
        doPost(IFinancialUrl.BJ_ORDER_URL, params, callBack);
    }

    /**
     * 查找已提交订单
     *
     * @param page 页数
     * @param rows 页面记录数
     * @param callBack  回调
     */
    public void findCommitOrder(String orderSts ,int page, int rows, CallBack callBack) {
        Map<String,String> params= new HashMap<>();
        params.put("orderSts", orderSts);
        params.put("page", page+"");
        params.put("rows", rows+"");
        doPost(IFinancialUrl.CM_COMMIT_ORDER_URL, params, callBack);
    }

    /**
     * 获取附件图片
     * @param accountId 账户id
     * @param orderNo 订单号
     * @param callBack  回调
     */
    public void getFujianPic(String accountId,String orderNo,CallBack callBack){
        doGet(IFinancialUrl.GET_SUBMIT_FUJIAN_URL + "?accountId="+accountId+"&orderNo="+orderNo+"&isSupplementFile=1", callBack);

    }
}
