package com.beyonditsm.financial.activity.user;

import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.activity.wallet.MyWalletActivity;
import com.beyonditsm.financial.fragment.MineFragment;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.util.MyToastUtils;
import com.beyonditsm.financial.util.ParamsUtil;
import com.beyonditsm.financial.view.LoadingView;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.ImageViewState;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/5/18.
 */
public class ServantSpecialAct extends BaseActivity {
    @ViewInject(R.id.tv_1reward)
    private TextView tv_1reward;
    @ViewInject(R.id.tv_2reward)
    private TextView tv_2reward;
    @ViewInject(R.id.tv_3reward)
    private TextView tv_3reward;
    @ViewInject(R.id.tv_1card)
    private TextView tv_1card;
    @ViewInject(R.id.tv_2card)
    private TextView tv_2card;
    @ViewInject(R.id.tv_3card)
    private TextView tv_3card;
    @ViewInject(R.id.tv_reward_guide1)
    private TextView tv_reward_guide1;
    @ViewInject(R.id.tv_reward_guide2)
    private TextView tv_reward_guide2;

    @Override
    public void setLayout() {
        setContentView(R.layout.act_servantspecial);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("代言人指南");
        setLeftTv("返回");
        tv_1reward.setText(ParamsUtil.getInstance().getFirstWard()+"");
        tv_2reward.setText(ParamsUtil.getInstance().getSecWard()+"");
        tv_3reward.setText(ParamsUtil.getInstance().getThirdWard()+"");
        tv_1card.setText("¥"+ParamsUtil.getInstance().getFirstCardWard()+"");
        tv_2card.setText("¥"+ParamsUtil.getInstance().getSecCardWard()+"");
        tv_3card.setText("¥"+ParamsUtil.getInstance().getThirdCardWard()+"");
        tv_reward_guide1.setText("－ 推荐一层用户成功申办信用卡可获得"+ParamsUtil.getInstance().getFirstCardWard()+"元现金券；\n\n" +
                "－ 推荐二层用户成功申办信用卡可获得"+ParamsUtil.getInstance().getSecCardWard()+"元现金券；\n\n" +
                "－ 推荐三层用户成功申办信用卡可获得"+ParamsUtil.getInstance().getThirdCardWard()+"元现金券。\n");
        tv_reward_guide2.setText("－ 推荐一层用户成功申请贷款可获得放贷额*"+(ParamsUtil.getInstance().getFirstWard())+"%的现金券；\n\n" +
                "－ 推荐二层用户成功申请贷款可获得放贷额*"+(ParamsUtil.getInstance().getSecWard())+"%的现金券；\n\n" +
                "－ 推荐三层用户成功申请贷款可获得放贷额*"+(ParamsUtil.getInstance().getThirdWard())+"%的现金券。");

//        getWidth();
//        Bitmap bitmap = MyBitmapUtils.decodeSampledBitmapFromResource(getResources(), R.mipmap.servantguide, screenWidth, (int)(screenWidth/750 * 3871));
//        ivBg.setOnImageEventListener(new SubsamplingScaleImageView.OnImageEventListener() {
//            @Override
//            public void onReady() {
//
//            }
//
//            @Override
//            public void onImageLoaded() {
//                loadingView.loadComplete();
//            }
//
//            @Override
//            public void onPreviewLoadError(Exception e) {
//
//            }
//
//            @Override
//            public void onImageLoadError(Exception e) {
//
//            }
//
//            @Override
//            public void onTileLoadError(Exception e) {
//
//            }
//        });
//        ivBg.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_CROP);
//        ivBg.setImage(ImageSource.uri("file:///android_asset/servantguide.png"), new ImageViewState(0, new PointF(0, 0), 0));
    }
    /**
     * 点击事件
     *
     * @param v
     */
    @OnClick({ R.id.im_url1, R.id.im_url2})
    public void toClick(View v) {
        switch (v.getId()) {

            case R.id.im_url1:
                Intent intent = new Intent(ServantSpecialAct.this,JinZhuIndexWeb.class);
                startActivity(intent);
                break;

            case R.id.im_url2:
                Intent intent2 = new Intent(ServantSpecialAct.this,JinZhuIndexWeb.class);
                startActivity(intent2);
                break;
        }
    }
}
