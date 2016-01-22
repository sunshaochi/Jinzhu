package com.beyonditsm.financial.http;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.beyonditsm.financial.entity.ChangePwdEntity;
import com.beyonditsm.financial.entity.MyCreditEntity;
import com.beyonditsm.financial.entity.OrderBean;
import com.beyonditsm.financial.entity.ServantEntity;
import com.beyonditsm.financial.entity.UserEntity;
import com.beyonditsm.financial.util.GsonUtils;
import com.beyonditsm.financial.util.MyLogUtils;
import com.beyonditsm.financial.util.ParamsUtil;
import com.lidroid.xutils.http.client.multipart.content.FileBody;
import com.lidroid.xutils.util.LogUtils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 共同接口
 * Created by wangbin on 15/11/23.
 */
public class CommManager extends RequestManager {

    /**
     * 登录
     *
     * @param ue
     * @param callBack
     */
    public void toLogin(UserEntity ue, CallBack callBack) {
        List<NameValuePair> queryParams = new ArrayList<NameValuePair>();
        queryParams.add(new BasicNameValuePair("username", ue.getUsername()));
        queryParams.add(new BasicNameValuePair("password", ue.getPassword()));
        queryParams.add(new BasicNameValuePair("captcha", ue.getCaptcha()));
        doPost(IFinancialUrl.LOGIN_URL, queryParams, callBack);
//        doPost("http://172.16.12.236:8080/easyplay/loginView.do", queryParams, callBack);
    }

    /**
     * 注册
     *
     * @param ue
     * @param callBack
     */
    public void toRegister(UserEntity ue, CallBack callBack) {
        List<NameValuePair> queryParams = new ArrayList<NameValuePair>();
        queryParams.add(new BasicNameValuePair("username", ue.getUsername()));
        queryParams.add(new BasicNameValuePair("password", ue.getPassword()));
        LogUtils.i(ue.getCaptcha());
        queryParams.add(new BasicNameValuePair("captcha", ue.getCaptcha()));
        doPost(IFinancialUrl.REGISTER_URL, queryParams, callBack);
    }

    /**
     * 注册
     *
     * @param ue
     * @param callBack
     */
    public void toRegister(UserEntity ue, String phoneNumber, String captcha, CallBack callBack) {
        List<NameValuePair> queryParams = new ArrayList<NameValuePair>();
        queryParams.add(new BasicNameValuePair("username", ue.getUsername()));
//        queryParams.add(new BasicNameValuePair("password", ue.getPassword()));
        queryParams.add(new BasicNameValuePair("phoneNumber", phoneNumber));
        queryParams.add(new BasicNameValuePair("captcha", captcha));
        if (!TextUtils.isEmpty(ue.getReferralCode())) {
            queryParams.add(new BasicNameValuePair("referralCode", ue.getReferralCode()));

        }
        doPost(IFinancialUrl.REGISTER_URL, queryParams, callBack);
    }

    /**
     * 获取验证码
     *
     * @param phoneNumber
     * @param callBack
     */
    public void getCode(String phoneNumber, CallBack callBack) {
        List<NameValuePair> queryParams = new ArrayList<NameValuePair>();
        queryParams.add(new BasicNameValuePair("phoneNumber", phoneNumber));
        doPost(IFinancialUrl.GET_CODE, queryParams, callBack);
    }

    /**
     * 查看用户资料
     *
     * @param callBack
     */
    public void findUserInfo(final CallBack callBack) {
        doPost(IFinancialUrl.USERINFO_URL, null, callBack);
    }

    /**
     * 修改密码
     *
     * @param cpe
     * @param callBack
     */
    public void toChangePwd(ChangePwdEntity cpe, final CallBack callBack) {
        MyLogUtils.info("老密码：" + cpe.getPassword() + ";新密码：" + cpe.getNewPassword());
        List<NameValuePair> queryParams = new ArrayList<NameValuePair>();
        queryParams.add(new BasicNameValuePair("password", cpe.getPassword()));
        queryParams.add(new BasicNameValuePair("newPassword", cpe.getNewPassword()));
        doPost(IFinancialUrl.UPDATE_PWD_URL, queryParams, callBack);
    }

