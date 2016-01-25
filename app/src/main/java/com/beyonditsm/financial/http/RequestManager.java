package com.beyonditsm.financial.http;


import android.os.AsyncTask;
import android.text.TextUtils;

import com.beyonditsm.financial.MyApplication;
import com.beyonditsm.financial.util.MyLogUtils;
import com.beyonditsm.financial.util.SpUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.http.client.multipart.content.FileBody;

import org.apache.http.NameValuePair;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 接口
 * Created by wangbin on 15/11/13.
 */
public class RequestManager {
    private static CommManager commManager;//共同接口
    private static UserManager userManager;//用户接口
    private static ServicerManager servicerManager;//服务者接口
    private static MangManger mangManger;//信贷经理人接口
    private static WalletManager walletManager;//钱包

    private static RequestManager manager;

    public static CommManager getCommManager(){
        if(commManager==null){
            commManager=new CommManager();
        }
        return commManager;
    }

    public static UserManager getUserManager(){
        if(userManager==null){
            userManager=new UserManager();
        }
        return userManager;
    }

    public  static ServicerManager getServicerManager(){
        if(servicerManager==null){
            servicerManager=new ServicerManager();
        }
        return servicerManager;
    }

    public static MangManger getMangManger(){
        if(mangManger==null){
            mangManger=new MangManger();
        }
        return mangManger;
    }

    public static RequestManager getInstance() {
        if (manager == null) {
            manager = new RequestManager();
        }
        return manager;
    }

    public static WalletManager getWalletManager() {
        if (walletManager == null) {
            walletManager = new WalletManager();
        }
        return walletManager;
    }



