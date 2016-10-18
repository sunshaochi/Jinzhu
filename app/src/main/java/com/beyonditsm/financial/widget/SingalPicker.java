package com.beyonditsm.financial.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.widget.city.ScrollerNumberPicker;

import java.util.ArrayList;

/**
 * 获取省信息
 * Created by Yang on 2015/11/30 0030.
 */
public class SingalPicker extends LinearLayout {
    /**
     * 滑动控件
     */
    private ScrollerNumberPicker numberPicker;
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
    private Context context;
    private ArrayList<String> province_list = new ArrayList<>();
    private String city_string;
    private String city_code_string;

    //    private CitycodeUtil citycodeUtil;
    public SingalPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        getaddressinfo();
    }

    public SingalPicker(Context context) {
        super(context);
        this.context = context;
        getaddressinfo();
    }

    /**
     * 填充数据
     */
    private void getaddressinfo() {
            province_list.add("暂无数据");
    }


    public void setDefault(int position,ArrayList<String> list) {
        if(list!=null){
            numberPicker.setData(list);
        }else{
            numberPicker.setData(province_list);
        }
        numberPicker.setDefault(position);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater.from(getContext()).inflate(R.layout.single_picker, this);
//        citycodeUtil = CitycodeUtil.getSingleton();
        // 获取控件引用
        numberPicker = (ScrollerNumberPicker) findViewById(R.id.province);
//        LogUtils.i(province_list+"");
        numberPicker.setOnSelectListener(new ScrollerNumberPicker.OnSelectListener() {

            public void endSelect(int id, String text) {
//                System.out.println("id-->" + id + "text----->" + text);
                if (text.equals("") || text == null)
                    return;
                if (tempProvinceIndex != id) {
//                    System.out.println("endselect");
                    int lastDay = Integer.valueOf(numberPicker.getListSize());
                    if (id > lastDay) {
                        numberPicker.setDefault(lastDay - 1);
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
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
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
        city_string = numberPicker.getSelectedText();
        return city_string;
    }

    public interface OnSelectingListener {

        public void selected(boolean selected);
    }

}
