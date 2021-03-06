package com.beyonditsm.financial.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.entity.ProductBean;
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

    private List<ProductBean> list;

    java.text.DecimalFormat df = new java.text.DecimalFormat("#0.00");//保留小数
    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showStubImage(R.mipmap.pro_default) // 设置图片下载期间显示的图片
            .showImageForEmptyUri(R.mipmap.pro_default) // 设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.mipmap.pro_default) // 设置图片加载或解码过程中发生错误显示的图片
            .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
            .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
            .build(); // 创建配置过得DisplayImageOption对象


    public CreditAdapter(Context contex, List<ProductBean> list) {
        this.context = contex;
        this.list=list;
    }
    public void notifyChange(List<ProductBean> list){
        this.list=list;
        notifyDataSetChanged();
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
        ProductBean productEntity = list.get(position);
        ImageLoader.getInstance().displayImage(IFinancialUrl.BASE_IMAGE_URL + productEntity.getProductLogo(), viewHolder.ivBanl, options);
        if (!TextUtils.isEmpty(productEntity.getProductName())) {
            viewHolder.tvBank.setText(productEntity.getProductName());
        }
        if (!TextUtils.isEmpty(productEntity.getPreLoanPeriod())) {
            viewHolder.tvTime.setText("放款周期:" + productEntity.getPreLoanPeriod() + "个工作日");
        }
        if (!TextUtils.isEmpty(productEntity.getMaxLoanRate())||!TextUtils.isEmpty(productEntity.getMinLoanRate())) {
            if (Double.valueOf(productEntity.getMinLoanRate()) - Double.valueOf(productEntity.getMaxLoanRate()) == 0) {
                viewHolder.tvRate.setText("月利率:" + productEntity.getMinLoanRate() + "%");
            } else {
                viewHolder.tvRate.setText("月利率:" + productEntity.getMinLoanRate() + "%~" + productEntity.getMaxLoanRate() + "%");
            }
        }

//        if (!TextUtils.isEmpty(productEntity.get_totalRath())) {
//            viewHolder.tvTotal.setText( df.format(Double.valueOf(productEntity.get_totalRath())));
//        }
//        if (!TextUtils.isEmpty(productEntity.get_monthlyPayment())) {
//            viewHolder.tvReim.setText(df.format(Double.valueOf(productEntity.get_monthlyPayment())) );
//        }
        if (!TextUtils.isEmpty(productEntity.getDisposableFeeType())){
            switch (productEntity.getDisposableFeeType()){

                case "1"://展示百分比
                    if (!TextUtils.isEmpty(productEntity.getDisposableRateMax()) || !TextUtils.isEmpty(productEntity.getDisposableRateMin())) {
                        if (TextUtils.isEmpty(productEntity.getDisposableRateMax())){
                            productEntity.setDisposableRateMax("0.0");
                        }
                        if (TextUtils.isEmpty(productEntity.getDisposableRateMin())){
                            productEntity.setDisposableRateMin("0.0");
                        }
                        if (Double.valueOf(productEntity.getDisposableRateMax()) - Double.valueOf(productEntity.getDisposableRateMin()) == 0) {
                            viewHolder.onepay.setText("一次性收费：" + productEntity.getDisposableRateMax() + "%");
                        } else {
                            viewHolder.onepay.setText("一次性收费：" + productEntity.getDisposableRateMin() + "%" + "~" + productEntity.getDisposableRateMax() + "%");
                        }

                    }
                    break;
                case "2"://展示元
                    if (!TextUtils.isEmpty(productEntity.getOneTimeCharge()))
                        viewHolder.onepay.setText(productEntity.getOneTimeCharge()+"元");
                    break;
            }
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
