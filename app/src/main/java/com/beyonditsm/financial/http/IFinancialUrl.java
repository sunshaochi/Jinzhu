package com.beyonditsm.financial.http;

/**
 * Url
 * <p>
 * Created by wangbin on 15/11/18.
 */
public interface IFinancialUrl {
    /*吕建明*/
//    String BASE_URL = "http://172.16.6.236:8080";
//    String BASE_URL = "http://172.16.6.95:8083";
//    String BASE_URL = "http://172.16.5.32:8083";
    /*吕东测试*/
//    String BASE_URL = "http://172.16.5.59";

//    String BASE_URL ="http://172.16.6.218:8082";

//    String BASE_URL ="http://172.16.6.218:8082";
//    String BASE_URL ="http://172.16.6.159:8061";

//    String BASE_URL ="http://172.16.7.196:8083";
    /*袁峰测试*/
//    String BASE_URL = "http://139.196.227.53:9010";
//    String BASE_URL = "http://172.16.6.228:8080";
    /*验收环境地址*/
//    String BASE_URL = "http://test.myjinzhu.com:8086";
////        /*验收环境游戏地址*/
//    String GAME_URL = "http://myjinzhu.com:5011/";
    /*正式地址*/
//    String BASE_URL = "http://option.myjinzhu.com";
    /*王建*/
//<<<<<<< HEAD
//    String BASE_URL = "http://139.196.227.38:8086";
//    String BASE_URL = "http://139.196.227.53:8080";
//    String BASE_URL = "http://139.196.227.38:8080";
//>>>>>>> jijietong

    //        /*正式游戏地址*/
    String GAME_URL = "http://m.farm.myjinzhu.com/";

//        String BASE_URL = "http://test.myjinzhu.com:8086";
    /*测试急借通接口*/
//    String BASE_URL = "http://172.16.5.32:8080";

//<<<<<<< HEAD
//    String BASE_URL = "http://test.myjinzhu.com:8086";
//        String BASE_URL = "http://test.myjinzhu.com:8082";
//=======
//    String BASE_URL = "http://139.196.227.38:8080";
//    String BASE_URL = "http://172.16.5.151:8080";
    //产品优化接口测试
//    String BASE_URL = "http://139.196.227.38:8080";
//    String BASE_URL = "http://139.196.227.38:8080";
//    String BASE_URL = "http://139.196.227.38:8080";
//>>>>>>> jijietong
    /*测试地址3*/
//    String BASE_URL="http://test.myjinzhu.com:60";
//    String BASE_URL = "http://172.16.6.116:8080";

//    String BASE_URL="http://172.16.6.55:8080";
    //帮助中心测试地址
//    String BASE_URL="http://172.16.6.182:8100";

    //内网地址（测试钱包功能）
//    String BASE_URL = "http://172.16.8.22:8082";
    /*测试游戏地址2*/
//    String GAME_URL = "http://test.myjinzhu.com:63";
    //调试地址
//    String BASE_URL = "http://172.16.5.250:8080";
//    String BASE_URL="http://172.16.6.216";
//    String BASE_URL = "http://172.16.6.228:8080";
//    String BASE_URL = "http://172.16.6.228:8080";


      String BASE_URL="http://139.196.227.53:9010";

//    String BASE_URL="http://172.16.5.32:8083";

//      String BASE_URL="http://139.196.227.53:9010";
//    String BASE_URL="http://172.16.5.32:8083";
//    String BASE_URL="http://172.16.5.32:8083";

//    String BASE_URL="http://139.196.227.38:9000";//#  后台

    /**
     * @MARKEY_CODE 渠道号设置，默认渠道设置为空字符串
     */
    String MARKET_CODE = "qudao4";
        String API_VERSION = "/easyplay";
//    String API_VERSION = "/file";

    String BASE_IMAGE_URL = BASE_URL +  "/file/";

