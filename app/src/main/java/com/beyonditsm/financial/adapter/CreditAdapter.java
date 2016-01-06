package com.beyonditsm.financial.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.entity.ProductInfo;
import com.beyonditsm.financial.http.IFinancialUrl;
import com.tandong.sa.zUImageLoader.core.DisplayImageOptions;
import com.tandong.sa.zUImageLoader.core.ImageLoader;

import java.util.List;


/**
 * 贷款适配器
 * Created by wangbin on 15/11/11.
 */
public class CreditAdapter extends BaseAdapter {
    private Context context;

    private List<ProductInfo> list;

    private double creditMoney,creditTime;

    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showStubImage(R.mipmap.pro_default) // 设置图片下载期间显示的图片
            .showImageForEmptyUri(R.mipmap.pro_default) // 设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.mipmap.pro_default) // 设置图片加载或解码过程中发生错误显示的图片
            .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
            .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
            .build(); // 创建配置过得DisplayImageOption对象


    public CreditAdapter(Context contex,List<ProductInfo> list,double creditMoney,double creditTime) {
        this.context = contex;
        this.list=list;
        this.creditMoney=creditMoney;
        this.creditTime=creditTime;
    }

    public void notifyChange(List<ProductInfo> list,double creditMoney,double creditTime){
        this.list=list;
        this.creditMoney=creditMoney;
        this.creditTime=creditTime;
        notifyDataSetChanged();;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.lv_credit_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder =(ViewHolder)convertView.getTag();
        }
        ProductInfo productEntity = list.get(position);
        ImageLoader.getInstance().displayImage(IFinancialUrl.BASE_IMAGE_URL + productEntity.getImageLogoPath(), viewHolder.ivBanl, options);
        viewHolder.tvBank.setText(productEntity.getProductName());
        viewHolder.tvTime.setText("放款周期:"+productEntity.getLoanPeriod()+"个工作日");
        if (Double.valueOf(productEntity.getMonthlyRathMin())-Double.valueOf(productEntity.getMonthlyRathMax())==0){
            viewHolder.tvRate.setText("月利率:"+productEntity.getMonthlyRathMin()+"%");
        }else {
            viewHolder.tvRate.setText("月利率:" + productEntity.getMonthlyRathMin() + "%~" + productEntity.getMonthlyRathMax() + "%");
        }
        viewHolder.tvTotal.setText("¥"+productEntity.get_totalRath());
        viewHolder.tvReim.setText("¥"+productEntity.get_monthlyPayment());
        if (Double.valueOf(productEntity.getDisposableRateMax())-Double.valueOf(productEntity.getDisposableRateMin())==0){
            viewHolder.onepay.setText("一次性收费:"+productEntity.getDisposableRateMax()+"%");
        }else {
            viewHolder.onepay.setText("一次性收费:" + productEntity.getDisposableRateMin() + "%~" + productEntity.getDisposableRateMax() + "%");
        }
        return convertView;
    }

    public class ViewHolder {
        public final ImageView ivBanl;//银行图标
        public final TextView tvBank;//银行贷款
        public final TextView tvTime;//放贷时间
        public final TextView tvRate;//利率
        public final TextView tvTotal;//总金额
        public final TextView tvReim;//还贷金额
        public final TextView onepay;//一次性收费
        public final View root;

        public ViewHolder(View root) {
            ivBanl = (ImageView) root.findViewById(R.id.ivBanl);
            tvBank = (TextView) root.findViewById(R.id.tvBank);
            tvTime = (TextView) root.findViewById(R.id.tvTime);
            tvRate = (TextView) root.findViewById(R.id.tvRate);
            tvTotal = (TextView) root.findViewById(R.id.tvTotal);
            tvReim = (TextView) root.findViewById(R.id.tvReim);
            onepay = (TextView) root.findViewById(R.id.onepay);
            this.root = root;
        }
    }
}
