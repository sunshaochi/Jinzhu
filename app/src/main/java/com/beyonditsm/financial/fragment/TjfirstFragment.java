package com.beyonditsm.financial.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.beyonditsm.financial.MyApplication;
import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.user.EditorAct;
import com.beyonditsm.financial.entity.JJTCityEntity;
import com.beyonditsm.financial.entity.JJTCounyEntity;
import com.beyonditsm.financial.entity.JJTProvinceEntity;
import com.beyonditsm.financial.entity.ProductBean;
import com.beyonditsm.financial.entity.RelationEntity;
import com.beyonditsm.financial.entity.TujianBean;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.MyToastUtils;

import com.beyonditsm.financial.util.ParamsUtil;
import com.beyonditsm.financial.view.MySelfSheetDialog;
import com.beyonditsm.financial.widget.jijietong.DialogJJTAddress;
import com.beyonditsm.financial.widget.jijietong.JJTInterface;
import com.beyonditsm.financial.widget.jijietong.ShenSiDialog;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.tandong.sa.json.Gson;
import com.tandong.sa.json.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * 推荐(第一步)fragent
 * Created by bitch-1 on 2016/12/8.
 */

public class TjfirstFragment extends BaseFragment {
    @ViewInject(R.id.scview)
    private ScrollView scview;
    @ViewInject(R.id.lv_credit_sort)
    private ListView lv_credit_sort;//列表listview
    @ViewInject(R.id.tv_money)
    private TextView tv_money;//贷款金额
    @ViewInject(R.id.tv_qixian)
    private TextView tv_qixian;//期限
    @ViewInject(R.id.tv_diqu)
    private TextView tv_diqu;//地区
    @ViewInject(R.id.tv_xueli)
    private TextView tv_xueli;//学历
    @ViewInject(R.id.tv_huji)
    private TextView tv_huji;//户籍
    @ViewInject(R.id.tv_xyjl)
    private TextView tv_xyjl;//信用记录
    @ViewInject(R.id.tv_zhiwei)
    private TextView tv_zhiwei;//职务
    @ViewInject(R.id.tv_nianji)
    private TextView tv_nianji;//年级
    @ViewInject(R.id.tv_zhucesc)
    private TextView tv_zhucesc;//注册时长
    @ViewInject(R.id.tv_cc)
    private TextView tv_cc;//车产
    @ViewInject(R.id.tv_fc)
    private TextView tv_fc;//房产
    @ViewInject(R.id.tv_dfgzje)
    private TextView tv_dfgzje;//代发工资金额
    @ViewInject(R.id.tv_bd)
    private TextView tv_bd;//保单
    @ViewInject(R.id.tv_qtzc)
    private TextView tv_qtzc;//其他资产
    @ViewInject(R.id.tv_cbsc)
    private TextView tv_cbsc;//公积金社保时长
    @ViewInject(R.id.ll_searchTitle)
    private LinearLayout ll_searchTitle;

    private List<JJTProvinceEntity> provinceList;
    private List<JJTCityEntity> cityList;
    private List<JJTCounyEntity> counyList;
//    private DialogJJTAddress dialogJJTAddress;
    private ShenSiDialog dialogJJTAddress;


    public static TujianBean tujianBean;
    private MySelfSheetDialog dialog;
    private List<String> keyLists;


    private String money, time, diqu, xueli, huji, xyjl, zhiwu, nianji, gzsc, cc, fc, dfgz, bd, qtzc, sbcs;
    private List<ProductBean> list;

