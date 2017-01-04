package com.beyonditsm.financial.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.beyonditsm.financial.R;

/**
 * Created by liwk on 2016/7/21.
 */
public class AutoDismissDialog {

    private Context context;
    private Dialog dialog;
    private boolean flag = true;
    private int FLAG_DISMISS = 1;

    public AutoDismissDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
    }

    @SuppressLint("InflateParams")
    public AutoDismissDialog builder(){
        View view = LayoutInflater.from(context).inflate(R.layout.auto_dismiss_dialog, null);
        // 设置Dialog宽高
        view.setMinimumWidth(466);
        view.setMinimumHeight(248);

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
        mThread.start();
    }

    public void dismiss(){
        dialog.dismiss();
        mThread.interrupt();
        flag = false;
    }
    private Thread mThread = new Thread(){
        @Override
        public void run() {
            super.run();

                try {
                    Thread.sleep(2000);
                    Message msg = mHandler.obtainMessage();
                    msg.what = FLAG_DISMISS;
                    mHandler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

        }
    };

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == FLAG_DISMISS)
                if (context!=null)
                dismiss();
        }

    };

}
