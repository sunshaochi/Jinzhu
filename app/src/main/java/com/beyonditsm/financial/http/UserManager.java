package com.beyonditsm.financial.http;

import com.beyonditsm.financial.entity.HotProduct;
import com.beyonditsm.financial.entity.MyRecommeEntity;
import com.beyonditsm.financial.entity.TaskEntity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户接口
 * Created by wangbin on 15/11/23.
 */
public class UserManager extends RequestManager{

    /**
     * 查询用户的任务列表
     * @param callBack
     */
    public void findTaskList(final CallBack callBack){
        doGet(IFinancialUrl.TASK_LIST, callBack);
    }
    /**
     * 查询用户的所有任务列表（未完成，审核中，已完成）
     * @param callBack
     */
    public void findAllTask(final CallBack callBack){
        doGet(IFinancialUrl.ALLTASK_URL, callBack);
    }

    /**
     * 根据任务id查询已提交的任务详情
     * @param taskEntity
     * @param callBack
     */
    public void findTaskDetail(TaskEntity taskEntity,CallBack callBack){
//        List<NameValuePair> queryParams = new ArrayList<NameValuePair>();
//        queryParams.add(new BasicNameValuePair("taskId", taskEntity.getId()));
        doGet(IFinancialUrl.FINISH_DO_URL+"?taskId="+taskEntity.getId(),callBack);
    }

    /**
     * 根据产品列表任务id查询已提交任务详情
     * @param taskEntity
     * @param callBack
     */
    public void findProTaskDetail(TaskEntity taskEntity,CallBack callBack){
        doGet(IFinancialUrl.FINISH_DO_URL+"?taskId="+taskEntity.getTaskId(),callBack);
    }

    /**
     * 根据任务id查询任务策略
     * @param taskEntity
     * @param callBack
     */
    public void findTaskStrategy(TaskEntity taskEntity,CallBack callBack){
        List<NameValuePair> queryParams = new ArrayList<NameValuePair>();
        queryParams.add(new BasicNameValuePair("taskId", taskEntity.getId()));
        doGet(IFinancialUrl.TASK_STRATEGY+"?taskId="+taskEntity.getId(),callBack);
    }

    /**
     * 根据任务id查询任务策略
     * @param taskEntity
     * @param callBack
     */
    public void findProTaskStrategy(TaskEntity taskEntity,CallBack callBack){
        doGet(IFinancialUrl.TASK_STRATEGY+"?taskId="+taskEntity.getTaskId(),callBack);
    }

    public void findTaskBytaskIds(String taskId,CallBack callBack){
//        List<NameValuePair> queryParams=new ArrayList<>();
//        queryParams.add(new BasicNameValuePair("taskManageIds",taskId));
        doGet(IFinancialUrl.FINDTASK_BY_TASKID_URL+"?taskManageIds="+taskId,callBack);
    }

    /**
     * 做任务(提交任务相关信息)
     * @param json
     * @param callBack
     */
    public void addTaskAnswer(String json,CallBack callBack){
        List<NameValuePair> queryParams = new ArrayList<NameValuePair>();
        queryParams.add(new BasicNameValuePair("answerJsonStr",json));
        doPost(IFinancialUrl.DOTASK, queryParams, callBack);
    }

    /**
     * 查询已完成任务列表
     * @param callBack
     */
    public void findFinishTaskByAccount(CallBack callBack){
        doGet(IFinancialUrl.TASK_FINISH_URL, callBack);
    }

    /**
     * 查找我推荐的好友列表
     * @param fre
     * @param callBack
     */
    public void findFriendList(MyRecommeEntity fre,final CallBack callBack){
        List<NameValuePair> queryParams = new ArrayList<NameValuePair>();
        queryParams.add(new BasicNameValuePair("page", fre.getPage()+""));
        queryParams.add(new BasicNameValuePair("rows", fre.getRows()+""));
        doPost(IFinancialUrl.FIND_MY_FRIEND_LIST_URL, queryParams, callBack);
    }

