package com.beyonditsm.financial.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.entity.CreditSpeedEntity;
import com.beyonditsm.financial.http.IFinancialUrl;
import com.tandong.sa.zUImageLoader.core.DisplayImageOptions;
import com.tandong.sa.zUImageLoader.core.ImageLoader;

import java.util.List;

import static com.beyonditsm.financial.R.id.tvRate;

/**
 * 急借通列表adapter
 * Created by Administrator on 2016/9/26 0026.
 */

public class SpeedCreditAdapter extends BaseAdapter {

    java.text.DecimalFormat df = new java.text.DecimalFormat("#0.0");//保留小数

    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showStubImage(R.mipmap.pro_default) // 设置图片下载期间显示的图片
            .showImageForEmptyUri(R.mipmap.pro_default) // 设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.mipmap.pro_default) // 设置图片加载或解码过程中发生错误显示的图片
            .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
            .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
            .build(); // 创建配置过得DisplayImageOption对象
    private Context context;
    private List<CreditSpeedEntity> list;
    public SpeedCreditAdapter(Context context, List<CreditSpeedEntity> list) {
        this.context = context;
        this.list = list;
    }

    public void notifyChange(List<CreditSpeedEntity> list){
        this.list=list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if (convertView==null){
            holder = new Holder();
            convertView = View.inflate(context, R.layout.speed_credit_item,null);
            holder.ivProductLogo = (ImageView) convertView.findViewById(R.id.iv_productLogo);
            holder.tvProductName = (TextView) convertView.findViewById(R.id.tv_productName);
            holder.tvProductChara = (TextView) convertView.findViewById(R.id.tv_productChara);
            holder.tvCostDescribe = (TextView) convertView.findViewById(R.id.tv_costDescribe);
            holder.tvTime = (TextView) convertView.findViewById(R.id.tv_time);
            holder.tvValue = (TextView) convertView.findViewById(R.id.tv_value);
            holder.tvRate = (TextView) convertView.findViewById(R.id.tv_rate);
            holder.tvLoanPeriod = (TextView) convertView.findViewById(R.id.tv_loanPeriod);
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }
        CreditSpeedEntity creditSpeedEntity = list.get(position);

        ImageLoader.getInstance().displayImage(IFinancialUrl.BASE_IMAGE_URL + creditSpeedEntity.getImageLogoPath(), holder.ivProductLogo, options);

        if (!TextUtils.isEmpty(creditSpeedEntity.getProductName())) {
            holder.tvProductName.setText(creditSpeedEntity.getProductName());
        }
        if (!TextUtils.isEmpty(creditSpeedEntity.getProductChara())) {
            holder.tvProductChara.setText(creditSpeedEntity.getProductChara());
        }
        if (!TextUtils.isEmpty(creditSpeedEntity.getCostDescribe())) {
            holder.tvCostDescribe.setText(creditSpeedEntity.getCostDescribe());
        }
        if (!TextUtils.isEmpty(creditSpeedEntity.getRepaymentPeriod())) {
            holder.tvTime.setText(creditSpeedEntity.getRepaymentPeriod());
        }
        double minVal = Double.valueOf(creditSpeedEntity.getMinVal());
        double maxVal = Double.valueOf(creditSpeedEntity.getMaxVal());
        holder.tvValue.setText(df.format(minVal/10000)+"-"+df.format(maxVal/10000)+"万");
//        holder.tvRate.setText(creditSpeedEntity.getMinRate()+"-"+creditSpeedEntity.getMaxRate());
        if (!TextUtils.isEmpty(creditSpeedEntity.getMinRate()) || !TextUtils.isEmpty(creditSpeedEntity.getMaxRate())) {
            if (Double.valueOf(creditSpeedEntity.getMaxRate()) - Double.valueOf(creditSpeedEntity.getMinRate()) == 0) {
                holder.tvRate.setText(creditSpeedEntity.getMinRate()+"%(天)");
            } else {
                holder.tvRate.setText( creditSpeedEntity.getMinRate() + "%-" + creditSpeedEntity.getMaxRate()+"%(天)");
            }
        }
        if (!TextUtils.isEmpty(String.valueOf(creditSpeedEntity.getLoanPeriod()))) {
            holder.tvLoanPeriod.setText(creditSpeedEntity.getLoanPeriod() + "个工作日");
        }

        return convertView;
    }

    class Holder{
        ImageView ivProductLogo;
        TextView tvProductName;
        TextView tvProductChara;
        TextView tvCostDescribe;
        TextView tvTime;
        TextView tvValue;
        TextView tvRate;
        TextView tvLoanPeriod;
    }
}
