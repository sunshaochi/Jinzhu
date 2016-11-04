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
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.speedcredit.listener.DialogListener;
import com.beyonditsm.financial.fragment.listener.CreditOfflineDialogListener;

/**
 * Created by xuleyuan on 2016/8/17.
 */
public class DialogCloseBtn implements View.OnClickListener {
    private Context context;
    private Dialog dialog;
    private boolean flag = true;
    private int FLAG_DISMISS = 1;
    private TextView content;
    private TextView no;
    private TextView yes;
    private RelativeLayout close;
    private DialogListener dialogListener;

    public DialogCloseBtn(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }

    public DialogCloseBtn setCreditOfflineDialogListener(DialogListener creditOfflineDialogListener) {
        this.dialogListener = creditOfflineDialogListener;
        return this;
    }

    @SuppressLint("InflateParams")
    public DialogCloseBtn builder() {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_close_btn, null);
        // 设置Dialog宽高
        view.setMinimumWidth(466);
        view.setMinimumHeight(248);
        // 定义Dialog布局和参数
        content = (TextView) view.findViewById(R.id.tv_content);
        no = (TextView) view.findViewById(R.id.tv_no);
        yes = (TextView) view.findViewById(R.id.tv_yes);
        close = (RelativeLayout) view.findViewById(R.id.rl_close);
        no.setOnClickListener(DialogCloseBtn.this);
        yes.setOnClickListener(DialogCloseBtn.this);
        close.setOnClickListener(DialogCloseBtn.this);
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

    public void dismiss() {
        dialog.dismiss();
        flag = false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_close:
                dismiss();
                break;
            case R.id.tv_yes:
                dismiss();
                dialogListener.onYes();
                break;
            case R.id.tv_no:
                dismiss();
                break;
            default:
                break;
        }
    }
}
