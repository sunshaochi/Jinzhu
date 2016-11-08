package com.beyonditsm.financial.widget.jijietong;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.entity.JJTCityEntity;
import com.beyonditsm.financial.entity.JJTCounyEntity;
import com.beyonditsm.financial.entity.JJTProvinceEntity;
import com.beyonditsm.financial.util.ParamsUtil;
import com.beyonditsm.financial.widget.city.CitycodeUtil;
import com.beyonditsm.financial.widget.city.Cityinfo;
import com.beyonditsm.financial.widget.city.ScrollerNumberPicker;
import com.tandong.sa.json.JsonArray;
import com.tandong.sa.json.JsonElement;
import com.tandong.sa.json.JsonObject;
import com.tandong.sa.json.JsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

/**
 * 城市Picker
 *
 * @author zd
 */
public class JJTPicker extends LinearLayout {
    /**
     * 滑动控件
     */
    private ScrollerNumberPicker provincePicker;
    private ScrollerNumberPicker cityPicker;
    private ScrollerNumberPicker counyPicker;
    /**
     * 选择监听
     */
    private OnSelectingListener onSelectingListener;
    /**
     * 刷新界面
     */
    private static final int REFRESH_VIEW = 0x001;
    /**
     * 临时日期
     */
    private int tempProvinceIndex = -1;
    private int temCityIndex = -1;
    private int tempCounyIndex = -1;
    private Context context;
    private List<Cityinfo> province_list = new ArrayList<>();
    private HashMap<String, List<Cityinfo>> city_map = new HashMap<>();
    private HashMap<String, List<Cityinfo>> couny_map = new HashMap<>();
    private static ArrayList<String> province_list_code = new ArrayList<String>();
    private static ArrayList<String> city_list_code = new ArrayList<String>();
    private static ArrayList<String> couny_list_code = new ArrayList<String>();

    private CitycodeUtil citycodeUtil;
    private String city_code_string;
    private String city_string;

    private List<JJTProvinceEntity> provinceEntityList;
    private List<JJTCityEntity> cityEntityList;
    private List<JJTCounyEntity> counyEntityList;
    private JJTInterface jjtInterface;

    public void setOnSrollListener(JJTInterface jjtInterface) {
        this.jjtInterface = jjtInterface;
    }

