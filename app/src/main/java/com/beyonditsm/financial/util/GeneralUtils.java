package com.beyonditsm.financial.util;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.beyonditsm.financial.MyApplication;
import com.beyonditsm.financial.R;
import com.beyonditsm.financial.activity.user.SettingAct;
import com.beyonditsm.financial.entity.ResultData;
import com.beyonditsm.financial.entity.VersionInfo;
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
        MyLogUtils.info("当前版本："+currentVersion+"");
        RequestManager.getCommManager().toVersion(currentVersion, "ANDROID", new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) throws JSONException {
                ResultData<VersionInfo> rd= (ResultData<VersionInfo>) GsonUtils.json(result,VersionInfo.class);
                if (!rd.getData().isNeedUpdrage()) {
                    if(type==0)
                    MyToastUtils.showShortToast(context, "当前已是最新版本！");

                }else{
                    String path = rd.getData().getVersion().getPackagePath();
                    String updrageLog = rd.getData().getVersion().getUpdrageLog();//新版本更新内容
                    String remark = rd.getData().getVersion().getRemark();//新版本大小
                    showIsDownLoad(context, path,"",remark,updrageLog);
//                MyToastUtils.showShortToast(MyApplication.getInstance(),rd.getData().getMessage());
                }
            }

            @Override
            public void onError(int status, String msg) {

            }
        });
    }


    /*
 * 从服务器中下载APK
 */
    protected void downLoadApk(final Context context,final String url) {
        final ProgressDialog pd;    //进度条对话框
        pd = new  ProgressDialog(context);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMax(100);
        pd.setProgress(0);
        pd.setMessage("正在下载更新");
        pd.show();
        new Thread(){
            @Override
            public void run() {
                try {
                    File file = DownLoadManager.getFileFromServer(url, pd);
                    sleep(500);
                    installApk(context,file);
                    pd.dismiss(); //结束掉进度条对话框
                } catch (Exception e) {
                    Message msg = new Message();
                    msg.what = DOWN_ERROR;
                    msg.obj=context;
                    handler.sendMessage(msg);
                    e.printStackTrace();
                }
            }}.start();
    }

    private final int DOWN_ERROR=1;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            switch (msg.what) {

                case DOWN_ERROR:
                    Context context= (Context) msg.obj;
                    //下载apk失败
                    Toast.makeText(context, "下载新版本失败",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    //安装apk
    protected void installApk(Context context,File file) {
        Intent intent = new Intent();
        //执行动作
        intent.setAction(Intent.ACTION_VIEW);
        //执行的数据类型
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");//编者按：此处Android应为android，否则造成安装不了
        context.startActivity(intent);
    }



    private NotificationManager mNotificationManager = null;
    private Notification mNotification;
    private String fileName = "/sdcard/jinzhu_.apk";
    private int result, fileSize, downLoadFileSize;
    private boolean isStart;

    /**
     * 是否下载
     */
    private void showIsDownLoad(final Context context,final String path,String versionName,String versionSize,String versionContent) {
        MyAlertDialog dialog = new MyAlertDialog(context).builder();
        dialog.setTitle("提示").setMsg("检测到最新版本，是否现在下载？").setCancelable(false)
                .setPositiveButton("立即更新", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//        dialog.setTitle("提示").setMsg("检测到最新版本，是否现在下载？").setCancelable(false)
//                .setPositiveButton("确定", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        File file = new File(fileName);
//                        if (file.exists()) {
//                            file.delete();
//                        }
//                        downLoad(context,path);
                        downLoadApk(context, path);
                    }
                }).setNegativeButton("稍后再说", null).show();
//                    }
//                }).setNegativeButton("取消", null).show();
//        dialog.setTitle("发现新版本").setMsgLayout(R.layout.layout_versionupload,"",versionSize,versionContent).setCancelable(false).setPositiveButton("立即更新", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                File file = new File(fileName);
////                        if (file.exists()) {
////                            file.delete();
////                        }
////                        downLoad(context,path);
//                downLoadApk(context,path);
//            }
//        }).setNegativeButton("稍后再说", null).show();
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
                    Intent intent = new Intent(SettingAct.ISLOADING);
                    @Override
                    public void onLoading(long total, long current,
                                          boolean isUploading) {
                        isUploading = true;
                        intent.putExtra("isUploading",isUploading);
                        context.sendBroadcast(intent);
                        fileSize = (int) total;
                        downLoadFileSize = (int) current;
                        result = (int) (current * 100 / total);
                        myhandler.sendEmptyMessage(1);
                    }

                    @Override
                    public void onStart() {
                        isStart =true;
                        intent.putExtra("isStart",isStart);
                        context.sendBroadcast(intent);
                        Message message=myhandler.obtainMessage();
                        message.obj=context;
                        message.what=0;
                        myhandler.sendMessage(message);
                        MyToastUtils.showShortToast(context, "开始升级新版本");
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
