package com.beyonditsm.financial.activity.user;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.adapter.HardCreditAdapter;
import com.beyonditsm.financial.entity.TaskEntity;
import com.beyonditsm.financial.http.RequestManager;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gxy on 2015/11/26.
 */
public class HardCreditAct extends BaseActivity {
    @ViewInject(R.id.lv_task)
    private ListView lv_task;
    private List<TaskEntity> myList;//用户任务列表

    private List<TaskEntity> primaryList;//初级任务
    private List<TaskEntity> middleList;//中级任务
    private List<TaskEntity> highList;//高级任务

    private HardCreditAdapter hcaAdapter;

    private List<String> list;//任务类型
    @ViewInject(R.id.ll)
    private LinearLayout ll;
    @ViewInject(R.id.tv_basetask)
    private TextView tv_basetask;
    @ViewInject(R.id.iv_finish)
    private ImageView iv_finish;
    @ViewInject(R.id.iv_go)
    private ImageView iv_go;
    @ViewInject(R.id.view)
    private View view;

    private int flag;


    @Override
    public void setLayout() {
        setContentView(R.layout.hard_credit);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setLeftTv("返回");
        setTopTitle("耕耘信用");
        Intent intent = getIntent();
        if (intent != null) {
            flag = intent.getIntExtra("flag", 0);
        }
        if (flag == 10) {
            ll.setVisibility(View.GONE);
        } else {
            ll.setVisibility(View.VISIBLE);
        }
        tv_basetask.setVisibility(View.VISIBLE);
        iv_go.setVisibility(View.VISIBLE);
        view.setVisibility(View.VISIBLE);
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(HardCreditAct.this, BaseTaskAct.class);
                startActivity(intent);
            }
        });
        getAllTask();
        lv_task.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                if (list.size() == 1) {
                    switch (position) {
                        case 0:
                            intent = new Intent();
                            if (list.get(0).equals("初级任务")) {
                                intent.putExtra("tasklevel", 0);
                                intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) primaryList);
                                intent.setClass(HardCreditAct.this, TaskLevelAct.class);
                            } else if (list.get(0).equals("中级任务")) {
                                intent.putExtra("tasklevel", 1);
                                intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) middleList);
                                intent.setClass(HardCreditAct.this, TaskLevelAct.class);

                            } else if (list.get(0).equals("高级任务")) {
                                intent.putExtra("tasklevel", 2);
                                intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) highList);
                                intent.setClass(HardCreditAct.this, TaskLevelAct.class);

                            }
                            startActivity(intent);
                            break;
                    }
                } else if (list.size() == 2) {
                    switch (position) {
                        case 0:
                            intent = new Intent();
                            if (list.get(0).equals("初级任务")) {
                                intent.putExtra("tasklevel", 0);
                                intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) primaryList);
                                intent.setClass(HardCreditAct.this, TaskLevelAct.class);

                            } else if (list.get(0).equals("中级任务")) {
                                intent.putExtra("tasklevel", 1);
                                intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) middleList);
                                intent.setClass(HardCreditAct.this, TaskLevelAct.class);

                            }
                            startActivity(intent);
                            break;
                        case 1:
                            intent = new Intent();
                            if (list.get(1).equals("中级任务")) {
                                intent.putExtra("tasklevel", 1);
                                intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) middleList);
                                intent.setClass(HardCreditAct.this, TaskLevelAct.class);

                            } else if (list.get(1).equals("高级任务")) {
                                intent.putExtra("tasklevel", 2);
                                intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) highList);
                                intent.setClass(HardCreditAct.this, TaskLevelAct.class);

                            }
                            startActivity(intent);
                            break;
                    }
                } else if (list.size() == 3) {
                    switch (position) {
                        case 0:
                            intent = new Intent();
                            intent.putExtra("tasklevel", 0);
                            intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) primaryList);
                            intent.setClass(HardCreditAct.this, TaskLevelAct.class);

                            startActivity(intent);
                            break;
                        case 1:
                            intent = new Intent();
                            intent.putExtra("tasklevel", 1);
                            intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) middleList);
                            intent.setClass(HardCreditAct.this, TaskLevelAct.class);

                            startActivity(intent);
                            break;
                        case 2:
                            intent = new Intent();
                            intent.putExtra("tasklevel", 2);
                            intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) highList);
                            intent.setClass(HardCreditAct.this, TaskLevelAct.class);

                            startActivity(intent);
                            break;
                    }
                }

            }
        });


    }

    private void getAllTask() {
        RequestManager.getUserManager().findAllTask(new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                myList = new ArrayList<TaskEntity>();

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
                    myList.add(taskEntity);
                }

                list = new ArrayList<String>();
                if (getPrimaryTask(myList).size() != 0) {
                    list.add("初级任务");
                }
                if (getMiddleTask(myList).size() != 0) {
                    list.add("中级任务");
                }
                if (getHighTask(myList).size() != 0) {
                    list.add("高级任务");
                }
                hcaAdapter = new HardCreditAdapter(list, getApplicationContext());
                lv_task.setAdapter(hcaAdapter);
            }

            @Override
            public void onError(int status, String msg) {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<TaskEntity> getPrimaryTask(List<TaskEntity> tasklist) {
        primaryList = new ArrayList<TaskEntity>();
        for (int i = 0; i < tasklist.size(); i++) {
            if (tasklist.get(i).getTaskType().equals("初级任务")) {
                primaryList.add(tasklist.get(i));
            }
        }
        return primaryList;

    }

    private List<TaskEntity> getMiddleTask(List<TaskEntity> tasklist) {
        middleList = new ArrayList<TaskEntity>();
        for (int i = 0; i < tasklist.size(); i++) {
            if (tasklist.get(i).getTaskType().equals("中级任务")) {
                middleList.add(tasklist.get(i));
            }
        }
        return middleList;

    }

    private List<TaskEntity> getHighTask(List<TaskEntity> tasklist) {
        highList = new ArrayList<TaskEntity>();
        for (int i = 0; i < tasklist.size(); i++) {
            if (tasklist.get(i).getTaskType().equals("高级任务")) {
                highList.add(tasklist.get(i));
            }
        }
        return highList;

    }

    @Override
    public void onStart() {
        super.onStart();
        if (receiver == null) {
            receiver = new MyBroadCastReceiver();
        }
        registerReceiver(receiver, new IntentFilter(UPDATE_MYLIST));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
    }

    private MyBroadCastReceiver receiver;
    public static final String UPDATE_MYLIST = "com.update.mylist";

    public class MyBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            getAllTask();
        }
    }
}
