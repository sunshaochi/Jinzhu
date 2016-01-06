package com.beyonditsm.financial.activity.manager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.activity.photo.ImagePagerActivity;
import com.beyonditsm.financial.adapter.PhotoAdapter;
import com.beyonditsm.financial.entity.PictureInfo;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.GsonUtils;
import com.beyonditsm.financial.util.MyLogUtils;
import com.beyonditsm.financial.view.LoadingView;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangbin on 15/12/17.
 */
public class GetPictureAct extends BaseActivity {
    @ViewInject(R.id.gv_pic)
    private GridView gvPic;
    @ViewInject(R.id.loadView)
    private LoadingView loadView;
    @ViewInject(R.id.tv_sm)
    private TextView tv_sm;  //展示title
    @ViewInject(R.id.no_content)
    private TextView no_content;//没有内容
    @ViewInject(R.id.tv_remark)
    private TextView tv_remark;//补件说明

    String account_id;
    String order_id;
    String remark;
    private List<String> images = new ArrayList<String>();

    @Override
    public void setLayout() {
        setContentView(R.layout.act_get_picture);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setLeftTv("返回");
        setTopTitle("查看附件");
        account_id = getIntent().getStringExtra("account_id");
        order_id = getIntent().getStringExtra("order_id");
        remark = getIntent().getStringExtra("remark");
        MyLogUtils.info("id:" + account_id + ";;;" + order_id);
        getPicture(account_id, order_id);

        gvPic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int posi, long arg3) {
                imageBrower(posi, (ArrayList<String>) images);
            }
        });
    }

    /**
     * 打开图片查看器
     *
     * @param position
     * @param urls2
     */
    protected void imageBrower(int position, ArrayList<String> urls2) {
        Intent intent = new Intent(this, ImagePagerActivity.class);
        // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls2);
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
        startActivity(intent);
    }

    public void getPicture(String accountId, String orderNo) {
        RequestManager.getMangManger().getFujianPic(accountId, orderNo, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                loadView.loadComplete();
                PictureInfo info = GsonUtils.json2Bean(result, PictureInfo.class);
                List<PictureInfo.DataEntity.RowsEntity> datas = info.getData().getRows();
                if (datas != null && datas.size() > 0) {
                    tv_sm.setVisibility(View.VISIBLE);
                    tv_remark.setVisibility(View.VISIBLE);
                    if (!TextUtils.isEmpty(remark)) {
                        tv_remark.setText("附件说明：" + remark);
                    }
                    for (int i = 0; i < datas.size(); i++) {
                        images.add(datas.get(i).getAccessoryPath());
                    }

                    PhotoAdapter adapter = new PhotoAdapter(GetPictureAct.this, datas);
                    gvPic.setAdapter(adapter);
                } else {
                    gvPic.setVisibility(View.GONE);
                    no_content.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(int status, String msg) {

            }
        });
    }
}