    /**
     * 退出
     *
     * @param callBack
     */
    public void toLoginOut(final CallBack callBack) {
        doPost(IFinancialUrl.LOGINOUT_URL, null, callBack);
    }

    /**
     * 我的贷款
     *
     * @param mce
     * @param callBack
     */
    public void myCredit(MyCreditEntity mce, final CallBack callBack) {
        List<NameValuePair> queryParams = new ArrayList<NameValuePair>();
        queryParams.add(new BasicNameValuePair("page", mce.getPage() + ""));
        queryParams.add(new BasicNameValuePair("rows", mce.getRows() + ""));
        doPost(IFinancialUrl.USERCREDIT_URL, queryParams, callBack);
    }

    /**
     * 下拉获取省份
     *
     * @param callBack
     */
    public void findProvince(final CallBack callBack) {
        List<NameValuePair> queryParams = new ArrayList<NameValuePair>();
        doPost(IFinancialUrl.QUERYALLPROVINCE_URL, queryParams, callBack);

    }

    /**
     * 下拉列表根据省份编码获取对应下属城市
     *
     * @param
     * @param callBack
     */
    public void findCity(final CallBack callBack) {
        List<NameValuePair> queryParams = new ArrayList<NameValuePair>();
        queryParams.add(new BasicNameValuePair("code", ""));
        doPost(IFinancialUrl.QUERYALLCITY_URL, queryParams, callBack);

    }

    /**
     * 下拉区域列表，根据城市编码获取对应的下属区域
     *
     * @param
     * @param callBack
     */
    public void findArea(final CallBack callBack) {
        List<NameValuePair> queryParams = new ArrayList<NameValuePair>();
        queryParams.add(new BasicNameValuePair("cityCode", ""));
        doPost(IFinancialUrl.QUERYALLAREA_URL, queryParams, callBack);

    }

