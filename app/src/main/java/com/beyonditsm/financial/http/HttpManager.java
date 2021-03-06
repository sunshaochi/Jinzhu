package com.beyonditsm.financial.http;

import com.beyonditsm.financial.ConstantValue;
import com.beyonditsm.financial.MyApplication;
import com.beyonditsm.financial.util.FinancialUtil;
import com.beyonditsm.financial.util.MyLogUtils;
import com.beyonditsm.financial.util.SpUtils;
import com.lidroid.xutils.http.client.multipart.MultipartEntity;
import com.lidroid.xutils.http.client.multipart.content.FileBody;
import com.lidroid.xutils.http.client.multipart.content.StringBody;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 联网工具
 * 
 * 
 */
public class HttpManager {
	private HttpClient client;
	private HttpPost post;
	private HttpGet get;

	private static Header[] headers;

	static {
		headers = new Header[2];
		headers[0] = new BasicHeader("cookie",SpUtils.getCookie(MyApplication.getInstance()));
		headers[1] = new BasicHeader("User-Agent","Jinzhu Android Client "+ FinancialUtil.getAppVer(MyApplication.getInstance())+"&");
	}

	@SuppressWarnings("deprecation")
	public HttpManager() {
		// 初始化client
		// 如果是wap方式联网，需要设置代理信息
		client = new DefaultHttpClient();
		// 响应超时时间为5秒
		client.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
	}

	/**
	 * get请求
	 * 
	 * @param uri :自己拼装好get参数的字符串: "http://test.com/api_v270?userId=" + userId +"&version=" + version;
	 * @return json字符串
	 */
	public String sendGet(String uri) {
		get = new HttpGet(uri);
		get.setHeaders(headers);
		try {
			HttpResponse response = client.execute(get);
			int code = response.getStatusLine().getStatusCode();
			if (code == 200) {
				return EntityUtils.toString(response.getEntity(),
						ConstantValue.ENCODING);
			} else {
				MyLogUtils.error("访问失败--状态码："
						+ response.getStatusLine().getStatusCode());
			}

		} catch (Exception e) {
			e.printStackTrace();
			MyLogUtils.error("访问异常：" + e.getMessage());
		}

		return null;
	}

	/**
	 * get请求
	 * 
	 * @param uri 请求地址
	 * @return 流
	 */
	public InputStream sendGetI(String uri) {
		get = new HttpGet(uri);
		get.setHeaders(headers);
		try {
			HttpResponse response = client.execute(get);
			int code = response.getStatusLine().getStatusCode();
			if (code == 200) {
				return response.getEntity().getContent();
			} else {
				MyLogUtils.error("访问失败--状态码："
						+ response.getStatusLine().getStatusCode());
			}

		} catch (Exception e) {
			e.printStackTrace();
			MyLogUtils.error("访问异常：" + e.getMessage());
		}
		return null;

	}

	/**
	 * post请求
	 * 
	 * @param uri 请求地址
	 * @param data xml或者json字符串
	 * @return json字符串
	 */
	public String sendPost(String uri, String data) {
		post = new HttpPost(uri);
		post.setHeaders(headers);
		try {
			StringEntity entity = new StringEntity(data, ConstantValue.ENCODING);
			post.setEntity(entity);
			HttpResponse response = client.execute(post);
			if (response.getStatusLine().getStatusCode() == 200) {
				// InputStream inputStream = response.getEntity().getContent();
				return EntityUtils.toString(response.getEntity(),
						ConstantValue.ENCODING);
			} else {
				MyLogUtils.error("访问失败--状态码："
						+ response.getStatusLine().getStatusCode());
			}
		} catch (Exception e) {
			e.printStackTrace();
			MyLogUtils.error("访问异常：" + e.getMessage());
		}
		return null;

	}

