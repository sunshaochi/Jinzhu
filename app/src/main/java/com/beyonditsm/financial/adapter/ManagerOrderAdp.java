package com.beyonditsm.financial.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.entity.GrabOrderBean;
import com.beyonditsm.financial.http.IFinancialUrl;
import com.beyonditsm.financial.util.Arith;
import com.tandong.sa.zUImageLoader.core.DisplayImageOptions;
import com.tandong.sa.zUImageLoader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 信贷经理订单管理适配器
 * Created by Yang on 2015/11/16 0016.
 */
public class ManagerOrderAdp extends BaseAdapter {
    private LayoutInflater inflater;
    private List<GrabOrderBean.RowsEntity> datas = new ArrayList<>();
    java.text.DecimalFormat df = new java.text.DecimalFormat("#0.00");//保留小数
    @SuppressWarnings("deprecation")
    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showStubImage(R.mipmap.pro_default) // 设置图片下载期间显示的图片
            .showImageForEmptyUri(R.mipmap.pro_default) // 设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.mipmap.pro_default) // 设置图片加载或解码过程中发生错误显示的图片
            .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
            .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
            .build(); // 创建配置过得DisplayImageOption对象

    public ManagerOrderAdp(Context context, List<GrabOrderBean.RowsEntity> datas) {
        this.datas = datas;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int i) {
        return datas.get(i);
    }

    public void UpData(List<GrabOrderBean.RowsEntity> list) {
        this.datas = list;
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.managerorder_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        if (datas != null) {
            if (!TextUtils.isEmpty(datas.get(i).getProductName())) {
                holder.tvmoney.setText(datas.get(i).getProductName());
            }
            if (!TextUtils.isEmpty(datas.get(i).getBadPercent())) {
                holder.tvdefaultrate.setText("预测违约率：" + datas.get(i).getBadPercent() + "%");
            }
            if (!TextUtils.isEmpty(datas.get(i).getTotalAmount())) {
                Double totalMPay = Arith.sub(Double.valueOf(datas.get(i).getPeriodsAmount()) * Double.valueOf(datas.get(i).getTotalPeriods()), Double.valueOf(datas.get(i).getTotalAmount()));
                holder.tvtotalmoney.setText("¥" + df.format(totalMPay));
            }
//            if (!TextUtils.isEmpty(datas.get(i).getUserName())) {
            if (!TextUtils.isEmpty(datas.get(i).getUserName())) {
                holder.tvrate.setText("客户姓名：" + datas.get(i).getUserName());
            }else {
                holder.tvrate.setText("客户姓名：--");
            }
//                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                layoutParams.setMargins(20,0,0,0);
//                holder.llDefaultRate.setLayoutParams(layoutParams);
//            }else{
//                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                layoutParams.setMargins(0,0,0,0);
//                holder.llDefaultRate.setLayoutParams(layoutParams);
//            }
//            holder.tvpingji.setText("用户评级：" + "D");
            ImageLoader.getInstance().displayImage(IFinancialUrl.BASE_IMAGE_URL + datas.get(i).getImageLogoPath(), holder.ivPic, options);
        }
        return view;
    }

//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.graborder_btn:
//                final int i = (int) view.getTag();
//                String orderId = datas.get(i).getId();
//                String orderSts = datas.get(i).getOrderSts();
//                if (orderSts.equals("SUPPLEMENT_DATA")) {
//                    LogUtils.i("补件");
//                    RequestManager.getMangManger().orderbj(orderId, new RequestManager.CallBack() {
//                        @Override
//                        public void onSucess(String result) throws JSONException {
//                            MyToastUtils.showShortToast(context, "提交成功");
//                        }
//
//                        @Override
//                        public void onError(int status,String msg) {
//                            MyToastUtils.showShortToast(context, msg);
//                        }
//                    });
//                } else {
//                    LogUtils.i("提交");
//                    RequestManager.getMangManger().commitOrder(orderId, new RequestManager.CallBack() {
//                        @Override
//                        public void onSucess(String result) throws JSONException {
//                            MyToastUtils.showShortToast(context, "提交成功");
//                            datas.remove(i);
//                            notifyDataSetChanged();
//                        }
//
//                        @Override
//                        public void onError(int status,String msg) {
//                            MyToastUtils.showShortToast(context, msg);
//                        }
//                    });
//                }
//                break;
//        }
//    }

    public class ViewHolder {
        public final ImageView ivPic;//显示图片
        public final TextView tvmoney;//贷款名称
        public final TextView tvrate;//利率
        public final TextView tvdefaultrate;//违约率
        public final TextView tvtotalmoney;//贷款总额
        public final Button graborderbtn;//提交待提交按钮
        public final LinearLayout llDefaultRate;
        public final TextView tvpingji;//用户评级
        public final View root;

        public ViewHolder(View root) {
            ivPic = (ImageView) root.findViewById(R.id.ivPic);
            tvmoney = (TextView) root.findViewById(R.id.tv_money);
            tvrate = (TextView) root.findViewById(R.id.tv_rate);
            tvdefaultrate = (TextView) root.findViewById(R.id.tv_default_rate);
            tvtotalmoney = (TextView) root.findViewById(R.id.tv_total_money);
            graborderbtn = (Button) root.findViewById(R.id.graborder_btn);
            tvpingji = (TextView) root.findViewById(R.id.tv_pingji);
            llDefaultRate = (LinearLayout) root.findViewById(R.id.ll_default_rate);
            this.root = root;
        }
    }
}
