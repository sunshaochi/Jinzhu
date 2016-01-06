package com.beyonditsm.financial.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.beyonditsm.financial.R;

/**
 * Created by dsy on 15-11-26.
 * 描述:
 */
public class ContainView extends LinearLayout{
    private Context context;
    int counts=180;
    //通过布局文件生成view
    public ContainView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        initview();
    }

    private void initview(){
        View custormview=View.inflate(context, R.layout.contain_layout, null );
        ((CustomView)custormview.findViewById(R.id.view_custom)).setcountListener(new CustomView.getcountsListener() {
            @Override
            public int getcount() {
                return counts;
            }
        });
        addView(custormview);
    }

    public void setCounts(int counts){
        this.counts=counts;
    }
}
