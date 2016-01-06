package com.beyonditsm.financial.widget;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.beyonditsm.financial.R;

/**
 * Created by wangbin on 15/12/17.
 */
public class FinalLoadDialog extends Dialog{
    private static final int CHANGE_TITLE_WHAT = 1;
    private static final int CHNAGE_TITLE_DELAYMILLIS = 300;
    private static final int MAX_SUFFIX_NUMBER = 3;
    private static final char SUFFIX = '.';
    private TextView tv;
    private TextView tv_point;
    private ImageView ivIcon;
    private boolean cancelable = true;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        private int num = 0;

        public void handleMessage(android.os.Message msg) {
            if (msg.what == CHANGE_TITLE_WHAT) {
                StringBuilder builder = new StringBuilder();
                if (num >= MAX_SUFFIX_NUMBER) {
                    num = 0;
                }
                num++;
                for (int i = 0; i < num; i++) {
                    builder.append(SUFFIX);
                }
                tv_point.setText(builder.toString());
                if (isShowing()) {
                    handler.sendEmptyMessageDelayed(CHANGE_TITLE_WHAT,
                            CHNAGE_TITLE_DELAYMILLIS);
                } else {
                    num = 0;
                }
            }
        };
    };

    public FinalLoadDialog(Context context) {
        super(context, R.style.Dialog_bocop);
        init();
    }

    private void init() {
        View contentView = View.inflate(getContext(),
                R.layout.load_dialog, null);
        setContentView(contentView);

        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cancelable) {
                    dismiss();
                }
            }
        });
        tv = (TextView) findViewById(R.id.tv);
        tv_point = (TextView) findViewById(R.id.tv_point);
        ivIcon=(ImageView) findViewById(R.id.ivIcon);
        initAnim();
        getWindow().setWindowAnimations(R.anim.alpha_in);
    }

    private void initAnim() {
        ivIcon.setBackgroundResource(R.anim.load_anim);
        AnimationDrawable animation=(AnimationDrawable) ivIcon.getBackground();
        animation.setOneShot(false);
        if(animation.isRunning())//是否正在运行？
        {
            animation.stop();//停止
        }
        animation.start();//启动
    }

    @Override
    public void show() {
//		iv_route.startAnimation(mAnim);
        handler.sendEmptyMessage(CHANGE_TITLE_WHAT);
        super.show();
    }

    @Override
    public void dismiss() {
//		mAnim.cancel();
        super.dismiss();
    }

    @Override
    public void setCancelable(boolean flag) {
        cancelable = flag;
        super.setCancelable(flag);
    }

    @Override
    public void setTitle(CharSequence title) {
        tv.setText(title);
    }

    @Override
    public void setTitle(int titleId) {
        setTitle(getContext().getString(titleId));
    }

}
