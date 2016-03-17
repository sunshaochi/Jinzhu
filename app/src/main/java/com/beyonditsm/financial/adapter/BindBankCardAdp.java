package com.beyonditsm.financial.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.wallet.BindBankCardAct;
import com.beyonditsm.financial.entity.QueryBankCardEntity;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.MyLogUtils;
import com.beyonditsm.financial.util.MyToastUtils;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/3/15.
 */
public class BindBankCardAdp extends BaseAdapter {
    private Context context;
    private List<QueryBankCardEntity> list;
    private final LayoutInflater inflater;

    // 标记当前选择的银行卡
    private int index = -1;
    private int status;

    HashMap<String,Boolean> isSelecteds = new HashMap<>();
    private TextView tvStatus;

    public BindBankCardAdp(Context context,List<QueryBankCardEntity> list) {
        this.context =context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    public void setNotifyChange(List<QueryBankCardEntity> list){
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

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.lv_bindcard_item, null);
            holder.tvBankName = (TextView) convertView.findViewById(R.id.tv_bankName);
            holder.tvCardNo = (TextView) convertView.findViewById(R.id.tv_cardNo);
            holder.jiebang = (TextView) convertView.findViewById(R.id.jiebang);
            tvStatus = (TextView) convertView.findViewById(R.id.tv_status);
            holder.tvStatus = tvStatus;
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        final QueryBankCardEntity queryBankCardEntity = list.get(position);
        holder.tvBankName.setText(queryBankCardEntity.getBankName());
        final String cardNo = queryBankCardEntity.getCardNo();

        status = queryBankCardEntity.getStatus();
        holder.tvCardNo.setText("**** "+"**** "+"**** "+cardNo.substring(cardNo.length()-4,cardNo.length()));

        holder.tvStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = 2;
                modifyBankCardStatus(cardNo,status);
            }
        });
        holder.jiebang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(position);
                status = 0;
                modifyBankCardStatus(cardNo, status);

            }
        });
        if (status==1){
            holder.tvStatus.setText("设为默认");
        }else if (status ==2){
            holder.tvStatus.setText("默认");
        }
        return convertView;
    }

    private void modifyBankCardStatus(String cardNo,int status) {
        RequestManager.getWalletManager().modifyBankCard(cardNo, status, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
//                tvStatus.setText("默认");
                Intent intent = new Intent(BindBankCardAct.ADDBANKCARD);
                context.sendBroadcast(intent);
            }

            @Override
            public void onError(int status, String msg) {

            }
        });
    }

    class ViewHolder{
        TextView tvBankName,tvCardNo,jiebang,tvStatus;
    }
}