	/**
	 * post请求
	 * 
	 * @param uri 请求地址
	 * @param params 参数集合
	 * @return json字符串
	 */
	public String sendPost(String uri, Map<String, String> params) {
		post = new HttpPost(uri);
		post.setHeaders(headers);
		try {
			if (params != null && params.size() > 0) {
				List<BasicNameValuePair> parameters = new ArrayList<>();
				for (Map.Entry<String, String> item : params.entrySet()) {
					BasicNameValuePair pair = new BasicNameValuePair(
							item.getKey(), item.getValue());
					parameters.add(pair);
				}
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(
						parameters, ConstantValue.ENCODING);
				post.setEntity(entity);// 设置需要传递的数据
			}
			HttpResponse response = client.execute(post);
			if (response.getStatusLine().getStatusCode() == 200) {
				// return reponse.getEntity().getContent();
				return EntityUtils.toString(response.getEntity(),
						ConstantValue.ENCODING);
			} else {
				MyLogUtils.error("访问失败--状态码："
						+ response.getStatusLine().getStatusCode());

			}
		} catch (Exception e) {
			e.printStackTrace();
			MyLogUtils.error("访问异常：" + e.getMessage());
		}
		return null;
	}

	/**
	 * 文件上传 post
	 * 
	 * @param uri 请求地址
	 * @param params 集合
	 * @param fileMaps 图片
	 */
	public String upLoadFile(RequestManager.CallBack callBack,String uri, Map<String, String> params,
							 Map<String, FileBody> fileMaps) {
		post = new HttpPost(uri);

		post.setHeaders(headers);
		try {
			MultipartEntity mpEntity = new MultipartEntity();
			if (params != null && params.size() > 0) {
				for (Map.Entry<String, String> item : params.entrySet()) {
					StringBody par = new StringBody(item.getValue());
					mpEntity.addPart(item.getKey(), par);
				}

			}

			if (fileMaps != null && fileMaps.size() > 0) {
				for (Map.Entry<String, FileBody> entry : fileMaps.entrySet()) {
					mpEntity.addPart(entry.getKey(), entry.getValue());
				}
			}
			post.setEntity(mpEntity);// 设置需要传递的数据
			HttpResponse response = client.execute(post);
			if (response.getStatusLine().getStatusCode() == 200) {
				return EntityUtils.toString(response.getEntity(),
						ConstantValue.ENCODING);
			} else {
				MyLogUtils.error("访问失败--状态码："
						+ response.getStatusLine().getStatusCode());
				callBack.onError(response.getStatusLine().getStatusCode(),"访问失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			callBack.onError(500,"访问异常");
			MyLogUtils.error("访问异常：" + e.getMessage());
		}
		return null;
	}

	/**
	 * 文件上传 post
	 *
	 * @param uri 请求地址
	 */
	public String upListFile(String uri, Map<String, String> params,
							 Map<String, List<FileBody>> fileMaps) {
		post = new HttpPost(uri);

		post.setHeaders(headers);
		try {
			MultipartEntity mpEntity = new MultipartEntity();
			if (params != null && params.size() > 0) {
				for (Map.Entry<String, String> item : params.entrySet()) {
					StringBody par = new StringBody(item.getValue());
					mpEntity.addPart(item.getKey(), par);
				}

			}

			if (fileMaps != null && fileMaps.size() > 0) {
				for (Map.Entry<String, List<FileBody>> entry : fileMaps.entrySet()) {
					List<FileBody> lists= entry.getValue();
					for(int i=0;i<lists.size();i++){
						mpEntity.addPart(entry.getKey(), lists.get(i));
					}

				}
			}
			post.setEntity(mpEntity);// 设置需要传递的数据
			HttpResponse response = client.execute(post);
			if (response.getStatusLine().getStatusCode() == 200) {
				return EntityUtils.toString(response.getEntity(),
						ConstantValue.ENCODING);
			} else {
				MyLogUtils.error("访问失败--状态码："
						+ response.getStatusLine().getStatusCode());
			}
		} catch (Exception e) {
			e.printStackTrace();
			MyLogUtils.error("访问异常：" + e.getMessage());
		}
		return null;
	}

}
