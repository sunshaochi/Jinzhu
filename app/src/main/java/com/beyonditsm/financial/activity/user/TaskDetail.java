package com.beyonditsm.financial.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.adapter.SingleTaskDetailAdapter;
import com.beyonditsm.financial.adapter.TaskDetailAdapter;
import com.beyonditsm.financial.entity.TaskEntity;
import com.beyonditsm.financial.entity.TaskMessage;
import com.beyonditsm.financial.entity.TaskStrategyEntity;
import com.beyonditsm.financial.fragment.MineFragment;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.GsonUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * 受教育程度
 * Created by gxy on 2015/11/26.
 */
public class TaskDetail extends BaseActivity {
    @ViewInject(R.id.tv_detail)
    private TextView tv_detail;
    @ViewInject(R.id.lv_detail)
    private ListView lv_task;
    @ViewInject(R.id.btn_submit)
    private Button btn_submit;
    private SingleTaskDetailAdapter singleAdapter;

    private List<TaskEntity> list;//任务列表
    private List<TaskStrategyEntity> strategyList;//任务策略列表
    private List<String> itemList;//任务选项列表
    private List<TaskMessage> messageList;//任务信息列表
    private int position;//位置
    private ImageView iv_yes;
    private String value = "";
    private List<Boolean> blist;

    @Override
    public void setLayout() {
        setContentView(R.layout.task_detail);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("做任务");
        setLeftTv("返回");
        list = new ArrayList<>();
        strategyList = new ArrayList<>();
        itemList = new ArrayList<>();
        messageList = new ArrayList<>();
        blist = new ArrayList<>();
        //    ml=new MyListener();
        //   lv_task.setOnItemClickListener(ml);
        list = getIntent().getParcelableArrayListExtra("list");//任务列表

        strategyList = getIntent().getParcelableArrayListExtra("listStrategey");
        position = getIntent().getIntExtra("position", -1);
        itemList = strategyList.get(0).getOptionValues();
        //给blist的每一项都赋值为false
        for (int i = 0; i < itemList.size(); i++) {
            blist.add(false);
        }
        tv_detail.setText(list.get(position).getTaskDesc());//设置任务内容
        TaskDetailAdapter adapter = new TaskDetailAdapter(itemList, getApplicationContext());
        singleAdapter = new SingleTaskDetailAdapter(itemList, getApplicationContext());
        if (strategyList.get(0).getModelType() == 4) {
            lv_task.setAdapter(adapter);
            for (int i = 0; i < blist.size(); i++) {
                if (blist.get(i)) {
//                    sb.append(itemList.get(i));
//                    sb.append(",");
                    value += itemList.get(i) + ",";
                }
            }
        } else if ((strategyList.get(0).getModelType() == 2) || (strategyList.get(0).getModelType() == 3)) {
            lv_task.setAdapter(singleAdapter);
        }
        if (strategyList.get(0).getModelType() != 4) {
            lv_task.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        }
        lv_task.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (strategyList.get(0).getModelType() == 4) {
                    view = parent.getChildAt(position);
                    iv_yes = (ImageView) view.findViewById(R.id.iv_yes);
                    if (blist.get(position)) {
                        blist.set(position, false);
                        iv_yes.setImageResource(R.mipmap.status_nor);
                    } else {
                        blist.set(position, true);
                        iv_yes.setImageResource(R.mipmap.status_yes);
                    }
                } else if ((strategyList.get(0).getModelType() == 2) || (strategyList.get(0).getModelType() == 3)) {
                    // 设置适配器的选中项
                    singleAdapter.setSelectedPosition(position);
                    // 更新列表框
                    singleAdapter.notifyDataSetInvalidated();
                    value = itemList.get(position);

                }
            }
        });


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (strategyList.get(0).getModelType() == 4) {
                    for (int i = 0; i < blist.size(); i++) {
                        if (blist.get(i)) {
//                            sb.append(itemList.get(i));
//                            sb.append(",");
                            value += itemList.get(i) + ",";
                        }
                    }
                }
                if (value.length() == 0/*||sb.toString().length()==0*/) {
                    Toast.makeText(getApplicationContext(), "提交内容不能为空！", Toast.LENGTH_SHORT).show();
                } else {
                    TaskMessage taskMessage = new TaskMessage();
                    if (!TextUtils.isEmpty(list.get(position).getId())) {
                        taskMessage.setTaskId(list.get(position).getId());
                    }
                    if (!TextUtils.isEmpty(list.get(position).getTaskId())) {
                        taskMessage.setTaskId(list.get(position).getTaskId());
                    }
                    taskMessage.setStrategyId(strategyList.get(0).getStragegyId());
                    taskMessage.setFieldName(strategyList.get(0).getFieldName());
                    taskMessage.setFieldKey(strategyList.get(0).getFieldKey());
                    taskMessage.setFieldValue(value);
//                    if(value.length()!=0){
//                        taskMessage.setFieldValue(value);
//                    }
//                    if(sb.toString().length()!=0){
//                        taskMessage.setFieldValue(sb.toString());
//                    }
                    taskMessage.setScoreType(strategyList.get(0).getScoreType());
                    messageList.add(taskMessage);
                    String json = GsonUtils.bean2Json(messageList);
                    RequestManager.getUserManager().addTaskAnswer(json, new RequestManager.CallBack() {
                        @Override
                        public void onSucess(String result) throws JSONException {
                            finish();
                            sendBroadcast(new Intent(TaskLevelAct.UPDATE_LIST));
                            sendBroadcast(new Intent(HardCreditAct.UPDATE_MYLIST));
                            sendBroadcast(new Intent(CreditPointAct.UPDATE_MYSCORE));
                            sendBroadcast(new Intent(MineFragment.UPDATE_SCORE));
                        }

                        @Override
                        public void onError(int status, String msg) {
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                        }
                    });
                }


            }
        });
    }

}
