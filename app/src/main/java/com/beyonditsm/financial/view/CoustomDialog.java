package com.beyonditsm.financial.view;

import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.beyonditsm.financial.R;

/**
 * 自定义dialog，基本信息不符合的dialog
 * Created by Administrator on 2016/10/20 0020.
 */

public class CoustomDialog {

    private Context context;
    private Display display;
    public CoustomDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }
    public CoustomDialog builder(){
        View view = LayoutInflater.from(context).inflate(R.layout.coustom_layout, null);


        view.setMinimumWidth(498);
        view.setMinimumHeight(300);
        ImageView ivClose = (ImageView) view.findViewById(R.id.iv_close);
        TextView tvLook = (TextView) view.findViewById(R.id.tv_look);
        TextView tvCancel = (TextView) view.findViewById(R.id.tv_cancel);
        ListView lvUnnatural = (ListView) view.findViewById(R.id.lv_unnatural);

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        
        return this;
    }
}