    /*登录*/
//    String LOGIN_URL = BASE_URL + API_VERSION + "/manager/unLogin/login.do";
    /*注册*/
//    String REGISTER_URL = BASE_URL + API_VERSION + "/manager/unLogin/register.do";
    /*退出*/
//    String LOGINOUT_URL = BASE_URL + API_VERSION + "/manager/unLogin/logout.do";
    /*发送验证码*/
//    String GET_CODE = BASE_URL + API_VERSION + "/manager/unLogin/registerSmsCaptcha.do";
    /*查找用户个人信息*/
//    String USERINFO_URL = BASE_URL + API_VERSION + "/platform/login/findUserSelfInfo.do";
    /* 获得当前登陆人的信息*/
//    String USER_LOGIN_URL = BASE_URL + API_VERSION + "/manager/login/findUserInfo.do";
    /*修改用户信息*/
//    String UPDATE_USER_URL = BASE_URL + API_VERSION + "/platform/login/modifyCustomerInfoByEntity.do";
//    /*修改密码*/
//    String UPDATE_PWD_URL = BASE_URL + API_VERSION + "/manager/login/modifyUserPassword.do";
    /*通过产品表相关参数查询产品信息*/
    String FIND_PRODUCT_LIST_URL = BASE_URL + API_VERSION + "/platform/unLogin/productList.do";

    /*查询我推荐的好友列表*/
    String FIND_MY_FRIEND_LIST_URL = BASE_URL + API_VERSION + "/platform/login/findMyFriendListByParams.do";


    /*得到移动端的产品列表页面，所有查询参数的选项*/
    String FIND_PRODUCT_SORT_PARA = BASE_URL + "/unLogin/getProductQueryOptions";
    /*通过查询参数获取产品列表页面产品*/
    String FIND_PRODCUT_BY_PARAM = BASE_URL + "/unLogin/getProductsFromOffline";

    /*獲取推薦产品*/
    String RECOMMENDPRODUCTS = BASE_URL + "/unLogin/getRecommendProducts";

    /*根据code值获取字典列表*/
    String FINDALLDICTMAP_URL = BASE_URL + "/unLogin/getAllByDictCode";


    /* 查询热门产品列表*/
//    String FIND_HOT_PRODUCT_LIST = BASE_URL + API_VERSION + "/bankTerminal/unLogin/findHotProducRanklist.do";
    /* 查询订单历史处理流程*/
    String FIND_ORDER_DEAL_HISTORY = BASE_URL + API_VERSION + "/platform/login/findOrderDealHistory.do";
    /* 根据id查看产品详情 */
    String FIND_ORDER_DETAIL = BASE_URL + "/unLogin/getProductDetail";

    /*获取代言人详细*/
    String SERVANT_URL = BASE_URL + API_VERSION + "/servant/login/servantDetail.do";
    /*修改代言人资料*/
    String UPDATE_SERVANT_URL = BASE_URL + API_VERSION + "/servant/login/modifyServant.do";

    /*根据id号查询订单详情*/
//    String ORDER_DETAIL_URL = BASE_URL + API_VERSION + "/platform/login/orderDetail.do";
    /*上传文件*/
    String UPLOAD_URL = BASE_URL + "/uploadFiles.do";

    /*上传小图片文件*/
    String UPLOAD_small_URL = BASE_URL  + "/uploadFiles";

    /*客户提交订单*/
    String SUBMITORDER_URL = BASE_URL + "/submitOrder";
    /*忘记密码*/
//    String FOGET_PWD_URL = BASE_URL + API_VERSION + "/manager/unLogin/forgetPassword.do";
    /*发送验证码找回密码的验证码 */
//    String FPRGET_PWD_SMSCAPTCHA = BASE_URL + API_VERSION + "/manager/unLogin/forgetPasswordSmsCaptcha.do";
    /*计算月供*/
    String MONTH_PAY_URL = BASE_URL + API_VERSION + "/common/commonAPI/unLogin/calcMonthlyPayments.do";

    /*上传附件*/
    String SUBMIT_FUJIAN_URL = BASE_URL + API_VERSION + "/common/commonAPI/login/uploadOtherAccessory.do";
    /*获取附件图片*/
    String GET_SUBMIT_FUJIAN_URL = BASE_URL + API_VERSION + "/platform/login/findOtherAccessoryByAccountIdOrderId.do";

