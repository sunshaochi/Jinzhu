package com.beyonditsm.financial.view;

import android.content.Context;
import android.view.LayoutInflater;

import com.beyonditsm.financial.R;

/**
 * 自定义dialog，基本信息不符合的dialog
 * Created by Administrator on 2016/10/20 0020.
 */

public class CoustomDialog {

    private Context context;
    public CoustomDialog(Context context) {
        this.context = context;
    }
    public CoustomDialog builder(){
        LayoutInflater.from(context).inflate(R.layout.coustom_layout,null);
        return this;
    }
}
