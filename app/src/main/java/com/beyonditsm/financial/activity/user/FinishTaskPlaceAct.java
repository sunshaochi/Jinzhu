package com.beyonditsm.financial.activity.user;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.entity.TaskEntity;
import com.beyonditsm.financial.http.RequestManager;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gxy on 2015/11/18.
 */
public class FinishTaskPlaceAct extends BaseActivity {
    @ViewInject(R.id.tv_place)
    private TextView tv_place;
    @ViewInject(R.id.et_place)
    private TextView et_place;
    private List<TaskEntity> list;
    private int position;
    @Override
    public void setLayout() {
        setContentView(R.layout.finish_taskpla_detail);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("任务详情");
        setLeftTv("返回");
        list=new ArrayList<TaskEntity>();
        list=getIntent().getParcelableArrayListExtra("list");
        position=getIntent().getIntExtra("position",-1);
        for (int i=0;i<list.size();i++){
            tv_place.setText(list.get(i).getFieldName());
            et_place.setText(list.get(i).getFieldValue());
        }

    }

}
