package com.beyonditsm.financial.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.entity.PagerColor;
import com.beyonditsm.financial.view.RoundProgressBar;

import java.util.List;

/**
 * 打工挣钱适配器item
 * Created by wangbin on 15/12/13.
 */
public class WorkFragment extends LazyFragment{

    /** 页面是否已经加载 */
    boolean isLoad = false;
    private TextView tvUpName;
    private TextView tvDownName;
    private TextView tvUp;
    private TextView tvDown;
    private RoundProgressBar rpb_up;
    private RoundProgressBar rpb_down;

    private PagerColor up = new PagerColor();
    private PagerColor down = new PagerColor();

    private int i=0;
    private int j=0;

//    private View workView;


    // 标志位，标志已经初始化完成。
    private boolean isPrepared;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View workView = inflater.inflate(R.layout.item_work, container, false);
        //XXX初始化view的各控件
        tvUpName = (TextView) workView.findViewById(R.id.tv_up_name);
        tvDownName = (TextView) workView.findViewById(R.id.tv_down_name);
        tvUp = (TextView) workView.findViewById(R.id.tv_up);
        tvDown = (TextView) workView.findViewById(R.id.tv_down);
        rpb_up = (RoundProgressBar) workView.findViewById(R.id.rpb_up);
        rpb_down = (RoundProgressBar) workView.findViewById(R.id.rpb_down);
        isPrepared = true;
        lazyLoad();
        return workView;
    }
    @Override
    protected void lazyLoad() {
        if(!isPrepared || !isVisible) {
            return;
        }
        if(isLoad){
            return;
        }
        isLoad=true;
        //填充各控件的数据
        Bundle bundle = getArguments();
        List<PagerColor> mList=bundle.getParcelableArrayList("work_list");
        up=mList.get(0);
        down=mList.get(1);

        rpb_up.setProgress(up.getProgress());
        rpb_up.setTextColor(Color.parseColor(up.getColor()));
        rpb_up.setCricleProgressColor(Color.parseColor(up.getColor()));
        tvUpName.setText(up.getTvname());
        tvUpName.setTextColor(Color.parseColor(up.getColor()));
        tvUp.setText(up.getTv());
        tvUp.setTextColor(Color.parseColor(up.getColor()));

        rpb_down.setProgress(down.getProgress());
        rpb_down.setTextColor(Color.parseColor(down.getColor()));
        rpb_down.setCricleProgressColor(Color.parseColor(down.getColor()));
        tvDownName.setText(down.getTvname());
        tvDownName.setTextColor(Color.parseColor(down.getColor()));
        tvDown.setText(down.getTv());
        tvDown.setTextColor(Color.parseColor(down.getColor()));
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (i<up.getProgress()) {
                    i++;
                    Message message = handler.obtainMessage();
                    message.what=0;
                    message.arg1 = i;
                    handler.sendMessage(message);
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (j<down.getProgress()) {
                    j++;
                    Message message = handler.obtainMessage();
                    message.what=1;
                    message.arg1 = j;
                    handler.sendMessage(message);

                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    rpb_up.setProgress(msg.arg1);
                    break;
                case 1:
                    rpb_down.setProgress(msg.arg1);
                    break;
            }

        }
    };

//    @Override
//    public View initView(LayoutInflater inflater) {
//        workView=inflater.inflate(R.layout.item_work,null);
//         tvUpName = (TextView) workView.findViewById(R.id.tv_up_name);
//         tvDownName = (TextView) workView.findViewById(R.id.tv_down_name);
//         tvUp = (TextView) workView.findViewById(R.id.tv_up);
//         tvDown = (TextView) workView.findViewById(R.id.tv_down);
//         rpb_up = (RoundProgressBar) workView.findViewById(R.id.rpb_up);
//         rpb_down = (RoundProgressBar) workView.findViewById(R.id.rpb_down);
//
//          if(!isLoad){
//              Bundle bundle = getArguments();
//              List<PagerColor> mList=bundle.getParcelableArrayList("work_list");
//              up=mList.get(0);
//              down=mList.get(1);
//
//              rpb_up.setProgress(up.getProgress());
//              rpb_up.setTextColor(Color.parseColor(up.getColor()));
//              rpb_up.setCricleProgressColor(Color.parseColor(up.getColor()));
//              tvUpName.setText(up.getTvname());
//              tvUpName.setTextColor(Color.parseColor(up.getColor()));
//              tvUp.setText(up.getTv());
//              tvUp.setTextColor(Color.parseColor(up.getColor()));
//
//              rpb_down.setProgress(down.getProgress());
//              rpb_down.setTextColor(Color.parseColor(down.getColor()));
//              rpb_down.setCricleProgressColor(Color.parseColor(down.getColor()));
//              tvDownName.setText(down.getTvname());
//              tvDownName.setTextColor(Color.parseColor(down.getColor()));
//              tvDown.setText(down.getTv());
//              tvDown.setTextColor(Color.parseColor(down.getColor()));
//          }
//        return workView;
//    }
//
//    @Override
//    public void initData(Bundle savedInstanceState) {
//    }
//
//    @Override
//    public void setListener() {
//
//    }
//
//    /**
//     * 懒加载,取消预加载
//     */
//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        // 如果可见而且页面没有加载，load
//        if (isVisibleToUser && !isLoad) {
//            isLoad = true;
//            Bundle bundle = getArguments();
//            List<PagerColor> mList=bundle.getParcelableArrayList("work_list");
//            up=mList.get(0);
//            down=mList.get(1);
//
//            rpb_up.setProgress(up.getProgress());
//            rpb_up.setTextColor(Color.parseColor(up.getColor()));
//            rpb_up.setCricleProgressColor(Color.parseColor(up.getColor()));
//            tvUpName.setText(up.getTvname());
//            tvUpName.setTextColor(Color.parseColor(up.getColor()));
//            tvUp.setText(up.getTv());
//            tvUp.setTextColor(Color.parseColor(up.getColor()));
//
//            rpb_down.setProgress(down.getProgress());
//            rpb_down.setTextColor(Color.parseColor(down.getColor()));
//            rpb_down.setCricleProgressColor(Color.parseColor(down.getColor()));
//            tvDownName.setText(down.getTvname());
//            tvDownName.setTextColor(Color.parseColor(down.getColor()));
//            tvDown.setText(down.getTv());
//            tvDown.setTextColor(Color.parseColor(down.getColor()));
//        }
//        super.setUserVisibleHint(isVisibleToUser);
//    }
}
