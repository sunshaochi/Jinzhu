package com.beyonditsm.financial.http;

/**
 * Url
 * <p>
 * Created by wangbin on 15/11/18.
 */
public interface IFinancialUrl {
    /*吕建明*/
//    String BASE_URL = "http://172.16.6.236:8080";
//    String BASE_URL = "http://172.16.6.95:8080";
    /*吕东测试*/
//    String BASE_URL = "http://172.16.5.59";
//    String BASE_URL ="http://172.16.6.218";
    /*袁峰测试*/
//    String BASE_URL = "http://172.16.6.153:8080";
//    String BASE_URL = "http://172.16.6.228:8080";
    /*验收环境地址*/
//    String BASE_URL = "http://test.myjinzhu.com:8080";
////        /*验收环境游戏地址*/
//    String GAME_URL = "http://myjinzhu.com:5011/";
    /*正式地址*/
//    String BASE_URL = "http://option.myjinzhu.com";
    /*王建*/
//    String BASE_URL = "http://172.16.6.121:8080";

    //        /*正式游戏地址*/
    String GAME_URL = "http://m.farm.myjinzhu.com/";

    //    String BASE_URL = "http://test.myjinzhu.com:8082";
    /*测试急借通接口*/
//    String BASE_URL = "http://172.16.5.151:8080";

    String BASE_URL = "http://139.196.227.38:8080";
//    String BASE_URL = "http://172.16.5.151:8080";
//    String BASE_URL = "http://139.196.227.38:8080";
    /*测试地址3*/
//    String BASE_URL="http://test.myjinzhu.com:60";
    //内网地址（测试钱包功能）
//    String BASE_URL = "http://172.16.8.22:8082";
    /*测试游戏地址2*/
//    String GAME_URL = "http://test.myjinzhu.com:63";
    //调试地址
//    String BASE_URL = "http://172.16.5.250:8080";
//    String BASE_URL="http://172.16.6.216";
//    String BASE_URL = "http://172.16.6.227:8080";
    /**
     * @MARKEY_CODE 渠道号设置，默认渠道设置为空字符串
     */
    String MARKET_CODE = "";
    String API_VERSION = "/easyplay";

    String BASE_IMAGE_URL = BASE_URL + API_VERSION + "/";

    /*登录*/
    String LOGIN_URL = BASE_URL + API_VERSION + "/manager/unLogin/login.do";
    /*验证手机号是否可用*/
    String ISVALIDATE_URL = BASE_URL + API_VERSION + "/manager/unLogin/validateUsername.do";
    /*注册*/
    String REGISTER_URL = BASE_URL + API_VERSION + "/manager/unLogin/register.do";
    /*退出*/
    String LOGINOUT_URL = BASE_URL + API_VERSION + "/manager/unLogin/logout.do";
    /*发送验证码*/
    String GET_CODE = BASE_URL + API_VERSION + "/manager/unLogin/registerSmsCaptcha.do";
    /*查找用户个人信息*/
    String USERINFO_URL = BASE_URL + API_VERSION + "/platform/login/findUserSelfInfo.do";
    /* 获得当前登陆人的信息*/
    String USER_LOGIN_URL = BASE_URL + API_VERSION + "/manager/login/findUserInfo.do";
    /*修改用户信息*/
    String UPDATE_USER_URL = BASE_URL + API_VERSION + "/platform/login/modifyCustomerInfoByEntity.do";
    /*修改密码*/
    String UPDATE_PWD_URL = BASE_URL + API_VERSION + "/manager/login/modifyUserPassword.do";
    /*通过产品表相关参数查询产品信息*/
    String FIND_PRODUCT_LIST_URL = BASE_URL + API_VERSION + "/platform/unLogin/productList.do";
    /*我的贷款*/
    String USERCREDIT_URL = BASE_URL + API_VERSION + "/platform/login/findOrderListByUserName.do";
    /*做任务*/
    String DOTASK = BASE_URL + API_VERSION + "/assignmentInterface/login/addTaskAnswer.do";
    /*任务策略*/
    String TASK_STRATEGY = BASE_URL + API_VERSION + "/assignmentInterface/login/findTaskStrategyByTaskId.do";
    /*获取所有任务(包括已完成)*/
    String ALLTASK_URL = BASE_URL + API_VERSION + "/assignmentInterface/login/getAllTaskMsg.do";
    /* 查询已完成、审核中的任务提交的信息*/
    String FINISH_DO_URL = BASE_URL + API_VERSION + "/assignmentInterface/login/findTaskValueByTaskId.do";
    /*查询我推荐的好友列表*/
    String FIND_MY_FRIEND_LIST_URL = BASE_URL + API_VERSION + "/platform/login/findMyFriendListByParams.do";
    /*得到移动端的产品列表页面，所有查询参数的选项*/
    String FIND_PRODUCT_SORT_PARA = BASE_URL + API_VERSION + "/platform/unLogin/getProductQueryOptions.do";
    /*通过查询参数获取产品列表页面产品*/
    String FIND_PRODCUT_BY_PARAM = BASE_URL + API_VERSION + "/platform/unLogin/fingMobileProductPager.do";
    /* 查询热门产品列表*/
    String FIND_HOT_PRODUCT_LIST = BASE_URL + API_VERSION + "/bankTerminal/unLogin/findHotProducRanklist.do";
    /* 查询订单历史处理流程*/
    String FIND_ORDER_DEAL_HISTORY = BASE_URL + API_VERSION + "/platform/login/findOrderDealHistory.do";
    /* 根据id查看产品详情 */
    String FIND_ORDER_DETAIL = BASE_URL + API_VERSION + "/bankTerminal/unLogin/findProductById.do";

