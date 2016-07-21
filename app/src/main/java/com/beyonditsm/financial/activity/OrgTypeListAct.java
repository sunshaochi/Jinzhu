package com.beyonditsm.financial.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.adapter.OrgTypeAdapter;
import com.beyonditsm.financial.fragment.CreditFragment;
import com.beyonditsm.financial.util.ParamsUtil;
import com.lidroid.xutils.view.annotation.ViewInject;

public class OrgTypeListAct extends BaseActivity {

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
        lvOrgType.setAdapter(new OrgTypeAdapter(ParamsUtil.getInstance().getOrgTypeInfos(),OrgTypeListAct.this));
        lvOrgType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("org",position);
                setResult(0, intent);
                finish();
            }
        });
    }
}
