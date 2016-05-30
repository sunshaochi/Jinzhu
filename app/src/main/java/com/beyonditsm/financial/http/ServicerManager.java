package com.beyonditsm.financial.http;

import com.beyonditsm.financial.entity.ServantWithdrawEntity;
import com.beyonditsm.financial.entity.UserEntity;
import com.beyonditsm.financial.util.GsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
        Map<String,String> params=new HashMap<String,String>();
        doPost(IFinancialUrl.SERVANT_URL, params, callBack);
    }

    /**
     * 更改服务者资料
     * @param callBack
     */
    public void UpadateServantData(UserEntity userEntity,CallBack callBack){
        Map<String,String> params=new HashMap<String,String>();
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
                params.put(key,String.valueOf(obj.get(key)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        params.put("json",json);
        doPost(IFinancialUrl.UPDATE_SERVANT_URL, params, callBack);
    }

    /**
     * 提现
     * @param amount
     */
    public void serviceWithDraw(String amount,CallBack callBack){
        Map<String,String> params=new HashMap<String,String>();
        params.put("amount", amount);
        doPost(IFinancialUrl.SERVICE_WITH_DRAW_URL, params, callBack);
    }

    /**
     * 查询提现返佣记录
     * @param swe
     * @param callBack
     */
    public void findServantWithdraw(ServantWithdrawEntity swe,CallBack callBack){
//        List<NameValuePair> queryParams = new ArrayList<NameValuePair>();
//        queryParams.add(new BasicNameValuePair("startTime",swe.getStartTime()+""));
//        queryParams.add(new BasicNameValuePair("endTime",swe.getEndTime()+""));
//        queryParams.add(new BasicNameValuePair("page",swe.getPage()+""));
//        queryParams.add(new BasicNameValuePair("rows", swe.getRows() + ""));
        doGet(IFinancialUrl.FIND_SERVANT_WITHDRAW+"?page="+swe.getPage()+"&rows="+swe.getRows(), callBack);
    }
}