    /*根据任务id查询任务*/
    String FINDTASK_BY_TASKID_URL = BASE_URL + API_VERSION + "/assignmentInterface/login/findTaskBytaskIds.do";
    /*获取代言人详细*/
    String SERVANT_URL = BASE_URL + API_VERSION + "/servant/login/servantDetail.do";
    /*修改代言人资料*/
    String UPDATE_SERVANT_URL = BASE_URL + API_VERSION + "/servant/login/modifyServant.do";

    /* 查看当前登陆的信贷经理详情*/
    String CURR_CREDIT_MANGER_DETAIL = BASE_URL + API_VERSION + "/bankTerminal/login/currLoginCreditManagerDetail.do";
    /* 修改信贷经理信息*/
    String UPDATE_CREDIT_DATAS = BASE_URL + API_VERSION + "/bankTerminal/login/modifyCreditManager.do";
    /*信贷经理抢单列表*/
    String GRAB_ORDER_URL = BASE_URL + API_VERSION + "/platform/login/findOrderListOfCm.do";
    /*信贷经理已抢到的订单列表*/
    String CM_ORDER_URL = BASE_URL + API_VERSION + "/platform/login/findOrderListOfCmEd.do";
    /*信贷经理已提交、补件中、已放款、未通过的订单列表*/
    String CM_COMMIT_ORDER_URL = BASE_URL + API_VERSION + "/platform/login/findOrderListOfCmCommitted.do";
    /*信贷经理要求客户补件*/
    String BJ_ORDER_URL = BASE_URL + API_VERSION + "/platform/login/cmBackOrderToAddFile.do";
    /*信贷经理提交已抢到的订单*/
    String COMMIT_ORDER_URL = BASE_URL + API_VERSION + "/platform/login/cmPassOrder.do";
    /*信贷经理根据id号查询订单详情*/
    String ORDER_DETAIL_URL = BASE_URL + API_VERSION + "/platform/login/orderDetail.do";
    /*上传文件*/
    String UPLOAD_URL = BASE_URL + API_VERSION + "/common/commonAPI/login/upLoadFile.do";

    /*上传小图片文件*/
    String UPLOAD_small_URL = BASE_URL + API_VERSION + "/common/commonAPI/login/upLoadSmallImage.do";

