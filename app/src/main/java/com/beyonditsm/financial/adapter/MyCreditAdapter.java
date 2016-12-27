package com.beyonditsm.financial.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.entity.OrderBean3;
import com.beyonditsm.financial.entity.OrderListBean;
import com.beyonditsm.financial.entity.ProductBean2;
import com.beyonditsm.financial.http.IFinancialUrl;
import com.beyonditsm.financial.util.StatuUtil;
import com.tandong.sa.zUImageLoader.core.ImageLoader;

import java.util.List;

/**
 * 我的贷款适配器
 * Created by wangbin on 15/11/12.
 */
public  class MyCreditAdapter extends BaseAdapter {
    private Context context;
    private List<OrderListBean> list;
    private String orderId;

    java.text.DecimalFormat df = new java.text.DecimalFormat("#0.00");//保留小数

    public void setDatas(List<OrderListBean> list) {
        this.list = list;
        this.orderId = orderId;
        notifyDataSetChanged();
    }

    public MyCreditAdapter(Context context, List<OrderListBean> list) {
        this.context = context;
        this.list = list;
        this.orderId = orderId;
    }

    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return list != null ? list.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.lv_mycredit_item, null);
//            holder.tvProductName = (TextView) convertView.findViewById(R.id.tv_productName);
//            holder.tvTotalAmount = (TextView) convertView.findViewById(R.id.tv_totalAmount);
//            holder.tvPeriodsAmount = (TextView) convertView.findViewById(R.id.tv_periodsAmount);
//            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
//            holder.ivRedPoint = (ImageView) convertView.findViewById(R.id.ivMs);
//            holder.rlCreditList = (RelativeLayout) convertView.findViewById(R.id.rl_creditList);
//            holder.tvProductName.setSelected(true);
            holder.tv_bianhao= (TextView) convertView.findViewById(R.id.tv_bianhao);
            holder.tv_zhuangtai= (TextView) convertView.findViewById(R.id.tv_zhuangtai);
            holder.tv_dkname= (TextView) convertView.findViewById(R.id.tv_dkname);
            holder.tv_dkje= (TextView) convertView.findViewById(R.id.tv_dkje);
            holder.tv_dksj= (TextView) convertView.findViewById(R.id.tv_dksj);
            holder.iv_iv_tubiao= (ImageView) convertView.findViewById(R.id.iv_tubiao);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        OrderBean3 order = list.get(position).getOrder();
        ProductBean2 product = list.get(position).getProduct();
        if (product!=null){
            if (!TextUtils.isEmpty(product.getProductLogo())){
                ImageLoader.getInstance().displayImage(IFinancialUrl.BASE_IMAGE_URL+product.getProductLogo(),holder.iv_iv_tubiao);
            }
            if(!TextUtils.isEmpty(product.getProductName())){
                holder.tv_dkname.setText(product.getProductName());
            }
        }
        if (order!=null){
            holder.tv_bianhao.setText(order.getOrderNo());
//            if (!TextUtils.isEmpty(order.getOrderType())){
//                if (TextUtils.equals(order.getOrderType(),"1")){
//                    holder.tv_dkname.setText("线上订单");
//                }else  if (TextUtils.equals(order.getOrderType(),"2")){
//                    holder.tv_dkname.setText("CC订单");
//                }else if (TextUtils.equals(order.getOrderType(),"3")){
//                    holder.tv_dkname.setText("地推订单");
//                }else if (TextUtils.equals(order.getOrderType(),"4")){
//                    holder.tv_dkname.setText("急贷通");
//                }
//            }
            if (!TextUtils.isEmpty(order.getOrderStatus())){
//                if ("ORGANIZATION_APPROVAL".equals(order.getOrderStatus())) {
//            holder.tv_zhuangtai.setText("机构审批中");
////            holder.tv_zhuangtai.setTextColor(Color.parseColor("#ff6633"));
//
//        } else if ("CREDIT_MANAGER_GRAB".equals(order.getOrderStatus())) {
//            holder.tv_zhuangtai.setText("待抢单");
////            holder.tv_zhuangtai.setTextColor(Color.parseColor("#ff6633"));
//        }else if ("CREDIT_MANAGER_APPROVAL".equals(order.getOrderStatus())){
//            holder.tv_zhuangtai.setText("已抢单");
////            holder.tv_zhuangtai.setTextColor(Color.parseColor("#ff6633"));
//        }else if ("PASS".equals(order.getOrderStatus())) {
//            holder.tv_zhuangtai.setText("审批通过");
////            holder.tv_zhuangtai.setTextColor(Color.parseColor("#1fd45f"));
//
//        } else if ("WAIT_BACKGROUND_APPROVAL".equals(order.getOrderStatus())) {
//            holder.tv_zhuangtai.setText("待审批");
////            holder.tv_zhuangtai.setTextColor(Color.parseColor("#ff6633"));
//
//        } else if ("SUPPLEMENT_DATA".equals(order.getOrderStatus())) {
//            holder.tv_zhuangtai.setText("补件中");
////            holder.tv_zhuangtai.setTextColor(Color.parseColor("#ff6633"));
//
//        } else if ("NO_PASS".equals(order.getOrderStatus())) {
//            holder.tv_zhuangtai.setText("审批不通过");
////            holder.tv_zhuangtai.setTextColor(Color.parseColor("#ff0000"));
//        } else if ("CANCEL_REQUET".equals(order.getOrderStatus())) {
//            holder.tv_zhuangtai.setText("已取消");
////            holder.tv_zhuangtai.setTextColor(Color.parseColor("#ff8383"));
//        } else if ("DRAFT".equals(order.getOrderStatus())) {
//            holder.tv_zhuangtai.setText("资料待上传");
////            holder.tv_zhuangtai.setTextColor(Color.parseColor("#ff6633"));
//        }else if ("REJECT".equals(order.getOrderStatus())){
//            holder.tv_zhuangtai.setText("驳回");
////            holder.tv_zhuangtai.setTextColor(Color.parseColor("#ff0000"));
//        }else if (TextUtils.equals("CREDIT_MANAGER_DRAFT",order.getOrderStatus())){
//                    holder.tv_zhuangtai.setText("信贷经理-资料待上传");
//                }
//                else if (TextUtils.equals("AREA_MANAGER_APPROVAL",order.getOrderStatus())){
//                    holder.tv_zhuangtai.setText("区域经理-审批");
//                }else if (TextUtils.equals("ELECTRICPIN_TOBE_DISTRIBUTED",order.getOrderStatus())){
//                    holder.tv_zhuangtai.setText("电销待分派");
//                }else if (TextUtils.equals("ELECTRICPIN_EXAMINING",order.getOrderStatus())){
//                    holder.tv_zhuangtai.setText("电销审批中");
//                }else if (TextUtils.equals("ELECTRICPIN_PATCH",order.getOrderStatus())){
//                    holder.tv_zhuangtai.setText("电销补件中");
//                }else if (TextUtils.equals("ELECTRICPIN_LOANEND",order.getOrderStatus())){
//                    holder.tv_zhuangtai.setText("申贷结束");
//                }else if (TextUtils.equals("ELECTRICPIN_LEADER_EXAMINING",order.getOrderStatus())){
//                    holder.tv_zhuangtai.setText("电销主管审批中");
//                }else if (TextUtils.equals("AREA_MANAGER_EXAMINE",order.getOrderStatus())){
//                    holder.tv_zhuangtai.setText("区域经理审批");
//                }else if (TextUtils.equals("CREDIT_MANAGER_PATCH",order.getOrderStatus())){
//                    holder.tv_zhuangtai.setText("信贷经理补件");
//                }else if (TextUtils.equals("AGENT_EXAMINE",order.getOrderStatus())){
//                    holder.tv_zhuangtai.setText("代理商审批");
//                }else if (TextUtils.equals("AREA_MANAGER_PATCH",order.getOrderStatus())){
//                    holder.tv_zhuangtai.setText("区域经理补件");
//                }else if (TextUtils.equals("RISK_TOBE_DISTRIBUTED",order.getOrderStatus())){
//                    holder.tv_zhuangtai.setText("风控分派中");
//                }else if (TextUtils.equals("PLATFORM_FIRSTEXAMINING",order.getOrderStatus())){
//                    holder.tv_zhuangtai.setText("平台初审中");
//                }else if (TextUtils.equals("RISK_PATCH",order.getOrderStatus())){
//                    holder.tv_zhuangtai.setText("风控补件中");
//                }else if (TextUtils.equals("FIRSTEXAMINING_PASS",order.getOrderStatus())){
//                    holder.tv_zhuangtai.setText("初审通过");
//                }else if (TextUtils.equals("FIRSTEXAMINING_REFUSE",order.getOrderStatus())){
//                    holder.tv_zhuangtai.setText("初审拒绝");
//                }else if (TextUtils.equals("PLATFORM_REFUSE",order.getOrderStatus())){
//                    holder.tv_zhuangtai.setText("平台拒绝");
//                }else if (TextUtils.equals("RECOMMEND_TO_OTHERS",order.getOrderStatus())){
//                    holder.tv_zhuangtai.setText("推荐其他产品");
//                }else if (TextUtils.equals("RISK_LOAN_END",order.getOrderStatus())){
//                    holder.tv_zhuangtai.setText("申贷结束");
//                }else if (TextUtils.equals("PLATFORM_PASS",order.getOrderStatus())){
//                    holder.tv_zhuangtai.setText("平台通过");
//                }else if (TextUtils.equals("TOBE_SIGNED",order.getOrderStatus())){
//                    holder.tv_zhuangtai.setText("待面签");
//                }else if (TextUtils.equals("SIGNED_CONFIRM",order.getOrderStatus())){
//                    holder.tv_zhuangtai.setText("已面签待确认");
//                }else if (TextUtils.equals("SIGNED_DONE",order.getOrderStatus())){
//                    holder.tv_zhuangtai.setText("已面签");
//                }else if (TextUtils.equals("SUBMIT_ORG",order.getOrderStatus())){
//                    holder.tv_zhuangtai.setText("提交机构");
//                }else if (TextUtils.equals("LOAN_SUCC",order.getOrderStatus())){
//                    holder.tv_zhuangtai.setText("银行通过");
//                }else if (TextUtils.equals("LOAN_FAIL",order.getOrderStatus())){
//                    holder.tv_zhuangtai.setText("银行拒绝");
//                }
                holder.tv_zhuangtai.setText(StatuUtil.getStatutext(order.getOrderStatus()));
            }
            if (!TextUtils.isEmpty(order.getApplyAmount())){
                holder.tv_dkje.setText("贷款金额："+order.getApplyAmount());
            }
            if (order.getApplyPeriods()!=0){
                holder.tv_dksj.setText("贷款期限："+order.getApplyPeriods()+"个月");
            }

        }
