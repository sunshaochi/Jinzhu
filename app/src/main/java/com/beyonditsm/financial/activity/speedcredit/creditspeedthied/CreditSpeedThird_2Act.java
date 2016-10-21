package com.beyonditsm.financial.activity.speedcredit.creditspeedthied;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.activity.MainActivity;
import com.beyonditsm.financial.activity.credit.CreditUploadAct;
import com.beyonditsm.financial.entity.UserOrderInfoEntity;
import com.beyonditsm.financial.entity.VendorEntity;
import com.beyonditsm.financial.http.CommManager;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.CheckUtil;
import com.beyonditsm.financial.util.MyLogUtils;
import com.beyonditsm.financial.util.MyToastUtils;
import com.beyonditsm.financial.widget.DialogSingalPicker;
import com.beyonditsm.financial.widget.gpscity.DialogChooseCity;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.tandong.sa.json.Gson;
import com.tandong.sa.json.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 极速贷第三步，第二小步
 * Created by Administrator on 2016/10/14 0014.
 */

public class CreditSpeedThird_2Act extends BaseActivity {
    @ViewInject(R.id.tv_top_isload)
    TextView tvTopIsLoad;
    @ViewInject(R.id.tv_top_state)
    TextView tvTopState;
    @ViewInject(R.id.tv_back_is_load)
    TextView tvBackIsLoad;
    @ViewInject(R.id.tv_back_state)
    TextView tvBackState;
    @ViewInject(R.id.tv_city)
    TextView tvCity;
    @ViewInject(R.id.tv_address)
    TextView tvAddress;
    @ViewInject(R.id.ll_null_shop_tip)
    LinearLayout llNullShopTip;
    @ViewInject(R.id.tv_submit4)
    TextView tvSubmit4;
    @ViewInject(R.id.llSucess)
    LinearLayout llSuccess;
    @ViewInject(R.id.ll_fatherLayout)
    LinearLayout llFatherLayout;
    private ArrayList<String> vendorNameList;
    private String frontCardUrl;//身份证正面url
    private String backCardUrl;//身份证反面url
    public static final int FRONT_CARD = 0;
    public static final int BACK_CARD = 1;
    private List<VendorEntity> vendorList;
    private VendorEntity curVendero;
    private String orderId;

    @Override
    public void setLayout() {
        setContentView(R.layout.act_creditspeedthird_2);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        Intent intent = getIntent();
        orderId = intent.getStringExtra("orderId");
        getAddress();  //获取门店信息
        refreshUploadState();
    }

    private void getAddress() {

    }

    /*刷新上传身份证状态*/
    private void refreshUploadState() {
        if (TextUtils.isEmpty(frontCardUrl)) {
            tvTopIsLoad.setText("未上传");
            tvTopState.setVisibility(View.GONE);
        } else if (!TextUtils.isEmpty(frontCardUrl)) {
            tvTopIsLoad.setText("已上传");
            tvTopIsLoad.setBackgroundResource(R.drawable.btn_bg_green);
//            tvTopState.setVisibility(View.VISIBLE);
        }
        if (TextUtils.isEmpty(backCardUrl)) {
            tvBackIsLoad.setText("未上传");
            tvBackState.setVisibility(View.GONE);
        } else if (!TextUtils.isEmpty(backCardUrl)) {
            tvBackIsLoad.setText("已上传");
            tvBackIsLoad.setBackgroundResource(R.drawable.btn_bg_green);
//            tvBackState.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.tvSure, R.id.rl_back_card, R.id.rl_top_card, R.id.rl_city, R.id.rl_address, R.id.tv_submit4})
    public void todo(View v) {
        switch (v.getId()) {
            case R.id.tvSure:
                Intent intent = new Intent(CreditSpeedThird_2Act.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                break;
            case R.id.rl_top_card:
                Intent intent1 = new Intent(CreditSpeedThird_2Act.this, CreditSpeedUploadAct.class);
                intent1.putExtra("desc", "请上传身份证正面");
                startActivityForResult(intent1, FRONT_CARD);
                break;
            case R.id.rl_back_card:
                Intent intent2 = new Intent(CreditSpeedThird_2Act.this, CreditSpeedUploadAct.class);
                intent2.putExtra("desc", "请上传身份证反面");
                startActivityForResult(intent2, BACK_CARD);
                break;
            case R.id.rl_city:
                DialogChooseCity dialogChooseAdress1 = new DialogChooseCity(CreditSpeedThird_2Act.this).builder();
                dialogChooseAdress1.show();
                dialogChooseAdress1.setOnSheetItemClickListener(new DialogChooseCity.SexClickListener() {
                    @Override
                    public void getAdress(final List<String> adress) {
//                        MyLogUtils.info("选择的地址:" + adress.get(1));
                        if (adress.size() > 1) {
                            tvCity.setText(adress.get(1));
                            queryVendorByCity(adress.get(1));
                        }

                    }
                });
                break;
            case R.id.rl_address:
                if (vendorList.size() > 0) {
                    llNullShopTip.setVisibility(View.GONE);
                    DialogSingalPicker dialogChooseMonth = new DialogSingalPicker(CreditSpeedThird_2Act.this, vendorNameList).builder(0);
                    dialogChooseMonth.show();
                    dialogChooseMonth.setOnSheetItemClickListener(new DialogSingalPicker.SexClickListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void getAdress(String adress, int position) {
                            curVendero = vendorList.get(position);
                            tvAddress.setText(vendorList.get(position).getAddr() + "");
//                        getMOnthPay(creditMoney, productInfo.getMonthlyRathAvg(), creditMonth);
                        }
                    });
                } else {
                    llNullShopTip.setVisibility(View.VISIBLE);
                }


                break;
            case R.id.tv_submit4:

                if (TextUtils.isEmpty(frontCardUrl)) {
                    MyToastUtils.showShortToast(CreditSpeedThird_2Act.this, "请上传身份证正面");
                } else if (TextUtils.isEmpty(backCardUrl)) {
                    MyToastUtils.showShortToast(CreditSpeedThird_2Act.this, "请上传身份证反面");
                }else if (null!=CheckUtil.CheckOutNull(llFatherLayout)){
                    MyToastUtils.showShortToast(CreditSpeedThird_2Act.this, CheckUtil.CheckOutNull(llFatherLayout));
                }else {
                    saveUserInfo4();
                }

                break;
            default:
                break;
        }
    }

    private void saveUserInfo4() {

        CommManager.getCommManager().saveUserOrderInfo4(orderId, frontCardUrl, backCardUrl, tvCity.getText().toString(), curVendero.getId() + "", curVendero.getAddr() + "", new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                llSuccess.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(int status, String msg) {

            }
        });
    }

    private void queryVendorByCity(String city) {
        CommManager.getCommManager().queryVendorByCity(city, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                JSONObject object = new JSONObject(result);
                JSONObject data = object.getJSONObject("data");
                JSONArray res = data.getJSONArray("result");
                Gson gson = new Gson();
                vendorList = gson.fromJson(res.toString(), new TypeToken<List<VendorEntity>>() {
                }.getType());
                vendorNameList = new ArrayList<String>();
                for (int i = 0; i < vendorList.size(); i++) {
                    vendorNameList.add(vendorList.get(i).getName() + "");
                }
            }

            @Override
            public void onError(int status, String msg) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case FRONT_CARD:
                frontCardUrl = data.getStringExtra("url");
                refreshUploadState();
                break;
            case BACK_CARD:
                backCardUrl = data.getStringExtra("url");
                refreshUploadState();
                break;
        }
    }


}