    private List<RelationEntity> xuelilist, joplist, creditlist, salarylist, booleantypelis, loanlist, cardlist, houselist;

    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.frag_tjfirst, null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        tujianBean = new TujianBean();
        xuelilist=new ArrayList<RelationEntity>();//学历
        joplist=new ArrayList<RelationEntity>();//职务
        creditlist=new ArrayList<RelationEntity>();//信用
        salarylist=new ArrayList<RelationEntity>();//代发工资
        booleantypelis=new ArrayList<RelationEntity>();
        loanlist=new ArrayList<RelationEntity>();
        cardlist=new ArrayList<RelationEntity>();
        houselist=new ArrayList<RelationEntity>();
        provinceList=new ArrayList<>();
        cityList=new ArrayList<>();
        counyList=new ArrayList<>();

//        getDictCode(5, "loanTerm");//贷款期限
        getPorvice();//获得省地区户籍也可以用这个
//        getDictCode(0, "qualifications");//学历
//        getDictCode(2, "productCreditStatus");//信用状况
//        getDictCode(1, "productJop");//职业身份
//        getDictCode(3, "salary");//代发工资金额
        keyLists = new ArrayList<>();
        keyLists.add("qualifications");
        keyLists.add("productJop");
        keyLists.add("productCreditStatus");
        keyLists.add("salary");
        keyLists.add("loanTerm");
        getDictionaryContent(keyLists);//获取选项


    }


//省
    private void getPorvice() {
       RequestManager.getCommManager().getProvince(new RequestManager.CallBack() {
           @Override
           public void onSucess(String result) throws JSONException {
               JSONObject jsonObject = new JSONObject(result);
               JSONArray data = jsonObject.getJSONArray("data");
               Gson gson = new Gson();
               provinceList = gson.fromJson(data.toString(), new TypeToken<List<JJTProvinceEntity>>() {
               }.getType());
               ParamsUtil.getInstance().setProvinceEntityList(provinceList);
               queryCity(provinceList.get(0).getCode());//获取城市
               if (provinceList != null && provinceList.size() > 0) {
                   dialogJJTAddress = new ShenSiDialog(getActivity(), provinceList).builder();
                   dialogJJTAddress.getJJTPicker().setOnSrollListener(new JJTInterface() {
                       @Override
                       public void onProvinceSelected(JJTProvinceEntity jjtProvinceEntity) {
                           queryCity(jjtProvinceEntity.getCode());//获取城市
                       }

                       @Override
                       public void onCitySelected(JJTCityEntity jjtCityEntity) {
                           queryDistrict(jjtCityEntity.getCode());//获取地区
                       }

                       @Override
                       public void onCounySelected(JJTCounyEntity jjtCounyEntity) {
                       }
                   });
                   queryCity(provinceList.get(0).getCode());//通过省份id查询城市
               }
           }

           @Override
           public void onError(int status, String msg) {

           }
       });
    }
    //市
    private void queryCity(String proviceCode) {
        RequestManager.getCommManager().getCity(proviceCode, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray data = jsonObject.getJSONArray("data");
                Gson gson = new Gson();
                cityList = gson.fromJson(data.toString(), new TypeToken<List<JJTCityEntity>>() {
                }.getType());
                ParamsUtil.getInstance().setCityEntityList(cityList);
                dialogJJTAddress.getJJTPicker().setCityList();
                if (cityList != null && cityList.size() > 0) {
                    queryDistrict(cityList.get(0).getCode());//查询地区
                }
            }

            @Override
            public void onError(int status, String msg) {

            }
        });
    }
    //區域
    private void queryDistrict(String cityCode) {
        RequestManager.getCommManager().getDistrict(cityCode, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray data = jsonObject.getJSONArray("data");
                Gson gson = new Gson();
                counyList = gson.fromJson(data.toString(), new TypeToken<List<JJTCounyEntity>>() {
                }.getType());
                ParamsUtil.getInstance().setCounyEntityList(counyList);
                dialogJJTAddress.getJJTPicker().setCouny();
            }

            @Override
            public void onError(int status, String msg) {

            }
        });
    }
    //获取选项
    private void getDictionaryContent( List<String> key) {

        RequestManager.getCommManager().findDicMap(key, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                JSONObject jsonObject = new JSONObject(result);
                JSONObject data = jsonObject.getJSONObject("data");
                Gson gson = new Gson();
                xuelilist = gson.fromJson(data.getJSONArray("qualifications").toString(),new TypeToken<List<RelationEntity>>() {
                }.getType());
                joplist = gson.fromJson(data.getJSONArray("productJop").toString(),new TypeToken<List<RelationEntity>>() {
                }.getType());
                creditlist = gson.fromJson(data.getJSONArray("productCreditStatus").toString(),new TypeToken<List<RelationEntity>>() {
                }.getType());
                salarylist = gson.fromJson(data.getJSONArray("salary").toString(),new TypeToken<List<RelationEntity>>() {
                }.getType());
                loanlist = gson.fromJson(data.getJSONArray("loanTerm").toString(),new TypeToken<List<RelationEntity>>() {
                }.getType());


//                }

            }

            @Override
            public void onError(int status, String msg) {

            }
        });
    }