    /*抢单接口*/
    String ORDER_GRAB_URL = BASE_URL + API_VERSION + "/platform/login/cmChooseOrder.do";
    /*客户提交订单*/
    String SUBMITORDER_URL = BASE_URL + API_VERSION + "/platform/login/submitOrder.do";
    /*忘记密码*/
    String FOGET_PWD_URL = BASE_URL + API_VERSION + "/manager/unLogin/forgetPassword.do";
    /*发送验证码找回密码的验证码 */
    String FPRGET_PWD_SMSCAPTCHA = BASE_URL + API_VERSION + "/manager/unLogin/forgetPasswordSmsCaptcha.do";
    /*是否提交过升级 */
    String ISSUBMIT_UP_URL = BASE_URL + API_VERSION + "/platform/login/isSubmitedUpgrade.do";
    /*是否允许升级 */
    String ISALLOW_UPGRADE_URL = BASE_URL + API_VERSION + "/platform/login/isAllowUpgrade.do";
    /*升级代言人*/
    String APPLY_LVUP_SERVANT_URL = BASE_URL + API_VERSION + "/servant/login/applyLvUpToServant.do";
    /*计算月供*/
    String MONTH_PAY_URL = BASE_URL + API_VERSION + "/common/commonAPI/unLogin/calcMonthlyPayments.do";
    /*通过推荐码 加好友*/
    String MYREFERRALCODE_URL = BASE_URL + API_VERSION + "/rongCloudAPIcontroller/login/addFriendByMyReferralCode.do";
    /*通过账号id获取好友列表*/
    String FRIENDLIST_URL = BASE_URL + API_VERSION + "/rongCloudAPIcontroller/login/findFriendListById.do";
    /*信贷经理抢单成功后加客户为好友*/
    String ADDFRIENDABOUTORDER_URL = BASE_URL + API_VERSION + "/rongCloudAPIcontroller/login/addFriendAboutOrder.do";
    /*获取信用分超过多少用户*/
    String GET_SCORE_PER_URL = BASE_URL + API_VERSION + "/platform/login/findToGetCreditScorePer.do";
    /*上传其他附件*/
    String UPLOAD_OTHER_URL = BASE_URL + API_VERSION + "/common/commonAPI/login/uploadOtherAccessory.do";
    /*上传附件*/
    String SUBMIT_FUJIAN_URL = BASE_URL + API_VERSION + "/common/commonAPI/login/uploadOtherAccessory.do";
    /*获取附件图片*/
    String GET_SUBMIT_FUJIAN_URL = BASE_URL + API_VERSION + "/platform/login/findOtherAccessoryByAccountIdOrderId.do";

    /*更改订单状态*/
    String UPDATE_ORDER = BASE_URL + API_VERSION + "/platform/login/customerOperationOrder.do";
    /*添加好友*/
    String ADD_FRIEND_URL = BASE_URL + API_VERSION + "/platform/login/findBookAddByPhone.do";
    /*移动端获取上次使用的地区*/
    String GET_MOBILE_LAST_REGION_URL = BASE_URL + API_VERSION + "/platform/unLogin/getMobileLastRegion.do";
    /*检查版本更新*/
    String VERSION_URL = BASE_URL + API_VERSION + "/platform/unLogin/checkUpdrage.do";
    /*取消订单*/
    String CANCEL_ORDER = BASE_URL + API_VERSION + "/platform/login/modifyToCancelOrderById.do";
    /*根据key查找各种特点*/
    String DIC_MAP_URL = BASE_URL + API_VERSION + "/manager/unLogin/findDictMap.do";

    /*抵扣券兑现提交订单*/
    String SUBMIT_DEDUCTION_ORDER = BASE_URL + API_VERSION + "/wallet/login/submitDeductionTOrder.do";
    /*抵扣券兑现提交订单*/
    String SUBMIT_Cash_ORDER = BASE_URL + API_VERSION + "/wallet/login/submitCashTOrder.do";


    /*设置资金密码*/
    String SET_PWD_URL = BASE_URL + API_VERSION + "/wallet/login/setFundPassword.do";
    /*现金券收支明细*/
    String CASH_HISTORY = BASE_URL + API_VERSION + "/wallet/login/cashTHistory.do";
    /*抵扣券收支明细*/
    String DEDUCTION_HISTORY = BASE_URL + API_VERSION + "/wallet/login/deductionTHistory.do";
    /*订单明细*/
    String ORDER_LIST = BASE_URL + API_VERSION + "/wallet/login/tOrderList.do";

