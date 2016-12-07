package com.beyonditsm.financial.http;

import com.beyonditsm.financial.entity.FindProductListEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * 信贷经理人接口调用
 * Created by wangbin on 15/11/23.
 */
public class MangManger extends RequestManager {

    /**
     * 通过产品表相关参数查询产品信息
     *
     * @param
     * @param callBack  回调
     */
    public void findProductList(String cityId,String creditMoney,String creditTime ,String orgTypeKey,String productOrder,String orderByOfType,int page,int rows,CallBack callBack) {

        doGet(IFinancialUrl.FIND_PRODCUT_BY_PARAM  + "?cityId=" + cityId + "&creditMoney=" + creditMoney + "&creditTime=" + creditTime + "&orgTypeKey=" + orgTypeKey + "&productOrder=" + productOrder+"&orderByOfType="+orderByOfType+"&page="+page+"&rows="+rows, callBack);
    }
    /**
     * 通过产品表筛选参数查询产品信息
     */
    public void findProductByParam(String userName,String area, String orgType, String productOrder, String moneyScope, String loanTerm, int currentPage, int rows ,CallBack callBack) {
        Map<String,String> params= new HashMap<>();
        params.put("userName",userName+"");
        params.put("area", area + "");
        params.put("orgType", orgType + "");
        params.put("productOrder", productOrder + "");
        params.put("moneyScope", moneyScope + "");
        params.put("loanTerm", loanTerm + "");
        params.put("page", currentPage + "");
        params.put("rows", rows + "");
//        doPost(IFinancialUrl.FIND_PRODUCT_LIST_URL, queryParams, callBack);
        doPost(IFinancialUrl.FIND_PRODCUT_BY_PARAM ,params, callBack);
//        doPost(IFinancialUrl.FIND_PRODUCT_LIST_URL+"?creditMoney="+creditMoney+"&creditTime="+creditTime+"&orderByOfType="+""+"&page="+fpe.getPage()+"&rows="+fpe.getRows(),queryParams,callBack);
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
     * 获取附件图片
     * @param accountId 账户id
     * @param orderNo 订单号
     * @param callBack  回调
     */
    public void getFujianPic(String accountId,String orderNo,CallBack callBack){
        doGet(IFinancialUrl.GET_SUBMIT_FUJIAN_URL + "?accountId="+accountId+"&orderNo="+orderNo+"&isSupplementFile=1", callBack);

    }
}
