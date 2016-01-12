package com.beyonditsm.financial.util;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.RemoteViews;

import com.beyonditsm.financial.MyApplication;
import com.beyonditsm.financial.R;
import com.beyonditsm.financial.entity.ResultData;
import com.beyonditsm.financial.entity.VersionInfo;
import com.beyonditsm.financial.http.IFinancialUrl;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.widget.MyAlertDialog;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import org.json.JSONException;

import java.io.File;

/**
 *
 * Created by wangbin on 15/11/23.
 */
public class GeneralUtils {



    public void getCode(String phoneNum) {
        RequestManager.getCommManager().getCode(phoneNum, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) {
                MyToastUtils.showShortToast(MyApplication.getInstance(),"验证码发送成功");
                iCode.isSucess(1);
            }

            @Override
            public void onError(int status,String msg) {
                MyToastUtils.showShortToast(MyApplication.getInstance(),msg);
                iCode.isSucess(-1);
            }
        });
    }

    private ICode iCode;

    public void setiCode(ICode iCode) {
        this.iCode = iCode;
    }

    public interface ICode {
        public void isSucess(int flag);
    }


    public void toVersion(final Context context, final int currentVersion,final int type){
        RequestManager.getCommManager().toVersion(currentVersion, "ANDROID", new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                ResultData<VersionInfo> rd= (ResultData<VersionInfo>) GsonUtils.json(result,VersionInfo.class);
                if (!rd.getData().isNeedUpdrage()) {
                    if(type==0)
                    MyToastUtils.showShortToast(context, "当前已是最新版本！");

                }else{
                    String path = rd.getData().getVersion().getPackagePath();
                    showIsDownLoad(context, IFinancialUrl.BASE_URL+path);
//                MyToastUtils.showShortToast(MyApplication.getInstance(),rd.getData().getMessage());
                }
            }

            @Override
            public void onError(int status, String msg) {

            }
        });
    }


    private NotificationManager mNotificationManager = null;
    private Notification mNotification;
    private String fileName = "/sdcard/jinzhu_.apk";
    private int result, fileSize, downLoadFileSize;

    /**
     * 是否下载
     */
    private void showIsDownLoad(final Context context,final String path) {
        MyAlertDialog dialog = new MyAlertDialog(context).builder();
        dialog.setTitle("提示").setMsg("检测到最新版本，是否现在下载？").setCancelable(false)
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        File file = new File(fileName);
                        if (file.exists()) {
                            file.delete();
                        }
                        downLoad(context,path);
                    }
                }).setNegativeButton("取消", null).show();
    }

    /**
     * 下载
     *
     * @param path
     */
    @SuppressWarnings({ "unused", "rawtypes" })
    private void downLoad(final Context context,String path) {
        HttpUtils http = new HttpUtils();
        HttpHandler handler = http.download(path, fileName, true, true,
                new RequestCallBack<File>() {
                    @Override
                    public void onLoading(long total, long current,
                                          boolean isUploading) {
                        fileSize = (int) total;
                        downLoadFileSize = (int) current;
                        result = (int) (current * 100 / total);
                        myhandler.sendEmptyMessage(1);
                    }

                    @Override
                    public void onStart() {
                        Message message=myhandler.obtainMessage();
                        message.obj=context;
                        message.what=0;
                        myhandler.sendMessage(message);
                    }

                    @SuppressLint("SdCardPath")
                    @Override
                    public void onSuccess(ResponseInfo<File> arg0) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.fromFile(new File(fileName)),
                                "application/vnd.android.package-archive");
                        context.startActivity(intent);
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        MyToastUtils.showShortToast(context, "下载失败");
                    }
                });
    }

    private void showNotification(Context context) {
        mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent();
        PendingIntent pIntent = PendingIntent
                .getActivity(context, 0, intent, 0);
        mNotification = new Notification();
        mNotification.icon = R.mipmap.logo;
        mNotification.tickerText = "开始下载";
        mNotification.contentView = new RemoteViews(context.getPackageName(),
                R.layout.content_view);// 通知栏中进度布局
        mNotification.contentIntent = pIntent;
    }

    private Handler myhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Context context=(Context) msg.obj;
                    showNotification(context);
                    break;
                case 1:
                    mNotification.contentView.setTextViewText(
                            R.id.content_view_text1, "进度" + result + "%");
                    mNotification.contentView.setProgressBar(
                            R.id.content_view_progress, fileSize, downLoadFileSize,
                            false);
                    mNotificationManager.notify(0, mNotification);
                    break;

            }
        }

    };
}