    /*用户的订单编号集合*/
    String ORDER_NO_LIST = BASE_URL + API_VERSION + "/platform/login/findOrderNoListByUserName.do";
    /*总利息、可抵扣利息*/
    String LIXI = BASE_URL + API_VERSION + "/platform/login/findInterestByOrderNo.do";

    /*获取用户绑定的银行卡*/
    String QUERY_BANK_CARD = BASE_URL + API_VERSION + "/wallet/login/queryBankCard.do";
    /*获取支持的银行列表*/
    String QUERY_BANK = BASE_URL + API_VERSION + "/wallet/login/queryBanK.do";
    /*添加银行卡*/
    String ADD_BANK_CARD = BASE_URL + API_VERSION + "/wallet/login/addBankCard.do";
    /*修改银行卡状态*/
    String MODIFY_BANK_CARD_STATUS = BASE_URL + API_VERSION + "/wallet/login/modifyBankCard.do";
    /*线下订单资料列表*/
    String CREDIT_OFFLINE_LIST_URL = BASE_URL + API_VERSION + "/platform/login/getOfflineOrderDetail.do";
    /*上传资料列表*/
    String UPLOAD_LIST_URL = BASE_URL + API_VERSION + "/platform/login/findOrderFlow.do";
    /*查找上传详情*/
    String FIND_FLOW_DETAIL_URL = BASE_URL + API_VERSION + "/platform/login/findFlowDetail.do";
    /*查看是否有增信资料*/
    String FIND_EXTRA_FlOW_URL = BASE_URL + API_VERSION + "/platform/login/findExtraOrderFlow.do";
    /*提交图片*/
    String SUBIT_ORDER_FLOW_URL = BASE_URL + API_VERSION + "/platform/login/submitOrderFlow.do";
    /*提交审核*/
    String APPLAY_CREDIT_URL = BASE_URL + API_VERSION + "/platform/login/applyCredit.do";
    /*订单状态*/
    String APPLAY_CREDIT_STATUS = BASE_URL + API_VERSION + "/platform/login/applyCreditStatus.do";
    /*跳过某个流程*/
    String SKIP_FLOW = BASE_URL + API_VERSION + "/platform/login/skipFlow.do";
    /*代言人条件获取*/
    String SERVANT_COND_INFO = BASE_URL + API_VERSION + "/servant/login/getServantCondInfo.do";
    /*成为代言人*/
    String APPLY_TO_SERVANT = BASE_URL + API_VERSION + "/servant/login/applyToServant.do";
    //    /*代言人推荐信息获取*/
    String SERVANT_RMD_INFO = BASE_URL + API_VERSION + "/servant/login/getServantSummary.do";
    //    /*代言人推荐信息获取*/
//    String SERVANT_GET_REWARD = BASE_URL + API_VERSION + "/servant/unlogin/getServantSummary.do";
    /*领取奖励*/
    String SERVANT_GET_REWARD = BASE_URL + API_VERSION + "/servant/login/pickUnhandledAward.do";
    /*最低提现额度*/
    String MIN_EXCHANGE = BASE_URL + API_VERSION + "/wallet/login/ifGetMinExchange.do";
    /*领取奖励（修改资料）*/
    String RECEIVE_REWARD = BASE_URL + API_VERSION + "/creditCard/login/modifyClient.do";
    /*查询角色信息*/
    String ROLE_INTO = BASE_URL + API_VERSION + "/servant/login/getRoleInfo.do";
    /*Vip信息*/
    String VIP_INFO = BASE_URL + API_VERSION + "/manager/unlogin/findVipInfo.do";
    /*更新位置*/
    String UPDATE_LOCATION = BASE_URL + API_VERSION + "/platform/login/updateLocation.do";
    /*线下订单上传资料*/
    String SAVE_OR_UPDATE_ORDER_IMAGE = BASE_URL + API_VERSION + "/platform/login/saveOrUpdateOrderImage.do";
    /*获得信用卡列表页面的信用卡数据*/
    String GET_CREDIT_CARD_INFO = BASE_URL + API_VERSION + "/creditCard/unlogin/getCreditCards.do";
    /*在登录状态，点击申卡时,调用统计函数*/
    String APPLY_CREDIT_CARD_CLICK = BASE_URL + API_VERSION + "/creditCard/login/applyCreditCardClick.do";
    /*获取首页Banner*/
    String GET_BANNER = BASE_URL + API_VERSION + "/platform/unLogin/getPublishdMobileBanners.do";

