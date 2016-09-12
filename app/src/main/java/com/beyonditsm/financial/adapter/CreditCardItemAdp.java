package com.beyonditsm.financial.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.user.creditcard.CreditCardInterface;
import com.beyonditsm.financial.entity.CreditCardEntity;
import com.tandong.sa.zUImageLoader.core.DisplayImageOptions;
import com.tandong.sa.zUImageLoader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2016/9/8.
 */
public class CreditCardItemAdp extends BaseAdapter {
    private Context context;
    private List<CreditCardEntity.CreditCardsBean> datas;
    private final LayoutInflater inflater;
    private CreditCardInterface cardInterface;
    private boolean isLastPage;
    @SuppressWarnings("deprecation")
    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showStubImage(R.mipmap.pro_default) // 设置图片下载期间显示的图片
            .showImageForEmptyUri(R.mipmap.pro_default) // 设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.mipmap.pro_default) // 设置图片加载或解码过程中发生错误显示的图片
            .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
            .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
            .build(); // 创建配置过得DisplayImageOption对象

    public CreditCardItemAdp(Context context, List<CreditCardEntity.CreditCardsBean> datas,boolean isLastPage) {
        this.context = context;
        this.datas = datas;
        this.isLastPage = isLastPage;
        inflater = LayoutInflater.from(context);
    }
    public void setOnCreditCardListner(CreditCardInterface creditCardListner){
        this.cardInterface = creditCardListner;
    }
    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            holder =  new ViewHolder();
            convertView = inflater.inflate(R.layout.credit_card_lv_item, null);
            holder.iv_creditCard = (ImageView) convertView.findViewById(R.id.iv_creditCard);
            holder.tv_description = (TextView) convertView.findViewById(R.id.tv_description);
            holder.btn_applyCard = (Button) convertView.findViewById(R.id.btn_applyCard);
            holder.ll_creditCardBottom = (LinearLayout) convertView.findViewById(R.id.ll_creditCardBottom);
            holder.iv_application = (ImageView) convertView.findViewById(R.id.iv_application);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        ImageLoader.getInstance().displayImage(datas.get(position).getMobileCreditcardImg(),holder.iv_creditCard,options);
        for (int i =0;i<datas.get(position).getMobileCreditcardDesc().size();i++){
            holder.tv_description.setText(holder.tv_description.getText().toString()+""+datas.get(position).getMobileCreditcardDesc().get(i)+"/n");
        }
        holder.btn_applyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardInterface.onApply(datas.get(position).getId(),datas.get(position).getMobileUrl());
            }
        });
        if (isLastPage && position == datas.size()){
            holder.ll_creditCardBottom.setVisibility(View.VISIBLE);
            holder.iv_application.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cardInterface.onClickApplyCredit();
                }
            });
        }else {
            holder.ll_creditCardBottom.setVisibility(View.GONE);
        }

        return convertView;
    }

    public void setDatas(List<CreditCardEntity.CreditCardsBean> datas) {
        this.datas = datas;
    }

    class ViewHolder{
        ImageView iv_creditCard;
        TextView tv_description;
        Button btn_applyCard;
        LinearLayout ll_creditCardBottom;
        ImageView iv_application;
    }
}