//        if (!TextUtils.isEmpty(orderId)) {
//            if (list.get(position).getId().equals(orderId)) {
//                holder.ivRedPoint.setVisibility(View.VISIBLE);
//            }
//        }else{
//            holder.ivRedPoint.setVisibility(View.GONE);
//        }
//        holder.tvProductName.setText(list.get(position).getProductName());
//        holder.tvTotalAmount.setText(df.format(Double.valueOf(list.get(position).getTotalAmount()) / 10000) + "万");
//        holder.tvPeriodsAmount.setText(df.format(Double.valueOf(list.get(position).getPeriodsAmount()) / 10000) + "万");
//        String status = list.get(position).getOrderSts();
//        if ("ORGANIZATION_APPROVAL".equals(status)) {
//            holder.tvName.setText("机构审批中");
//            holder.tvName.setTextColor(Color.parseColor("#ff6633"));
//
//        } else if ("CREDIT_MANAGER_GRAB".equals(status)) {
//            holder.tvName.setText("待抢单");
//            holder.tvName.setTextColor(Color.parseColor("#ff6633"));
//        }else if ("CREDIT_MANAGER_APPROVAL".equals(status)){
//            holder.tvName.setText("已抢单");
//            holder.tvName.setTextColor(Color.parseColor("#ff6633"));
//        }else if ("PASS".equals(status)) {
//            holder.tvName.setText("审批通过");
//            holder.tvName.setTextColor(Color.parseColor("#1fd45f"));
//
//        } else if ("WAIT_BACKGROUND_APPROVAL".equals(status)) {
//            holder.tvName.setText("待审批");
//            holder.tvName.setTextColor(Color.parseColor("#ff6633"));
//
//        } else if ("SUPPLEMENT_DATA".equals(status)) {
//            holder.tvName.setText("补件中");
//            holder.tvName.setTextColor(Color.parseColor("#ff6633"));
//
//        } else if ("NO_PASS".equals(status)) {
//            holder.tvName.setText("审批不通过");
//            holder.tvName.setTextColor(Color.parseColor("#ff0000"));
//        } else if ("CANCEL_REQUET".equals(status)) {
//            holder.tvName.setText("已取消");
//            holder.tvName.setTextColor(Color.parseColor("#ff8383"));
//        } else if ("DRAFT".equals(status)) {
//            holder.tvName.setText("资料待上传");
//            holder.tvName.setTextColor(Color.parseColor("#ff6633"));
//        }else if ("REJECT".equals(status)){
//            holder.tvName.setText("驳回");
//            holder.tvName.setTextColor(Color.parseColor("#ff0000"));
//        }

        return convertView;
    }

    class ViewHolder {
//        TextView tvProductName, tvTotalAmount, tvPeriodsAmount, tvName;
//        ImageView ivRedPoint;//推送红点
//        RelativeLayout rlCreditList;
        TextView tv_bianhao,tv_zhuangtai,tv_dkname,tv_dkje,tv_dksj;//贷款编号，状态，金额，时间
        ImageView iv_iv_tubiao;//贷款图标
    }

}

