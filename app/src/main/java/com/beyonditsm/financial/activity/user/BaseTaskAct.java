package com.beyonditsm.financial.activity.user;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.adapter.BaseTaskAdapter;
import com.beyonditsm.financial.adapter.PrimaryTaskAdapter;
import com.beyonditsm.financial.entity.ResultData;
import com.beyonditsm.financial.entity.UserEntity;
import com.beyonditsm.financial.entity.UserEvent;
import com.beyonditsm.financial.fragment.MineFragment;
import com.beyonditsm.financial.http.IFinancialUrl;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.GsonUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.tandong.sa.eventbus.EventBus;
import com.tandong.sa.zUImageLoader.core.ImageLoader;

import org.json.JSONException;

/**
 * 基本任务
 * Created by gxy on 2015/11/26.
 */
public class BaseTaskAct extends BaseActivity {
    @ViewInject(R.id.lv_task)
    private ListView lv_task;
    private UserEntity user;
    @Override
    public void setLayout() {
        setContentView(R.layout.hard_credit);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("基本任务");
        setLeftTv("返回");
        getUsetInfo();
      /*  if((!TextUtils.isEmpty(user.getUserName()))&&(user.getUserSex()!=null)&&
                (user.getUserAge()!=null)&&(!TextUtils.isEmpty(user.getIdentCard()))&&
                (!TextUtils.isEmpty(user.getDetailAddr()))&&(user.getMarrySts()!=null)&&
                (!TextUtils.isEmpty(user.getNativePlace()))&&(!TextUtils.isEmpty(user.getNativePlaceAddr()))){
            LinearLayout childAt = (LinearLayout) lv_task.getChildAt(0);
            ImageView iv= (ImageView) childAt.findViewById(R.id.iv_finish);
            iv.setVisibility(View.VISIBLE);
        }*/
        lv_task.setAdapter(new BaseTaskAdapter(getApplicationContext()));
        lv_task.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                intent = new Intent();
                switch (position) {
                    case 0:
                        //判断该项任务是否已完成，如果完成点击则进入FinishInfoAct的界面，否则进入下面activity
                        intent.setClass(BaseTaskAct.this, SuppleInfoAct.class);
//                        intent.putExtra(EditAct.USER_TYPE,0);
                        intent.putExtra(MineFragment.USER_KEY, user);
                        startActivity(intent);
                        break;

                }
            }
        });
        //注册EventBus
//        EventBus.getDefault().register(this);
    }


  /*  @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
*/
    public void onEvent(UserEvent event) {
      /*  userInfo = event.ue;
        switch (event.position) {
            case 0://真实姓名
                tvName.setText(event.ue.getUserName());
                break;
            case 1://身份证号
                tvCard.setText(userInfo.getIdentCard());
                if (userInfo.getUserSex() == 0) {
                    tvSex.setText("女");
                } else {
                    tvSex.setText("男");
                }
                if (userInfo.getUserAge() != null)
                    tvAge.setText(userInfo.getUserAge() + "");
                tvnative.setText(userInfo.getNativePlace());
                break;
            case 8://修改年龄
                tvAge.setText(userInfo.getUserAge() + "");
                break;
            case 10://修改性别
                if (userInfo.getUserSex() == 0) {
                    tvSex.setText("女");
                } else {
                    tvSex.setText("男");
                }
                break;
        }*/
    }
    private void getUsetInfo(){
        RequestManager.getCommManager().findUserInfo(new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                ResultData<UserEntity> rd= (ResultData<UserEntity>) GsonUtils.json(result, UserEntity.class);
                user=rd.getData();

            }

            @Override
            public void onError(int status,String msg) {
                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if(receiver==null){
            receiver=new MyBroadCastReceiver();
        }
        registerReceiver(receiver, new IntentFilter(UPDATE_USER));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(receiver!=null){
            unregisterReceiver(receiver);
        }
    }
    private MyBroadCastReceiver receiver;
    public static final String UPDATE_USER="com.update.user";

    public class MyBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            user=intent.getParcelableExtra(MineFragment.USER_KEY);

        }
    }
}