    /*更改订单状态*/
    String UPDATE_ORDER = BASE_URL + API_VERSION + "/platform/login/customerOperationOrder.do";
    /*移动端获取上次使用的地区*/
    String GET_MOBILE_LAST_REGION_URL = BASE_URL + API_VERSION + "/platform/unLogin/getMobileLastRegion.do";
    /*检查版本更新*/
    String VERSION_URL = BASE_URL + API_VERSION + "/platform/unLogin/checkUpdrage.do";
    /*取消订单*/
//    String CANCEL_ORDER = BASE_URL + API_VERSION + "/platform/login/modifyToCancelOrderById.do";
    /*根据key查找各种特点*/
    String DIC_MAP_URL = BASE_URL + "/unLogin/findAllDictMap.do";
    /*个人信息字典*/

//    String FINDALLBYDICTCOD = BASE_URL +"/unLogin/getAllByDictCode";

//    String FINDALLBYDICTCOD = BASE_URL + "/productApi" + "/manager/unLogin/findDictMap.do";


    /*抵扣券兑现提交订单*/
    String SUBMIT_DEDUCTION_ORDER = BASE_URL + API_VERSION + "/wallet/login/submitDeductionTOrder.do";
    /*抵扣券兑现提交订单*/
    String SUBMIT_Cash_ORDER = BASE_URL + API_VERSION + "/wallet/login/submitCashTOrder.do";


    /*设置资金密码*/
//    String SET_PWD_URL = BASE_URL + API_VERSION + "/wallet/login/setFundPassword.do";
    /*现金券收支明细*/
//    String CASH_HISTORY = BASE_URL + API_VERSION + "/wallet/login/cashTHistory.do";
    /*抵扣券收支明细*/
//    String DEDUCTION_HISTORY = BASE_URL + API_VERSION + "/wallet/login/deductionTHistory.do";
    /*订单明细*/
    String ORDER_LIST = BASE_URL + API_VERSION + "/wallet/login/tOrderList.do";

    /*用户的订单编号集合*/
//    String ORDER_NO_LIST = BASE_URL + API_VERSION + "/platform/login/findOrderNoListByUserName.do";
    /*总利息、可抵扣利息*/
    String LIXI = BASE_URL + API_VERSION + "/platform/login/findInterestByOrderNo.do";

    /*获取用户绑定的银行卡*/
//    String QUERY_BANK_CARD = BASE_URL + API_VERSION + "/wallet/login/queryBankCard.do";
    /*获取支持的银行列表*/
//    String QUERY_BANK = BASE_URL + API_VERSION + "/wallet/login/queryBanK.do";
    /*添加银行卡*/
//    String ADD_BANK_CARD = BASE_URL + API_VERSION + "/wallet/login/addBankCard.do";
    /*修改银行卡状态*/
//    String MODIFY_BANK_CARD_STATUS = BASE_URL + API_VERSION + "/wallet/login/modifyBankCard.do";
    /*线下订单资料列表*/
    String CREDIT_OFFLINE_LIST_URL = BASE_URL + API_VERSION + "/platform/login/getOfflineOrderDetail.do";
    /*上传资料列表*/
    String UPLOAD_LIST_URL = BASE_URL + "/findOrderFlow.do";
    /*查找上传详情*/
    String FIND_FLOW_DETAIL_URL = BASE_URL + "/findFlowDetail.do";
    /*查看是否有增信资料*/
    String FIND_EXTRA_FlOW_URL = BASE_URL + API_VERSION + "/platform/login/findExtraOrderFlow.do";
    /*提交图片*/
    String SUBIT_ORDER_FLOW_URL = BASE_URL + "/submitOrderFlow.do";
    /*提交审核*/
    String APPLAY_CREDIT_URL = BASE_URL + "/applyCredit.do";
    /*订单状态*/
    String APPLAY_CREDIT_STATUS = BASE_URL + "/applyCreditStatus.do";
    /*跳过某个流程*/
    String SKIP_FLOW = BASE_URL + "/skipFlow.do";
    //    /*代言人推荐信息获取*/
//    String SERVANT_RMD_INFO = BASE_URL + API_VERSION + "easyplay/servant/login/getServantSummary.do";
    //    /*代言人推荐信息获取*/
//    String SERVANT_GET_REWARD = BASE_URL + API_VERSION + "/servant/unlogin/getServantSummary.do";
    /*领取奖励*/
    String SERVANT_GET_REWARD = BASE_URL + API_VERSION + "/servant/login/pickUnhandledAward.do";
    /*最低提现额度*/
    String MIN_EXCHANGE = BASE_URL + API_VERSION + "/wallet/login/ifGetMinExchange.do";
    /*领取奖励（修改资料）*/
    String RECEIVE_REWARD = BASE_URL + API_VERSION + "/creditCard/login/modifyClient.do";
    /*更新位置*/
//    String UPDATE_LOCATION = BASE_URL + API_VERSION + "/platform/login/updateLocation.do";
    /*线下订单上传资料*/
    String SAVE_OR_UPDATE_ORDER_IMAGE = BASE_URL + API_VERSION + "/platform/login/saveOrUpdateOrderImage.do";
    /*获取信用卡信息*/
    String GET_CREDIT_CARD_INFO = BASE_URL + "/creditCard/unlogin/getCreditCards.do";
    /*在登录状态，点击申卡时,调用统计函数*/
    String APPLY_CREDIT_CARD_CLICK = BASE_URL + API_VERSION + "/creditCard/login/applyCreditCardClick.do";
    /*获取首页Banner*/
//    String GET_BANNER = BASE_URL + "/platform/unLogin/getPublishedMobileBanners.do";