    /**
     * 计算月供
     * @param callBack
     */
    public void getMonthPay(String repaymentMoney,String rate,String month,CallBack callBack){
        List<NameValuePair> queryParams = new ArrayList<NameValuePair>();
        queryParams.add(new BasicNameValuePair("repaymentMoney", repaymentMoney));
        queryParams.add(new BasicNameValuePair("month", month));
        queryParams.add(new BasicNameValuePair("rate", rate));
        doPost(IFinancialUrl.MONTH_PAY_URL,queryParams, callBack);
    }

    /**
     *
     * @param servantRoleType
     * servantRoleType - 参数说明如下： servantRoleType 普通用户升级初级服务者 赋值 primary_servant 中级服务者升级中级服务者 赋值 middle_servant 中级服务者升级为高级服务者 赋值 senior_servant

     * @param callBack
     */
    public void uptoServant(String servantRoleType,CallBack callBack){
        List<NameValuePair> queryParams = new ArrayList<NameValuePair>();
        queryParams.add(new BasicNameValuePair("servantRoleType", servantRoleType));
        doPost(IFinancialUrl.UPTOSERVANT_URL,queryParams, callBack);

    }


    /**
     * 查询热门产品列表
     * @param hp
     * @param callBack
     */
    public void findHotProductList(HotProduct hp,CallBack callBack){
        List<NameValuePair> queryParams = new ArrayList<NameValuePair>();
        queryParams.add(new BasicNameValuePair("page", hp.getPage()+""));
        queryParams.add(new BasicNameValuePair("rows", hp.getRows()+""));
        doPost(IFinancialUrl.FIND_HOT_PRODUCT_LIST,queryParams, callBack);
    }


    /**
     * 获取超过多少的用户
     * @param creditScore
     * @param callBack
     */
    public  void getScorePer(String creditScore,CallBack callBack){
        List<NameValuePair> queryParams = new ArrayList<NameValuePair>();
        queryParams.add(new BasicNameValuePair("creditScore", creditScore));
        doPost(IFinancialUrl.GET_SCORE_PER_URL,queryParams, callBack);
    }

    /**
     * 查看当前登陆人的信息
     * @param callBack
     */
    public void findUserLoginInfo(CallBack callBack){
        doPost(IFinancialUrl.USER_LOGIN_URL,null,callBack);
    }

    public void findOrderDealHistory(String orderId,CallBack callBack){
        List<NameValuePair> queryParams = new ArrayList<NameValuePair>();
        queryParams.add(new BasicNameValuePair("orderId", orderId));
        doPost(IFinancialUrl.FIND_ORDER_DEAL_HISTORY, queryParams, callBack);
    }

    /**
     * 上传其他附件
     * @param orderNo
     * @param callBack
     */
    public void uploadOtherAccessory(String orderNo,CallBack callBack){
        List<NameValuePair> queryParams=new ArrayList<NameValuePair>();
        queryParams.add(new BasicNameValuePair("orderNo",orderNo));
        doPost(IFinancialUrl.OTHER_FILE_URL,queryParams,callBack);
    }

    public void findOrderDetailById(String productId,CallBack callBack){
        List<NameValuePair> queryParams=new ArrayList<NameValuePair>();
        queryParams.add(new BasicNameValuePair("productId",productId));
        doPost(IFinancialUrl.FIND_ORDER_DETAIL,queryParams,callBack);
    }

    /**
     * 更改订单状态
     * @param orderId
     * @param callBack
     */
    public void updateOrder(String orderId,CallBack callBack){
        List<NameValuePair> queryParams=new ArrayList<NameValuePair>();
        queryParams.add(new BasicNameValuePair("orderId",orderId));
        doPost(IFinancialUrl.UPDATE_ORDER,queryParams,callBack);
    }

    /**
     * 取消订单
     * @param orderId
     * @param callBack
     */
    public  void cancelOrder(String orderId,CallBack callBack){
        List<NameValuePair> queryParams=new ArrayList<NameValuePair>();
        queryParams.add(new BasicNameValuePair("orderId",orderId));
        doPost(IFinancialUrl.CANCEL_ORDER,queryParams,callBack);
    }
}


