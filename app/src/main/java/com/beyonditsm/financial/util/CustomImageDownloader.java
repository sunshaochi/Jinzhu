package com.beyonditsm.financial.util;

import android.content.Context;

import com.beyonditsm.financial.MyApplication;
import com.tandong.sa.zUImageLoader.core.download.BaseImageDownloader;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Created by xuleyuan on 20/12/16.
 */

public class CustomImageDownloader extends BaseImageDownloader {
    // 需在application中进行配置添加此类。
    public CustomImageDownloader(Context context) {
        super(context);
    }

    @Override
    protected HttpURLConnection createConnection(String url, Object extra) throws IOException {
        // Super...
        HttpURLConnection connection = super.createConnection(url, extra);
//        connection.setRequestProperty("Cookie", MyApplication.getInstance().getCookieString());
        connection.setRequestProperty("Cookie", SpUtils.getCookie(MyApplication.getInstance().getApplicationContext()));
        return connection;
    }
}