//    //获取选择项
//    private void getDictCode(final int i, String key) {
//        RequestManager.getMangManger().findTjselet(key, new RequestManager.CallBack() {
//            @Override
//            public void onSucess(String result) throws JSONException {
////                JSONObject jsonObject = new JSONObject(result);
////                JSONArray dataArr = jsonObject.getJSONArray("data");
//                Gson gson = new Gson();
//                switch (i) {
//                    case 0://学历
//                        xuelilist = gson.fromJson(result.toString(), new TypeToken<List<RelationEntity>>() {
//                        }.getType());
//                        break;
//                    case 1://职务
//                        joplist = gson.fromJson(result.toString(), new TypeToken<List<RelationEntity>>() {
//                        }.getType());
//                        break;
//                    case 2://信用记录
//                        creditlist = gson.fromJson(result.toString(), new TypeToken<List<RelationEntity>>() {
//                        }.getType());
//                        break;
//                    case 3://代发工资
//                        salarylist = gson.fromJson(result.toString(), new TypeToken<List<RelationEntity>>() {
//                        }.getType());
//                        break;
////
//                    case 5://贷款期限
//                        loanlist = gson.fromJson(result.toString(), new TypeToken<List<RelationEntity>>() {
//                        }.getType());
//                        break;
////
////
//                }
//
//            }
//
//            @Override
//            public void onError(int status, String msg) {
//
//            }
//        });
//    }


    @Override
    public void setListener() {

    }

    @OnClick({R.id.rl_money, R.id.rl_qixian, R.id.rl_diqu, R.id.rl_xueli, R.id.rl_huji, R.id.rl_xyjl, R.id.rl_zhiwei, R.id.rl_nianji, R.id.rl_zhucesc, R.id.rl_cc, R.id.rl_fc, R.id.rl_dfgzje, R.id.rl_bd, R.id.rl_qtzc, R.id.rl_sbsc, R.id.tv_tujian})
    public void toClick(View view) {
        Intent intent = new Intent(getActivity(), EditorAct.class);
        switch (view.getId()) {
            case R.id.rl_money://金额
                money = tv_money.getText().toString();
                intent.putExtra("type", 1);
                if(!TextUtils.isEmpty(money)){
                String text=money.substring(1,money.length());
                    intent.putExtra("text",text);
                }
                startActivityForResult(intent,1);
                break;
            case R.id.rl_qixian://期限
                dialog = new MySelfSheetDialog(getActivity()).builder();
                if (loanlist.size() != 0) {
                    for (int i = 0; i < loanlist.size(); i++) {
                        dialog.addSheetItem(loanlist.get(i).getOptionName(), null, new MySelfSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                tv_qixian.setText(loanlist.get(which - 1).getOptionName());
                                tujianBean.setCreditTime(loanlist.get(which - 1).getOptionValue());
                            }
                        });
                    }
                }
                dialog.show();