    /**
     * 更新资料
     *
     * @param ue
     * @param callBack
     */
    public void updateData(UserEntity ue, CallBack callBack) {
        List<NameValuePair> queryParams = new ArrayList<NameValuePair>();
        MyLogUtils.info("客户信息：" + GsonUtils.bean2Json(ue));
        String json = GsonUtils.bean2Json(ue);
        try {
            JSONObject obj = new JSONObject(json);
            if ((obj.toString()).indexOf("createTime") != -1) {
                obj.remove("createTime");
            }
            if ((obj.toString()).indexOf("createPersonId") != -1) {
                obj.remove("createPerson");
            }
            if ((obj.toString()).indexOf("modifyTime") != -1) {
                obj.remove("modifyTime");
            }
            if ((obj.toString()).indexOf("modifyPersonId") != -1) {
                obj.remove("modifyPersonId");
            }
            Iterator<String> it = obj.keys();
            while (it.hasNext()) {
                String key = it.next();
                queryParams.add(new BasicNameValuePair(key, String.valueOf(obj.get(key))));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        doPost(IFinancialUrl.UPDATE_USER_URL, queryParams, callBack);
    }

    public void toUpLoadFile(final Map<String, FileBody> fileMaps, final CallBack callBack) {
        loadImage(IFinancialUrl.UPLOAD_URL, fileMaps, callBack);
    }

    /**
     * 提交订单
     *
     * @param orderBean
     * @param callBack
     */
    public void submitOrder(OrderBean orderBean, final CallBack callBack) {
        List<NameValuePair> queryParams = new ArrayList<NameValuePair>();
        queryParams.add(new BasicNameValuePair("orderNo", orderBean.getOrderNo()));

        queryParams.add(new BasicNameValuePair("productId", orderBean.getProductId()));
        queryParams.add(new BasicNameValuePair("totalAmount", orderBean.getTotalAmount()));
        queryParams.add(new BasicNameValuePair("totalPeriods", orderBean.getTotalPeriods()));
        queryParams.add(new BasicNameValuePair("periodsAmount", orderBean.getPeriodsAmount()));

        doPost(IFinancialUrl.SUBMITORDER_URL, queryParams, callBack);
    }

    public void loadSmalImage(final Map<String, FileBody> fileMaps, final CallBack callBack) {
        loadImage(IFinancialUrl.UPLOAD_small_URL, fileMaps, callBack);
    }

    /**
     * 忘记密码
     *
     * @param
     * @param newPassword
     * @param callBack
     */
    public void fogetPwd(String phoneNumber, String captcha, String newPassword, CallBack callBack) {
        List<NameValuePair> queryParams = new ArrayList<NameValuePair>();
        queryParams.add(new BasicNameValuePair("phoneNumber", phoneNumber));
        queryParams.add(new BasicNameValuePair("captcha", captcha));
        queryParams.add(new BasicNameValuePair("newPassword", newPassword));
        doPost(IFinancialUrl.FOGET_PWD_URL, queryParams, callBack);
    }

    /**
     * 判断手机号码是否可用
     *
     * @param phoneNumber
     * @param callBack
     */
    public void isPhoneValidate(String phoneNumber, CallBack callBack) {
        List<NameValuePair> queryParams = new ArrayList<NameValuePair>();
        queryParams.add(new BasicNameValuePair("username", phoneNumber));
        doPost(IFinancialUrl.ISVALIDATE_URL, queryParams, callBack);
    }

    /**
     * 发送验证码找回密码验证码
     *
     * @param phoneNumber
     * @param callBack
     */
    public void findpwbyCode(String phoneNumber, CallBack callBack) {
        List<NameValuePair> queryParams = new ArrayList<NameValuePair>();
        queryParams.add(new BasicNameValuePair("phoneNumber", phoneNumber));
        doPost(IFinancialUrl.FPRGET_PWD_SMSCAPTCHA, queryParams, callBack);
    }

    /**
     * 是否允许升级
     *
     * @param callBack
     */
    public void allow(CallBack callBack) {
        List<NameValuePair> queryParams = new ArrayList<NameValuePair>();
        doPost(IFinancialUrl.ISALLOW_UPGRADE_URL, queryParams, callBack);
    }

    /**
     * 是否提交过升级申请
     *
     * @param callBack
     */
    public void isup(CallBack callBack) {
        List<NameValuePair> queryParams = new ArrayList<NameValuePair>();
        doPost(IFinancialUrl.ISSUBMIT_UP_URL, queryParams, callBack);
    }

    /**
     * 升级服务者
     *
     * @param servant
     * @param servant_role_id
     * @param callBack
     */
    public void upgrade(ServantEntity servant, String servant_role_id, CallBack callBack) {
        List<NameValuePair> queryParams = ParamsUtil.getInstance().BeanToForm(GsonUtils.bean2Json(servant));
        queryParams.add(new BasicNameValuePair("servant_role_id", servant_role_id));
        LogUtils.i(queryParams + "");
        doPost(IFinancialUrl.APPLY_LVUP_SERVANT_URL, queryParams, callBack);
    }

    /**
     * 用户查看订单详情
     *
     * @param orderId  订单id
     * @param callBack
     */
    public void checkMyOrderDetail(String orderId, CallBack callBack) {
        MyLogUtils.info("orderId:" + orderId);
        List<NameValuePair> queryParams = new ArrayList<NameValuePair>();
        queryParams.add(new BasicNameValuePair("orderId", orderId));
        doPost(IFinancialUrl.ORDER_DETAIL_URL, queryParams, callBack);
    }

    /**
     * 通过推荐码加好友
     *
     * @param myReferralCode
     * @param callBack
     */
    public void myReferralCode(String myReferralCode, final CallBack callBack) {
        List<NameValuePair> queryParams = new ArrayList<>();
        queryParams.add(new BasicNameValuePair("myReferralCode", myReferralCode));
        doPost(IFinancialUrl.MYREFERRALCODE_URL, queryParams, callBack);
    }

    /**
     * 通过账户id获取好友列表
     *
     * @param callBack
     */
    public void getFriendList(final CallBack callBack) {
        List<NameValuePair> queryParams = new ArrayList<>();
//        queryParams.add(new BasicNameValuePair("clientId", clientId));
        doPost(IFinancialUrl.FRIENDLIST_URL, queryParams, callBack);
    }

    /**
     * 信贷经理抢单成功后 添加订单里的客户为好友
     *
     * @param clientId 订单里面的客户id
     * @param callBack
     */
    public void addFriendAboutOrder(String clientId, final CallBack callBack) {
        List<NameValuePair> queryParams = new ArrayList<>();
        queryParams.add(new BasicNameValuePair("clientId", clientId));
        doPost(IFinancialUrl.ADDFRIENDABOUTORDER_URL, queryParams, callBack);
    }

    /**
     * 上传其他附件所返回订单号
     *
     * @param callBack
     */
    public void loadImage(final Map<String, FileBody> fileMaps, final CallBack callBack) {
        final HttpManager manager = new HttpManager();
        final Map<String, String> par = new HashMap<String, String>();
        par.put("type", "img");
        par.put("uploadLableName", "file");
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                return manager.upLoadFile(IFinancialUrl.UPLOAD_OTHER_URL, par, fileMaps);
            }

            @Override
            protected void onPreExecute() {

            }

            @Override
            protected void onPostExecute(String result) {
                MyLogUtils.info("上传图片：" + result);
                JSONObject obj = null;
                try {
                    obj = new JSONObject(result);
                    int status = obj.getInt("status");
                    if (status == 200) {
                        callBack.onSucess(result);
                    } else {
                        callBack.onError(obj.getInt("status"), obj.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }

    /**
     * 提交附件
     *
     * @param orderNo
     * @param fileMaps
     * @param callBack
     */
    public void submitFujian(String orderNo, Map<String, List<FileBody>> fileMaps, CallBack callBack) {
        submitFujian(IFinancialUrl.SUBMIT_FUJIAN_URL, orderNo, fileMaps, callBack);
    }

    /**
     * 添加好友
     *
     * @param phone
     * @param callBack
     */
    public void addFriend(String phone, CallBack callBack) {
        List<NameValuePair> queryParams = new ArrayList<>();
        queryParams.add(new BasicNameValuePair("phone", phone));
        doPost(IFinancialUrl.ADD_FRIEND_URL, queryParams, callBack);
    }

    /**
     * 检查版本更新
     *
     * @param currentVersion 当前版本
     * @param platform       平台 ANDROID IOS
     * @param callBack
     */
    public void toVersion(int currentVersion, String platform, CallBack callBack) {
        MyLogUtils.info("currentVersion:" + currentVersion + ";;platform:" + platform);
        List<NameValuePair> queryParams = new ArrayList<>();
        queryParams.add(new BasicNameValuePair("currentVersion", currentVersion + ""));
        queryParams.add(new BasicNameValuePair("platform", platform));
        doPost(IFinancialUrl.VERSION_URL, queryParams, callBack);
    }


    /**
     * 字典类型查询字典
     *
     * @param key 字典类型 名下是否有车 key="under_own_car";  两年内信用 key="two_year_credit";
     *            名下房产 key="under_own_hour";  职业身份 key="job_identity" ;信用状况 key="ep_credit_conditions";
     *            产品特点 key="ep_product_features"; 个人资料 key="ep_order_001"; 佣金计算百分比 key="grade_percentage";
     *            机构类型 key ="ep_org_001"; 订单状态 key ="ep_order_001"; 还款方式 key ="ep_order_002";
     *            贷款金额 key ="ep_loan_amount"; 贷款期限 key ="ep_loan_deadline"; 产品特点 key ="ep_product_features";
     *            个人资料 key ="ep_order_001"; 担保类型 key ="111"; 订单状态 key ="ep_order_001";
     *            贷款状态 key ="ep_loan_003"; 抵押类型 key ="ep_product_004"; 担保类型 key ="ep_product_005";
     *            待审批key ="ep_servant_app_sts"; 审批通过 key ="ep_servant_app_sts"; 审批未通过 key ="ep_servant_app_sts";
     *            贷款期限key ="ep_loan_deadline"; 无抵押key ="ep_product_004";
     * @return Map&lt;字典ID, 字典名称&gt;
     */
    public void getDicMap(String key, CallBack callBack) {
        List<NameValuePair> queryParams = new ArrayList<>();
        queryParams.add(new BasicNameValuePair("key", key));
        doPost(IFinancialUrl.DIC_MAP_URL, queryParams, callBack);
    }
}
