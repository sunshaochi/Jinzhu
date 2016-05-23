package com.beyonditsm.financial.http;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.beyonditsm.financial.entity.ChangePwdEntity;
import com.beyonditsm.financial.entity.MyCreditEntity;
import com.beyonditsm.financial.entity.OrderBean;
import com.beyonditsm.financial.entity.ServantEntity;
import com.beyonditsm.financial.entity.SumLoadEntity;
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
        Map<String, String> params = new HashMap<String, String>();
        params.put("username", ue.getUsername());
        params.put("password", ue.getPassword());
        doPost(IFinancialUrl.LOGIN_URL, params, callBack);
    }

    /**
     * 注册
     *
     * @param ue
     * @param callBack
     */
//    public void toRegister(UserEntity ue, CallBack callBack) {
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("username", ue.getUsername());
//        params.put("password", ue.getPassword());
//        params.put("captcha", ue.getCaptcha());
//        doPost(IFinancialUrl.REGISTER_URL, params, callBack);
//    }

    /**
     * 注册
     *
     * @param ue
     * @param callBack
     */
    public void toRegister(UserEntity ue, String phoneNumber, String captcha, CallBack callBack) {
        List<NameValuePair> queryParams = new ArrayList<NameValuePair>();
        queryParams.add(new BasicNameValuePair("phoneNumber",phoneNumber));
        queryParams.add(new BasicNameValuePair("username",ue.getUsername()));
        queryParams.add(new BasicNameValuePair("captcha",captcha));
        if (!TextUtils.isEmpty(ue.getReferralCode())) {
            queryParams.add(new BasicNameValuePair("referralCode", ue.getReferralCode()));

        }

//        Map<String, String> params = new HashMap<String, String>();
//        params.put("phoneNumber", phoneNumber);
//        params.put("username",ue.getUsername());
////        params.put("password", ue.getPassword());
//        MyLogUtils.info("手机号："+phoneNumber+",验证码："+captcha);
//        params.put("captcha", captcha);
//        if (!TextUtils.isEmpty(ue.getReferralCode())) {
//            params.put("referralCode", ue.getReferralCode());
//
//        }
        doPost(IFinancialUrl.REGISTER_URL, queryParams, callBack);
    }

    /**
     * 获取验证码
     *
     * @param phoneNumber
     * @param callBack
     */
    public void getCode(String phoneNumber, CallBack callBack) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("phoneNumber", phoneNumber);
        doPost(IFinancialUrl.GET_CODE, params, callBack);
    }

    /**
     * 查看用户资料
     *
     * @param callBack
     */
    public void findUserInfo(final CallBack callBack) {
        Map<String, String> params = new HashMap<String, String>();
        doPost(IFinancialUrl.USERINFO_URL, params, callBack);
    }

    /**
     * 修改密码
     *
     * @param cpe
     * @param callBack
     */
    public void toChangePwd(ChangePwdEntity cpe, final CallBack callBack) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("password", cpe.getPassword());
        params.put("newPassword", cpe.getNewPassword());
        doPost(IFinancialUrl.UPDATE_PWD_URL, params, callBack);
    }

    /**
     * 退出
     *
     * @param callBack
     */
    public void toLoginOut(final CallBack callBack) {
        Map<String, String> params = new HashMap<String, String>();
        doPost(IFinancialUrl.LOGINOUT_URL, params, callBack);
    }

    /**
     * 我的贷款
     *
     * @param mce
     * @param callBack
     */
    public void myCredit(MyCreditEntity mce, final CallBack callBack) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("page", mce.getPage() + "");
        params.put("rows", mce.getRows() + "");
        doPost(IFinancialUrl.USERCREDIT_URL, params, callBack);
    }

