package com.beyonditsm.financial.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.entity.HomeHotProductEntity;
import com.beyonditsm.financial.http.IFinancialUrl;
import com.tandong.sa.zUImageLoader.core.DisplayImageOptions;
import com.tandong.sa.zUImageLoader.core.ImageLoader;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/12/8.
 */
public class HomeCreditAdapter extends BaseAdapter {
    private List<HomeHotProductEntity> list;
    private Context context;
    private final LayoutInflater inflater;

    private String LILV="2c908c7651474aa40151478c2fc70004";//利率较低
    private String BIANJIE="2c908c7651474aa40151478beed50003";//手续便捷
    private String FAST="2c908c7651474aa40151478b13820002";//速度快

    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showStubImage(R.mipmap.pro_default) // 设置图片下载期间显示的图片
            .showImageForEmptyUri(R.mipmap.pro_default) // 设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.mipmap.pro_default) // 设置图片加载或解码过程中发生错误显示的图片
            .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
            .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
            .build(); // 创建配置过得DisplayImageOption对象

    public HomeCreditAdapter(Context context,List<HomeHotProductEntity> list) {
        this.context= context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }
    public void setDatas(List<HomeHotProductEntity> list){
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
        Map<String,String> map=list.get(position).getProductCharas();
        if(map==null||map.size()==0){
            holder.fast.setVisibility(View.GONE);
            holder.sd.setVisibility(View.GONE);
            holder.down.setVisibility(View.GONE);
        }else{
            if(map.get(FAST)!=null){
                holder.fast.setVisibility(View.VISIBLE);
            }else {
                holder.fast.setVisibility(View.GONE);

            }

            if(map.get(BIANJIE)!=null){
                holder.sd.setVisibility(View.VISIBLE);
            }else {
                holder.sd.setVisibility(View.GONE);

            }

            if(map.get(LILV)!=null){
                holder.down.setVisibility(View.VISIBLE);
            }else {
                holder.down.setVisibility(View.GONE);

            }
        }

         HomeHotProductEntity hotProductEntity =  list.get(position);
         ImageLoader.getInstance().displayImage(IFinancialUrl.BASE_IMAGE_URL + hotProductEntity.getImageLogoPath(), holder.ivProductLogo, options);

        if(hotProductEntity.getMonthlyRateMin()==hotProductEntity.getMonthlyRateMax()){
            holder.tvRate.setText(hotProductEntity.getMonthlyRateMin()+"");
            holder.tvRate.setTextSize(28);
            holder.tvSymbol.setVisibility(View.VISIBLE);
            holder.tvSymbol.setTextSize(18);

        }else{
//            DecimalFormat df = new DecimalFormat("0.00");
            holder.tvRate.setText(hotProductEntity.getMonthlyRateMin() + "%~" + hotProductEntity.getMonthlyRateMax()+"%");
            holder.tvRate.setTextSize(14);
            holder.tvSymbol.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(hotProductEntity.getProductName())) {
            holder.tvCreditName.setText(hotProductEntity.getProductName());
        }
        if (hotProductEntity.getLoanPeriod()!=null) {
            holder.tvCycle.setText("放款周期：" + hotProductEntity.getLoanPeriod() + "个工作日");
        }
        return convertView;
    }
    class ViewHolder{
         TextView tvRate,tvCreditName,tvCycle,tvSymbol;
        ImageView ivProductLogo;
        TextView fast,sd,down;
    }
}
