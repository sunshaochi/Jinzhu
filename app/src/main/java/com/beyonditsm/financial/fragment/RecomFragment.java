package com.beyonditsm.financial.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.view.CommonView;
import com.beyonditsm.financial.widget.DialogChooseMonth;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**沟通fragment
 * Created by bitch-1 on 2016/11/28.
 */

public class RecomFragment extends BaseFragment {
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.rl_money)
    private RelativeLayout rl_money;//金额
    @ViewInject(R.id.tv_money)
    private TextView tv_money;//金额
    @ViewInject(R.id.com_qixian)
    private CommonView com_qixian;//期限
    @ViewInject(R.id.com_dq)
    private CommonView com_dq;//地区
    @ViewInject(R.id.com_xl)
    private CommonView com_xl;//学历
    @ViewInject(R.id.com_hj)
    private CommonView com_hj;//户籍
    @ViewInject(R.id.com_xyjl)
    private CommonView com_xyjl;//信用记录
    @ViewInject(R.id.com_zw)
    private CommonView com_zw;//职务
    @ViewInject(R.id.rl_nj)
    private RelativeLayout rl_nj;//年纪
    @ViewInject(R.id.tv_nj)
    private TextView tv_nj;//年纪
    @ViewInject(R.id.rl_gzsc)
    private RelativeLayout rl_gzsc;//工作时长
    @ViewInject(R.id.tv_gzsc)
    private TextView tv_gzsc;//工作时长
    @ViewInject(R.id.com_cc)
    private CommonView com_cc;//车场
    @ViewInject(R.id.com_fc)
    private CommonView com_fc;//房产
    @ViewInject(R.id.com_dfgo)
    private CommonView com_dfgo;//代发工资
    @ViewInject(R.id.com_bd)
    private String com_bd;//保单
    @ViewInject(R.id.com_qtzc)
    private String com_qtzc;//其他资产
    @ViewInject(R.id.rl_gjjsc)
    private RelativeLayout rl_gjjsc;//公积金时长
    @ViewInject(R.id.tv_gjjsc)
    private TextView tv_gjjsc;//公积金时长

    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.act_recomfrg,null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        tv_title.setText("为我推荐");

    }

    @OnClick({R.id.rl_money,R.id.com_qixian,R.id.com_dq,R.id.com_xl,R.id.com_hj,R.id.com_xyjl,R.id.com_zw,R.id.rl_nj,R.id.rl_gzsc,R.id.com_cc,R.id.com_dfgo,R.id.com_bd,R.id.com_qtzc,R.id.rl_gjjsc})
    public void toClick(View view){
        switch (view.getId()){
            case R.id.rl_money://金额


                break;
            case R.id.com_qixian://期限

//                DialogChooseMonth dialogChooseMonth=new DialogChooseMonth(getActivity(),tList)


                break;
            case R.id.com_dq://地区


                break;
            case R.id.com_xl://学历


                break;
            case R.id.com_hj://户籍


                break;
            case R.id.com_xyjl://性用记录


                break;
            case R.id.com_zw://职务


                break;
            case R.id.rl_nj://年纪


                break;
            case R.id.rl_gzsc://工作时长


                break;
            case R.id.com_cc://车产


                break;
            case R.id.com_dfgo://代发工资


                break;
            case R.id.com_bd://保单


                break;
            case R.id.com_qtzc://其他资产

                break;
            case R.id.rl_gjjsc://公积金时长

                break;
        }
    }
    @Override
    public void setListener() {

    }
}
