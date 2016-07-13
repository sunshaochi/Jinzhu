package com.beyonditsm.financial.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.activity.photo.PicSelectActivity;
import com.beyonditsm.financial.adapter.GvPhotoAdapter;
import com.beyonditsm.financial.entity.ImageBean;
import com.beyonditsm.financial.entity.TaskEntity;
import com.beyonditsm.financial.entity.TaskMessage;
import com.beyonditsm.financial.entity.TaskStrategyEntity;
import com.beyonditsm.financial.fragment.MineFragment;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.GsonUtils;
import com.beyonditsm.financial.util.MyLogUtils;
import com.lidroid.xutils.http.client.multipart.content.FileBody;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gxy on 2015/11/18
 */
public class DoTaskPicture extends BaseActivity {


    @ViewInject(R.id.tv_pic)
    private TextView tv_pic;
    @ViewInject(R.id.gv_pic)
    private GridView gvPhoto;
    private List<TaskEntity> list;
    private List<TaskStrategyEntity> strategyList;
    private List<TaskMessage> messageList;
    private int position;//位置

    /**照片List */
    private List<ImageBean> selecteds= new ArrayList<>();
    private GvPhotoAdapter adapter;
    private String uploadStr=null;
    @Override
    public void setLayout() {
        setContentView(R.layout.dotaskpicture);
    }

    @Override
    public void init(Bundle savedInstanceState) {

        setTopTitle("做任务");
        setLeftTv("返回");
        list= new ArrayList<>();
        strategyList= new ArrayList<>();
        messageList= new ArrayList<>();
        list=getIntent().getParcelableArrayListExtra("list");
        strategyList=getIntent().getParcelableArrayListExtra("listStrategey");
        position=getIntent().getIntExtra("position",-1);
        tv_pic.setText(list.get(position).getTaskDesc());//设置任务内容
        adapter=new GvPhotoAdapter(selecteds,1,this);
        gvPhoto.setAdapter(adapter);

    }

    @OnClick({R.id.btn_submit})
    public void toClick(View v){
        switch (v.getId()){
            case R.id.btn_submit:
                if (selecteds.size()==0){
                    Toast.makeText(getApplicationContext(),"提交内容不能为空！",Toast.LENGTH_SHORT).show();
                }else {
                    for (int i=0;i<selecteds.size();i++){
                        uploadFile(selecteds.get(i).path);
                    }
                }


                break;
        }
    }

    /**
     * 上传图片
     * @param file
     */
    @SuppressWarnings("JavaDoc")
    private void uploadFile(final String file){
        Map<String, FileBody> fileMaps= new HashMap<>();
        FileBody fb=new FileBody(new File(file));
        fileMaps.put("file",fb);

        RequestManager.getCommManager().toUpLoadFile(fileMaps, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) {
                uploadStr=result;
                if(uploadStr!=null){
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
                    taskMessage.setFieldValue(uploadStr);
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
            }

            @Override
            public void onError(int status,String msg) {
                MyLogUtils.info(msg);
            }
        });
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**第一次添加照片 */
        if(requestCode==0x123&&resultCode==RESULT_OK){
            if(data!=null){
                gvPhoto.setVisibility(View.VISIBLE);
                selecteds=(List<ImageBean>) data.getSerializableExtra(PicSelectActivity.IMAGES);
                if(adapter==null){
                    adapter=new GvPhotoAdapter(selecteds,1, DoTaskPicture.this);
                    gvPhoto.setAdapter(adapter);
                }else{
                    adapter.notifyDataSetChanged();
                }

//                tvSelect.setVisibility(View.GONE);
            }
        }

        /**继续添加照片 */
        if(requestCode==GvPhotoAdapter.ADD_PHOTO_CODE&&resultCode==RESULT_OK){
            if(data!=null){
                List<ImageBean> addSelect=(List<ImageBean>) data.getSerializableExtra(PicSelectActivity.IMAGES);
                selecteds.addAll(addSelect);
                adapter.notifyDataSetChanged();
            }
        }
        /**预览删除照片*/
        if(requestCode==GvPhotoAdapter.PHOTO_CODE0&&resultCode==RESULT_OK){
            if(data!=null){
                if(selecteds.size()>0){
                    selecteds=(List<ImageBean>)data.getSerializableExtra("M_LIST");
                    adapter.notifyDataChange(selecteds);
                }
            }
        }

    }
}
