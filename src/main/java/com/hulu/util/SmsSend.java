package com.hulu.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsSend {
	
	private final static Logger logger = Logger.getLogger(SmsSend.class.getName());

	@Value("${huyi.url}")
	private static String Url;

	@Value("${huyi.account}")
	private static String account;

	@Value("${huyi.password}")
	private static String password;
	
	
	public static void sendSms(String mobile,String content){
		
		
		HttpPost httpRequest = new HttpPost(Url);
			
		httpRequest.addHeader("ContentType","application/x-www-form-urlencoded;charset=UTF-8");
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("account", account));
		params.add(new BasicNameValuePair("password", password));
		params.add(new BasicNameValuePair("mobile", mobile));
	    params.add(new BasicNameValuePair("content", content));
		
		
			
		try {

		    HttpEntity httpentity = new UrlEncodedFormEntity(params, "utf-8");
			// 请求httpRequest
			httpRequest.setEntity(httpentity);
			// 取得默认的HttpClient
			HttpClient httpclient = new DefaultHttpClient();
			// 取得HttpResponse
			HttpResponse httpResponse = httpclient.execute(httpRequest);
			
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// 取得返回的字符串
				String SubmitResult = EntityUtils.toString(httpResponse.getEntity());
				Document doc = DocumentHelper.parseText(SubmitResult); 
				Element root = doc.getRootElement();
				logger.info("发送消息,手机号=" +mobile + ", content=" + content);

				String code = root.elementText("code");	
				String msg = root.elementText("msg");	
				String smsid = root.elementText("smsid");	
				logger.info((new StringBuilder("Sms was sended. To=").append(mobile)
						.append(", Result=").append(code)
						.append(", msg=").append(msg)
						.append(", smsid=").append(smsid).toString()).toString());
			}
		} catch (IOException e) {
			logger.error("发送短信发生错误！", e);
		} catch (DocumentException e) {
			logger.error("发送短信发生错误！", e);
		}	catch (Exception e) {
			logger.error("发送短信发生错误！", e);
		}
	}
	public static void main(String[] args) {
		sendSms("18601222287", null);
	}
}
