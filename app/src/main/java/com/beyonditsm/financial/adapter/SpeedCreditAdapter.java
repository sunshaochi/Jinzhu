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
    private String protype;//贷款类型
    private String maxloanperiod;//最大期限
    private String minloanperiod;//最小期限
    private String ratatype;//利率类型
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
            holder.ivProductLogo = (ImageView) convertView.findViewById(R.id.iv_productLogo);//图片
            holder.tvProductName = (TextView) convertView.findViewById(R.id.tv_productName);//名字
            holder.tvProductChara = (TextView) convertView.findViewById(R.id.tv_productChara);//介绍
            holder.tvCostDescribe = (TextView) convertView.findViewById(R.id.tv_costDescribe);//要求
            holder.tvTime = (TextView) convertView.findViewById(R.id.tv_time);//期限
            holder.tvValue = (TextView) convertView.findViewById(R.id.tv_value);//额度
            holder.tvRate = (TextView) convertView.findViewById(R.id.tv_rate);//利率
            holder.tvLoanPeriod = (TextView) convertView.findViewById(R.id.tv_loanPeriod);//放款工作日
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }
        CreditSpeedEntity creditSpeedEntity = list.get(position);

        ImageLoader.getInstance().displayImage(IFinancialUrl.BASE_IMAGE_URL + creditSpeedEntity.getImageLogoPath(), holder.ivProductLogo, options);

        if (!TextUtils.isEmpty(creditSpeedEntity.getProductName())) {
            holder.tvProductName.setText(creditSpeedEntity.getProductName());
        }
        if (!TextUtils.isEmpty(creditSpeedEntity.getProductChara())) {//产品介绍
            holder.tvProductChara.setText(creditSpeedEntity.getProductChara());
        }
        if (!TextUtils.isEmpty(creditSpeedEntity.getCostDescribe())) {//产品要求
            holder.tvCostDescribe.setText(creditSpeedEntity.getCostDescribe());
        }
        if(!TextUtils.isEmpty(creditSpeedEntity.getMaxLoanPeriod())){//最大期限不为空
            maxloanperiod=creditSpeedEntity.getMaxLoanPeriod();
        }
        if(!TextUtils.isEmpty(creditSpeedEntity.getMinLoanPeriod())){//最小期限不为空
            minloanperiod=creditSpeedEntity.getMinLoanPeriod();
        }
        if(!TextUtils.isEmpty(creditSpeedEntity.getLoanPeriodType())){//贷款期限类型(1.年2.月3.周4.日5.期数)
            protype=creditSpeedEntity.getLoanPeriodType().toString();
            if(protype.equals("1")){
                if (!TextUtils.isEmpty(minloanperiod)||!TextUtils.isEmpty(maxloanperiod)) {//最高最低还款期限
                    if (Double.valueOf(minloanperiod) - Double.valueOf(maxloanperiod) == 0) {
                        holder.tvTime.setText(minloanperiod+"年");
                    } else {
                        holder.tvRate.setText( minloanperiod + "-" + maxloanperiod+"年");
                    }
                }

                }
            else if(protype.equals("2")){
                if (!TextUtils.isEmpty(minloanperiod)||!TextUtils.isEmpty(maxloanperiod)) {//最高最低还款期限
                    if (Double.valueOf(minloanperiod) - Double.valueOf(maxloanperiod) == 0) {
                        holder.tvTime.setText(minloanperiod+"月");
                    } else {
                        holder.tvRate.setText( minloanperiod + "-" + maxloanperiod+"月");
                    }
                }
            }
            else if(protype.equals("3")){
                if (!TextUtils.isEmpty(minloanperiod)||!TextUtils.isEmpty(maxloanperiod)) {//最高最低还款期限
                    if (Double.valueOf(minloanperiod) - Double.valueOf(maxloanperiod) == 0) {
                        holder.tvTime.setText(minloanperiod+"周");
                    } else {
                        holder.tvRate.setText( minloanperiod + "-" + maxloanperiod+"周");
                    }
                }
            }
            else if(protype.equals("4")){
                if (!TextUtils.isEmpty(minloanperiod)||!TextUtils.isEmpty(maxloanperiod)) {//最高最低还款期限
                    if (Double.valueOf(minloanperiod) - Double.valueOf(maxloanperiod) == 0) {
                        holder.tvTime.setText(minloanperiod+"日");
                    } else {
                        holder.tvRate.setText( minloanperiod + "-" + maxloanperiod+"日");
                    }
                }
            }
            else if(protype.equals("5")){
                if (!TextUtils.isEmpty(minloanperiod)||!TextUtils.isEmpty(maxloanperiod)) {//最高最低还款期限
                    if (Double.valueOf(minloanperiod) - Double.valueOf(maxloanperiod) == 0) {
                        holder.tvTime.setText(minloanperiod+"期数");
                    } else {
                        holder.tvRate.setText( minloanperiod + "-" + maxloanperiod+"期数");
                    }
                }
            }
            }

        double minVal = Double.valueOf(creditSpeedEntity.getMinVal());//最小额度
        double maxVal = Double.valueOf(creditSpeedEntity.getMaxVal());//最大额度
        holder.tvValue.setText(df.format(minVal/10000)+"-"+df.format(maxVal/10000)+"万");//额度
//        holder.tvRate.setText(creditSpeedEntity.getMinRate()+"-"+creditSpeedEntity.getMaxRate());
        if(!TextUtils.isEmpty(creditSpeedEntity.getLoanRateType())) {//利率类型(1.月利率2.日利率)
            ratatype=creditSpeedEntity.getLoanRateType();
            if(ratatype.equals("1")){
            if (!TextUtils.isEmpty(creditSpeedEntity.getMinRate()) || !TextUtils.isEmpty(creditSpeedEntity.getMaxRate())) {//利率
                if (Double.valueOf(creditSpeedEntity.getMaxRate()) - Double.valueOf(creditSpeedEntity.getMinRate()) == 0) {
                    holder.tvRate.setText(creditSpeedEntity.getMinRate() + "%(月)");
                } else {
                    holder.tvRate.setText(creditSpeedEntity.getMinRate() + "%-" + creditSpeedEntity.getMaxRate() + "%(月)");
                }
            }
            }else if(ratatype.equals("2")){
                if (!TextUtils.isEmpty(creditSpeedEntity.getMinRate()) || !TextUtils.isEmpty(creditSpeedEntity.getMaxRate())) {//利率
                    if (Double.valueOf(creditSpeedEntity.getMaxRate()) - Double.valueOf(creditSpeedEntity.getMinRate()) == 0) {
                        holder.tvRate.setText(creditSpeedEntity.getMinRate() + "%(天)");
                    } else {
                        holder.tvRate.setText(creditSpeedEntity.getMinRate() + "%-" + creditSpeedEntity.getMaxRate() + "%(天)");
                    }
                }
            }
        }
        if (!TextUtils.isEmpty(String.valueOf(creditSpeedEntity.getLoanPeriod()))) {
            holder.tvLoanPeriod.setText(creditSpeedEntity.getLoanPeriod() + "个工作日");
        }

        return convertView;
    }

    class Holder{
        ImageView ivProductLogo;//产品图片
        TextView tvProductName;//产品名称
        TextView tvProductChara;//产品介绍
        TextView tvCostDescribe;//产品要求
        TextView tvTime;
        TextView tvValue;
        TextView tvRate;
        TextView tvLoanPeriod;
    }
}
