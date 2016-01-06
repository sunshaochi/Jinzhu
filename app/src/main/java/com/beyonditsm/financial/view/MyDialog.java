package com.beyonditsm.financial.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.beyonditsm.financial.ConstantValue;
import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.manager.OrderDetailAct;
import com.beyonditsm.financial.entity.GrabOrderBean;

/**
 * Created by Yang on 2015/11/16 0016.
 */
public class MyDialog {
    private Context context;
    private Display display;
    private Dialog dialog;
    private TextView cancle;
    private TextView check;
    private GrabOrderBean.RowsEntity data;

    public MyDialog(Context context,GrabOrderBean.RowsEntity data) {
        this.context = context;
        this.data = data;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public MyDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(
                R.layout.mydialog, null);

        // 设置Dialog宽高
        view.setMinimumWidth(466);
        view.setMinimumHeight(248);
        // 获取自定义Dialog布局中的控件
        cancle = (TextView) view.findViewById(R.id.tv_cancle);
        check = (TextView) view.findViewById(R.id.tv_gocheck);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, OrderDetailAct.class);
                intent.putExtra(ConstantValue.ORDER,data);
                context.startActivity(intent);
                dialog.dismiss();
            }
        });

        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.x = 0;
        lp.y = 0;
        dialogWindow.setAttributes(lp);

        return this;
    }

    public void show() {
        dialog.show();
    }
}