    /*急借通列表*/
    String GET_CREDIT_SPEED = BASE_URL + "/unlogin/findProductPager.do";

    /*急借通列表*/
    String GETSHORTLOANPRODUCTDETAIL = BASE_URL + "/unLogin/getShortLoanProductDetail";

    /*借款用途接口*/
    String QUERY_LOAN_USE = BASE_URL + API_VERSION + "/platform/unlogin/queryLoanUse.do";
    /*婚姻状况下拉接口*/
    String MARITALE = BASE_URL + API_VERSION + "/platform/unlogin/Maritale.do";
    /*学历状况下拉接口*/
    String EDUCATION = BASE_URL + "/unLogin/findAllDictMap";
    /*单位性质*/
    String QUERY_UNIT_PROPERTY = BASE_URL + API_VERSION + "/platform/unlogin/queryUnitProperty.do";
    /*工作性质*/
    String QUERY_WORK_PROPERTY = BASE_URL + API_VERSION + "/platform/unlogin/queryWorkingProperty.do";
    /*月薪发放形式*/
    String QUERY_SALARY = BASE_URL + API_VERSION + "/platform/unlogin/querySalary.do";
    /*极速贷省份接口*/
    String QUERY_ALL_PROVINCE = BASE_URL + "/unlogin/queryAllProvince.do";
    /*极速贷款区域接口*/
    String QUERY_ALL_AREA = BASE_URL + "/unlogin/queryAllArea.do";
    /*极速贷款城市接口*/
    String QUERY_ALL_CITY = BASE_URL + "/unlogin/queryAllCity.do";
    /*保存基本信息*/
    String SAVE_ESSENTIAL_INFO = BASE_URL + "/saveUserOrderInfo1.do";
    /*极速度贷保存资质信息*/
    String SAVE_QUALIFICATIONS_INFO = BASE_URL + "/saveUserOrderInfo2.do";

    /*提交极速贷接口*/
    String SUBMIT_SPEED_CREDIT = BASE_URL + API_VERSION + "/platform/login/submitExtremeCreditOrder.do";
    /*上传急借通第四板块信息接口*/
    String SAVE_USER_ORDER_INFO4 = BASE_URL + "/saveUserOrderInfo4.do";
    /*通过城市查询门店*/
    String QUERY_VENDOR_BY_CITY = BASE_URL + "/unlogin/queryVendorByCity.do";
    /*亲属关系接口*/
    String RELATION = BASE_URL + API_VERSION + "/platform/unlogin/Relation.do";
    /*上传急借通第三板块信息接口*/
    String SAVE_USER_ORDER_INFO3 = BASE_URL + "/saveUserOrderInfo3.do";
    /*个人信息查询省*/
    String QUERY_PROVINCE = BASE_URL + "/unLogin/getPrivinces";
    /*个人信息查询市*/
    String QUERY_CITY = BASE_URL + "/unLogin/getCitys";
    /*个人信息查询区县*/
    String QUERY_DISTRICT = BASE_URL + "/unLogin/getRegions";
    /*极速贷贷款详情接口*/
    String SHORT_LOAN_ORDER_DETAIL = BASE_URL + API_VERSION + "/platform/login/ShortLoanOrderDetail.do";
    /*急速贷推送第三方接口*/
//    String APPLY_SHORT_LOAN_ORDER = BASE_URL +API_VERSION + "/platform/login/applyShortLoanOrder.do";