//    /**
//     * 下拉获取省份
//     *
//     * @param callBack
//     */
//    public void findProvince(final CallBack callBack) {
//        List<NameValuePair> queryParams = new ArrayList<NameValuePair>();
//        doPost(IFinancialUrl.QUERYALLPROVINCE_URL, queryParams, callBack);
//
//    }
//
//    /**
//     * 下拉列表根据省份编码获取对应下属城市
//     *
//     * @param
//     * @param callBack
//     */
//    public void findCity(final CallBack callBack) {
//        List<NameValuePair> queryParams = new ArrayList<NameValuePair>();
//        queryParams.add(new BasicNameValuePair("code", ""));
//        doPost(IFinancialUrl.QUERYALLCITY_URL, queryParams, callBack);
//
//    }
//
//    /**
//     * 下拉区域列表，根据城市编码获取对应的下属区域
//     *
//     * @param
//     * @param callBack
//     */
//    public void findArea(final CallBack callBack) {
//        List<NameValuePair> queryParams = new ArrayList<NameValuePair>();
//        queryParams.add(new BasicNameValuePair("cityCode", ""));
//        doPost(IFinancialUrl.QUERYALLAREA_URL, queryParams, callBack);
//
//    }

    /**
     * 更新资料
     *
     * @param ue
     * @param callBack
     */
    public void updateData(UserEntity ue, CallBack callBack) {
        Map<String, String> params = new HashMap<String, String>();
//        MyLogUtils.info("客户信息：" + GsonUtils.bean2Json(ue));
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
                params.put(key, String.valueOf(obj.get(key)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        doPost(IFinancialUrl.UPDATE_USER_URL, params, callBack);
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
        Map<String, String> params = new HashMap<String, String>();
//        params.put("orderNo", orderBean.getOrderNo());
        params.put("productId", orderBean.getProductId());
        params.put("totalAmount", orderBean.getTotalAmount());
        params.put("totalPeriods", orderBean.getTotalPeriods());
        params.put("periodsAmount", orderBean.getPeriodsAmount());
        doPost(IFinancialUrl.SUBMITORDER_URL, params, callBack);
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
        Map<String, String> params = new HashMap<String, String>();
        params.put("phoneNumber", phoneNumber);
        params.put("captcha", captcha);
        params.put("newPassword", newPassword);
        doPost(IFinancialUrl.FOGET_PWD_URL, params, callBack);
    }

    /**
     * 判断手机号码是否可用
     *
     * @param phoneNumber
     * @param callBack
     */
    public void isPhoneValidate(String phoneNumber, CallBack callBack) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("username", phoneNumber);
        doPost(IFinancialUrl.ISVALIDATE_URL, params, callBack);
    }

    /**
     * 发送验证码找回密码验证码
     *
     * @param phoneNumber
     * @param callBack
     */
    public void findpwbyCode(String phoneNumber, CallBack callBack) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("phoneNumber", phoneNumber);
        doPost(IFinancialUrl.FPRGET_PWD_SMSCAPTCHA, params, callBack);
    }

    /**
     * 是否允许升级
     *
     * @param callBack
     */
    public void allow(CallBack callBack) {
        Map<String, String> params = new HashMap<String, String>();
        doPost(IFinancialUrl.ISALLOW_UPGRADE_URL, params, callBack);
    }

    /**
     * 是否提交过升级申请
     *
     * @param callBack
     */
    public void isup(CallBack callBack) {
        Map<String, String> params = new HashMap<String, String>();
        doPost(IFinancialUrl.ISSUBMIT_UP_URL, params, callBack);
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
        Map<String, String> params = new HashMap<String, String>();
        params.put("orderId", orderId);
        doPost(IFinancialUrl.ORDER_DETAIL_URL, params, callBack);
    }

    /**
     * 通过推荐码加好友
     *
     * @param myReferralCode
     * @param callBack
     */
    public void myReferralCode(String myReferralCode, final CallBack callBack) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("myReferralCode", myReferralCode);
        doPost(IFinancialUrl.MYREFERRALCODE_URL, params, callBack);
    }

    /**
     * 通过账户id获取好友列表
     *
     * @param callBack
     */
    public void getFriendList(final CallBack callBack) {
        Map<String, String> params = new HashMap<String, String>();
        doPost(IFinancialUrl.FRIENDLIST_URL, params, callBack);
    }

    /**
     * 信贷经理抢单成功后 添加订单里的客户为好友
     *
     * @param clientId 订单里面的客户id
     * @param callBack
     */
    public void addFriendAboutOrder(String clientId, final CallBack callBack) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("clientId", clientId);
        doPost(IFinancialUrl.ADDFRIENDABOUTORDER_URL, params, callBack);
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
    public void submitFujian(String orderNo, String isSupplementFile, Map<String, List<FileBody>> fileMaps, CallBack callBack) {
        submitFujian(IFinancialUrl.SUBMIT_FUJIAN_URL, orderNo, isSupplementFile, fileMaps, callBack);
    }

    /**
     * 添加好友
     *
     * @param phone
     * @param callBack
     */
    public void addFriend(String phone, CallBack callBack) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("phone", phone);
        doPost(IFinancialUrl.ADD_FRIEND_URL, params, callBack);
    }

    /**
     * 检查版本更新
     *
     * @param currentVersion 当前版本
     * @param platform       平台 ANDROID IOS
     * @param callBack
     */
    public void toVersion(int currentVersion, String platform, CallBack callBack) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("currentVersion", currentVersion + "");
        params.put("platform", platform);
        doPost(IFinancialUrl.VERSION_URL, params, callBack);
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
        Map<String, String> params = new HashMap<String, String>();
        params.put("key", key);
        doPost(IFinancialUrl.DIC_MAP_URL, params, callBack);
    }


    /**
     * 获取上传资料列表
     * @param orderId
     */
    public void getUpLoadList(String orderId,CallBack callBack){
        Map<String, String> params = new HashMap<String, String>();
        params.put("orderId", orderId);
        doPost(IFinancialUrl.UPLOAD_LIST_URL, params, callBack);
    }

    /**
     * 获取需要上传图片详情
     * @param orderId
     * @param flowId
     * @param callBack
     */
    public void findFlowDetail(String orderId,String flowId,CallBack callBack){
        Map<String, String> params = new HashMap<String, String>();
        params.put("orderId", orderId);
        params.put("flowId", flowId);
        doPost(IFinancialUrl.FIND_FLOW_DETAIL_URL, params, callBack);
    }

    /**
     * 提交上传图片
     * @param data
     * @param callBack
     */
    public void submitOrderFlow(SumLoadEntity data,CallBack callBack){
        Map<String, String> params = new HashMap<String, String>();
        params.put("json", GsonUtils.bean2Json(data));
        doPost(IFinancialUrl.SUBIT_ORDER_FLOW_URL, params, callBack);
    }

    /**
     * 提交审核
     * @param orderId
     * @param callBack
     */
    public void applyCredit(String orderId,CallBack callBack){
        Map<String, String> params = new HashMap<String, String>();
        params.put("orderId",orderId);
        doPost(IFinancialUrl.APPLAY_CREDIT_URL, params, callBack);
    }

    /**
     * 查看是否需要增信资料
     * @param orderId
     * @param callBack
     */
    public  void findOrderFlow(String orderId,CallBack callBack){
        Map<String, String> params = new HashMap<String, String>();
        params.put("orderId",orderId);
        doPost(IFinancialUrl.FIND_EXTRA_FlOW_URL, params, callBack);
    }

    /**
     * 订单状态
     * @param orderId
     * @param callBack
     */
    public void applayStatus(String orderId,CallBack callBack){
        Map<String,String> params = new HashMap<>();
        params.put("orderId",orderId);
        doPost(IFinancialUrl.APPLAY_CREDIT_STATUS,params,callBack);
    }

    /**
     * 跳过流程
     * @param orderId
     * @param flowId
     * @param callBack
     */
    public void skipFlow(String orderId,String flowId,CallBack callBack){
        Map<String,String> params = new HashMap<>();
        params.put("orderId",orderId);
        params.put("flowId",flowId);
        doPost(IFinancialUrl.SKIP_FLOW,params,callBack);
    }

    /**
     * 获取服务者条件
     * @param callBack
     */
    public void getServantCondInfo(CallBack callBack){
        Map<String,String> params = new HashMap<>();
        doPost(IFinancialUrl.SERVANT_COND_INFO,params,callBack);
    }

    /**
     * 成为服务者
     * @param callBack
     */
    public void applyServant(CallBack callBack){
        Map<String,String> params = new HashMap<>();
        doPost(IFinancialUrl.APPLY_TO_SERVANT,params,callBack);
    }

    /**
     * 服务和推荐信息获取
     * @param callBack
     */
    public void getServantRmdInfo(CallBack callBack){
        Map<String,String> params = new HashMap<>();
        doPost(IFinancialUrl.SERVANT_RMD_INFO,params,callBack);
    }

    /**
     * 最低提现额度
     * @param callBack
     */
    public void getMinExchange(CallBack callBack){
        Map<String,String> params = new HashMap<>();
        doPost(IFinancialUrl.MIN_EXCHANGE,params,callBack);
    }

    /*获取角色信息*/
    public void getRoleInfo(CallBack callBack){
        Map<String,String> params = new HashMap<>();
        doPost(IFinancialUrl.ROLE_INTO,params,callBack);
    }
}
