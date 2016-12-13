package com.beyonditsm.financial.http;


import android.os.AsyncTask;
import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.beyonditsm.financial.MyApplication;
import com.beyonditsm.financial.util.FinancialUtil;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 接口
 * Created by wangbin on 15/11/13.
 */
public class RequestManager {
    private static CommManager commManager;//共同接口
    private static UserManager userManager;//用户接口
    private static ServicerManager servicerManager;//代言人接口
    private static MangManger mangManger;//信贷经理人接口
    private static WalletManager walletManager;//钱包

    private static RequestManager manager;

    public static CommManager getCommManager() {
        if (commManager == null) {
            commManager = new CommManager();
        }
        return commManager;
    }

    public static UserManager getUserManager() {
        if (userManager == null) {
            userManager = new UserManager();
        }
        return userManager;
    }

    public static ServicerManager getServicerManager() {
        if (servicerManager == null) {
            servicerManager = new ServicerManager();
        }
        return servicerManager;
    }

    public static MangManger getMangManger() {
        if (mangManger == null) {
            mangManger = new MangManger();
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
     * Volley get请求
     *
     * @param url      请求地址
     * @param callback 回调
     */
    public void doGet(String url ,final CallBack callback) {
//        Map<String,String>params
//        MyLogUtils.info("地址:" + url);
//        url = url + "?";
//        Iterator<String> iter = params.keySet().iterator();
//        String key;
//        while (iter.hasNext()) {
//
//            key = iter.next();
//
//            url = url + key + "=" +params.get(key)+ "&";
//
//        }
//        url = url.substring(0,url.length()-1);

        StringRequest request = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        MyLogUtils.info(response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            int status = obj.getInt("status");
                            if (status == 200) {
                                callback.onSucess(response);
                            } else {
                                callback.onError(obj.getInt("status"), obj.getString("message"));

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(no_net, "亲，网络不给力");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> localHashMap = new HashMap<>();
                localHashMap.put("Cookie", SpUtils.getCookie(MyApplication.getInstance()));
                localHashMap.put("User-Agent", "Jinzhu Android Client " + FinancialUtil.getAppVer(MyApplication.getInstance()));
                return localHashMap;
            }

            @Override
            protected Response<String> parseNetworkResponse(
                    NetworkResponse response) {
                // TODO Auto-generated method stub
                try {

                    Map<String, String> responseHeaders = response.headers;
                    String rawCookies = responseHeaders.get("Set-Cookie");
                    String dataString = new String(response.data, "UTF-8");
                    if (!TextUtils.isEmpty(rawCookies))
                        SpUtils.setCooike(MyApplication.getInstance(), rawCookies);
                    return Response.success(dataString, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                }
            }
        };
        // 设定超时时间
        request.setRetryPolicy(new DefaultRetryPolicy(5000, 1, 1.0f));
        MyApplication.getInstance().addToRequestQueue(request);
        MyApplication.getInstance().getRequestQueue().start();

    }

 //get請求（直接返回結果的，沒有statu）
    public void todoGet(String url ,final CallBack callback) {
       StringRequest request=new StringRequest(url, new Response.Listener<String>() {
           @Override
           public void onResponse(String s) {
               try {
                   callback.onSucess(s);
               } catch (JSONException e) {
                   e.printStackTrace();
               }
           }
       },new Response.ErrorListener(){
           @Override
           public void onErrorResponse(VolleyError volleyError) {
               callback.onError(no_net, "亲，网络不给力");
           }
       });

        // 设定超时时间
        request.setRetryPolicy(new DefaultRetryPolicy(5000, 1, 1.0f));
        MyApplication.getInstance().addToRequestQueue(request);
        MyApplication.getInstance().getRequestQueue().start();
    }

//    public  void doGet(String url, List<NameValuePair> queryParams, final CallBack callback){
//        final HttpUtils httpUtils = new HttpUtils();
//        httpUtils.configCurrentHttpCacheExpiry(0);
//        MyLogUtils.info("地址:" + url);
//        MyLogUtils.degug("cookie:" + SpUtils.getCookie(MyApplication.getInstance()));
//
//        RequestParams params = null;
//        if(!"".equals(SpUtils.getCookie(MyApplication.getInstance()))){
//            params=new RequestParams();
//            params.addHeader("cookie",SpUtils.getCookie(MyApplication.getInstance()));
//        }
//        if(queryParams != null && queryParams.size() > 0){
//            if(params==null) {
//                params = new RequestParams();
//            }
//            params.addBodyParameter(queryParams);
//        }
//        if(params==null){
//            params=new RequestParams();
//            params.addHeader("User-Agent", "Jinzhu Android Client " + FinancialUtil.getAppVer(MyApplication.getInstance()));
//        }else{
//            params.addHeader("User-Agent", "Jinzhu Android Client " + FinancialUtil.getAppVer(MyApplication.getInstance()));
//        }
//        httpUtils.send(HttpRequest.HttpMethod.GET, url, params, new RequestCallBack() {
//            @Override
//            public void onSuccess(ResponseInfo responseInfo) {
//                if(!TextUtils.isEmpty(getCoolie(httpUtils)))
//                    SpUtils.setCooike(MyApplication.getInstance(),getCoolie(httpUtils));
//                String result=responseInfo.result.toString();
//                MyLogUtils.info(result);
//                try {
//                    JSONObject obj=new JSONObject(result);
//                    int status= obj.getInt("status");
//                    if(status==200){
//                        callback.onSucess(result);
//                    }else{
//                        callback.onError(obj.getInt("status"),obj.getString("message"));
//
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(HttpException e, String s) {
//                callback.onError(no_net,"亲，网络不给力");
//            }
//
//
//        });
//    }

    /**
     * Volley Post请求
     *
     * @param url      请求地址
     * @param callback 回调
     */
    public void doPost(final String url, final Map<String, String> params, final CallBack callback) {
        MyLogUtils.info("地址:" + url);
        MyLogUtils.info("params"+params);
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        MyLogUtils.info(response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            int status = obj.getInt("status");
                            if (status == 200) {
                                callback.onSucess(response);
                            } else {
                                callback.onError(obj.getInt("status"), obj.getString("message"));

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(no_net, "亲，网络不给力");
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                return checkParams(params);
            }

            private Map<String,String> checkParams(Map<String,String> map){
                Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, String> pairs = (Map.Entry<String, String>)it.next();
                    if(pairs.getValue()==null){
                        map.put(pairs.getKey(), "");
                    }
                }
                return map;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> localHashMap = new HashMap<>();
                localHashMap.put("Cookie", SpUtils.getCookie(MyApplication.getInstance()));
                if("".equals(IFinancialUrl.MARKET_CODE)){
                    localHashMap.put("User-Agent", "Jinzhu Android Client " + FinancialUtil.getAppVer(MyApplication.getInstance()));
                }else {
                    localHashMap.put("User-Agent", "Jinzhu Android Client " + FinancialUtil.getAppVer(MyApplication.getInstance())+"&"+ IFinancialUrl.MARKET_CODE);
                }
                return localHashMap;
            }

            @Override
            protected Response<String> parseNetworkResponse(
                    NetworkResponse response) {
                // TODO Auto-generated method stub
                try {
                    Map<String, String> responseHeaders = response.headers;
                    String rawCookies = responseHeaders.get("Set-Cookie");
                    String dataString = new String(response.data, "UTF-8");
                    if (!TextUtils.isEmpty(rawCookies))
                        SpUtils.setCooike(MyApplication.getInstance(), rawCookies);
                    return Response.success(dataString, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                }
            }
        };
        // 设定超时时间
        request.setRetryPolicy(new DefaultRetryPolicy(5000, 1, 1.0f));
        MyApplication.getInstance().addToRequestQueue(request);
        MyApplication.getInstance().getRequestQueue().start();
    }

    public void doPost(String url, List<NameValuePair> queryParams, final CallBack callBack) {
        final HttpUtils httpUtils = new HttpUtils();
        httpUtils.configCurrentHttpCacheExpiry(0);
        MyLogUtils.info("地址:" + url);
        RequestParams params = null;
        if (!"".equals(SpUtils.getCookie(MyApplication.getInstance()))) {
            params = new RequestParams();
            params.addHeader("cookie", SpUtils.getCookie(MyApplication.getInstance()));
        }
        if (queryParams != null && queryParams.size() > 0) {
            if (params == null) {
                params = new RequestParams();
            }
            params.addBodyParameter(queryParams);
        }
        if (params == null) {
            params = new RequestParams();
            if ("".equals( IFinancialUrl.MARKET_CODE)){
                params.addHeader("User-Agent", "Jinzhu Android Client " + FinancialUtil.getAppVer(MyApplication.getInstance()));
            }else {
                params.addHeader("User-Agent", "Jinzhu Android Client " + FinancialUtil.getAppVer(MyApplication.getInstance())+"&"+ IFinancialUrl.MARKET_CODE);
            }
        } else {
            if ("".equals( IFinancialUrl.MARKET_CODE)){
                params.addHeader("User-Agent", "Jinzhu Android Client " + FinancialUtil.getAppVer(MyApplication.getInstance()));
            }else {
                params.addHeader("User-Agent", "Jinzhu Android Client " + FinancialUtil.getAppVer(MyApplication.getInstance())+"&"+ IFinancialUrl.MARKET_CODE);
            }
        }

        httpUtils.send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack() {
            @Override
            public void onSuccess(ResponseInfo responseInfo) {
                if (!TextUtils.isEmpty(getCoolie(httpUtils)))
                    SpUtils.setCooike(MyApplication.getInstance(), getCoolie(httpUtils));
                String result = responseInfo.result.toString();
                MyLogUtils.info(result);
                try {
                    JSONObject obj = new JSONObject(result);
                    int status = obj.getInt("status");
                    if (status == 200) {
                        callBack.onSucess(result);
                    } else if (status == 600) {
                        SpUtils.clearSp(MyApplication.getInstance());
                        SpUtils.clearRoleName(MyApplication.getInstance());
                    } else {
                        callBack.onError(obj.getInt("status"), obj.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                callBack.onError(no_net, "亲，网络不给力");
            }


        });
    }










    /**
     * 获取图形验证码
     *
     * @param url      请求地址
     * @param callback 回调
     */
    public void getCaput(final String url, final Map<String, String> params, final CallBack callback) {
        MyLogUtils.info("地址:" + url);
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        MyLogUtils.info(response);
                        try {
                            callback.onSucess(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(no_net, "亲，网络不给力");
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> localHashMap = new HashMap<>();
                localHashMap.put("Cookie", SpUtils.getCookie(MyApplication.getInstance()));
                if("".equals(IFinancialUrl.MARKET_CODE)){
                    localHashMap.put("User-Agent", "Jinzhu Android Client " + FinancialUtil.getAppVer(MyApplication.getInstance()));
                }else {
                    localHashMap.put("User-Agent", "Jinzhu Android Client " + FinancialUtil.getAppVer(MyApplication.getInstance())+"&"+ IFinancialUrl.MARKET_CODE);
                }
                return localHashMap;
            }

            @Override
            protected Response<String> parseNetworkResponse(
                    NetworkResponse response) {
                // TODO Auto-generated method stub
                try {
                    Map<String, String> responseHeaders = response.headers;
                    String rawCookies = responseHeaders.get("Set-Cookie");
                    String dataString = new String(response.data, "UTF-8");
                    if (!TextUtils.isEmpty(rawCookies))
                        SpUtils.setCooike(MyApplication.getInstance(), rawCookies);
                    return Response.success(dataString, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                }
            }
        };
        // 设定超时时间
        request.setRetryPolicy(new DefaultRetryPolicy(5000, 1, 1.0f));
        MyApplication.getInstance().addToRequestQueue(request);
        MyApplication.getInstance().getRequestQueue().start();
    }








    /**
     * 上传图片
     *
     * @param url      请求地址
     * @param fileMaps 图片
     * @param callBack 回调
     */
    public void loadImage(final String url, final Map<String, FileBody> fileMaps, final CallBack callBack) {
        MyLogUtils.info("地址:" + url);
        MyLogUtils.info("fileMaps"+fileMaps);
        final HttpManager manager = new HttpManager();
        final Map<String, String> par = new HashMap<>();
        par.put("type", "img");
        par.put("uploadLableName", "file");
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                return manager.upLoadFile(callBack,url, par, fileMaps);
            }

            @Override
            protected void onPreExecute() {

            }

            @Override
            protected void onPostExecute(String result) {
                MyLogUtils.info("上传图片：" + result);
                if (!TextUtils.isEmpty(result)) {
                    try {
//                        JSONObject obj = new JSONObject(result);
//                        int status = obj.getInt("status");
//                        if (status == 200) {
                        JSONArray jsonArray = new JSONArray(result);
                        callBack.onSucess(jsonArray.getString(0));
//                        } else {
//                            callBack.onError(obj.getInt("status"), obj.getString("message"));
//                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.execute();
    }

    /**
     * 上传图片
     *
     * @param url      请求地址
     * @param fileMaps 图片
     * @param callBack 回调
     */
    public void submitFujian(final String url, final String orderNo, final String isSupplementFile, final Map<String, List<FileBody>> fileMaps, final CallBack callBack) {
        final HttpManager manager = new HttpManager();
        final Map<String, String> par = new HashMap<>();
        MyLogUtils.info("地址：" + url);
        par.put("type", "img");
        par.put("uploadLableName", "file");
        if (orderNo != null)
            par.put("orderNo", orderNo);
        par.put("isSupplementFile", isSupplementFile);
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                return manager.upListFile(url, par, fileMaps);
            }

            @Override
            protected void onPreExecute() {

            }

            @Override
            protected void onPostExecute(String result) {
                MyLogUtils.info("上传图片：" + result);
                if (result != null) {
                    try {
                        JSONObject obj = new JSONObject(result);
                        int status = obj.getInt("status");
                        if (status == 200) {
                            callBack.onSucess(obj.getString("data"));
                        } else {
                            callBack.onError(obj.getInt("status"), obj.getString("message"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    callBack.onError(no_net, "亲，网络不给力");

                }

            }
        }.execute();

    }

    private String getCoolie(HttpUtils httpUtils) {
        DefaultHttpClient dh = (DefaultHttpClient) httpUtils.getHttpClient();
        List<Cookie> cookies = dh.getCookieStore().getCookies();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cookies.size(); i++) {
            Cookie cookie = cookies.get(i);
            String cookieName = cookie.getName();
            String cookieValue = cookie.getValue();
            if (!TextUtils.isEmpty(cookieName)
                    && !TextUtils.isEmpty(cookieValue)) {
                sb.append(cookieName).append("=");
                sb.append(cookieValue).append(";");
            }
        }
        return sb.toString();
    }




    public interface CallBack {
        void onSucess(String result) throws JSONException;

        void onError(int status, String msg);
    }


    private int no_net = -1;

}
