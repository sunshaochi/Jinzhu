package com.beyonditsm.financial.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.adapter.OrgTypeAdapter;
import com.beyonditsm.financial.entity.OrgEvent;
import com.beyonditsm.financial.fragment.CreditFragment;
import com.beyonditsm.financial.util.ParamsUtil;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.tandong.sa.eventbus.EventBus;

public class OrgTypeListAct extends BaseActivity {
    private int type;

    @ViewInject(R.id.lv_org_type)
    ListView lvOrgType;
    @Override
    public void setLayout() {
        setContentView(R.layout.org_type_list);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setLeftTv("返回");
        setTopTitle("贷款机构");
        type=getIntent().getExtras().getInt("type");
        if(type==1){//大额贷
        lvOrgType.setAdapter(new OrgTypeAdapter(ParamsUtil.getInstance().getOrgTypeInfos(),OrgTypeListAct.this));}
        else if(type==2){//推荐
            lvOrgType.setAdapter(new OrgTypeAdapter(ParamsUtil.getInstance().getTjorgTypeInfos(),OrgTypeListAct.this));
        }
        lvOrgType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("org",position);
                setResult(0, intent);
                EventBus.getDefault().post(new OrgEvent(position));//这个方法是传到大额贷里面的因为
                finish();
            }
        });
    }
}
