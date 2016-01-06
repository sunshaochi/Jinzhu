package com.beyonditsm.financial.widget;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.beyonditsm.financial.R;

/**
 * 自定义选择月份
 *
 * @author yang
 */
public class DialogHint {
    private Context context;
    private Dialog dialog;
    private Display display;
    private String content;
    private TextView dialog_tv;

    public DialogHint(Context context, String content) {
        this.context = context;
        this.content = content;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    @SuppressLint("InflateParams")
    @SuppressWarnings("deprecation")
    public DialogHint builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(
                R.layout.dialoghint, null);

        // 设置Dialog最小宽度为屏幕宽度
        view.setMinimumWidth(display.getWidth()-100);

        // 获取自定义Dialog布局中的控件
        dialog_tv = (TextView) view.findViewById(R.id.dialog_tv);
        if(!TextUtils.isEmpty(content)){
            dialog_tv.setText(content.toString());
        }
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

    public DialogHint setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public DialogHint setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    public void show() {
        dialog.show();
    }

}
