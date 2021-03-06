package com.beyonditsm.financial.activity.credit;

import android.graphics.PointF;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.BaseActivity;
import com.beyonditsm.financial.view.LoadingView;

import com.beyonditsm.financial.view.subscaleview.ImageSource;
import com.beyonditsm.financial.view.subscaleview.ImageViewState;
import com.beyonditsm.financial.view.subscaleview.SubsamplingScaleImageView;
import com.lidroid.xutils.view.annotation.ViewInject;


/**
 * 贷款指南
 * Created by wangbin on 16/2/26.
 */
public class CreditGuideAct extends BaseActivity {
    @ViewInject(R.id.iv_bg)
    private SubsamplingScaleImageView ivBg;
    @ViewInject(R.id.loadingView)
    private LoadingView loadingView;

    @Override
    public void setLayout() {
        setContentView(R.layout.act_credit_guide);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        getWidth();
        setTopTitle("贷款指南");
//        ivBg.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_CROP);
//        ivBg.setScaleAndCenter(1.0F, new PointF(375, 0));
//        ivBg.setMinScale(1.0F);
        ivBg.setOnImageEventListener(new SubsamplingScaleImageView.OnImageEventListener() {
            @Override
            public void onReady() {

            }

            @Override
            public void onImageLoaded() {
                loadingView.loadComplete();
            }

            @Override
            public void onPreviewLoadError(Exception e) {

            }

            @Override
            public void onImageLoadError(Exception e) {
                loadingView.loadComplete();
            }

            @Override
            public void onTileLoadError(Exception e) {

            }
        });
        ivBg.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_CROP);
        ivBg.setImage(ImageSource.uri("file:///android_asset/creditguide.png"), new ImageViewState(0, new PointF(0, 0), 0));
//        ivBg.setPanLimit()
//        ivBg.ImageViewState
//            Bitmap bitmap = MyBitmapUtils.decodeSampledBitmapFromResource(getResources(), R.mipmap.credit_guide, screenWidth/2, (int)(screenWidth/2/750 * 3871));

//        }
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
        //        int height = metric.heightPixels;   // 屏幕高度（像素）
//        float density = metric.density;      // 屏幕密度（0.75 / 1.0 / 1.5）
//        int densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）
    }


}
