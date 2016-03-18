package com.beyonditsm.financial.http;

/**
 * Url
 * <p/>
 * Created by wangbin on 15/11/18.
 */
public interface IFinancialUrl {

    /*test1地址*/
    String BASE_URL = "http://139.196.111.82:5018";
    /*正式地址*/
//    String BASE_URL = "http://option.myjinzhu.com";

    /*测试地址2*/
//    String BASE_URL = "http://test.myjinzhu.com:8082";
//    String BASE_URL = "http://option.myjinzhu.com";

    /*正式游戏地址*/
//    String GAME_URL = "http://m.farm.myjinzhu.com/";

    /*测试游戏地址1*/
    String GAME_URL = "http://myjinzhu.com:5011/";
    /*测试游戏地址2*/
//    String GAME_URL = "http://test.myjinzhu.com:63";
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
    /*是否登录*/
    String IS_LOGINER_URL = BASE_URL + API_VERSION + "/manager/user/isLogined.do";
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
    /*任务列表*/
    String TASK_LIST = BASE_URL + API_VERSION + "/assignmentInterface/login/findTaskByAccount.do";
    /*查询用户已完成的任务列表 */
    String TASK_FINISH_URL = BASE_URL + API_VERSION + "/assignmentInterface/login/findFinishTaskByAccount.do";
    /*下发任务到用户*/
    String SEND_TASK_TO_USER = BASE_URL + API_VERSION + "login/sendTaskToUser";
    /*做任务*/
    String DOTASK = BASE_URL + API_VERSION + "/assignmentInterface/login/addTaskAnswer.do";
    /*任务策略*/
    String TASK_STRATEGY = BASE_URL + API_VERSION + "/assignmentInterface/login/findTaskStrategyByTaskId.do";
    /*根据任务大类查询任务列表*/
    String TASK_BIGTYPE_VALUE = BASE_URL + API_VERSION + "/assignmentInterface/login/findTaskByBigType.do";
    /*获取所有任务(包括已完成)*/
    String ALLTASK_URL = BASE_URL + API_VERSION + "/assignmentInterface/login/getAllTaskMsg.do";
    /* 查询已完成、审核中的任务提交的信息*/
    String FINISH_DO_URL = BASE_URL + API_VERSION + "/assignmentInterface/login/findTaskValueByTaskId.do";
    /*查询我推荐的好友列表*/
    String FIND_MY_FRIEND_LIST_URL = BASE_URL + API_VERSION + "/platform/login/findMyFriendListByParams.do";
    /* 查询热门产品列表*/
    String FIND_HOT_PRODUCT_LIST = BASE_URL + API_VERSION + "/bankTerminal/unLogin/findHotProducRanklist.do";
    /* 查询订单历史处理流程*/
    String FIND_ORDER_DEAL_HISTORY = BASE_URL + API_VERSION + "/platform/login/findOrderDealHistory.do";
    /* 根据id查看产品详情 */
    String FIND_ORDER_DETAIL = BASE_URL + API_VERSION + "/bankTerminal/unLogin/findProductById.do";

    /*上传其他附件*/
    String OTHER_FILE_URL = BASE_URL + API_VERSION + "/common/commonAPI/login/uploadOtherAccessory.do";
    /*根据任务id查询任务*/
    String FINDTASK_BY_TASKID_URL = BASE_URL + API_VERSION + "/assignmentInterface/login/findTaskBytaskIds.do";
    /*获取服务者详细*/
    String SERVANT_URL = BASE_URL + API_VERSION + "/servant/login/servantDetail.do";
    /*修改服务者资料*/
    String UPDATE_SERVANT_URL = BASE_URL + API_VERSION + "/servant/login/modifyServant.do";
    /*服务者提现*/
    String SERVICE_WITH_DRAW_URL = BASE_URL + API_VERSION + "/servant/login/servantWithdraw.do";
    /* 查询提现返佣记录*/
    String FIND_SERVANT_WITHDRAW = BASE_URL + API_VERSION + "/servant/login/findServantWithdraw.do";

    /* 查看信贷经理的详情*/
    String FIND_CREDIT_MANAGER_DETAIL = BASE_URL + API_VERSION + "/bankTerminal/login/creditManagerDetail.do";
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
    /*根据产品id查询产品详情*/
    String PRODUCT_DETAIL_URL = BASE_URL + API_VERSION + "/bankTerminal/login/productDetail.do";

    /*下拉式列表，根据省份编码获取对应的下属城市*/
    String QUERYALLCITY_URL = BASE_URL + API_VERSION + "/common/commonAPI/queryAllCity.do";
    /*下拉查询省份*/
    String QUERYALLPROVINCE_URL = BASE_URL + API_VERSION + "/common/commonAPI/queryAllProvince.do";
    /*下拉区域列表，根据城市编码获取对应的下属区域 */
    String QUERYALLAREA_URL = BASE_URL + API_VERSION + "/common/commonAPI/queryAllArea.do";
    /*上传文件*/
    String UPLOAD_URL = BASE_URL + API_VERSION + "/common/commonAPI/login/upLoadFile.do";

    /*上传小图片文件*/
    String UPLOAD_small_URL = BASE_URL + API_VERSION + "/common/commonAPI/login/upLoadSmallImage.do";

    /*获取产品列表*/
    String GET_PRODUCT_URL = BASE_URL + API_VERSION + "/bankTerminal/login/productProsceniums.do";
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
    /*升级服务者*/
    String APPLY_LVUP_SERVANT_URL = BASE_URL + API_VERSION + "/servant/login/applyLvUpToServant.do";
    /*用户查看自己的订单详情*/
    String CHECK_COMMON_ORDER_URL = BASE_URL + API_VERSION + "/platform/login/findMyOrderById.do";
    /*计算月供*/
    String MONTH_PAY_URL = BASE_URL + API_VERSION + "/common/commonAPI/unLogin/calcMonthlyPayments.do";
    /*获取升级服务者信息*/
    String UPTOSERVANT_URL = BASE_URL + API_VERSION + "/servant/login/judgeIsLvUpOfServant.do";
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
    /*订单明细（现金券）*/
    String CASH_ORDER_DETAIL = BASE_URL + API_VERSION + "/wallet/login/cashTOrderDetail.do";
    /*订单明细（折扣券）*/
    String DEDUCTION_ORDER_DETAIL = BASE_URL + API_VERSION + "/wallet/login/deductionTOrderDetail.do";
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

}