//                DialogChooseMonth dialogChooseMonth=new DialogChooseMonth(getActivity(),tList)


                break;
            case R.id.rl_diqu://地区
                dialogJJTAddress.show();
                dialogJJTAddress.setOnSheetItemClickListener(new ShenSiDialog.SexClickListener() {
                    @Override
                    public void getAdress(List<String> adress,List<Integer> id) {
                        String defaultProvince = adress.get(0);
//                        String provinceCode = queryProvinceCodeByName(defaultProvince);
                        String defaultCity = adress.get(1);
//                        String cityCode = queryCityCodeByName(defaultCity);
                        String defaultArea = adress.get(2);//区
//                        String districtCode = queryAreaCodeByName(defaultArea);
                        tv_diqu.setText(defaultProvince + defaultCity );
                        tujianBean.setCityId(defaultCity);
                    }
                });

                break;
            case R.id.rl_xueli://学历
                dialog = new MySelfSheetDialog(getActivity()).builder();
                if (xuelilist.size() != 0) {
                    for (int i = 0; i < xuelilist.size(); i++) {
                        dialog.addSheetItem(xuelilist.get(i).getOptionName(), null, new MySelfSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                tv_xueli.setText(xuelilist.get(which - 1).getOptionName());
                                tujianBean.setEduLevel(xuelilist.get(which - 1).getOptionValue());
                            }
                        });
                    }
                }
                dialog.show();

                break;
            case R.id.rl_huji://户籍
                dialogJJTAddress.show();
                dialogJJTAddress.setOnSheetItemClickListener(new ShenSiDialog.SexClickListener() {
                    @Override
                    public void getAdress(List<String> adress,List<Integer> id) {
                        String defaultProvince = adress.get(0);
//                        String provinceCode = queryProvinceCodeByName(defaultProvince);
                        String defaultCity = adress.get(1);
//                        String cityCode = queryCityCodeByName(defaultCity);
                        String defaultArea = adress.get(2);
//                        String districtCode = queryAreaCodeByName(defaultArea);
                        tv_huji.setText(defaultProvince + defaultCity);
                        tujianBean.setDomicile(cityList.get(id.get(1)).getCode());//户籍传id
                    }
                });

                break;
            case R.id.rl_xyjl://性用记录
                dialog = new MySelfSheetDialog(getActivity()).builder();
                if (creditlist.size() != 0) {
                    for (int i = 0; i < creditlist.size(); i++) {
                        dialog.addSheetItem(creditlist.get(i).getOptionName(), null, new MySelfSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                tv_xyjl.setText(creditlist.get(which - 1).getOptionName());
                                tujianBean.setCreditStatusKey(creditlist.get(which - 1).getOptionName());
                            }
                        });
                    }
                }
                dialog.show();


                break;
            case R.id.rl_zhiwei://职务
                dialog = new MySelfSheetDialog(getActivity()).builder();
                if (joplist.size() != 0) {
                    for (int i = 0; i < joplist.size(); i++) {
                        dialog.addSheetItem(joplist.get(i).getOptionName(), null, new MySelfSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                tv_zhiwei.setText(joplist.get(which - 1).getOptionName());
                                tujianBean.setJobIdentityKey(joplist.get(which - 1).getOptionName());
                            }
                        });
                    }
                }
                dialog.show();

                break;
            case R.id.rl_nianji://年纪
                nianji = tv_nianji.getText().toString().trim();
                intent.putExtra("type", 2);
                if(!TextUtils.isEmpty(nianji)&&!nianji.equals("例如:33岁")){
                String text=nianji.substring(0,nianji.length()-1);
                intent.putExtra("text",text);}
                startActivityForResult(intent,2);


                break;
            case R.id.rl_zhucesc://注册时长
                gzsc = tv_zhucesc.getText().toString().trim();
                intent.putExtra("type", 3);
                if(!TextUtils.isEmpty(gzsc)&&!gzsc.equals("例如:3个月")){
                    String text=gzsc.substring(0,gzsc.length()-2);
                    intent.putExtra("text",text);
                }
                startActivityForResult(intent,3);

                break;
            case R.id.rl_cc://车产
                dialog = new MySelfSheetDialog(getActivity()).builder();

                dialog.addSheetItem("是", null, new MySelfSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        tv_cc.setText("是");
                        tujianBean.setCarStatusKey("1");
                    }
                }).addSheetItem("否", null, new MySelfSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        tv_cc.setText("否");
                        tujianBean.setCarStatusKey("0");
                    }
                });
                dialog.show();

                break;
            case R.id.rl_fc://房产
                dialog = new MySelfSheetDialog(getActivity()).builder();
                dialog.addSheetItem("是", null, new MySelfSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        tv_fc.setText("是");
                        tujianBean.setPropertyTypeKey("1");
                    }
                }).addSheetItem("否", null, new MySelfSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        tv_fc.setText("否");
                        tujianBean.setPropertyTypeKey("0");
                    }
                });
                dialog.show();

                break;
            case R.id.rl_dfgzje://代发工资
                dialog = new MySelfSheetDialog(getActivity()).builder();
                if (salarylist.size() != 0) {
                    for (int i = 0; i < salarylist.size(); i++) {
                        dialog.addSheetItem(salarylist.get(i).getOptionName(), null, new MySelfSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                tv_dfgzje.setText(salarylist.get(which - 1).getOptionName());
                                tujianBean.setSalary(salarylist.get(which - 1).getOptionValue());
                            }
                        });
                    }
                }
                dialog.show();
                break;
            case R.id.rl_bd://保单
                dialog = new MySelfSheetDialog(getActivity()).builder();
                dialog.addSheetItem("是", null, new MySelfSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        tv_bd.setText("是");
                        tujianBean.setGuaranteeSlip("1");
                    }
                }).addSheetItem("否", null, new MySelfSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        tv_bd.setText("否");
                        tujianBean.setGuaranteeSlip("0");
                    }
                });
                dialog.show();
                break;
            case R.id.rl_qtzc://其他资产
                dialog = new MySelfSheetDialog(getActivity()).builder();
                dialog.addSheetItem("是", null, new MySelfSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        tv_qtzc.setText("是");
                        tujianBean.setOtherAssets("1");
                    }
                }).addSheetItem("否", null, new MySelfSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        tv_qtzc.setText("否");
                        tujianBean.setOtherAssets("0");
                    }
                });
                dialog.show();
                break;
            case R.id.rl_sbsc://社保时长
                sbcs = tv_cbsc.getText().toString().trim();
                intent.putExtra("type", 4);
                if(!TextUtils.isEmpty(sbcs)&&!sbcs.equals("例如:24个月")){
                    String text=sbcs.substring(0,sbcs.length()-2);
                    intent.putExtra("text",text);
                }
                startActivityForResult(intent,4);
                break;
            case R.id.tv_tujian://推荐
                if (isNext()) {
//                    tujianBean.setCreditMoney(money);//金额
//                    tujianBean.setDomicile(huji);//户籍
//                    tujianBean.setAge(nianji);//年纪
//                    tujianBean.setLicenseTimeLength(gzsc);//工作时长
//                    tujianBean.setFundTimeLength(sbcs);//社保时长
////                   EventBus.getDefault().post(new RecomFragment.FirstEvent());
//                    Bundle bundle=new Bundle();
                    getActivity().sendBroadcast(new Intent(RecomFragment.CHANGE));
                }

                break;
        }
    }

    public boolean isNext() {
        money = tv_money.getText().toString();
        time = tv_qixian.getText().toString().trim();
        diqu = tv_diqu.getText().toString().trim();
        xueli = tv_xueli.getText().toString().trim();
        huji = tv_huji.getText().toString().trim();
        xyjl = tv_xyjl.getText().toString().trim();
        zhiwu = tv_zhiwei.getText().toString().trim();
        nianji = tv_nianji.getText().toString().trim();
        gzsc = tv_zhucesc.getText().toString().trim();
        cc = tv_cc.getText().toString().trim();
        fc = tv_fc.getText().toString().trim();
        dfgz = tv_dfgzje.getText().toString().trim();
        bd = tv_bd.getText().toString().trim();
        qtzc = tv_qtzc.getText().toString().trim();
        sbcs = tv_cbsc.getText().toString().trim();
        if(TextUtils.isEmpty(time)||time.equals("请选择")){
            tujianBean.setCreditTime("");
        }
        if(TextUtils.isEmpty(diqu)||diqu.equals("请选择")){
            tujianBean.setCityId("");
        }
        if(TextUtils.isEmpty(money)){//金额
            tujianBean.setCreditMoney("");
        }
        if(TextUtils.isEmpty(xueli)||xueli.equals("请选择")){
            tujianBean.setEduLevel("");
        }
        if(TextUtils.isEmpty(huji)||huji.equals("请选择")){
            tujianBean.setDomicile("");
        }
        if(TextUtils.isEmpty(xyjl)||xyjl.equals("请选择")){
            tujianBean.setCreditStatusKey("");
        }
        if(TextUtils.isEmpty(nianji)||nianji.equals("例如:33岁")){//年纪
            tujianBean.setAge("");
        }
        if(TextUtils.isEmpty(zhiwu)||zhiwu.equals("请选择")){
            tujianBean.setJobIdentityKey("");
        }
        if(TextUtils.isEmpty(gzsc)||gzsc.equals("例如:3个月")){//工作时长
            tujianBean.setLicenseTimeLength("");
        }
        if(TextUtils.isEmpty(cc)||cc.equals("请选择")){
            tujianBean.setCarStatusKey("");
        }
        if(TextUtils.isEmpty(fc)||fc.equals("请选择")){
            tujianBean.setPropertyTypeKey("");
        }
        if(TextUtils.isEmpty(dfgz)||dfgz.equals("请选择")){
            tujianBean.setSalary("");
        }
        if(TextUtils.isEmpty(bd)||bd.equals("请选择")){
            tujianBean.setGuaranteeSlip("");
        }
        if(TextUtils.isEmpty(qtzc)||qtzc.equals("请选择")){
            tujianBean.setOtherAssets("");
        }
        if(TextUtils.isEmpty(sbcs)||sbcs.equals("例如:24个月")){//社保时长
            tujianBean.setFundTimeLength("");
        }



        if (TextUtils.isEmpty(tv_money.getText())) {
            MyToastUtils.showShortToast(getActivity(), "贷款金额不能为空");
            return false;
        }
        if (TextUtils.isEmpty(tv_qixian.getText())||tv_qixian.getText().equals("请选择")) {
            MyToastUtils.showShortToast(getActivity(), "期限不能为空");
            return false;
        }
        if(diqu.equals("请选择")&&xueli.equals("请选择")&&huji.equals("请选择")&&xyjl.equals("请选择")&&zhiwu.equals("请选择")&&nianji.equals("例如:33岁")
                &&gzsc.equals("例如:3个月")&&cc.equals("请选择")&&fc.equals("请选择")&&dfgz.equals("请选择")&&bd.equals("请选择")&&qtzc.equals("请选择")&&sbcs.equals("例如:24个月")){
            MyToastUtils.showShortToast(getActivity(), "请至少填写一项资料信息");
            return false;
        }


        return true;
    }
