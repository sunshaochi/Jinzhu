package com.beyonditsm.financial.http;

import com.beyonditsm.financial.entity.HotProduct;
import com.beyonditsm.financial.entity.MyRecommeEntity;
import com.beyonditsm.financial.entity.TaskEntity;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户接口
 * Created by wangbin on 15/11/23.
 */
public class UserManager extends RequestManager {

    /**
     * 查询用户的所有任务列表（未完成，审核中，已完成）
     *
     * @param callBack  回调
     */
    public void findAllTask(final CallBack callBack) {
        doGet(IFinancialUrl.ALLTASK_URL, callBack);
    }

    /**
     * 根据任务id查询已提交的任务详情
     *
     * @param taskEntity  任务实体类
     * @param callBack  回调
     */
    public void findTaskDetail(TaskEntity taskEntity, CallBack callBack) {
//        List<NameValuePair> queryParams = new ArrayList<NameValuePair>();
//        queryParams.add(new BasicNameValuePair("taskId", taskEntity.getId()));
        doGet(IFinancialUrl.FINISH_DO_URL + "?taskId=" + taskEntity.getId(), callBack);
    }

    /**
     * 根据产品列表任务id查询已提交任务详情
     *
     * @param taskEntity 任务实体类
     * @param callBack  回调
     */
    public void findProTaskDetail(TaskEntity taskEntity, CallBack callBack) {
        doGet(IFinancialUrl.FINISH_DO_URL + "?taskId=" + taskEntity.getTaskId(), callBack);
    }

    /**
     * 根据任务id查询任务策略
     *
     * @param taskEntity 任务实体类
     * @param callBack  回调
     */
    public void findTaskStrategy(TaskEntity taskEntity, CallBack callBack) {
        doGet(IFinancialUrl.TASK_STRATEGY + "?taskId=" + taskEntity.getId(), callBack);
    }


    public void findTaskBytaskIds(String taskId, CallBack callBack) {
//        List<NameValuePair> queryParams=new ArrayList<>();
//        queryParams.add(new BasicNameValuePair("taskManageIds",taskId));
        doGet(IFinancialUrl.FINDTASK_BY_TASKID_URL + "?taskManageIds=" + taskId, callBack);
    }

    /**
     * 做任务(提交任务相关信息)
     *
     * @param json 整个json
     * @param callBack  回调
     */
    public void addTaskAnswer(String json, CallBack callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("answerJsonStr", json);
        doPost(IFinancialUrl.DOTASK, params, callBack);
    }


    /**
     * 查找我推荐的好友列表
     *
     * @param fre 我的推荐实体类
     * @param callBack  回调
     */
    public void findFriendList(MyRecommeEntity fre, final CallBack callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("page", fre.getPage() + "");
        params.put("rows", fre.getRows() + "");
        doPost(IFinancialUrl.FIND_MY_FRIEND_LIST_URL, params, callBack);
    }

    /**
     * 计算月供
     *
     * @param callBack  回调
     */
    public void getMonthPay(String repaymentMoney, String rate, String month, CallBack callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("repaymentMoney", repaymentMoney);
        params.put("month", month);
        params.put("rate", rate);
        doPost(IFinancialUrl.MONTH_PAY_URL, params, callBack);
    }

    /**
     * 查询热门产品列表
     *
     * @param hp 热门产品entity
     * @param callBack  回调
     */
    public void findHotProductList(HotProduct hp, CallBack callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("page", hp.getPage() + "");
        params.put("rows", hp.getRows() + "");
        doPost(IFinancialUrl.FIND_HOT_PRODUCT_LIST, params, callBack);
    }


    /**
     * 获取超过多少的用户
     *
     * @param creditScore 信用分
     * @param callBack  回调
     */
    public void getScorePer(String creditScore, CallBack callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("creditScore", creditScore);
        doPost(IFinancialUrl.GET_SCORE_PER_URL, params, callBack);
    }

    /**
     * 查看当前登陆人的信息
     *
     * @param callBack  回调
     */
    public void findUserLoginInfo(CallBack callBack) {
        Map<String, String> params = new HashMap<String, String>();
//        List<NameValuePair> params = new ArrayList<>();
        doPost(IFinancialUrl.USER_LOGIN_URL, params, callBack);
    }

    public void findOrderDealHistory(String orderId, CallBack callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("orderId", orderId);
        doPost(IFinancialUrl.FIND_ORDER_DEAL_HISTORY, params, callBack);
    }

    public void findOrderDetailById(String productId, CallBack callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("productId", productId);
        doPost(IFinancialUrl.FIND_ORDER_DETAIL, params, callBack);
    }

    /**
     * 更改订单状态
     *
     * @param orderId 订单id
     * @param callBack  回调
     */
    public void updateOrder(String orderId, CallBack callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("orderId", orderId);
        doPost(IFinancialUrl.UPDATE_ORDER, params, callBack);
    }

    /**
     * 取消订单
     *
     * @param orderId 订单id
     * @param callBack  回调
     */
    public void cancelOrder(String orderId, CallBack callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("orderId", orderId);
        doPost(IFinancialUrl.CANCEL_ORDER, params, callBack);
    }
}


