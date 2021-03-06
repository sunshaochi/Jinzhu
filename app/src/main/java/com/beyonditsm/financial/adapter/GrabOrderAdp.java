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
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.entity.GrabOrderBean;
import com.beyonditsm.financial.http.IFinancialUrl;
import com.tandong.sa.zUImageLoader.core.DisplayImageOptions;
import com.tandong.sa.zUImageLoader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 信贷经理抢单适配器
 * Created by Yang on 2015/11/16 0016.
 */
public class GrabOrderAdp extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<GrabOrderBean.RowsEntity> datas = new ArrayList<>();
    //    private HashMap<Integer, String> isSelected;
    @SuppressWarnings("deprecation")
    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showStubImage(R.mipmap.pro_default) // 设置图片下载期间显示的图片
            .showImageForEmptyUri(R.mipmap.pro_default) // 设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.mipmap.pro_default) // 设置图片加载或解码过程中发生错误显示的图片
            .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
            .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
            .build(); // 创建配置过得DisplayImageOption对象


    public GrabOrderAdp(Context context, List<GrabOrderBean.RowsEntity> datas) {
        this.context = context;
        this.datas = datas;
        inflater = LayoutInflater.from(context);
//        isSelected = new HashMap<Integer, String>();
//        indata();
    }

//    private void indata() {
//        for (int i = 0; i < datas.size(); i++) {
//            getIsSelected().put(i, "抢单");
//        }
//    }
//
//    public HashMap<Integer, String> getIsSelected() {
//        return isSelected;
//    }

    public void UpData(List<GrabOrderBean.RowsEntity> list) {
        this.datas = list;
        notifyDataSetChanged();
//        indata();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
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
            view = inflater.inflate(R.layout.graborder_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        if (datas != null) {
            holder.graborderbtn.setText("抢单");
            holder.graborderbtn.setBackgroundResource(R.drawable.cre_btn_bg_orange);
            holder.graborderbtn.setTag(i);
            holder.graborderbtn.setEnabled(true);
            setOnclick(holder, i);
            if (!TextUtils.isEmpty(datas.get(i).getProductName())) {
                holder.tvmoney.setText(datas.get(i).getProductName() + "");
            }
            if (!TextUtils.isEmpty(datas.get(i).getBadPercent())) {
                holder.tvdefaultrate.setText("预测违约率：" + datas.get(i).getBadPercent() + "%");
            }
            if (!TextUtils.isEmpty(datas.get(i).getTotalAmount())) {
                holder.tvtotalmoney.setText(datas.get(i).getTotalAmount());
            }
            if (!TextUtils.isEmpty(datas.get(i).getUserName())) {
                holder.tvrate.setText("客户姓名：" + datas.get(i).getUserName());
            }
            ImageLoader.getInstance().displayImage(IFinancialUrl.SECURITY_IMAGE_URL + datas.get(i).getImageLogoPath(), holder.ivPic, options);
        }

        return view;
    }

    public void setOnclick(final ViewHolder holder, final int i) {
        holder.graborderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                int position = (int) view.getTag();

            }
        });
    }



    public class ViewHolder {
        public final ImageView ivPic;//显示图片
        public final TextView tvmoney;//贷款金额
        public final TextView tvrate;//利率
        public final TextView tvdefaultrate;//违约率
        public final TextView tvtotalmoney;//贷款总额
        public final Button graborderbtn;//抢单按钮
        public final View root;

        public ViewHolder(View root) {
            ivPic = (ImageView) root.findViewById(R.id.ivPic);
            tvmoney = (TextView) root.findViewById(R.id.tv_money);
            tvrate = (TextView) root.findViewById(R.id.tv_rate);
            tvdefaultrate = (TextView) root.findViewById(R.id.tv_default_rate);
            tvtotalmoney = (TextView) root.findViewById(R.id.tv_total_money);
            graborderbtn = (Button) root.findViewById(R.id.graborder_btn);
            this.root = root;
        }
    }
}
