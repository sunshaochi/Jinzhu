package com.beyonditsm.financial.activity.credit;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.util.MyBitmapUtils;
import com.beyonditsm.financial.util.SpUtils;
import com.lidroid.xutils.view.annotation.ViewInject;


/**
 * 贷款指南
 * Created by wangbin on 16/2/26.
 */
public class CreditGuideAct extends BaseActivity {
    @ViewInject(R.id.iv_bg)
    private ImageView ivBg;
    private int screenWidth;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_credit_guide);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        getWidth();
        String roleName = SpUtils.getRoleName(getApplicationContext());
        if (!"ROLE_COMMON_CLIENT".equals(roleName)&&!TextUtils.isEmpty(roleName)){//非普通用户显示服务者指南
            setTopTitle("服务者指南");
            Bitmap bitmap = MyBitmapUtils.decodeSampledBitmapFromResource(getResources(), R.mipmap.servantguide, screenWidth/2, (int)(screenWidth/2/1000 * 2974));
            ivBg.setImageBitmap(bitmap);
        }else {
            setTopTitle("贷款指南");
            Bitmap bitmap = MyBitmapUtils.decodeSampledBitmapFromResource(getResources(), R.mipmap.credit_guide, screenWidth/2, (int)(screenWidth/2/750 * 3871));
            ivBg.setImageBitmap(bitmap);

        }
//        try
//        {
//
//            InputStream inputStream = getResources().getAssets().open("credit_guide.png");
//
//            //获得图片的宽、高
//            BitmapFactory.Options tmpOptions = new BitmapFactory.Options();
//            tmpOptions.inJustDecodeBounds = true;
//            BitmapFactory.decodeStream(inputStream, null, tmpOptions);
//            int width = tmpOptions.outWidth;
//            int height = tmpOptions.outHeight;
//
//            //设置显示图片的中心区域
//            BitmapRegionDecoder bitmapRegionDecoder = BitmapRegionDecoder.newInstance(inputStream, false);
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inPreferredConfig = Bitmap.Config.RGB_565;
//            Bitmap bitmap = bitmapRegionDecoder.decodeRegion(new Rect(width / 2 - 100, height / 2 - 100, width / 2 + 100, height / 2 + 100), options);
//            ivBg.setImageBitmap(bitmap);
//
//        } catch (IOException e)
//        {
//            e.printStackTrace();
//        }

    }

    private void getWidth() {
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        screenWidth = metric.widthPixels;     // 屏幕宽度（像素）
//        int height = metric.heightPixels;   // 屏幕高度（像素）
//        float density = metric.density;      // 屏幕密度（0.75 / 1.0 / 1.5）
//        int densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）
    }


}
