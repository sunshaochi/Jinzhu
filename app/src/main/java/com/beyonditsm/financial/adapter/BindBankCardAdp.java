package com.beyonditsm.financial.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.wallet.BindBankCardAct;
import com.beyonditsm.financial.entity.BindCardBean;
import com.beyonditsm.financial.http.RequestManager;

import org.json.JSONException;

import java.util.List;

/**
 * Created by Administrator on 2016/3/15
 */
public class BindBankCardAdp extends BaseAdapter {
    private Context context;
    private List<BindCardBean> list;
    private final LayoutInflater inflater;
    private int status;
    private int clickpos;

    public BindBankCardAdp(Context context, List<BindCardBean> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    public void setNotifyChange(List<BindCardBean> list) {
        this.list = list;
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

    @SuppressLint("InflateParams")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.lv_bindcard_item, null);
            holder.tvBankName = (TextView) convertView.findViewById(R.id.tv_bankName);
            holder.tvCardNo = (TextView) convertView.findViewById(R.id.tv_cardNo);
            holder.jiebang = (TextView) convertView.findViewById(R.id.jiebang);
            holder.tvStatus = (TextView) convertView.findViewById(R.id.tv_status);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//        final QueryBankCardEntity queryBankCardEntity = list.get(position);
        holder.tvBankName.setText(list.get(position).getBankName());
        final String cardNo = list.get(position).getCardNo();

        if(TextUtils.isEmpty(list.get(position).getStatus())){
            status =Integer.parseInt(list.get(position).getStatus());
        }
        holder.tvCardNo.setText(cardNo.substring(0,4) + "**** " + "**** " + cardNo.substring(cardNo.length() - 4, cardNo.length()));

        holder.tvStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = 2;
                modifyBankCardStatus(cardNo, status);
            }
        });
        holder.jiebang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = 0;
                clickpos=position;
                modifyBankCardStatus(cardNo, status);
//                list.remove(position);

            }
        });
        if (status == 1) {
            holder.tvStatus.setText("设为默认");
        } else if (status == 2) {
            holder.tvStatus.setText("默认");
        }
        return convertView;
    }

    private void modifyBankCardStatus(String cardNo, final int status) {
        RequestManager.getWalletManager().modifyBankCard(cardNo, status, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                if (status==0){
                    list.remove(clickpos);
                    notifyDataSetChanged();
                }
//                tvStatus.setText("默认");
                Intent intent = new Intent(BindBankCardAct.ADDBANKCARD);
                context.sendBroadcast(intent);
            }

            @Override
            public void onError(int status, String msg) {

            }
        });
    }

    class ViewHolder {
        TextView tvBankName, tvCardNo, jiebang, tvStatus;
    }
}
