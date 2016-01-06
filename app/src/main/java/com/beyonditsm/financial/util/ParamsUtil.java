package com.beyonditsm.financial.util;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 实体类转表单
 * Created by Yang on 2015/11/28 0028.
 */
public class ParamsUtil {

    public static ParamsUtil util;

    public static ParamsUtil getInstance() {
        if (util == null)
            util = new ParamsUtil();
        return util;
    }

    private ParamsUtil() {

    }

    public List<NameValuePair> BeanToForm(String bean) {
        List<NameValuePair> queryParams = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(bean);
            Iterator<String> it = jsonObject.keys();
            while (it.hasNext()) {
                String key = it.next();
                String value = String.valueOf(jsonObject.get(key));
                queryParams.add(new BasicNameValuePair(key, value));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return queryParams;
    }
}
