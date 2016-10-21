package com.beyonditsm.financial.http;

import android.text.TextUtils;

import com.beyonditsm.financial.entity.ChangePwdEntity;
import com.beyonditsm.financial.entity.CreditOfflineUploadEntity;
import com.beyonditsm.financial.entity.HotProduct;
import com.beyonditsm.financial.entity.MyCreditEntity;
import com.beyonditsm.financial.entity.OrderBean;
import com.beyonditsm.financial.entity.SubmitCreditSpeedEntity;
import com.beyonditsm.financial.entity.SumLoadEntity;
import com.beyonditsm.financial.entity.UserEntity;
import com.beyonditsm.financial.entity.UserOrderInfo1;
import com.beyonditsm.financial.entity.UserOrderInfo2;
import com.beyonditsm.financial.entity.UserOrderInfo3;
import com.beyonditsm.financial.entity.UserOrderInfoEntity;
import com.beyonditsm.financial.util.GsonUtils;
import com.beyonditsm.financial.util.MyLogUtils;
import com.lidroid.xutils.http.client.multipart.content.FileBody;

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
     * @param ue       用户实体类
     * @param callBack 回调
     */
    public void toLogin(UserEntity ue, CallBack callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("username", ue.getUsername());
        params.put("password", ue.getPassword());
        doPost(IFinancialUrl.LOGIN_URL, params, callBack);
    }

    /**
     * 注册
     *
     * @param ue       用户entity
     * @param callBack 回调
     */
    public void toRegister(UserEntity ue, String phoneNumber, String captcha, CallBack callBack) {
        List<NameValuePair> queryParams = new ArrayList<>();
        queryParams.add(new BasicNameValuePair("phoneNumber", phoneNumber));
        queryParams.add(new BasicNameValuePair("username", ue.getUsername()));
        queryParams.add(new BasicNameValuePair("captcha", captcha));
        if (!TextUtils.isEmpty(ue.getReferralCode())) {
            queryParams.add(new BasicNameValuePair("referralCode", ue.getReferralCode()));

        }

        doPost(IFinancialUrl.REGISTER_URL, queryParams, callBack);
    }

    /**
     * 获取验证码
     *
     * @param phoneNumber 手机号
     * @param callBack    回调
     */
    public void getCode(String phoneNumber, CallBack callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("phoneNumber", phoneNumber);
        doPost(IFinancialUrl.GET_CODE, params, callBack);
    }

    /**
     * 获取产品列表的筛选参数
     */
    public void findSortParam(String cityName, final CallBack callBack) {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("cityName", cityName));
        doPost(IFinancialUrl.FIND_PRODUCT_SORT_PARA, params, callBack);
    }

    /**
     * 查看用户资料
     *
     * @param callBack 回调
     */
    public void findUserInfo(final CallBack callBack) {
//        Map<String, String> params = new HashMap<String, String>();
        List<NameValuePair> params = new ArrayList<>();
        doPost(IFinancialUrl.USERINFO_URL, params, callBack);
    }

    /**
     * 修改密码
     *
     * @param cpe      修改密码实体类
     * @param callBack 回调
     */
    public void toChangePwd(ChangePwdEntity cpe, final CallBack callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("password", cpe.getPassword());
        params.put("newPassword", cpe.getNewPassword());
        doPost(IFinancialUrl.UPDATE_PWD_URL, params, callBack);
    }

    /**
     * 退出
     *
     * @param callBack 回调
     */
    public void toLoginOut(final CallBack callBack) {
        Map<String, String> params = new HashMap<>();
        doPost(IFinancialUrl.LOGINOUT_URL, params, callBack);
    }

    /**
     * 我的贷款
     *
     * @param mce      我的贷款实体类
     * @param callBack 回调
     */
    public void myCredit(MyCreditEntity mce, final CallBack callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("page", mce.getPage() + "");
        params.put("rows", mce.getRows() + "");
        doPost(IFinancialUrl.USERCREDIT_URL, params, callBack);
    }

    /**
     * 更新资料
     *
     * @param ue       用户entity
     * @param callBack 回调
     */
    public void updateData(UserEntity ue, CallBack callBack) {
        Map<String, String> params = new HashMap<>();
//        MyLogUtils.info("客户信息：" + GsonUtils.bean2Json(ue));
        String json = GsonUtils.bean2Json(ue);
        try {
            JSONObject obj = new JSONObject(json);
            if ((obj.toString()).contains("createTime")) {
                obj.remove("createTime");
            }
            if ((obj.toString()).contains("createPersonId")) {
                obj.remove("createPerson");
            }
            if ((obj.toString()).contains("modifyTime")) {
                obj.remove("modifyTime");
            }
            if ((obj.toString()).contains("modifyPersonId")) {
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

    /**
     * 上传图片
     *
     * @param fileMaps 图片
     * @param callBack 回调
     */
    public void toUpLoadFile(final Map<String, FileBody> fileMaps, final CallBack callBack) {
        loadImage(IFinancialUrl.UPLOAD_URL, fileMaps, callBack);
    }

    /**
     * 提交订单
     *
     * @param orderBean 订单实体类
     * @param callBack  回调
     */
    public void submitOrder(OrderBean orderBean, final CallBack callBack) {
        Map<String, String> params = new HashMap<>();
//        params.put("orderNo", orderBean.getOrderNo());
        params.put("productId", orderBean.getProductId());
        params.put("totalAmount", orderBean.getTotalAmount());
        params.put("totalPeriods", orderBean.getTotalPeriods());
        params.put("periodsAmount", orderBean.getPeriodsAmount());
        doPost(IFinancialUrl.SUBMITORDER_URL, params, callBack);
    }

    /**
     * 下载小图片
     *
     * @param fileMaps 参数集合
     * @param callBack 回调
     */
    public void loadSmalImage(final Map<String, FileBody> fileMaps, final CallBack callBack) {
        loadImage(IFinancialUrl.UPLOAD_small_URL, fileMaps, callBack);
    }

    /**
     * 忘记密码
     *
     * @param newPassword 新密码
     * @param callBack    回调
     */
    public void fogetPwd(String phoneNumber, String captcha, String newPassword, CallBack callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("phoneNumber", phoneNumber);
        params.put("captcha", captcha);
        params.put("newPassword", newPassword);
        doPost(IFinancialUrl.FOGET_PWD_URL, params, callBack);
    }

    /**
     * 发送验证码找回密码验证码
     *
     * @param phoneNumber 手机号
     * @param callBack    回调
     */
    public void findpwbyCode(String phoneNumber, CallBack callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("phoneNumber", phoneNumber);
        doPost(IFinancialUrl.FPRGET_PWD_SMSCAPTCHA, params, callBack);
    }


    /**
     * 用户查看订单详情
     *
     * @param orderId  订单id
     * @param callBack 回调
     */
    public void checkMyOrderDetail(String orderId, CallBack callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("orderId", orderId);
        doPost(IFinancialUrl.ORDER_DETAIL_URL, params, callBack);
    }

    /**
     * 通过推荐码加好友
     *
     * @param myReferralCode 推荐码
     * @param callBack       回调
     */
    public void myReferralCode(String myReferralCode, final CallBack callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("myReferralCode", myReferralCode);
        doPost(IFinancialUrl.MYREFERRALCODE_URL, params, callBack);
    }

    /**
     * 通过账户id获取好友列表
     *
     * @param callBack 回调
     */
    public void getFriendList(final CallBack callBack) {
        Map<String, String> params = new HashMap<>();
        doPost(IFinancialUrl.FRIENDLIST_URL, params, callBack);
    }

    /**
     * 信贷经理抢单成功后 添加订单里的客户为好友
     *
     * @param clientId 订单里面的客户id
     * @param callBack 回调
     */
    public void addFriendAboutOrder(String clientId, final CallBack callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("clientId", clientId);
        doPost(IFinancialUrl.ADDFRIENDABOUTORDER_URL, params, callBack);
    }

    /**
     * 提交附件
     *
     * @param orderNo  订单号
     * @param fileMaps 图片集合
     * @param callBack 回调
     */
    public void submitFujian(String orderNo, String isSupplementFile, Map<String, List<FileBody>> fileMaps, CallBack callBack) {
        submitFujian(IFinancialUrl.SUBMIT_FUJIAN_URL, orderNo, isSupplementFile, fileMaps, callBack);
    }

    /**
     * 添加好友
     *
     * @param phone    好友手机号
     * @param callBack 回调
     */
    public void addFriend(String phone, CallBack callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("phone", phone);
        doPost(IFinancialUrl.ADD_FRIEND_URL, params, callBack);
    }

    /**
     * 检查版本更新
     *
     * @param currentVersion 当前版本
     * @param platform       平台 ANDROID IOS
     * @param callBack       回调
     */
    public void toVersion(String marketCode, int currentVersion, String platform, CallBack callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("currentVersion", currentVersion + "");
        params.put("platform", platform);
        if (!"".equals(marketCode)) {
            params.put("marketCode", marketCode);
        }
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
     */
    public void getDicMap(String key, CallBack callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("key", key);
        doPost(IFinancialUrl.DIC_MAP_URL, params, callBack);
    }


    /**
     * 获取上传资料列表
     *
     * @param orderId 订单id
     */
    public void getUpLoadList(String orderId, CallBack callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("orderId", orderId);
        doPost(IFinancialUrl.UPLOAD_LIST_URL, params, callBack);
    }

    /**
     * 获取需要上传图片详情
     *
     * @param orderId  订单id
     * @param flowId   流程id
     * @param callBack 回调
     */
    public void findFlowDetail(String orderId, String flowId, CallBack callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("orderId", orderId);
        params.put("flowId", flowId);
        doPost(IFinancialUrl.FIND_FLOW_DETAIL_URL, params, callBack);
    }

    /**
     * 提交上传图片
     *
     * @param data     提交上传图片资料实体类
     * @param callBack 回调
     */
    public void submitOrderFlow(SumLoadEntity data, CallBack callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("json", GsonUtils.bean2Json(data));
        doPost(IFinancialUrl.SUBIT_ORDER_FLOW_URL, params, callBack);
    }

    /**
     * 提交审核
     *
     * @param orderId  订单id
     * @param callBack 回调
     */
    public void applyCredit(String orderId, CallBack callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("orderId", orderId);
        doPost(IFinancialUrl.APPLAY_CREDIT_URL, params, callBack);
    }

    /**
     * 查看是否需要增信资料
     *
     * @param orderId  订单id
     * @param callBack 回调
     */
    public void findOrderFlow(String orderId, CallBack callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("orderId", orderId);
        doPost(IFinancialUrl.FIND_EXTRA_FlOW_URL, params, callBack);
    }

    /**
     * 移动端获取上次使用的地区
     */
    public void getLastRegion(CallBack callBack) {
        Map<String, String> params = new HashMap<>();
        doPost(IFinancialUrl.GET_MOBILE_LAST_REGION_URL, params, callBack);
    }

    /**
     * 订单状态
     *
     * @param orderId  订单id
     * @param callBack 回调
     */
    public void applayStatus(String orderId, CallBack callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("orderId", orderId);
        doPost(IFinancialUrl.APPLAY_CREDIT_STATUS, params, callBack);
    }

    /**
     * 跳过流程
     *
     * @param orderId  订单id
     * @param flowId   流程id
     * @param callBack 回调
     */
    public void skipFlow(String orderId, String flowId, CallBack callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("orderId", orderId);
        params.put("flowId", flowId);
        doPost(IFinancialUrl.SKIP_FLOW, params, callBack);
    }


    /**
     * 服务和推荐信息获取
     *
     * @param callBack 回调
     */
    public void getServantRmdInfo(CallBack callBack) {
        Map<String, String> params = new HashMap<>();
        doPost(IFinancialUrl.SERVANT_RMD_INFO, params, callBack);
    }

    /**
     * 领取奖励
     *
     * @param callBack 回调
     */
    public void getServantReward(CallBack callBack) {
        Map<String, String> params = new HashMap<>();
        doPost(IFinancialUrl.SERVANT_GET_REWARD, params, callBack);
    }

    /**
     * 最低提现额度
     *
     * @param callBack 回调
     */
    public void getMinExchange(CallBack callBack) {
        Map<String, String> params = new HashMap<>();
        doPost(IFinancialUrl.MIN_EXCHANGE, params, callBack);
    }

    /*领取奖励或者修改资料*/
    public void getReceiveReward(UserEntity ue, CallBack callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("userName", ue.getUserName());
        params.put("accountId", ue.getAccountId());
        params.put("id", ue.getId());
        params.put("userSex", String.valueOf(ue.getUserSex()));
        params.put("userAge", String.valueOf(ue.getUserAge()));
        params.put("identCard", ue.getIdentCard());
        params.put("detailAddr", ue.getDetailAddr());
        params.put("marrySts", String.valueOf(ue.getMarrySts()));
        params.put("jobId", ue.getJobId());
        params.put("secailSecurity", String.valueOf(ue.getSecailSecurity()));
        params.put("haveCar", ue.getHaveCar());
        params.put("haveHours", ue.getHaveHours());
        params.put("proFund", String.valueOf(ue.getProFund()));
        params.put("towYearCred", ue.getTowYearCred());
        params.put("province", ue.getProvince());
        params.put("city", ue.getCity());
        params.put("district", ue.getDistrict());
        MyLogUtils.info("实体类里的参数：" + ue.getUserName() + "," + ue.getAccountId() + "," + ue.getId() + "," + ue.getUserSex() + "," + ue.getDetailAddr() + "," + ue.getMarrySts() + "," + ue.getJobId() + "," + ue.getSecailSecurity() + "," + ue.getHaveCar()
                + "," + ue.getHaveHours() + "," + ue.getProFund() + "," + ue.getTowYearCred() + "," + ue.getProvince() + "," + ue.getCity() + "," + ue.getDistrict());
        doPost(IFinancialUrl.RECEIVE_REWARD, params, callBack);
    }

    /*获取vip信息*/
    public void findVipInfo(String username, CallBack callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("userName", username);
        MyLogUtils.info("用户名是否为空：" + username);
        doPost(IFinancialUrl.VIP_INFO, params, callBack);
    }

    public void updateLocation(String area, CallBack callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("area", area);
        MyLogUtils.info("地址：" + area);
        doPost(IFinancialUrl.UPDATE_LOCATION, params, callBack);
    }

    /**
     * @param orderId  订单id
     * @param callBack 回调
     */
    public void getCreditOfflineDetil(String orderId, CallBack callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("orderId", orderId);
        doPost(IFinancialUrl.CREDIT_OFFLINE_LIST_URL, params, callBack);
    }

    /**
     * @param orderId  订单id
     * @param id       图片id
     * @param name     图片名称
     * @param imageUrl 图片地址
     * @param callBack 回调
     */
    public void saveOrUpdateOrderImage(String orderId, String id, String name, String imageUrl, CallBack callBack) {
        CreditOfflineUploadEntity co = new CreditOfflineUploadEntity();
        co.setId(id);
        co.setImageUrl(imageUrl);
        co.setName(name);
        co.setOrderId(orderId);
        Map<String, String> params = new HashMap<>();
        params.put("params", GsonUtils.bean2Json(co));
        doPost(IFinancialUrl.SAVE_OR_UPDATE_ORDER_IMAGE, params, callBack);
    }

    /**
     * 获取信用卡数据
     */
    public void getCreditCards(HotProduct hp, String area, CallBack callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("area", area);
        params.put("page", hp.getPage() + "");
        params.put("rows", hp.getRows() + "");
        doPost(IFinancialUrl.GET_CREDIT_CARD_INFO, params, callBack);
    }

    /**
     * 获取信用卡数据
     */
    public void applyCreditCardClick(String creditCardId, CallBack callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("creditCardId", creditCardId);
        doPost(IFinancialUrl.APPLY_CREDIT_CARD_CLICK, params, callBack);
    }

    /**
     * 获取首页banner
     *
     * @param callBack
     */
    public void getBanner(CallBack callBack) {
        Map<String, String> params = new HashMap<>();
        doPost(IFinancialUrl.GET_BANNER, params, callBack);
    }

    /**
     * 获取急借通列表
     *
     * @param callBack
     */
    public void getCreditSpeed(CallBack callBack) {
        Map<String, String> params = new HashMap<>();
        doPost(IFinancialUrl.GET_CREDIT_SPEED, params, callBack);
    }

    /**
     * 保存急借通第四版块信息接口（城市门店、身份证）
     *
     * @param callBack
     */
    public void saveUserOrderInfo4(String orderId,String idCardFront,String idCardBack,String storeCity,String storeId,String StoreAddress,CallBack callBack) {

        Map<String, String> params = new HashMap<>();
        params.put("orderId",orderId);
        params.put("idcardFront",idCardFront);
        params.put("idcardBack",idCardBack);
        params.put("storeCity",storeCity);
        params.put("storeId",storeId);
        params.put("storeAddr",StoreAddress);
//        JSONObject jsonObject =
        doPost(IFinancialUrl.SAVE_USER_ORDER_INFO4, params, callBack);
    }

    /**
     * 急借通通过城市名查询门店
     *
     * @param city
     * @param callBack
     */
    public void queryVendorByCity(String city, CallBack callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("city", city);
        doPost(IFinancialUrl.QUERY_VENDOR_BY_CITY, params, callBack);
    }

    /**
     * 获取亲属关系
     *
     * @param callBack
     */
    public void queryRelation(CallBack callBack) {
        Map<String, String> params = new HashMap<>();
        doPost(IFinancialUrl.RELATION, params, callBack);
    }

    /**
     * 保存急借通第三板块信息
     *
     * @param entity3
     * @param callBack
     */
    public void saveUserOrderInfo3(UserOrderInfo3 entity3, CallBack callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("orderId",entity3.getOrderId());
        params.put("relatives1Name",entity3.getRelatives1Name());
        params.put("relatives1Rs",entity3.getRelatives1Rs());
        params.put("relatives1ContactNum",entity3.getRelatives1ContactNum());
        params.put("relatives2Name",entity3.getRelatives2Name());
        params.put("relatives2Rs",entity3.getRelatives2Rs());
        params.put("relatives2ContactNum", entity3.getRelatives2ContactNum());
        params.put("colleagueName", entity3.getColleagueName());
        params.put("colleagueRs", entity3.getColleagueRs());
        params.put("colleagueContactNum", entity3.getColleagueContactNum());
        params.put("ecName", entity3.getEcName());
        params.put("ecRs", entity3.getEcRs());
        params.put("ecContactNum", entity3.getEcContactNum());
        doPost(IFinancialUrl.SAVE_USER_ORDER_INFO3, params, callBack);
    }

    /**
     * 保存急借通第二板块信息
     *
     * @param
     * @param callBack
     */
    public void saveUserOrderInfo2(UserOrderInfo2 entity2, CallBack callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("entity2", GsonUtils.bean2Json(entity2));
        doPost(IFinancialUrl.SAVE_USER_ORDER_INFO3, params, callBack);
    }

    /**
     * 借款用途接口
     *
     * @param callBack
     */
    public void queryLoanUse(CallBack callBack) {
        Map<String, String> params = new HashMap<>();
        doPost(IFinancialUrl.QUERY_LOAN_USE, params, callBack);
    }

    /**
     * 点击申贷接口（须传参数：借款用途:purpose;最大承受还款额度:maxRepaymentWeekly;贷款金额:
     * totalAmount;还款期限: totalPeriods;利息:totalLoanInterest;综合费率:realMonthlyRate）
     */

    public void submitSpeedOrder(SubmitCreditSpeedEntity scse, CallBack callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("purpose", scse.getPurpose());
        params.put("maxRepaymentWeekly", scse.getMaxRepaymentWeekly());
        params.put("totalAmount", scse.getTotalAmount());
        params.put("totalPeriods", scse.getTotalPeriods());
        params.put("totalLoanInterest", scse.getTotalLoanInterest());
        params.put("realMonthlyRate", scse.getRealMonthlyRate());
        doPost(IFinancialUrl.SUBMIT_SPEED_CREDIT, params, callBack);
    }

    /**
     * 急借通单位性质接口
     * @param callBack
     */
    public void queryUnitProperty(CallBack callBack) {
        Map<String, String> params = new HashMap<>();
        doPost(IFinancialUrl.QUERY_UNIT_PROPERTY, params, callBack);
    }

    /**
     * 急借通工作性质接口
     * @param callBack
     */
    public void queryWorkingProperty(CallBack callBack) {
        Map<String, String> params = new HashMap<>();
        doPost(IFinancialUrl.QUERY_WORK_PROPERTY, params, callBack);
    }
    /**
     * 急借通工资发放接口
     * @param callBack
     */
    public void querySalary(CallBack callBack){
        Map<String, String> params = new HashMap<>();
        doPost(IFinancialUrl.QUERY_SALARY, params, callBack);
    }
    /**
     * 急借通省份查询接口
     * @param callBack
     */
    public void queryAllProvince(CallBack callBack){
        Map<String, String> params = new HashMap<>();
        doPost(IFinancialUrl.QUERY_ALL_PROVINCE, params, callBack);
    }
    /**
     * 急借通市查询接口
     * @param callBack
     */
    public void queryAllCity(String parentId,CallBack callBack){
        Map<String, String> params = new HashMap<>();
        params.put("parentId",parentId+"");
        doPost(IFinancialUrl.QUERY_ALL_CITY, params, callBack);
    }
    /**
     * 急借通区查询接口
     * @param callBack
     */
    public void queryAllArea(String parentId,CallBack callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("parentId", parentId);
        doPost(IFinancialUrl.QUERY_ALL_AREA, params, callBack);
    }
    public void submitSpeedOrder(String productId,SubmitCreditSpeedEntity scse, CallBack callBack){
        Map<String, String> params = new HashMap<>();
        params.put("productId",productId);
        params.put("purpose",scse.getPurpose()+"");
        params.put("maxRepaymentWeekly",scse.getMaxRepaymentWeekly()+"");
        params.put("totalAmount",scse.getTotalAmount()+"");
        params.put("totalPeriods",scse.getTotalPeriods()+"");
        params.put("totalLoanInterest",scse.getTotalLoanInterest()+"");
        params.put("realMonthlyRate",scse.getRealMonthlyRate()+"");
        MyLogUtils.info("借款用途："+scse.getPurpose()+",最大承受还款额度："+scse.getMaxRepaymentWeekly()+",贷款金额："+scse.getTotalAmount()+",还款期限："+scse.getTotalPeriods()+",总利息："+scse.getTotalLoanInterest()+"，综合费率："+scse.getRealMonthlyRate());
        doPost(IFinancialUrl.SUBMIT_SPEED_CREDIT,params,callBack);

    }

    /**
     * 获取婚姻状况
     * @param callBack
     */
    public void getMarrias(CallBack callBack){
        Map<String, String> params = new HashMap<>();
        doPost(IFinancialUrl.MARITALE,params,callBack);
    }

    /**
     * 获取学历状况
     * @param callBack
     */
    public void getEducation(CallBack callBack){
        Map<String, String> params = new HashMap<>();
        doPost(IFinancialUrl.EDUCATION,params,callBack);
    }

    public void saveEssentialInfo(UserOrderInfo1 userInfo1,CallBack callBack){
        Map<String, String> params = new HashMap<>();
        params.put("orderId",userInfo1.getOrderId()+"");
        params.put("name",userInfo1.getName()+"");
        params.put("contactNum",userInfo1.getContactNum()+"");
        params.put("idcardno",userInfo1.getIdcardno()+"");
        params.put("marriagests",userInfo1.getMarriagests()+"");
        params.put("qualitications",userInfo1.getQualitications()+"");
        params.put("childNum",userInfo1.getChildNum()+"");
        params.put("domicileArea",userInfo1.getDomicileArea()+"");
        params.put("domicileCity",userInfo1.getDomicileCity()+"");
        params.put("domicileDetail",userInfo1.getDomicileDetail()+"");
        params.put("domicileProvince",userInfo1.getDomicileProvince()+"");
        params.put("permanentArea",userInfo1.getPermanentArea()+"");
        params.put("permanentCity",userInfo1.getPermanentCity()+"");
        params.put("permanentProvince",userInfo1.getPermanentProvince()+"");
        params.put("permanentDetail",userInfo1.getPermanentDetail()+"");
        params.put("resSts",userInfo1.getResSts()+"");
        MyLogUtils.info("订单id："+userInfo1.getOrderId()+",姓名："+userInfo1.getName()+",身份证号："+userInfo1.getIdcardno()
                +",联系电话:"+userInfo1.getContactNum()+"，子女数目："+userInfo1.getChildNum()+",学历："+userInfo1.getQualitications()
        +",户籍地省："+userInfo1.getDomicileProvince()+"户籍地市："+userInfo1.getDomicileCity()+",户籍地区："+userInfo1.getDomicileArea()+
        ",户籍地详细地址："+userInfo1.getDomicileDetail()+",常住地省："+userInfo1.getPermanentProvince()+",常住地市："+userInfo1.getPermanentCity()
        +",常住地区："+userInfo1.getPermanentArea()+"，常住地详细地址："+userInfo1.getPermanentDetail()+",居住状况："+userInfo1.getResSts());
        doPost(IFinancialUrl.SAVE_ESSENTIAL_INFO,params,callBack);
    }

    /**
     * 急借通储存第二步信息接口
     * @param callBack
     */
    public void saveQualificationsInfo(String orderId,
                                       String CompanyName,
                                       String CompanyArea,
                                       String CompanyCity,
                                       String CompanyProvince,
                                       String CompanyDetail,
                                       String areaCode,
                                       String CompanyPhoneNum,
                                       String companyNature,
                                       String workingProp,
                                       String salary,
                                       String payout,CallBack callBack){
        Map<String,String> params = new HashMap<>();
        params.put("orderId",orderId);
        params.put("CompanyName",CompanyName);
        params.put("CompanyArea",CompanyArea);
        params.put("CompanyCity",CompanyCity);
        params.put("CompanyProvince",CompanyProvince);
        params.put("CompanyDetail",CompanyDetail);
        params.put("areaCode",areaCode);
        params.put("CompanyPhoneNum",CompanyPhoneNum);
        params.put("companyNature",companyNature);
        params.put("workingProp",workingProp);
        params.put("salary",salary);
        params.put("payout",payout);
        doPost(IFinancialUrl.SAVE_QUALIFICATIONS_INFO,params,callBack);

    }
}
