package com.beyonditsm.financial.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.entity.TaskEntity;
import com.beyonditsm.financial.entity.TaskMessage;
import com.beyonditsm.financial.entity.TaskStrategyEntity;
import com.beyonditsm.financial.fragment.MineFragment;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.GsonUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gxy on 2015/11/18.
 */
public class DoTaskPlaceAct extends BaseActivity {
    @ViewInject(R.id.tv_place)
    private TextView tv_place;
    @ViewInject(R.id.et_place)
    private EditText et_place;

    private List<TaskEntity> list;
    private List<TaskStrategyEntity> strategyList;
    private List<TaskMessage> messageList;
    private int position;//位置
    @Override
    public void setLayout() {
        setContentView(R.layout.dotaskplace);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("做任务");
        setLeftTv("返回");
        list=new ArrayList<TaskEntity>();
        strategyList=new ArrayList<TaskStrategyEntity>();
        messageList=new ArrayList<TaskMessage>();
        list=getIntent().getParcelableArrayListExtra("list");
        strategyList=getIntent().getParcelableArrayListExtra("listStrategey");
        position=getIntent().getIntExtra("position",-1);
        tv_place.setText(list.get(position).getTaskDesc());//设置任务内容
    }

    @OnClick(R.id.btn_submit)
    public void toClick(View v){
        switch (v.getId()){
            case R.id.btn_submit:

                    if(et_place.getText().toString().length()==0){
                        Toast.makeText(getApplicationContext(),"请输入内容",Toast.LENGTH_SHORT).show();
                    }else{
                        TaskMessage taskMessage=new TaskMessage();
                        if(!TextUtils.isEmpty(list.get(position).getId())){
                            taskMessage.setTaskId(list.get(position).getId());
                        }
                        if(!TextUtils.isEmpty(list.get(position).getTaskId())){
                            taskMessage.setTaskId(list.get(position).getTaskId());
                        }
                        taskMessage.setStrategyId(strategyList.get(0).getStragegyId());
                        taskMessage.setFieldName(strategyList.get(0).getFieldName());
                        taskMessage.setFieldKey(strategyList.get(0).getFieldKey());
                        taskMessage.setFieldValue(et_place.getText().toString());
                        taskMessage.setScoreType(strategyList.get(0).getScoreType());
                        messageList.add(taskMessage);
                        String json= GsonUtils.bean2Json(messageList);
                        RequestManager.getUserManager().addTaskAnswer(json, new RequestManager.CallBack() {
                            @Override
                            public void onSucess(String result) throws JSONException {
                                sendBroadcast(new Intent(TaskLevelAct.UPDATE_LIST));
                                sendBroadcast(new Intent(HardCreditAct.UPDATE_MYLIST));
                                sendBroadcast(new Intent(CreditPointAct.UPDATE_MYSCORE));
                                sendBroadcast(new Intent(MineFragment.UPDATE_SCORE));
                                finish();
                            }

                            @Override
                            public void onError(int status,String msg) {
                                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                            }
                        });
                    }


                break;
        }
    }
}
