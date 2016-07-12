package com.beyonditsm.financial.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beyonditsm.financial.R;

/**
 * Created by liwk on 2016/7/12.
 */
public class GPSAlertDialog {
    private Context context;
    private Display display;
    private Dialog dialog;
    private LinearLayout lLayout_bg;
    private TextView txt_title;
    private Button btn_pos;
    private Button btn_neg;
    private ImageView img_line;
    private boolean showTitle = false;
    private boolean showPosBtn = false;
    private boolean showNegBtn = false;
    private boolean showMsg = false;
    private LinearLayout llMsg;
    private TextView tvfirstMsg;
    private TextView tvSecondMsg;
    private TextView tvChangeCity;

    public GPSAlertDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public GPSAlertDialog builder(){
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(
                R.layout.gps_alertdialog, null);

        // 获取自定义Dialog布局中的控件
        lLayout_bg = (LinearLayout) view.findViewById(R.id.lLayout_bg);
        txt_title = (TextView) view.findViewById(R.id.txt_title);
        txt_title.setVisibility(View.GONE);
        btn_neg = (Button) view.findViewById(R.id.btn_neg);
        btn_neg.setVisibility(View.GONE);
        btn_pos = (Button) view.findViewById(R.id.btn_pos);
        btn_pos.setVisibility(View.GONE);
        img_line = (ImageView) view.findViewById(R.id.img_line);
        img_line.setVisibility(View.GONE);
        llMsg = (LinearLayout) view.findViewById(R.id.ll_gpsMsg);
        llMsg.setVisibility(View.GONE);
        tvfirstMsg = (TextView) view.findViewById(R.id.tv_firstMsg);
        tvfirstMsg.setVisibility(View.GONE);
        tvSecondMsg = (TextView) view.findViewById(R.id.tv_secondMsg);
        tvSecondMsg.setVisibility(View.GONE);
        tvChangeCity = (TextView) view.findViewById(R.id.tv_changeCity);
        tvChangeCity.setVisibility(View.GONE);

        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(view);

        // 调整dialog背景大小
        lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display
                .getWidth() * 0.85), LinearLayout.LayoutParams.WRAP_CONTENT));
        return this;
    }

    public GPSAlertDialog setTitle(String title) {
        showTitle = true;
        if ("".equals(title)) {
            txt_title.setText("标题");
        } else {
            txt_title.setText(title);
        }
        return this;
    }

    public GPSAlertDialog setMsg(String firstMsg,String secondMsg,String changeCity){
        showMsg = true;
        if (!"".equals(firstMsg)){
            tvfirstMsg.setText(firstMsg);
        }

        if (!"".equals(secondMsg)){
            tvSecondMsg.setText(secondMsg);
        }

        if ("".equals(changeCity)){
            tvChangeCity.setVisibility(View.GONE);
        }else{
            tvChangeCity.setText(changeCity);
            tvChangeCity.setTextColor(context.getResources().getColor(R.color.blue_color));
        }

        return this;
    }

    public GPSAlertDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public GPSAlertDialog setPositiveButton(String text,
                                           final View.OnClickListener listener) {
        showPosBtn = true;
        if ("".equals(text)) {
            btn_pos.setText("确定");
        } else {
            btn_pos.setText(text);
        }
        btn_pos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    public GPSAlertDialog setNegativeButton(String text,
                                           final View.OnClickListener listener) {
        showNegBtn = true;
        if ("".equals(text)) {
            btn_neg.setText("取消");
        } else {
            btn_neg.setText(text);
        }
        btn_neg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.onClick(v);
                }else{
                    dialog.dismiss();
                }
            }
        });
        return this;
    }

    private void setLayout() {
        if (!showTitle&& !showMsg ) {
            txt_title.setText("提示");
            txt_title.setVisibility(View.VISIBLE);
        }

        if (showTitle) {
            txt_title.setVisibility(View.VISIBLE);
        }

        if (showMsg) {
            llMsg.setVisibility(View.VISIBLE);
            tvfirstMsg.setVisibility(View.VISIBLE);
            tvSecondMsg.setVisibility(View.VISIBLE);
            tvChangeCity.setVisibility(View.VISIBLE);
        }

//        if (showMsgLayout){
//            llMsg.setVisibility(View.VISIBLE);
//        }
        if (!showPosBtn && !showNegBtn) {
            btn_pos.setText("确定");
            btn_pos.setVisibility(View.VISIBLE);
            btn_pos.setBackgroundResource(R.drawable.alertdialog_single_selector);
            btn_pos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }

        if (showPosBtn && showNegBtn) {
            btn_pos.setVisibility(View.VISIBLE);
            btn_pos.setBackgroundResource(R.drawable.alertdialog_right_selector);
            btn_neg.setVisibility(View.VISIBLE);
            btn_neg.setBackgroundResource(R.drawable.alertdialog_left_selector);
            img_line.setVisibility(View.VISIBLE);
        }

        if (showPosBtn && !showNegBtn) {
            btn_pos.setVisibility(View.VISIBLE);
            btn_pos.setBackgroundResource(R.drawable.alertdialog_single_selector);
        }

        if (!showPosBtn && showNegBtn) {
            btn_neg.setVisibility(View.VISIBLE);
            btn_neg.setBackgroundResource(R.drawable.alertdialog_single_selector);
        }
    }

    public void show() {
        setLayout();
        dialog.show();
    }
}
