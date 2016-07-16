package com.beyonditsm.financial.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.db.MessageDao;
import com.beyonditsm.financial.entity.MessageBean;
import com.beyonditsm.financial.util.FinancialUtil;
import com.beyonditsm.financial.view.LoadingView;
import com.beyonditsm.financial.view.pullfreshview.PullToRefreshBase;
import com.beyonditsm.financial.view.pullfreshview.PullToRefreshListView;
import com.beyonditsm.financial.widget.MyAlertDialog;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;


/**
 * 消息中心
 *
 * @author wangbin
 */
public class MessageActivity extends BaseActivity {
    @ViewInject(R.id.lvMess)
    private PullToRefreshListView lvMess;
    @ViewInject(R.id.loadView)
    private LoadingView loadView;

    private MyAdapter adapter;
    private int width;
    @Override
    public void setLayout() {
        setContentView(R.layout.activity_message);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void init(Bundle savedInstanceState) {
        loadView.loadComplete();
        setTopTitle("消息中心");
        setLeftTv("返回");
        setRightBtn("清空", new OnClickListener() {
            @Override
            public void onClick(View v) {
                MyAlertDialog dialog = new MyAlertDialog(MessageActivity.this);
                dialog.builder().setTitle("提示").setMsg("确认清空消息？").setPositiveButton("确定", new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MessageDao.deleteAllMes();
                        setDatas();
                    }
                }).setNegativeButton("取消", null).show();


            }
        });
        WindowManager windowmanager = (WindowManager) getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);
        width = windowmanager.getDefaultDisplay().getWidth();
        lvMess.setPullRefreshEnabled(false);
        lvMess.setPullLoadEnabled(false);
        lvMess.getRefreshableView().setDivider(null);
        lvMess.getRefreshableView().setVerticalScrollBarEnabled(false);
        lvMess.getRefreshableView().setSelector(
                new ColorDrawable(Color.TRANSPARENT));
        lvMess.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {

            @Override
            public void onPullDownToRefresh(
                    PullToRefreshBase<ListView> refreshView) {
                lvMess.onPullDownRefreshComplete();
                lvMess.setLastUpdatedLabel(FinancialUtil.getCurrentTime());
            }

            @Override
            public void onPullUpToRefresh(
                    PullToRefreshBase<ListView> refreshView) {

            }
        });

        setDatas();

    }

//    @OnClick(R.id.rl_back)
//    public void toClick(View v) {
//        switch (v.getId()) {
//            case R.id.rl_back:
//                gotoActivity(MainActivity.class, true);
//                break;
//        }
//    }

    private void setDatas() {
        loadView.loadComplete();
        List<MessageBean> datas = MessageDao.findMess();
        if (datas.size() == 0) {
            loadView.noContent();
            loadView.setNoContentTxt("亲，你暂时没有消息");
            setRightVG(false);
        } else {
            setRightVG(true);
        }
        if (adapter == null) {
            adapter = new MyAdapter(datas);
            lvMess.getRefreshableView().setAdapter(adapter);
        } else {
            adapter.notifyChange(datas);
        }

    }


    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (receiver == null) {
            receiver = new MessageBroadCastReceiver();
        }
        registerReceiver(receiver, new IntentFilter(MESSAGE));
    }

    private MessageBroadCastReceiver receiver;
    public static final String MESSAGE = "com.update.message";

    class MessageBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            setDatas();
        }
    }

    private class MyAdapter extends BaseAdapter {

        public List<MessageBean> list;

        public MyAdapter(List<MessageBean> list) {
            this.list = list;
        }

        public void notifyChange(List<MessageBean> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        private HorizontalScrollView resetView;
        private Holder vh;
        private int x1, y1, x2, y2;

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getCount() {

            return list.size();

        }

        @SuppressLint("SetTextI18n")
        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {

            Holder holder;
            if (convertView != null
                    && convertView instanceof HorizontalScrollView) {
                holder = (Holder) convertView.getTag();
            } else {
                holder = new Holder();
                convertView = View.inflate(MessageActivity.this,
                        R.layout.msgact_item, null);
                convertView.setTag(holder);
                holder.hSView = (HorizontalScrollView) convertView
                        .findViewById(R.id.msgact_hsv);
                holder.rl = (LinearLayout) convertView
                        .findViewById(R.id.msgact_rl);
                // holder.tv_email = (TextView)
                // convertView.findViewById(R.id.tv_email);
                holder.action = convertView.findViewById(R.id.action_delete);
                holder.tvTitle = (TextView) convertView
                        .findViewById(R.id.tvTitle);
                holder.tvTime = (TextView) convertView
                        .findViewById(R.id.tvTime);
                holder.tvOrder = (TextView) convertView
                        .findViewById(R.id.tvOrder);

                LayoutParams lp = holder.rl.getLayoutParams();
                lp.width = width;
            }



            holder.tvOrder.setText("内容："
                    + list.get(position).getMessage());
            holder.tvTitle.setText(list.get(position).getTitle());
            holder.tvTime.setText(list.get(position).getTime());

            // 设置监听事件
            convertView.setOnTouchListener(new View.OnTouchListener() {
                @SuppressLint("ClickableViewAccessibility")
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            if (resetView != null) {
                                resetView.smoothScrollTo(0, 0);
                            }
                            x1 = (int) event.getRawX();// 得到相对应屏幕左上角的坐标
                            y1 = (int) event.getRawY();
                            break;

                        case MotionEvent.ACTION_UP:
                            x2 = (int) event.getRawX();
                            y2 = (int) event.getRawY();
                            double distance = Math.sqrt(Math.abs(x1 - x2)
                                    * Math.abs(x1 - x2) + Math.abs(y1 - y2)
                                    * Math.abs(y1 - y2));
                            if (distance < 15) { // 距离较小，当作click事件来处理

                                return false;
                            } else {
                                // 获得ViewHolder
                                vh = (Holder) v.getTag();
                                // 获得HorizontalScrollView滑动的水平方向值.
                                int scrollX = vh.hSView.getScrollX();
                                // 获得操作区域的长度
                                int actionW = vh.action.getWidth();
                                // 注意使用smoothScrollTo,这样效果看起来比较圆滑,不生硬
                                // 如果水平方向的移动值<操作区域的长度的一半,就复原
                                if (scrollX < actionW / 2) {
                                    vh.hSView.smoothScrollTo(0, 0);
                                } else// 否则的话显示操作区域
                                {
                                    vh.hSView.smoothScrollTo(actionW, 0);
                                    resetView = vh.hSView;
                                }
                                return true;
                            }

                    }
                    return false;
                }
            });

            holder.action.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // 删除地址
                    // deleteAddress(position, address.id);
                    MessageDao.deleteMes(list.get(position).getMsg_id());
                    setDatas();
                    vh.hSView.smoothScrollTo(0, 0);
                }
            });

            // 这里防止删除一条item后,ListView处于操作状态,直接还原
            if (holder.hSView.getScrollX() != 0) {
                holder.hSView.scrollTo(0, 0);
            }

            return convertView;
        }

    }

    class Holder {
        HorizontalScrollView hSView;
        LinearLayout rl;
        View action;
        TextView tvTitle;
        TextView tvTime;
        TextView tvOrder;
    }

}