    public JJTPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        getaddressinfo();
        // TODO Auto-generated constructor stub
    }


    public JJTPicker(Context context) {
        super(context);
        this.context = context;
        getaddressinfo();
        // TODO Auto-generated constructor stub
    }

    // 获取城市信息
    private void getaddressinfo() {

        province_list = new ArrayList<>();
        provinceEntityList = ParamsUtil.getInstance().getProvinceEntityList();
        for (int i = 0; i < provinceEntityList.size(); i++) {
            JJTProvinceEntity jjtProvinceEntity = provinceEntityList.get(i);
            Cityinfo cityinfo = new Cityinfo();
            cityinfo.setCity_name(jjtProvinceEntity.getName() + "");
            cityinfo.setId(jjtProvinceEntity.getId() + "");
            province_list.add(cityinfo);
        }

    }

    public void setCouny() {

        counyEntityList = ParamsUtil.getInstance().getCounyEntityList();

        counyPicker.setData(citycodeUtil.getJJTCouny(counyEntityList));
        counyPicker.setDefault(0);
    }


    public static class JSONParser {
        public ArrayList<String> province_list_code = new ArrayList<String>();
        public ArrayList<String> city_list_code = new ArrayList<String>();

        public List<Cityinfo> getJSONParserResult(String JSONString, String key) {
            List<Cityinfo> list = new ArrayList<Cityinfo>();
            JsonObject result = new JsonParser().parse(JSONString)
                    .getAsJsonObject().getAsJsonObject(key);

            Iterator iterator = result.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<String, JsonElement> entry = (Entry<String, JsonElement>) iterator
                        .next();
                Cityinfo cityinfo = new Cityinfo();
                cityinfo.setCity_name(entry.getValue().getAsString());
                cityinfo.setId(entry.getKey());
                province_list_code.add(entry.getKey());
                list.add(cityinfo);
            }
            return list;
        }

        public HashMap<String, List<Cityinfo>> getJSONParserResultArray(
                String JSONString, String key) {
            HashMap<String, List<Cityinfo>> hashMap = new HashMap<String, List<Cityinfo>>();
            JsonObject result = new JsonParser().parse(JSONString)
                    .getAsJsonObject().getAsJsonObject(key);
            Iterator iterator = result.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<String, JsonElement> entry = (Entry<String, JsonElement>) iterator
                        .next();
                List<Cityinfo> list = new ArrayList<Cityinfo>();
                JsonArray array = entry.getValue().getAsJsonArray();
                for (int i = 0; i < array.size(); i++) {
                    Cityinfo cityinfo = new Cityinfo();
                    cityinfo.setCity_name(array.get(i).getAsJsonArray().get(0)
                            .getAsString());
                    cityinfo.setId(array.get(i).getAsJsonArray().get(1)
                            .getAsString());
                    city_list_code.add(array.get(i).getAsJsonArray().get(1)
                            .getAsString());
                    list.add(cityinfo);
                }
                hashMap.put(entry.getKey(), list);
            }
            return hashMap;
        }
    }

    public void setCityList() {

        city_map = new HashMap<>();
        cityEntityList = ParamsUtil.getInstance().getCityEntityList();

        cityPicker.setData(citycodeUtil.getJJTCity(cityEntityList));
        if (cityEntityList!=null && cityEntityList.size()>0){
            cityPicker.setDefault(0);
        }else {
            cityPicker.clearData();
        }

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater.from(getContext()).inflate(R.layout.city_picker, this);
        citycodeUtil = CitycodeUtil.getSingleton();
        // 获取控件引用
        provincePicker = (ScrollerNumberPicker) findViewById(R.id.province);

        cityPicker = (ScrollerNumberPicker) findViewById(R.id.city);
        counyPicker = (ScrollerNumberPicker) findViewById(R.id.couny);
        provincePicker.setData(citycodeUtil.getProvince(province_list));
        provincePicker.setDefault(0);

//        counyPicker.setData(citycodeUtil.getCouny(couny_map, citycodeUtil
//                .getCity_list_code().get(0)));
//        counyPicker.setDefault(0);
        provincePicker.setOnSelectListener(new ScrollerNumberPicker.OnSelectListener() {

            public void endSelect(int id, String text) {
                // TODO Auto-generated method stub
//                System.out.println("id-->" + id + "text----->" + text);
                if (text.equals("") || text == null)
                    return;
                jjtInterface.onProvinceSelected(provinceEntityList.get(id));
                if (tempProvinceIndex != id) {
//                    System.out.println("endselect");
                    String selectDay = cityPicker.getSelectedText();
                    if (selectDay == null || selectDay.equals(""))
                        return;
                    String selectMonth = counyPicker.getSelectedText();
                    if (selectMonth == null || selectMonth.equals(""))
                        return;
                    int lastDay = Integer.valueOf(provincePicker.getListSize());
                    if (id > lastDay) {
                        provincePicker.setDefault(lastDay - 1);
                    }
                }
                tempProvinceIndex = id;
                Message message = new Message();
                message.what = REFRESH_VIEW;
                handler.sendMessage(message);
            }

            @Override
            public void selecting(int id, String text) {
                // TODO Auto-generated method stub
            }
        });
        cityPicker.setOnSelectListener(new ScrollerNumberPicker.OnSelectListener() {
            @Override
            public void endSelect(int id, String text) {
                // TODO Auto-generated method stub
                if (text.equals("") || text == null)
                    return;
                jjtInterface.onCitySelected(cityEntityList.get(id));
                if (temCityIndex != id) {

                    int lastDay = cityPicker.getListSize();
                    if (id > lastDay) {
                        cityPicker.setDefault(lastDay - 1);
                    }
                }
                temCityIndex = id;
                Message message = new Message();
                message.what = REFRESH_VIEW;
                handler.sendMessage(message);
            }

            @Override
            public void selecting(int id, String text) {
                // TODO Auto-generated method stub

            }
        });
        counyPicker.setOnSelectListener(new ScrollerNumberPicker.OnSelectListener() {

            @Override
            public void endSelect(int id, String text) {
                // TODO Auto-generated method stub

                if (text.equals("") || text == null)
                    return;
                jjtInterface.onCounySelected(counyEntityList.get(id));
                if (tempCounyIndex != id) {
                    String selectDay = provincePicker.getSelectedText();
                    if (selectDay == null || selectDay.equals(""))
                        return;
                    String selectMonth = cityPicker.getSelectedText();
                    if (selectMonth == null || selectMonth.equals(""))
                        return;
                    // 城市数组

                    int lastDay = Integer.valueOf(counyPicker.getListSize());
                    if (id > lastDay) {
                        counyPicker.setDefault(lastDay - 1);
                    }
                }
                tempCounyIndex = id;
                Message message = new Message();
                message.what = REFRESH_VIEW;
                handler.sendMessage(message);
            }

            @Override
            public void selecting(int id, String text) {
                // TODO Auto-generated method stub

            }
        });
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            switch (msg.what) {
                case REFRESH_VIEW:
                    if (onSelectingListener != null)
                        onSelectingListener.selected(true);
                    break;
                default:
                    break;
            }
        }

    };

    public void setOnSelectingListener(OnSelectingListener onSelectingListener) {
        this.onSelectingListener = onSelectingListener;
    }

    public String getCity_code_string() {
        return city_code_string;
    }

    public String getCity_string() {
        city_string = provincePicker.getSelectedText()
                + cityPicker.getSelectedText() + counyPicker.getSelectedText();
        return city_string;
    }

    public List<String> getListAddress() {
        List<String> province_city_couny = new ArrayList<String>();
        province_city_couny.add(provincePicker.getSelectedText());
        province_city_couny.add(cityPicker.getSelectedText());
        province_city_couny.add(counyPicker.getSelectedText());
        return province_city_couny;
    }

    public List<Integer> getListAddressId() {
        List<Integer> province_city_couny = new ArrayList<Integer>();
        province_city_couny.add(provincePicker.getSelectedId());
        province_city_couny.add(cityPicker.getSelectedId());
        province_city_couny.add(counyPicker.getSelectedId());
        return province_city_couny;
    }

    public interface OnSelectingListener {

        public void selected(boolean selected);
    }
}
