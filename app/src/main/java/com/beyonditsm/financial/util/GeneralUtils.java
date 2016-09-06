package com.beyonditsm.financial.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Toast;

import com.beyonditsm.financial.MyApplication;
import com.beyonditsm.financial.R;
import com.beyonditsm.financial.entity.ResultData;
import com.beyonditsm.financial.entity.VersionInfo;
import com.beyonditsm.financial.http.IFinancialUrl;
import com.beyonditsm.financial.http.RequestManager;
import com.beyonditsm.financial.widget.MyAlertDialog;

import org.json.JSONException;

import java.io.File;

/**
 * Created by wangbin on 15/11/23
 */
public class GeneralUtils {


    public void getCode(String phoneNum) {
        RequestManager.getCommManager().getCode(phoneNum, new RequestManager.CallBack() {
            @Override
            public void onSucess(String result) {
                MyToastUtils.showShortToast(MyApplication.getInstance().getApplicationContext(), "验证码发送成功");
                iCode.isSucess(1);
            }

            @Override
            public void onError(int status, String msg) {
                MyToastUtils.showShortToast(MyApplication.getInstance().getApplicationContext(), msg);
                iCode.isSucess(-1);
            }
        });
    }

    private ICode iCode;

    public void setiCode(ICode iCode) {
        this.iCode = iCode;
    }

    public interface ICode {
        void isSucess(int flag);
    }


    public void toVersion(final Context context, final int currentVersion, final int type) {
        RequestManager.getCommManager().toVersion(IFinancialUrl.MARKET_CODE, currentVersion, "ANDROID", new RequestManager.CallBack() {
            @SuppressWarnings("unchecked")
            @Override
            public void onSucess(String result) throws JSONException {
                ResultData<VersionInfo> rd = (ResultData<VersionInfo>) GsonUtils.json(result, VersionInfo.class);
                if (!rd.getData().isNeedUpdrage()) {
                    if (type == 0)
                        MyToastUtils.showShortToast(context, "当前已是最新版本！");

                } else {
                    String path = rd.getData().getVersion().getPackagePath();
                    String updrageLog = rd.getData().getVersion().getUpdrageLog();//新版本更新内容
                    String remark = rd.getData().getVersion().getRemark();//新版本大小
                    String versionName = rd.getData().getVersion().getVersionName();
                    showIsDownLoad(context, path, versionName, remark, updrageLog);
//                MyToastUtils.showShortToast(MyApplication.getInstance(),rd.getData().getMessage());
                }
            }

            @Override
            public void onError(int status, String msg) {

            }
        });
    }


    /**
     * 从服务器中下载APK
     */
    protected void downLoadApk(final Context context, final String url) {
        final ProgressDialog pd;    //进度条对话框
        pd = new ProgressDialog(context);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMax(100);
        pd.setProgress(0);
        pd.setCanceledOnTouchOutside(false);
        pd.setMessage("正在下载更新");
        pd.show();
        new Thread() {
            @Override
            public void run() {
                try {
                    File file = DownLoadManager.getFileFromServer(url, pd);
                    sleep(500);
                    installApk(context, file);
                    pd.dismiss(); //结束掉进度条对话框
                } catch (Exception e) {
                    Message msg = new Message();
                    msg.what = DOWN_ERROR;
                    msg.obj = context;
                    handler.sendMessage(msg);
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private final int DOWN_ERROR = 1;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            switch (msg.what) {

                case DOWN_ERROR:
                    Context context = (Context) msg.obj;
                    //下载apk失败
                    Toast.makeText(context, "下载新版本失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    //安装apk
    protected void installApk(Context context, File file) {
        Intent intent = new Intent();
        //执行动作
        intent.setAction(Intent.ACTION_VIEW);
        //安装完成后可选择打开或者完成
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //执行的数据类型
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");//编者按：此处Android应为android，否则造成安装不了
        context.startActivity(intent);
    }


    /**
     * 是否下载
     */
    private void showIsDownLoad(final Context context, final String path, String versionName, String versionSize, String versionContent) {
        final MyAlertDialog dialog = new MyAlertDialog(context).builder();
        dialog.setTitle("发现新版本").setMsgLayout(R.layout.layout_versionupload, versionName, versionSize, versionContent).setCancelable(false).setPositiveButton("立即更新", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String networkType = getNetworkType(context);
                if ("WIFI".equals(networkType)){
                    downLoadApk(context, path);
                }else{
                    MyAlertDialog dialog = new MyAlertDialog(context).builder();
                    dialog.setTitle("提示").setMsg("当前网络为非WIFI环境，是否继续下载？").setPositiveButton("继续下载", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            downLoadApk(context,path);
                        }
                    }).setNegativeButton("暂时不了", null).show();
                }
//                downLoadApk(context, path);
            }
        }).setNegativeButton("稍后再说", null).show();
    }


    private String getNetworkType(Context context) {
        String strNetworkType = "";
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                strNetworkType = "WIFI";
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                String _strSubTypeName = networkInfo.getSubtypeName();

                // TD-SCDMA   networkType is 17
                int networkType = networkInfo.getSubtype();
                switch (networkType) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN: //api<8 : replace by 11
                        strNetworkType = "2G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B: //api<9 : replace by 14
                    case TelephonyManager.NETWORK_TYPE_EHRPD:  //api<11 : replace by 12
                    case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
                        strNetworkType = "3G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
                        strNetworkType = "4G";
                        break;
                    default:
                        // http://baike.baidu.com/item/TD-SCDMA 中国移动 联通 电信 三种3G制式
                        if (_strSubTypeName.equalsIgnoreCase("TD-SCDMA") || _strSubTypeName.equalsIgnoreCase("WCDMA") || _strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                            strNetworkType = "3G";
                        } else {
                            strNetworkType = _strSubTypeName;
                        }
                        break;
                }
            }
        }

        return strNetworkType;
    }
}
