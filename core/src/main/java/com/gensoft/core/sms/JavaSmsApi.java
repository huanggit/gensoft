package com.gensoft.core.sms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * 短信http接口的java代码调用 基于Apache HttpClient 4.3
 * 
 */
public class JavaSmsApi{
	
	// 通用发送接口的http地址
	private static String URI_SEND_SMS = "http://yunpian.com/v1/sms/send.json";
	
	// 编码格式。发送编码格式统一用UTF-8
	private static String ENCODING = "UTF-8";
	
	private static String apikey = "96bdb8c87e9bf4e11f502508cb43fb9a";
	
	/**
	 * 基于HttpClient 4.3的通用POST方法
	 * 
	 * @param url 提交的URL
	 * @param paramsMap 提交<参数，值>Map
	 * @return 提交响应
	 */
	public static String send(String text, String mobile) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("apikey", apikey);
		params.put("text", text);
		params.put("mobile", mobile);
		
		CloseableHttpClient client = null;
		String responseText = "";
		CloseableHttpResponse response = null;
		try {
			client = HttpClients.createDefault();
			HttpPost method = new HttpPost(URI_SEND_SMS);
			
			List<NameValuePair> paramList = new ArrayList<NameValuePair>();
			NameValuePair p_apikey = new BasicNameValuePair("apikey",apikey);
			paramList.add(p_apikey);
			NameValuePair p_text = new BasicNameValuePair("text",text);
			paramList.add(p_text);
			NameValuePair p_mobile = new BasicNameValuePair("mobile",mobile);
			paramList.add(p_mobile);
			method.setEntity(new UrlEncodedFormEntity(paramList, ENCODING));
			
			response = client.execute(method);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				responseText = EntityUtils.toString(entity);
			}
			response.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(client !=null){
					client.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return responseText;
	}

}