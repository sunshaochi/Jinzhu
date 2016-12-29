package com.beyonditsm.financial.util;

import android.text.TextUtils;

/**订单状态匹配工具
 * Created by bitch-1 on 2016/12/27.
 */

public class StatuUtil {

    public static String getStatutext(String statu){
         String text = null;
        if ("ORGANIZATION_APPROVAL".equals(statu)) {
//            holder.tv_zhuangtai.setText("机构审批中");
//            holder.tv_zhuangtai.setTextColor(Color.parseColor("#ff6633"));
            text="机构审批中";
            return text;
        } else if ("CREDIT_MANAGER_GRAB".equals(statu)) {
//            holder.tv_zhuangtai.setText("待抢单");
//            holder.tv_zhuangtai.setTextColor(Color.parseColor("#ff6633"));
            text="待抢单";
            return text;
        }else if ("CREDIT_MANAGER_APPROVAL".equals(statu)){
//            holder.tv_zhuangtai.setText("已抢单");
//            holder.tv_zhuangtai.setTextColor(Color.parseColor("#ff6633"));
            text="已抢单";
            return text;
        }else if ("PASS".equals(statu)) {
//            holder.tv_zhuangtai.setText("审批通过");
//            holder.tv_zhuangtai.setTextColor(Color.parseColor("#1fd45f"));
            text="审批通过";
            return text;

        } else if ("WAIT_BACKGROUND_APPROVAL".equals(statu)) {
//            holder.tv_zhuangtai.setText("待审批");
//            holder.tv_zhuangtai.setTextColor(Color.parseColor("#ff6633"));
            text="待审批";
            return text;

        } else if ("SUPPLEMENT_DATA".equals(statu)) {
//            holder.tv_zhuangtai.setText("补件中");
//            holder.tv_zhuangtai.setTextColor(Color.parseColor("#ff6633"));
            text="补件中";
            return text;

        } else if ("NO_PASS".equals(statu)) {
//            holder.tv_zhuangtai.setText("审批不通过");
//            holder.tv_zhuangtai.setTextColor(Color.parseColor("#ff0000"));
            text="审批不通过";
            return text;
        } else if ("CANCEL_REQUET".equals(statu)) {
//            holder.tv_zhuangtai.setText("已取消");
//            holder.tv_zhuangtai.setTextColor(Color.parseColor("#ff8383"));
            text="已取消";
            return text;
        } else if ("DRAFT".equals(statu)) {
//            holder.tv_zhuangtai.setText("资料待上传");
//            holder.tv_zhuangtai.setTextColor(Color.parseColor("#ff6633"));
            text="资料待上传";
            return text;
        }else if ("REJECT".equals(statu)){
//            holder.tv_zhuangtai.setText("驳回");
//            holder.tv_zhuangtai.setTextColor(Color.parseColor("#ff0000"));
            text="驳回";
            return text;
        }else if (TextUtils.equals("CREDIT_MANAGER_DRAFT",statu)){
//            holder.tv_zhuangtai.setText("信贷经理-资料待上传");
            text="信贷经理-资料待上传";
            return text;
        }
        else if (TextUtils.equals("AREA_MANAGER_APPROVAL",statu)){
//            holder.tv_zhuangtai.setText("区域经理-审批");
            text="区域经理-审批";
            return text;
        }else if (TextUtils.equals("ELECTRICPIN_TOBE_DISTRIBUTED",statu)){
//            holder.tv_zhuangtai.setText("电销待分派");
            text="电销待分派";
            return text;
        }else if (TextUtils.equals("ELECTRICPIN_EXAMINING",statu)){
//            holder.tv_zhuangtai.setText("电销审批中");
            text="电销审批中";
            return text;
        }else if (TextUtils.equals("ELECTRICPIN_PATCH",statu)){
//            holder.tv_zhuangtai.setText("电销补件中");
            text="电销补件中";
            return text;
        }else if (TextUtils.equals("ELECTRICPIN_LOANEND",statu)){
//            holder.tv_zhuangtai.setText("申贷结束");
            text="申贷结束";
            return text;
        }else if (TextUtils.equals("ELECTRICPIN_LEADER_EXAMINING",statu)){
//            holder.tv_zhuangtai.setText("电销主管审批中");
            text="电销主管审批中";
            return text;
        }else if (TextUtils.equals("AREA_MANAGER_EXAMINE",statu)){
//            holder.tv_zhuangtai.setText("区域经理审批");
            text="区域经理审批";
            return text;
        }else if (TextUtils.equals("CREDIT_MANAGER_PATCH",statu)){
//            holder.tv_zhuangtai.setText("信贷经理补件");
            text="信贷经理补件";
            return text;
        }else if (TextUtils.equals("AGENT_EXAMINE",statu)){
//            holder.tv_zhuangtai.setText("代理商审批");
            text="代理商审批";
            return text;
        }else if (TextUtils.equals("AREA_MANAGER_PATCH",statu)){
//            holder.tv_zhuangtai.setText("区域经理补件");
            text="区域经理补件";
            return text;
        }else if (TextUtils.equals("RISK_TOBE_DISTRIBUTED",statu)){
//            holder.tv_zhuangtai.setText("风控分派中");
            text="风控分派中";
            return text;
        }else if (TextUtils.equals("PLATFORM_FIRSTEXAMINING",statu)){
//            holder.tv_zhuangtai.setText("平台初审中");
            text="平台初审中";
            return text;
        }else if (TextUtils.equals("RISK_PATCH",statu)){
//            holder.tv_zhuangtai.setText("风控补件中");
            text="风控补件中";
            return text;
        }else if (TextUtils.equals("FIRSTEXAMINING_PASS",statu)){
//            holder.tv_zhuangtai.setText("初审通过");
            text="初审通过";
            return text;
        }else if (TextUtils.equals("FIRSTEXAMINING_REFUSE",statu)){
//            holder.tv_zhuangtai.setText("初审拒绝");
            text="初审拒绝";
            return text;
        }else if (TextUtils.equals("PLATFORM_REFUSE",statu)){
//            holder.tv_zhuangtai.setText("平台拒绝");
            text="平台拒绝";
            return text;
        }else if (TextUtils.equals("RECOMMEND_TO_OTHERS",statu)){
//            holder.tv_zhuangtai.setText("推荐其他产品");
            text="推荐其他产品";
            return text;
        }else if (TextUtils.equals("RISK_LOAN_END",statu)){
//            holder.tv_zhuangtai.setText("申贷结束");
            text="申贷结束";
            return text;
        }else if (TextUtils.equals("PLATFORM_PASS",statu)){
//            holder.tv_zhuangtai.setText("平台通过");
            text="平台通过";
            return text;
        }else if (TextUtils.equals("TOBE_SIGNED",statu)){
//            holder.tv_zhuangtai.setText("待面签");
            text="待面签";
            return text;
        }else if (TextUtils.equals("SIGNED_CONFIRM",statu)){
//            holder.tv_zhuangtai.setText("已面签待确认");
            text="已面签待确认";
            return text;
        }else if (TextUtils.equals("SIGNED_DONE",statu)){
//            holder.tv_zhuangtai.setText("已面签");
            text="已面签";
            return text;
        }else if (TextUtils.equals("SUBMIT_ORG",statu)){
//            holder.tv_zhuangtai.setText("提交机构");
            text="提交机构";
            return text;
        }else if (TextUtils.equals("LOAN_SUCC",statu)){
//            holder.tv_zhuangtai.setText("银行通过");
            text="银行通过";
            return text;
        }else if (TextUtils.equals("LOAN_FAIL",statu)){
//            holder.tv_zhuangtai.setText("银行拒绝");
            text="银行拒绝";
            return text;
        }else if(TextUtils.equals("PASS_BLACK_NAME",statu)){
            text="通过黑名单";
            return text;
        }
        return text;
    }



    }

