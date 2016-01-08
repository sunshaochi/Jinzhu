package com.beyonditsm.financial.http;

import com.beyonditsm.financial.entity.ServantWithdrawEntity;
import com.beyonditsm.financial.entity.UserEntity;
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
 * 服务者接口
 * Created by wangbin on 15/11/23.
 */
public class ServicerManager extends RequestManager{

    /**
     * 获取服务者信息
     * @param callBack
     */
    public void findServantDetail(CallBack callBack){

        doPost(IFinancialUrl.SERVANT_URL, null, callBack);
    }

    /**
     * 更改服务者资料
     * @param callBack
     */
    public void UpadateServantData(UserEntity userEntity,CallBack callBack){
        List<NameValuePair> queryParams = new ArrayList<NameValuePair>();
//        Gson gson = new GsonBuilder().serializeNulls().create();
//        String json = gson.toJson(servantEntity);
        String json= GsonUtils.bean2Json(userEntity);
        try {
            JSONObject obj=new JSONObject(json);
            if((obj.toString()).indexOf("createTime")!=-1){
                obj.remove("createTime");
            }
            if((obj.toString()).indexOf("createPersonId")!=-1){
                obj.remove("createPersonId");
            }
            if((obj.toString()).indexOf("modifyTime")!=-1){
                obj.remove("modifyTime");
            }
            if((obj.toString()).indexOf("modifyPersonId")!=-1){
                obj.remove("modifyPersonId");
            }
            Iterator<String> it=obj.keys();
            while (it.hasNext()){
                String key=it.next();
                queryParams.add(new BasicNameValuePair(key,String.valueOf(obj.get(key))));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LogUtils.i(queryParams.toString());
        doPost(IFinancialUrl.UPDATE_SERVANT_URL, queryParams, callBack);
    }

    /**
     * 提现
     * @param amount
     */
    public void serviceWithDraw(String amount,CallBack callBack){
        List<NameValuePair> queryParams = new ArrayList<NameValuePair>();
        queryParams.add(new BasicNameValuePair("amount",amount));
        doPost(IFinancialUrl.SERVICE_WITH_DRAW_URL, queryParams, callBack);
    }

    /**
     * 查询提现返佣记录
     * @param swe
     * @param callBack
     */
    public void findServantWithdraw(ServantWithdrawEntity swe,CallBack callBack){
        List<NameValuePair> queryParams = new ArrayList<NameValuePair>();
        queryParams.add(new BasicNameValuePair("startTime",swe.getStartTime()+""));
        queryParams.add(new BasicNameValuePair("endTime",swe.getEndTime()+""));
        queryParams.add(new BasicNameValuePair("page",swe.getPage()+""));
        queryParams.add(new BasicNameValuePair("rows", swe.getRows() + ""));
        doGet(IFinancialUrl.FIND_SERVANT_WITHDRAW+"?page="+swe.getPage()+"&rows="+swe.getRows(), queryParams, callBack);
    }
}
