package com.beyonditsm.financial.view.banner;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * viewpager的切换方式
 * Created by Administrator on 2016/9/9.
 */
public class Transformer implements ViewPager.PageTransformer {
    private boolean isPagingEnabled = false;
    private boolean hideOffscreenPages = true;

    private static final float ROT_MOD = -15f;
    private static final float MIN_SCALE = 0.75f;
    private TransformerType type;
    public enum TransformerType{
        DEFAULT,//默认
        ACCORDION,
        BACKGROUND_TO_FOREGROUND,
        CUBE_IN,
        CUBE_OUT,
        DEPTH_PAGE,
        FLIP_HORIZONTAL,
        FLIP_VERTICAL,
        ROTATE_DOWN,
        ROTATE_UP,
        STACK,
        ZOOM_IN,
        ZOOM_OUT
    }

    public Transformer(TransformerType type) {
        this.type = type;
    }

    @Override
    public void transformPage(View view, float position) {
        onPreTransform(view, position);
        onPostTransform(view, position);
        final float width = view.getWidth();
        final float height = view.getHeight();
        switch (type){
            case DEFAULT:
                isPagingEnabled = true;
                break;
            case ACCORDION:
                view.setPivotX(position < 0 ? 0 : view.getWidth());
                view.setScaleX(position < 0 ? 1f + position : 1f - position);
                break;
            case BACKGROUND_TO_FOREGROUND:
                final float scale = min(position < 0 ? 1f : Math.abs(1f - position), 0.5f);

                view.setScaleX(scale);
                view.setScaleY(scale);
                view.setPivotX(width * 0.5f);
                view.setPivotY(height * 0.5f);
                view.setTranslationX(position < 0 ? width * position : -width * position * 0.25f);
                break;
            case CUBE_IN:
                isPagingEnabled = true;
                view.setPivotX(position > 0 ? 0 : view.getWidth());
                view.setPivotY(0);
                view.setRotationY(-90f * position);
                break;
            case CUBE_OUT:
                isPagingEnabled = true;
                view.setPivotX(position < 0f ? view.getWidth() : 0f);
                view.setPivotY(view.getHeight() * 0.5f);
                view.setRotationY(90f * position);
                break;
            case DEPTH_PAGE:
                isPagingEnabled = true;
                if (position <= 0f) {
                    view.setTranslationX(0f);
                    view.setScaleX(1f);
                    view.setScaleY(1f);
                } else if (position <= 1f) {
                    final float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));
                    view.setAlpha(1 - position);
                    view.setPivotY(0.5f * view.getHeight());
                    view.setTranslationX(view.getWidth() * -position);
                    view.setScaleX(scaleFactor);
                    view.setScaleY(scaleFactor);
                }
                break;
            case FLIP_HORIZONTAL:
                final float rotation_h = 180f * position;

                view.setAlpha(rotation_h > 90f || rotation_h < -90f ? 0 : 1);
                view.setPivotX(view.getWidth() * 0.5f);
                view.setPivotY(view.getHeight() * 0.5f);
                view.setRotationY(rotation_h);
                break;
            case FLIP_VERTICAL:
                final float rotation_v = -180f * position;

                view.setAlpha(rotation_v > 90f || rotation_v < -90f ? 0f : 1f);
                view.setPivotX(view.getWidth() * 0.5f);
                view.setPivotY(view.getHeight() * 0.5f);
                view.setRotationX(rotation_v);
                break;
            case ROTATE_DOWN:
                isPagingEnabled = true;

                final float rotation_d = ROT_MOD * position * -1.25f;

                view.setPivotX(width * 0.5f);
                view.setPivotY(height);
                view.setRotation(rotation_d);
                break;
            case ROTATE_UP:
                isPagingEnabled = true;
                final float rotation_u = ROT_MOD * position;

                view.setPivotX(width * 0.5f);
                view.setPivotY(0f);
                view.setTranslationX(0f);
                view.setRotation(rotation_u);
                break;
            case STACK:
                view.setTranslationX(position < 0 ? 0f : -view.getWidth() * position);
                break;
            case ZOOM_IN:
                final float scale_zoom_in = position < 0 ? position + 1f : Math.abs(1f - position);
                view.setScaleX(scale_zoom_in);
                view.setScaleY(scale_zoom_in);
                view.setPivotX(view.getWidth() * 0.5f);
                view.setPivotY(view.getHeight() * 0.5f);
//                view.setAlpha(position < -1f || position > 1f ? 0f : 1f - (scale_zoom_in - 1f));
                break;
            case ZOOM_OUT:
                final float scale_zoom_out = 1f + Math.abs(position);
                view.setScaleX(scale_zoom_out);
                view.setScaleY(scale_zoom_out);
                view.setPivotX(view.getWidth() * 0.5f);
                view.setPivotY(view.getHeight() * 0.5f);
//                view.setAlpha(position < -1f || position > 1f ? 0f : 1f - (scale_zoom_out - 1f));
                if(position == -1){
                    view.setTranslationX(view.getWidth() * -1);
                }
                break;

        }
    }

    protected void onPreTransform(View page, float position) {
        final float width = page.getWidth();

        page.setRotationX(0);
        page.setRotationY(0);
        page.setRotation(0);
        page.setScaleX(1);
        page.setScaleY(1);
        page.setPivotX(0);
        page.setPivotY(0);
        page.setTranslationY(0);
        page.setTranslationX(isPagingEnabled ? 0f : -width * position);

        if (hideOffscreenPages) {
            page.setAlpha(position <= -1f || position >= 1f ? 0f : 1f);
            page.setEnabled(false);
        } else {
            page.setEnabled(true);
            page.setAlpha(1f);
        }
    }

    protected void onPostTransform(View page, float position) {
    }

    protected static final float min(float val, float min) {
        return val < min ? min : val;
    }
}