//    public String queryProvinceCodeByName(String name) {
//        for (int i = 0; i < provinceList.size(); i++) {
//            JJTProvinceEntity jjtProvinceEntity = provinceList.get(i);
//            if (jjtProvinceEntity.getName().equals(name)) {
//                return jjtProvinceEntity.getCode();
//            }
//        }
//        return "";
//    }
//
//    public String queryCityCodeByName(String name) {
//        for (int i = 0; i < cityList.size(); i++) {
//            JJTCityEntity jjtProvinceEntity = cityList.get(i);
//            if (jjtProvinceEntity.getName().equals(name)) {
//                return jjtProvinceEntity.getCode();
//            }
//        }
//        return "";
//    }
//
//    public String queryAreaCodeByName(String name) {
//        for (int i = 0; i < counyList.size(); i++) {
//            JJTCounyEntity jjtProvinceEntity = counyList.get(i);
//            if (jjtProvinceEntity.getName().equals(name)) {
//                return jjtProvinceEntity.getCode();
//            }
//        }
//        return "";
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
            case RESULT_OK:
                String str = data.getExtras().getString("text");//str即为回传的值 //data为B中回传的Intent
                if(requestCode==1){
                    tv_money.setText("￥"+str);
                    tujianBean.setCreditMoney(str);
                }else
                if(requestCode==2){
                    tv_nianji.setText(str+"岁");
                    tujianBean.setAge(str);
                }else
                if(requestCode==3){
                    tv_zhucesc.setText(str+"个月");
                    tujianBean.setLicenseTimeLength(str);

                }else
                if(requestCode==4){
                    tv_cbsc.setText(str+"个月");
                    tujianBean.setFundTimeLength(str);
                }

                break;
            default:
                break;
        }
    }
}