    /*急借通列表*/
    String GET_CREDIT_SPEED = BASE_URL + API_VERSION + "/platform/unlogin/fingProductPager.do";

    /*借款用途接口*/
    String QUERY_LOAN_USE = BASE_URL + API_VERSION + "/platform/unlogin/queryLoanUse.do";
    /*婚姻状况下拉接口*/
    String MARITALE = BASE_URL + API_VERSION + "/platform/unlogin/Maritale.do";
    /*学历状况下拉接口*/
    String EDUCATION = BASE_URL + API_VERSION + "/platform/unlogin/Education.do";
    /*居住状况下拉接口*/
    String LIVE = BASE_URL + API_VERSION + "/platform/unlogin/Live.do";
    /*单位性质*/
    String QUERY_UNIT_PROPERTY = BASE_URL + API_VERSION + "/platform/unlogin/queryUnitProperty.do";
    /*工作性质*/
    String QUERY_WORK_PROPERTY = BASE_URL + API_VERSION + "/platform/unlogin/queryWorkingProperty.do";
    /*月薪发放形式*/
    String QUERY_SALARY = BASE_URL + API_VERSION + "/platform/unlogin/querySalary.do";
    /*银行机构*/
    String QUERY_SPEED_BANK = BASE_URL + API_VERSION + "/platform/unlogin/queryBank.do";
    /*还款期限下拉*/
    String LOAN_DEAD_LINE = BASE_URL + API_VERSION + "/platform/unlogin/LoanDeadLine.do";
    /*紧急联系人*/
    String CONTACT = BASE_URL + API_VERSION + "/platform/unlogin/Contact.do";
    /*省份接口*/
    String QUERY_ALL_PROVINCE = BASE_URL + API_VERSION + "/platform/unlogin/queryAllProvince.do";
    /*区域接口*/
    String QUERY_ALL_AREA = BASE_URL + API_VERSION + "/platform/unlogin/queryAllArea.do";
    /*城市接口*/
    String QUERY_ALL_CITY = BASE_URL +API_VERSION +"/platform/unlogin/queryAllCity.do";
    /*保存基本信息*/
    String SAVE_ESSENTIAL_INFO = BASE_URL + API_VERSION +"/platform/login/saveUserOrderInfo1.do";
    /*保存资质信息*/
    String SAVE_QUALIFICATIONS_INFO = BASE_URL +API_VERSION +"/platform/login/saveUserOrderInfo2.do";
    /*保存联系人信息*/
    String SAVE_CONTACTS_INFO = BASE_URL +API_VERSION +"/platform/login/saveUserOrderInfo3.do";
    /*上传资料信息（第四版块）*/
    String SAVE_UPLOAD_INFO = BASE_URL +API_VERSION+"/platform/login/saveUserOrderInfo4.do";

    /*提交极速贷接口*/
    String SUBMIT_SPEED_CREDIT =BASE_URL + API_VERSION + "/platform/login/submitExtremeCreditOrder.do";
    /*上传急借通第四板块信息接口*/
    String SAVE_USER_ORDER_INFO4 = BASE_URL + API_VERSION + "/platform/login/saveUserOrderInfo4.do";
    /*通过城市查询门店*/
    String QUERY_VENDOR_BY_CITY = BASE_URL + API_VERSION + "/platform/unlogin/queryVendorByCity.do";
    /*亲属关系接口*/
    String RELATION = BASE_URL + API_VERSION + "/platform/unlogin/Relation.do";
    /*上传急借通第三板块信息接口*/
    String SAVE_USER_ORDER_INFO3 = BASE_URL + API_VERSION + "/platform/login/saveUserOrderInfo3.do";
}
