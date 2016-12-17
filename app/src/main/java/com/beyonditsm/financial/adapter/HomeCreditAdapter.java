package com.beyonditsm.financial.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.entity.HomeHotProductBean;
import com.beyonditsm.financial.http.IFinancialUrl;
import com.tandong.sa.zUImageLoader.core.DisplayImageOptions;
import com.tandong.sa.zUImageLoader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2015/12/8
 */
public class HomeCreditAdapter extends BaseAdapter {
    private List<HomeHotProductBean> list;
    private final LayoutInflater inflater;

    @SuppressWarnings("deprecation")
    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showStubImage(R.mipmap.pro_default) // 设置图片下载期间显示的图片
            .showImageForEmptyUri(R.mipmap.pro_default) // 设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.mipmap.pro_default) // 设置图片加载或解码过程中发生错误显示的图片
            .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
            .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
            .build(); // 创建配置过得DisplayImageOption对象

    public HomeCreditAdapter(Context context,List<HomeHotProductBean> list) {
        this.list = list;
        inflater = LayoutInflater.from(context);
    }
    public void setDatas(List<HomeHotProductBean> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list!=null?list.size():0;
    }

    @Override
    public Object getItem(int position) {
        return list!=null?list.get(position):null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.home_credit_item,null);
            holder.tvRate = (TextView) convertView.findViewById(R.id.rate);
            holder.tvCreditName = (TextView) convertView.findViewById(R.id.creditName);
            holder.tvCycle = (TextView) convertView.findViewById(R.id.cycle);
//            holder.tvPass = (TextView) convertView.findViewById(R.id.pass);
//            holder.tvLoan = (TextView) convertView.findViewById(R.id.loan);
            holder.ivProductLogo = (ImageView) convertView.findViewById(R.id.image_logo);
            holder.fast = (TextView) convertView.findViewById(R.id.fast);
            holder.sd = (TextView) convertView.findViewById(R.id.sd);
            holder.down = (TextView) convertView.findViewById(R.id.rate_down);
            holder.tvSymbol= (TextView) convertView.findViewById(R.id.tvSymbol);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
//        Map<String,String> map=list.get(position).getProductCharas();
//        if(map==null||map.size()==0){
//            holder.fast.setVisibility(View.GONE);
//            holder.sd.setVisibility(View.GONE);
//            holder.down.setVisibility(View.GONE);
//        }else{
//            String FAST = "2c908c7651474aa40151478b13820002";
//            if(map.get(FAST)!=null){
//                holder.fast.setVisibility(View.VISIBLE);
//            }else {
//                holder.fast.setVisibility(View.GONE);
//
//            }
//
//            String BIANJIE = "2c908c7651474aa40151478beed50003";
//            if(map.get(BIANJIE)!=null){
//                holder.sd.setVisibility(View.VISIBLE);
//            }else {
//                holder.sd.setVisibility(View.GONE);
//
//            }
//
//            String LILV = "2c908c7651474aa40151478c2fc70004";
//            if(map.get(LILV)!=null){
//                holder.down.setVisibility(View.VISIBLE);
//            }else {
//                holder.down.setVisibility(View.GONE);
//
//            }
//        }

//        HomeHotProductBean hotProductEntity =  list.get(position);
         ImageLoader.getInstance().displayImage(IFinancialUrl.BASE_IMAGE_URL + list.get(position).getAndroidProductLogo(), holder.ivProductLogo, options);

        if(list.get(position).getMinLoanPeriod().equals(list.get(position).getMaxLoanRate())){
            holder.tvRate.setText(list.get(position).getMinLoanPeriod()+"");
            holder.tvRate.setTextSize(28);
            holder.tvSymbol.setVisibility(View.VISIBLE);
            holder.tvSymbol.setTextSize(18);

        }else{
//            DecimalFormat df = new DecimalFormat("0.00");
            holder.tvRate.setText(list.get(position).getMinLoanPeriod() + "%~" + list.get(position).getMaxLoanRate()+"%");
            holder.tvRate.setTextSize(14);
            holder.tvSymbol.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(list.get(position).getProductName())) {
            holder.tvCreditName.setText(list.get(position).getProductName());
        }
        if (list.get(position).getPreLoanPeriod()!=null) {
            holder.tvCycle.setText("放款周期：" + list.get(position).getPreLoanPeriod() + "个工作日");
        }
        return convertView;
    }
    class ViewHolder{
         TextView tvRate,tvCreditName,tvCycle,tvSymbol;
        ImageView ivProductLogo;
        TextView fast,sd,down;
    }
}