    /**
     * 登录、注册 保存Cookie
     * @param url
     * @param queryParams
     * @param callBack
     */
    public void doUser(String url, List<NameValuePair> queryParams, final CallBack callBack){
        {
           final  HttpUtils httpUtils = new HttpUtils();
            httpUtils.configCurrentHttpCacheExpiry(0);
            MyLogUtils.info("地址:" + url);

            RequestParams params = null;
            if(!TextUtils.isEmpty(SpUtils.getCookie(MyApplication.getInstance()))){
                params=new RequestParams();
                params.addHeader("cookie",SpUtils.getCookie(MyApplication.getInstance()));
            }
            if(queryParams != null && queryParams.size() > 0){
                if(params==null) {
                    params = new RequestParams();
                }
            }
            if(params!=null){
                params.addBodyParameter(queryParams);

            }

            httpUtils.send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack() {
                @Override
                public void onSuccess(ResponseInfo responseInfo) {
                    String result=responseInfo.result.toString();
                    MyLogUtils.info(result);
                    MyLogUtils.info("请求返回的cookie："+getCoolie(httpUtils));
                    try {
                        JSONObject obj=new JSONObject(result);
                        int status= obj.getInt("status");
                        if(status==200){

                            SpUtils.setCooike(MyApplication.getInstance(),getCoolie(httpUtils));
                            callBack.onSucess(result);
                        }else {
                            callBack.onError(obj.getInt("status"),obj.getString("message"));

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(HttpException e, String s) {
                    callBack.onError(no_net,"亲，网络不给力");
                }


            });
        }
    }

    public  void doGet(String url, List<NameValuePair> queryParams, final CallBack callback){
        final HttpUtils httpUtils = new HttpUtils();
        httpUtils.configCurrentHttpCacheExpiry(0);
        MyLogUtils.info("地址:" + url);
        MyLogUtils.degug("cookie:" + SpUtils.getCookie(MyApplication.getInstance()));

        RequestParams params = null;
        if(!"".equals(SpUtils.getCookie(MyApplication.getInstance()))){
            params=new RequestParams();
            params.addHeader("cookie",SpUtils.getCookie(MyApplication.getInstance()));
        }
        if(queryParams != null && queryParams.size() > 0){
            if(params==null) {
                params = new RequestParams();
            }
            params.addBodyParameter(queryParams);
        }
        httpUtils.send(HttpRequest.HttpMethod.GET, url, params, new RequestCallBack() {
            @Override
            public void onSuccess(ResponseInfo responseInfo) {
                if(!TextUtils.isEmpty(getCoolie(httpUtils)))
                    SpUtils.setCooike(MyApplication.getInstance(),getCoolie(httpUtils));
                String result=responseInfo.result.toString();
                MyLogUtils.info(result);
                try {
                    JSONObject obj=new JSONObject(result);
                    int status= obj.getInt("status");
                    if(status==200){
                        callback.onSucess(result);
                    }else{
                        callback.onError(obj.getInt("status"),obj.getString("message"));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                callback.onError(no_net,"亲，网络不给力");
            }


        });
    }


    public void doPost(String url, List<NameValuePair> queryParams,final CallBack callBack){
       final  HttpUtils httpUtils = new HttpUtils();
        httpUtils.configCurrentHttpCacheExpiry(0);
        MyLogUtils.info("地址:" + url);
        RequestParams params = null;
        if(!"".equals(SpUtils.getCookie(MyApplication.getInstance()))){
            params=new RequestParams();
            params.addHeader("cookie",SpUtils.getCookie(MyApplication.getInstance()));
        }
        if(queryParams != null && queryParams.size() > 0){
            if(params==null) {
                params = new RequestParams();
            }
            params.addBodyParameter(queryParams);
        }

        httpUtils.send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack() {
            @Override
            public void onSuccess(ResponseInfo responseInfo) {
                if(!TextUtils.isEmpty(getCoolie(httpUtils)))
                SpUtils.setCooike(MyApplication.getInstance(),getCoolie(httpUtils));
                String result=responseInfo.result.toString();
                MyLogUtils.info(result);
                try {
                    JSONObject obj=new JSONObject(result);
                    int status= obj.getInt("status");
                    if(status==200){

                        callBack.onSucess(result);
                    }else{
                        callBack.onError(obj.getInt("status"),obj.getString("message"));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                   callBack.onError(no_net,"亲，网络不给力");
            }


        });
    }

    /**
     * 获取验证码保存cookie
     * @param url
     * @param queryParams
     * @param callBack
     */
    public void getCode(String url,List<NameValuePair> queryParams,final CallBack callBack){
        {
            final  HttpUtils httpUtils = new HttpUtils();
            httpUtils.configCurrentHttpCacheExpiry(0);

            RequestParams params = null;
            if(queryParams != null && queryParams.size() > 0){
                params = new RequestParams();
                params.addBodyParameter(queryParams);
            }

            httpUtils.send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack() {
                @Override
                public void onSuccess(ResponseInfo responseInfo) {
                    String result=responseInfo.result.toString();
                    MyLogUtils.info(result);
                    try {
                        JSONObject obj=new JSONObject(result);
                        int status= obj.getInt("status");
                        if(status==200){
//                            MyApplication.code_cookie=getCoolie(httpUtils);
                            callBack.onSucess(result);
                        }else {
                            callBack.onError(obj.getInt("status"),obj.getString("message"));

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(HttpException e, String s) {
                    callBack.onError(no_net,"亲，网络不给力");
                }


            });
        }
    }


    /**
     * 上传图片
     * @param url
     * @param
     * @param fileMaps
     * @param callBack
     */
    public void loadImage(final String url ,final Map<String, FileBody> fileMaps,final CallBack callBack){
       final HttpManager manager=new HttpManager();
       final  Map<String, String> par=new HashMap<String,String>();
        par.put("type","img");
        par.put("uploadLableName","file");
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                return  manager.upLoadFile(url,par,fileMaps);
            }

            @Override
            protected void onPreExecute() {

            }

            @Override
            protected void onPostExecute(String result) {
               MyLogUtils.info("上传图片："+result);
                JSONObject obj= null;
                try {
                    obj = new JSONObject(result);
                    int status= obj.getInt("status");
                    if(status==200){
                        callBack.onSucess(obj.getString("data"));
                    }else{
                        callBack.onError(obj.getInt("status"),obj.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }

    /**
     * 上传图片
     * @param url
     * @param
     * @param fileMaps
     * @param callBack
     */
    public void submitFujian(final String url ,final String orderNo,final String isSupplementFile,final Map<String, List<FileBody>> fileMaps,final CallBack callBack){
        final HttpManager manager=new HttpManager();
        final  Map<String, String> par=new HashMap<String,String>();
        MyLogUtils.info("地址：" + url);
        par.put("type","img");
        par.put("uploadLableName","file");
        if(orderNo!=null)
        par.put("orderNo",orderNo);
        par.put("isSupplementFile",isSupplementFile);
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                return  manager.upListFile(url,par,fileMaps);
            }

            @Override
            protected void onPreExecute() {

            }

            @Override
            protected void onPostExecute(String result) {
                MyLogUtils.info("上传图片："+result);
                if(result!=null) {
                    JSONObject obj = null;
                    try {
                        obj = new JSONObject(result);
                        int status = obj.getInt("status");
                        if (status == 200) {
                            callBack.onSucess(obj.getString("data"));
                        } else {
                            callBack.onError(obj.getInt("status"), obj.getString("message"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    callBack.onError(no_net,"亲，网络不给力");

                }

            }
        }.execute();

    }



    private String getCoolie(HttpUtils httpUtils){
        DefaultHttpClient dh= (DefaultHttpClient) httpUtils.getHttpClient();
        List<Cookie> cookies=dh.getCookieStore().getCookies();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < cookies.size(); i++) {
            Cookie cookie = cookies.get(i);
            String cookieName = cookie.getName();
            String cookieValue = cookie.getValue();
            if (!TextUtils.isEmpty(cookieName)
                    && !TextUtils.isEmpty(cookieValue)) {
                sb.append(cookieName + "=");
                sb.append(cookieValue+";");
            }
        }
        return sb.toString();
    }





    public interface CallBack{
        public  void onSucess(String result) throws JSONException;

        public void onError(int status,String msg);
    }


    private  int no_net=-1;

}
