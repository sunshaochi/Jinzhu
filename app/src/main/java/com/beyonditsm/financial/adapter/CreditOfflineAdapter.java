//package com.beyonditsm.financial.adapter;
//
//import android.content.Context;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//
//import com.beyonditsm.financial.R;
//import com.beyonditsm.financial.entity.CreditOfflineDetil;
//
//import java.util.List;
//
///**
// * Created by xuleyuan on 2016/8/16.
// */
//public class CreditOfflineAdapter extends BaseAdapter {
//    private Context context;
//    private List<CreditOfflineDetil.ImagesBean> list;
//    private final String CHANGEABLE = "3";
//    private final String UNCHANGEABLE = "2";
//
//    public CreditOfflineAdapter(Context context, List<CreditOfflineDetil.ImagesBean> list) {
//        this.context = context;
//        this.list = list;
//    }
//
//    @Override
//    public int getCount() {
//        return list != null ? list.size() : 0;
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return list != null ? list.get(position) : null;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        final ViewHolder holder;
//        if (convertView == null) {
//            holder = new ViewHolder();
//            switch (list.get(position).getSts()) {
//                case CHANGEABLE:
//                    convertView = View.inflate(context, R.layout.lv_mycredit_item, null);
//                    break;
//                case UNCHANGEABLE:
//                    convertView = View.inflate(context, R.layout.lv_mycredit_item, null);
//                    break;
//                default:
//                    convertView = View.inflate(context, R.layout.lv_mycredit_item, null);
//                    break;
//            }
//            changeableGetView(position,convertView,parent);
//            holder.tvProductName = (TextView) convertView.findViewById(R.id.tv_productName);
//            holder.tvTotalAmount = (TextView) convertView.findViewById(R.id.tv_totalAmount);
//            holder.tvPeriodsAmount = (TextView) convertView.findViewById(R.id.tv_periodsAmount);
//            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
//
//            convertView.setTag(holder);
//        } else {
//            holder = (ViewHolder) convertView.getTag();
//        }
//        tvProductName.setText(list.get(position).getProductName());
//
//        String status = list.get(position).getOrderSts();
//
//        return convertView;
//    }
//
//    private void changeableGetView(int position, View convertView, ViewGroup parent) {
//        convertView = View.inflate(context, R.layout.gv_credit_offline_item, null);
//    }
//
//
//    class ViewHolder {
//        TextView tvProductName, tvTotalAmount, tvPeriodsAmount, tvName;
//        ImageView ivRedPoint;//推送红点
//        RelativeLayout rlCreditList;
//    }
//}
//}