    //获取提现记录
    String TIXIAN_HISTORY = BASE_URL + "/welfare/selectERsForReception";
    //获取可用券总额
    String WALLET_DISPONIBLES_URL = BASE_URL + "/welfare/selectCouponSummaryByUid";
    //获取现金券列表
    String CASH_HISTORY2 = BASE_URL + "/welfare/selectCRForReception";
    //获取贷款产品
    String GET_CREDIT_PRODUCT = BASE_URL + "/welfare/selOrderNoListByUid";
    //确认订单
    String QUEREN_ORDER_URL = BASE_URL + "/welfare/insertEnchashment";

    /*获取用户绑定的银行卡*/
    String QUERY_BANK_CARD2 = BASE_URL + "/bankcard/queryBankCard";
    /*获取支持的银行列表*/
    String QUERY_BANK2 = BASE_URL + "/bankcard/queryBank.do";
    /*设置资金密码*/
    String SET_PWD_URL2 = BASE_URL + "/wallet/login/setFundPassword.do";
    /*添加银行卡*/
    String ADD_BANK_CARD2 = BASE_URL + "/wallet/login/addBankCard.do";

    /*注册*/
    String REGISTER_URL2 = BASE_URL + "/customer/unLogin/registerCustomer";
    /*发送验证码不需要图片验证*/
    String GET_CODE2 = BASE_URL + "/customer/unLogin/sendCaptcha";
    /*修改银行卡状态*/
    String MODIFY_BANK_CARD_STATUS = BASE_URL + "/bankcard/modifyBankCard";

    /*根据id号查询订单详情*/
    String ORDER_DETAIL_URL = BASE_URL + "/ShortLoanOrderDetail.do";
    /*急速贷推送第三方接口*/
    String APPLY_SHORT_LOAN_ORDER = BASE_URL + "/applyShortLoanOrder.do";

    /*登录*/
    String LOGIN_URL = BASE_URL + "/userlogin/unLogin/login";
    /*取消订单*/
    String CANCEL_ORDER = BASE_URL + "/order/updateOrderStatus";
    /*代言人推荐信息获取*/
    String SERVANT_RMD_INFO = BASE_URL + "/servant/login/getServantSummary.do";
    /*查找获取登录用户个人信息*/
    String USERINFO_URL = BASE_URL + "/customer/getAccountInfo";
    /*修改密码*/
    String UPDATE_PWD_URL = BASE_URL + "customer/unLogin/modifyUserPassword";
    /*发送验证码找回密码的验证码 */
    String FPRGET_PWD_SMSCAPTCHA = BASE_URL + "/customer/unLogin/sendCaptchaPassword";
    /*更新位置*/
    String UPDATE_LOCATION = BASE_URL + "/customer/updateLocation";
    /* 查询热门产品列表*/
    String FIND_HOT_PRODUCT_LIST = BASE_URL + "/weekhotproduct/unLogin/getWeekHotListsByCity";
    /*退出*/
    String LOGINOUT_URL = BASE_URL + "/userlogin/logout";
    /* 获得当前登陆人的信息*/
    String USER_LOGIN_URL = BASE_URL + "/customer/getAccountInfo";
    /*修改用户信息*/
    String UPDATE_USER_URL = BASE_URL + "/customer/modifyCustomer";
    /*忘记密码*/
    String FOGET_PWD_URL = BASE_URL + "/customer/unLogin/forgetPassword";
    //忘记密码短信验证码校验
    String FOGET_PWD_JY_URL = BASE_URL + "/customer/unLogin/checkSMSCaptcha";
    /*我的贷款*/
    String USERCREDIT_URL = BASE_URL +"/getOrderlist";
    /*获取首页Banner*/
    String GET_BANNER = BASE_URL + "/platform/unLogin/getPublishedMobileBanners.do";
    /*查询申贷资料*/
    String GET_CUSTOMER_INFO4_APPLY_ORDER = BASE_URL + "/getCustomerInfo4ApplyOrder";

}
