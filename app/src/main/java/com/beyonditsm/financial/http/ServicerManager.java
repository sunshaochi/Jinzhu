package com.beyonditsm.financial.http;

import com.beyonditsm.financial.entity.UserEntity;
import com.beyonditsm.financial.util.GsonUtils;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 代言人接口
 * Created by wangbin on 15/11/23.
 */
public class ServicerManager extends RequestManager{

    /**
     * 获取代言人信息
     * @param callBack  回调
     */
    public void findServantDetail(CallBack callBack){
        List<NameValuePair> params=new ArrayList<>();
        doPost(IFinancialUrl.SERVANT_URL, params, callBack);
    }

    /**
     * 更改代言人资料
     * @param callBack  回调
     */
    public void UpadateServantData(UserEntity userEntity,CallBack callBack){
        Map<String,String> params= new HashMap<>();
        String json= GsonUtils.bean2Json(userEntity);
        try {
            JSONObject obj=new JSONObject(json);
            if((obj.toString()).contains("createTime")){
                obj.remove("createTime");
            }
            if((obj.toString()).contains("createPersonId")){
                obj.remove("createPersonId");
            }
            if((obj.toString()).contains("modifyTime")){
                obj.remove("modifyTime");
            }
            if((obj.toString()).contains("modifyPersonId")){
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
}
