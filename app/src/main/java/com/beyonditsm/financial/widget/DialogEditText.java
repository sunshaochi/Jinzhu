package com.beyonditsm.financial.widget;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.fragment.listener.CreditOfflineDialogListener;

/**
 * Created by xuleyuan on 2016/8/17.
 */
public class DialogEditText implements View.OnClickListener {
    private Context context;
    private Dialog dialog;
    private boolean flag = true;
    private int FLAG_DISMISS = 1;
    private EditText edName;
    private TextView tvPick;
    private String imageId;
    private CreditOfflineDialogListener creditOfflineDialogListener;

    public DialogEditText(Context context,String imageId) {
        this.context = context;
        this.imageId = imageId;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
    }

    public void setCreditOfflineDialogListener(CreditOfflineDialogListener creditOfflineDialogListener) {
        this.creditOfflineDialogListener = creditOfflineDialogListener;
    }

    @SuppressLint("InflateParams")
    public DialogEditText builder() {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_edittext, null);
        // 设置Dialog宽高
        view.setMinimumWidth(466);
        view.setMinimumHeight(248);
        // 定义Dialog布局和参数
        edName = (EditText) view.findViewById(R.id.et_name);
        tvPick = (TextView) view.findViewById(R.id.tv_pick);
        tvPick.setOnClickListener(DialogEditText.this);
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

    public void dismiss() {
        dialog.dismiss();
        flag = false;
    }

    private Thread mThread = new Thread() {
        @Override
        public void run() {
            super.run();
            while (flag) {
                try {
                    Thread.sleep(2000);
                    Message msg = mHandler.obtainMessage();
                    msg.what = FLAG_DISMISS;
                    mHandler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == FLAG_DISMISS)
                dismiss();
        }

    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_pick:
                if (!TextUtils.isEmpty(edName.getText().toString())) {
                    creditOfflineDialogListener.onPickImage(edName.getText().toString() + "",imageId);
                }
                break;
            default:
                break;
        }
    }
}
