package com.beyonditsm.financial.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.activity.photo.PicSelectActivity;
import com.beyonditsm.financial.adapter.GvPhotoAdapter;
import com.beyonditsm.financial.entity.ImageBean;
import com.beyonditsm.financial.fragment.MyCreditDetailFragment;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.MyToastUtils;
import com.beyonditsm.financial.widget.FinalLoadDialog;
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
 * Created by gxy on 2015/12/16.
 */
public class TiJiaoFuJianAct extends BaseActivity {
    @ViewInject(R.id.tv_pic)
    private TextView tv_pic;
    @ViewInject(R.id.gv_pic)
    private GridView gvPhoto;
    @ViewInject(R.id.btn_submit)
    private Button btn_submit;
    /**照片List */
    private List<ImageBean> selecteds=new ArrayList<ImageBean>();
    private GvPhotoAdapter adapter;
    private String uploadStr=null;

    private String orderNo;

    private String orderId;

    private FinalLoadDialog dialog;

    @Override
    public void setLayout() {
        setContentView(R.layout.tijiaofujian);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setLeftTv("返回");
        setTopTitle("提交附件");
        dialog=new FinalLoadDialog(this);
        dialog.setTitle("上传附件中");
        orderNo=getIntent().getStringExtra("orderNo");
        orderId=getIntent().getStringExtra("orderId");
        adapter=new GvPhotoAdapter(selecteds,9,this);
        gvPhoto.setAdapter(adapter);

    }


    @OnClick({R.id.btn_submit})
    public void toClick(View v){
        switch (v.getId()){
            case R.id.btn_submit:
                if (selecteds.size()==0){
                    Toast.makeText(getApplicationContext(),"请选择附件图片！",Toast.LENGTH_SHORT).show();
                }else {
//                    for (int i=0;i<selecteds.size();i++){
                        uploadFile(selecteds);
//                    }
                }


                break;
        }
    }

    /**
     * 上传图片
     * @param
     */
    private void uploadFile(List<ImageBean> selecteds){
        dialog.show();
        Map<String, List<FileBody>> fileMaps=new HashMap<String,List<FileBody>>();
        List<FileBody> lists=new ArrayList<FileBody>();
        for(int i=0;i<selecteds.size();i++) {
            FileBody fb = new FileBody(new File(selecteds.get(i).getPath()));
            lists.add(fb);
        }
        fileMaps.put("myfiles",lists);
        RequestManager.getCommManager().submitFujian(orderNo,fileMaps, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) {
//                try {
//                    JSONObject jsonObject=new JSONObject(result);
//                    String orderN=jsonObject.optString("orderNo");
//                    updateOrder(orderN);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }

                updateOrder(orderId);
            }
            @Override
            public void onError(int status, String msg) {

                MyToastUtils.showShortToast(getApplicationContext(),msg);
                dialog.cancel();
            }
        });
    }

    private void updateOrder(String orderId){
        RequestManager.getUserManager().updateOrder(orderId, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                MyToastUtils.showShortToast(getApplicationContext(), "提交附件成功，等待信贷经理审核");
                sendBroadcast(new Intent(MyCreditDetailFragment.UPDATE_ORDER));
                sendBroadcast(new Intent(MyCreditAct.CREDIT_RECEIVER));
                dialog.cancel();
                finish();
            }

            @Override
            public void onError(int status, String msg) {
                MyToastUtils.showShortToast(getApplicationContext(),msg);
                dialog.cancel();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**第一次添加照片 */
        if(requestCode==0x123&&resultCode==RESULT_OK){
            if(data!=null){
                gvPhoto.setVisibility(View.VISIBLE);
                selecteds=(List<ImageBean>) data.getSerializableExtra(PicSelectActivity.IMAGES);
                if(adapter==null){
                    adapter=new GvPhotoAdapter(selecteds,1, TiJiaoFuJianAct.this);
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
