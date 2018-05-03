package com.tcc.dingtalk.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.RedirectLocations;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import com.tcc.common.utils.FileUtils;
import com.tcc.dingtalk.https.MyX509TrustManager;

public class HttpsCaller {
	/**
	 * 处理https GET/POST请求 请求地址、请求方法、参数 json
	 */
	public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {
		StringBuffer buffer = null;
		requestMethod = requestMethod.toUpperCase();
		try {
			// 创建SSLContext
			SSLContext sslContext = SSLContext.getInstance("SSL");
			TrustManager[] tm = { new MyX509TrustManager() };
			// 初始化
			sslContext.init(null, tm, new java.security.SecureRandom());
			;
			// 获取SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod(requestMethod);
			conn.setRequestProperty("Content-type", "application/json");
			// 设置当前实例使用的SSLSoctetFactory
			conn.setSSLSocketFactory(ssf);
			conn.connect();
			// 往服务器端写内容

			if (requestMethod.equals("POST") && null != outputStr) {
				OutputStream os = conn.getOutputStream();
				os.write(outputStr.getBytes("utf-8"));
				os.close();
			}

			// 读取服务器端返回的内容
			InputStream is = conn.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "utf-8");
			BufferedReader br = new BufferedReader(isr);
			buffer = new StringBuffer();
			String line = null;
			while ((line = br.readLine()) != null) {
				buffer.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}

	/**
	 * 发起https get请求
	 * 
	 * @param requestUrl
	 * @return
	 */
	public static String httpsGetRequest(String requestUrl) {
		return httpsRequest(requestUrl, "GET", null);
	}

	/**
	 * 发起https post请求
	 * 
	 * @param requestUrl
	 * @param outputStr
	 * @return
	 */
	public static String httpsPostRequest(String requestUrl, String outputStr) {
		return httpsRequest(requestUrl, "POST", outputStr);
	}

	public static void main(String[] args) {
		String s = httpsGetRequest(
				"https://oapi.dingtalk.com/gettoken?corpid=ding1284cea1e755803535c2f4657eb6378f&corpsecret=slZcppr-LFOahmRuAOcHJ3_liXzopf_JOTIqt8yZWUVhuTXmROXB4Jgeor1tFeJF");
		System.out.println(s);
	}

	public static String postFile(String requestUrl, File file) {
		HttpPost httpPost = new HttpPost(requestUrl);
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();
		httpPost.setConfig(requestConfig);

		HttpEntity requestEntity = MultipartEntityBuilder.create()
				.addPart("media", new FileBody(file, ContentType.APPLICATION_OCTET_STREAM, file.getName())).build();
		httpPost.setEntity(requestEntity);

		try {
			response = httpClient.execute(httpPost, new BasicHttpContext());

			if (response.getStatusLine().getStatusCode() != 200) {

				System.out.println("request url failed, http code=" + response.getStatusLine().getStatusCode()
						+ ", url=" + requestUrl);
				return null;
			}
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String resultStr = EntityUtils.toString(entity, "utf-8");
				return resultStr;
			}
		} catch (IOException e) {
			System.out.println("request url=" + requestUrl + ", exception, msg=" + e.getMessage());
			e.printStackTrace();
		} finally {
			if (response != null)
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

		return null;
	}

	public String downloadFile(String requestUrl, String fileDir) {
		HttpGet httpGet = new HttpGet(requestUrl);
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();
		httpGet.setConfig(requestConfig);

		try {
			HttpContext localContext = new BasicHttpContext();

			response = httpClient.execute(httpGet, localContext);

			RedirectLocations locations = (RedirectLocations) localContext
					.getAttribute(HttpClientContext.REDIRECT_LOCATIONS);
			if (locations != null) {
				URI downloadUrl = locations.getAll().get(0);
				String filename = downloadUrl.toURL().getFile();
				System.out.println("downloadUrl=" + downloadUrl);
				File downloadFile = new File(fileDir + File.separator + filename);
				FileUtils.writeByteArrayToFile(downloadFile, EntityUtils.toByteArray(response.getEntity()));
				return "{\"errcode\": 0,\"errmsg\": \"ok\",\"file\":\"" + response.getEntity() + "\"}";
			} else {
				if (response.getStatusLine().getStatusCode() != 200) {

					System.out.println("request url failed, http code=" + response.getStatusLine().getStatusCode()
							+ ", url=" + requestUrl);
					return null;
				}
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					String resultStr = EntityUtils.toString(entity, "utf-8");
					return resultStr;
				}
			}
		} catch (IOException e) {
			System.out.println("request url=" + requestUrl + ", exception, msg=" + e.getMessage());
			e.printStackTrace();
		} finally {
			if (response != null)
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

		return null;
	}
}
