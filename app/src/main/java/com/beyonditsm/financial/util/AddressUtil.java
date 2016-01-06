package com.beyonditsm.financial.util;

import android.content.Context;
import android.text.TextUtils;

import com.beyonditsm.financial.widget.city.FileUtil;
import com.tandong.sa.json.JsonArray;
import com.tandong.sa.json.JsonElement;
import com.tandong.sa.json.JsonObject;
import com.tandong.sa.json.JsonParser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by wangbin on 15/12/29.
 */
public class AddressUtil {

    private Context context;
//    private List<Cityinfo> province_list = new ArrayList<Cityinfo>();
//    private HashMap<String, List<Cityinfo>> city_map = new HashMap<String, List<Cityinfo>>();
//    private HashMap<String, List<Cityinfo>> couny_map = new HashMap<String, List<Cityinfo>>();

    private HashMap<String, String> proMap = new HashMap<String,String>();
    private HashMap<String, HashMap<String, String>> city_map = new HashMap<String, HashMap<String, String>>();
    private HashMap<String, HashMap<String, String>> couny_map = new HashMap<String, HashMap<String, String>>();


    private HashMap<String, String> proTMap = new HashMap<String,String>();
    private HashMap<String, HashMap<String, String>> cityTmap = new HashMap<String, HashMap<String, String>>();
    private HashMap<String, HashMap<String, String>> counyTmap = new HashMap<String, HashMap<String, String>>();
    public AddressUtil(Context context) {
        this.context = context;
        getaddressinfo();
    }
    // 获取城市信息
    private void getaddressinfo() {
        // 读取城市信息string
        JSONParser parser = new JSONParser();
        String area_str = FileUtil.readAssets(context, "area.json");
        proMap = parser.getJSONParserResult(area_str, "area0");
        city_map = parser.getJSONParserResultArray(area_str, "area1");
        couny_map = parser.getJSONParserResultArray(area_str, "area2");

        proTMap=parser.getJSONVtoK(area_str, "area0");
        cityTmap=parser.getJSONParserVToKArray(area_str, "area1");
        counyTmap=parser.getJSONParserVToKArray(area_str,"area2");

    }

    /**
     * 更具省获取code
     * @param proname
     * @return
     */
    public String getProCode(String proname){
        String proCode="";
        if(!TextUtils.isEmpty(proTMap.get(proname))){
            proCode=proTMap.get(proname);
        }
        return proCode;
    }

    public String getProName(String proCode){
        String proName="";
        if(!TextUtils.isEmpty(proMap.get(proCode))){
            proName=proMap.get(proCode);
        }
        return proName;
    }

    public String getCityCode(String proCode,String cityname){
        HashMap<String, String> hm=cityTmap.get(proCode);
        String city_code="";
        if(hm!=null&&hm.size()>0){
            if(!TextUtils.isEmpty(hm.get(cityname))){
                city_code=hm.get(cityname);
            }
        }

        return city_code;
    }


    public String getCityName(String proCode,String cityCode){
        HashMap<String, String> hm=city_map.get(proCode);
        String city_name="";
        if(hm!=null&&hm.size()>0){
            if(!TextUtils.isEmpty(hm.get(cityCode))){
                city_name=hm.get(cityCode);
            }
        }
        return city_name;
    }


    public String getCountryCode(String cityCode,String countryName){
        HashMap<String, String> hm=counyTmap.get(cityCode);
        String countryCode="";
        if(hm!=null&&hm.size()>0){
            if(!TextUtils.isEmpty(hm.get(countryName))){
                countryCode=hm.get(countryName);
            }
        }
        return  countryCode;
    }


    public String getCountryName(String cityCode,String countryCode){
        HashMap<String, String> hm=couny_map.get(cityCode);
        String countryName="";
        if(hm!=null&&hm.size()>0){
            if(!TextUtils.isEmpty(hm.get(countryCode))){
                countryName=hm.get(countryCode);
            }
        }

        return countryName;
    }



    public static class JSONParser {

        public HashMap<String, String> getJSONParserResult(String JSONString, String key) {
            HashMap<String, String> list = new HashMap<String, String>();
            JsonObject result = new JsonParser().parse(JSONString)
                    .getAsJsonObject().getAsJsonObject(key);

            Iterator iterator = result.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = (Map.Entry<String, JsonElement>) iterator
                        .next();
                list.put(entry.getKey(),entry.getValue().getAsString());
            }
            return list;
        }

        public HashMap<String,HashMap<String, String>> getJSONParserResultArray(
                String JSONString, String key) {
            HashMap<String, HashMap<String, String>> hashMap = new HashMap<String, HashMap<String, String>>();
            JsonObject result = new JsonParser().parse(JSONString)
                    .getAsJsonObject().getAsJsonObject(key);
            Iterator iterator = result.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = (Map.Entry<String, JsonElement>) iterator
                        .next();
                HashMap<String, String> list = new HashMap<String, String>();
                JsonArray array = entry.getValue().getAsJsonArray();
                for (int i = 0; i < array.size(); i++) {
                    list.put(array.get(i).getAsJsonArray().get(1)
                            .getAsString(),array.get(i).getAsJsonArray().get(0)
                            .getAsString());
                }
                hashMap.put(entry.getKey(), list);
            }
            return hashMap;
        }


        public HashMap<String, String> getJSONVtoK(String JSONString, String key) {
            HashMap<String, String> list = new HashMap<String, String>();
            JsonObject result = new JsonParser().parse(JSONString)
                    .getAsJsonObject().getAsJsonObject(key);

            Iterator iterator = result.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = (Map.Entry<String, JsonElement>) iterator
                        .next();
                list.put(entry.getValue().getAsString(),entry.getKey());
            }
            return list;
        }

        public HashMap<String,HashMap<String, String>> getJSONParserVToKArray(
                String JSONString, String key) {
            HashMap<String, HashMap<String, String>> hashMap = new HashMap<String, HashMap<String, String>>();
            JsonObject result = new JsonParser().parse(JSONString)
                    .getAsJsonObject().getAsJsonObject(key);
            Iterator iterator = result.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = (Map.Entry<String, JsonElement>) iterator
                        .next();
                HashMap<String, String> list = new HashMap<String, String>();
                JsonArray array = entry.getValue().getAsJsonArray();
                for (int i = 0; i < array.size(); i++) {
                    list.put(array.get(i).getAsJsonArray().get(0)
                            .getAsString(),array.get(i).getAsJsonArray().get(1)
                            .getAsString());
                }
                hashMap.put(entry.getKey(), list);
            }
            return hashMap;
        }

    }







}
