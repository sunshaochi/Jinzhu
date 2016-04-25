package com.beyonditsm.financial.activity.user;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.adapter.PrimaryTaskAdapter;
import com.beyonditsm.financial.entity.TaskEntity;
import com.beyonditsm.financial.entity.TaskStrategyEntity;
import com.beyonditsm.financial.http.RequestManager;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.tandong.sa.json.Gson;
import com.tandong.sa.json.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gxy on 2015/12/7.
 */
public class TaskLevelAct extends BaseActivity {
    @ViewInject(R.id.lv_task)
    private ListView lv_task;
    private List<TaskEntity> list,primaryList,middleList,highList,finishList;//
    private List<TaskStrategyEntity> myList;
    private PrimaryTaskAdapter adapter;

    private int task_type;

    @Override
    public void setLayout() {
        setContentView(R.layout.hard_credit);
    }

    @Override
    public void init(Bundle savedInstanceState) {
    //    EventBus.getDefault().register(this);

        task_type=getIntent().getIntExtra("tasklevel",0);
        switch (task_type){
            case 0:
                setTopTitle("初级任务");
                break;
            case 1:
                setTopTitle("中级任务");
                break;
            case 2:
                setTopTitle("高级任务");
                break;
        }


        setLeftTv("返回");
        list = getIntent().getParcelableArrayListExtra("list");//初、中、高级任务列表
    //    getAllTask();
        getPrimaryTask(list);
        getMiddleTask(list);
        getHighTask(list);
        myList = new ArrayList<TaskStrategyEntity>();
        finishList = new ArrayList<TaskEntity>();//已完成任务列表

        lv_task.setAdapter(new PrimaryTaskAdapter(list,getApplicationContext()));
        lv_task.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                switch (task_type) {
                    case 0:
                        select(primaryList,position);
                        break;
                    case 1:
                        select(middleList,position);
                        break;
                    case 2:
                        select(highList,position);
                        break;
                }

            }
        });
    }

    /**
     * 给对应位置的任务类型设置对应的任务列表
     * @param list
     * @param position
     */
    private void select(final List<TaskEntity> list,final int position){
        if (list.get(position).getTaskStatus() == -1) {
            RequestManager.getUserManager().findTaskStrategy(list.get(position), new RequestManager.CallBack() {
                @Override
                public void onSucess(String result) throws JSONException {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray dataArr = jsonObject.getJSONArray("data");
                    Gson gson = new Gson();
                    myList = gson.fromJson(dataArr.toString(), new TypeToken<List<TaskStrategyEntity>>() {
                    }.getType());
                    selectToAct(list, myList, position);
                }

                @Override
                public void onError(int status,String msg) {

                }
            });
        } else if ((list.get(position).getTaskStatus() == 0) || (list.get(position).getTaskStatus() == 1)) {
            RequestManager.getUserManager().findTaskDetail(list.get(position), new RequestManager.CallBack() {
                @Override
                public void onSucess(String result) throws JSONException {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray dataArr = jsonObject.getJSONArray("data");
                    Gson gson = new Gson();
                    finishList = gson.fromJson(dataArr.toString(), new TypeToken<List<TaskEntity>>() {
                    }.getType());
                    selectToFinishAct(finishList, position);
                }

                @Override
                public void onError(int status,String msg) {

                }
            });
        }

    }

   /* @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }*/

    /**
     * 跳转到对应的已做过任务的activity
     * @param list 初中高对应的任务列表
     * @param position 点击的位置
     */
    private void selectToFinishAct(List<TaskEntity> list,int position){
        Intent intent = null;
        intent = new Intent();
        intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) list);
        intent.putExtra("position",position);
        if(list.get(0).getValueType()==0){
            intent.setClass(TaskLevelAct.this,FinishTaskPlaceAct.class);
        }
        else if(list.get(0).getValueType()==1){
            intent.setClass(TaskLevelAct.this,FinishTaskPicture.class);
        }
        startActivity(intent);
    }

    /**
     * 跳转到对应的做任务的界面
     * @param listTask 任务列表
     * @param list 任务策略列表
     * @param position 点击的位置
     */
    private void selectToAct(List<TaskEntity> listTask,List<TaskStrategyEntity> list,int position){
        Intent intent = null;
        intent = new Intent();
        if(list.size()!=0){
            //根据任务策略的第一条数据中任务组件跳转
            if (list.get(0).getModelType()==5) {//上传
                //判断是否已经完成任务，如果完成任务则显示任务详情（FinishTaskPicture只能看不能修改）否则进去能够执行的界面
                intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) listTask);
                intent.putExtra("position", position);
                intent.putParcelableArrayListExtra("listStrategey", (ArrayList<? extends Parcelable>) list);
                intent.setClass(TaskLevelAct.this, DoTaskPicture.class);


            } else if (list.get(0).getModelType()==1) {//输入
                //判断是否已经完成任务，如果完成任务则显示任务详情（FinishTaskPlaceAct只能看不能修改）否则进去能够执行的界面
                intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) listTask);
                intent.putExtra("position", position);
                intent.putParcelableArrayListExtra("listStrategey", (ArrayList<? extends Parcelable>) list);
                intent.setClass(TaskLevelAct.this, DoTaskPlaceAct.class);

            } else if (list.get(0).getModelType()==3) {//单选
                //判断是否已经完成任务，如果完成任务则显示任务详情（FinishTaskDetial只能看不能修改）否则进去能够执行的界面
                intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) listTask);
                intent.putParcelableArrayListExtra("listStrategey", (ArrayList<? extends Parcelable>) list);
                intent.putExtra("position", position);
                intent.setClass(TaskLevelAct.this, TaskDetail.class);

            } else if (list.get(0).getModelType()==2) {//下拉
                //判断是否已经完成任务，如果完成任务则显示任务详情（FinishTaskDetial只能看不能修改）否则进去能够执行的界面
                intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) listTask);
                intent.putParcelableArrayListExtra("listStrategey", (ArrayList<? extends Parcelable>) list);
                intent.putExtra("position", position);
                intent.setClass(TaskLevelAct.this, TaskDetail.class);

            }else if (list.get(0).getModelType()==4) {//多选
                //判断是否已经完成任务，如果完成任务则显示任务详情（FinishTaskDetial只能看不能修改）否则进去能够执行的界面
                intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) listTask);
                intent.putParcelableArrayListExtra("listStrategey", (ArrayList<? extends Parcelable>) list);
                intent.putExtra("position", position);
                intent.setClass(TaskLevelAct.this, TaskDetail.class);

            }
            startActivity(intent);
        }
    }

    /**
     * 获取所有任务列表
     */
    private void getAllTask(){
        RequestManager.getUserManager().findAllTask(new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                list = new ArrayList<TaskEntity>();

                JSONObject jsonObject = new JSONObject(result);
                JSONArray dataArr = jsonObject.getJSONArray("data");
                for (int i = 0; i < dataArr.length(); i++) {
                    TaskEntity taskEntity = new TaskEntity();
                    JSONObject resultJsonObject = dataArr.getJSONObject(i);
                    String taskType = resultJsonObject.getString("taskType");
                    String taskDesc = resultJsonObject.getString("taskDesc");
                    String taskName = resultJsonObject.getString("taskName");
                    int taskStatus = resultJsonObject.getInt("taskStatus");
                    String id = resultJsonObject.getString("id");
                    taskEntity.setId(id);
                    taskEntity.setTaskDesc(taskDesc);
                    taskEntity.setTaskName(taskName);
                    taskEntity.setTaskType(taskType);
                    taskEntity.setTaskStatus(taskStatus);
                    list.add(taskEntity);
                }

                switch (task_type){
                    case 0:
                        if(getPrimaryTask(list).size()!=0&&adapter==null) {
                            adapter=new PrimaryTaskAdapter(getPrimaryTask(list),getApplicationContext());
                            lv_task.setAdapter(adapter);
                        }else {
                            if(getPrimaryTask(list).size()!=0){
                                adapter.notifyChange(getPrimaryTask(list));
                            }
                        }
                        break;
                    case 1:
                        if(getMiddleTask(list).size()!=0&&adapter==null) {
                            adapter=new PrimaryTaskAdapter(getMiddleTask(list),getApplicationContext());
                            lv_task.setAdapter(adapter);
                        }else {
                            if(getMiddleTask(list).size()!=0){
                                adapter.notifyChange(getMiddleTask(list));
                            }
                        }
                        break;
                    case 2:
                        if(getHighTask(list).size()!=0&&adapter==null) {
                            adapter=new PrimaryTaskAdapter(getHighTask(list),getApplicationContext());
                            lv_task.setAdapter(adapter);
                        }else {
                            if(getHighTask(list).size()!=0){
                                adapter.notifyChange(getHighTask(list));
                            }
                        }
                        break;
                }

            }

            @Override
            public void onError(int status,String msg) {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 获取初级任务列表
     * @param tasklist
     * @return
     */
    private List<TaskEntity> getPrimaryTask(List<TaskEntity> tasklist){
        primaryList=new ArrayList<TaskEntity>();
        for (int i=0;i<tasklist.size();i++){
            if(tasklist.get(i).getTaskType().equals("初级任务")){
                primaryList.add(tasklist.get(i));
            }
        }
        return primaryList;

    }

    /**
     * 获取中级任务列表
     * @param tasklist
     * @return
     */
    private List<TaskEntity> getMiddleTask(List<TaskEntity> tasklist){
        middleList=new ArrayList<TaskEntity>();
        for (int i=0;i<tasklist.size();i++){
            if(tasklist.get(i).getTaskType().equals("中级任务")){
                middleList.add(tasklist.get(i));
            }
        }
        return middleList;

    }

    /**
     * 获取高级任务列表
     * @param tasklist
     * @return
     */
    private List<TaskEntity> getHighTask(List<TaskEntity> tasklist){
        highList=new ArrayList<TaskEntity>();
        for (int i=0;i<tasklist.size();i++){
            if(tasklist.get(i).getTaskType().equals("高级任务")){
                highList.add(tasklist.get(i));
            }
        }
        return highList;

    }


    @Override
    public void onStart() {
        super.onStart();
        if(receiver==null){
            receiver=new MyBroadCastReceiver();
        }
        registerReceiver(receiver,new IntentFilter(UPDATE_LIST));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(receiver!=null){
            unregisterReceiver(receiver);
        }
    }
    private MyBroadCastReceiver receiver;
    public static final String UPDATE_LIST="com.update.list";

    public class MyBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            getAllTask();
        }
    }
}
