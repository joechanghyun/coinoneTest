package com.indrajch.investment.mock.pool.http;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

public class HttpClientImpl implements HttpClient {
	private final RequestConfig requestConfig;

	public HttpClientImpl(Integer connectionTimeMilliseconds, Integer socketTimeOutMilliseconds) {
		this.requestConfig = buildRequestConfig(connectionTimeMilliseconds, socketTimeOutMilliseconds);
	}

	private RequestConfig buildRequestConfig(Integer connectionTimeMilliseconds, Integer socketTimeOutMilliseconds) {
		return RequestConfig.custom()
				.setConnectTimeout(connectionTimeMilliseconds != null ? connectionTimeMilliseconds : 1000)
				.setSocketTimeout(socketTimeOutMilliseconds != null ? socketTimeOutMilliseconds : 100).build();
	}

	public String doPost(Map<String, String> header, String url, String jsonContent) {
		HttpPost httpPost = null;
		CloseableHttpClient closeableHttpClient = null;
		CloseableHttpResponse response = null;
		try {
			httpPost = new HttpPost(url);
			for (Entry<String, String> entry : header.entrySet()) {
				httpPost.addHeader(entry.getKey(), entry.getValue());
			}
			httpPost.setEntity(new StringEntity(jsonContent, ContentType.APPLICATION_JSON));
			closeableHttpClient = HttpConnectionManager.getHttpClient(this.requestConfig);
			response = closeableHttpClient.execute(httpPost, HttpClientContext.create());
			return entityToString(response.getEntity());
		} catch (ClientProtocolException e) {
			HttpConnectionManager.abort(httpPost);
			e.printStackTrace();
		} catch (ConnectTimeoutException e) {
			HttpConnectionManager.abort(httpPost);
			e.printStackTrace();
		} catch (SocketTimeoutException e) {
			HttpConnectionManager.abort(httpPost);
			e.printStackTrace();
		} catch (InterruptedIOException e) {
			HttpConnectionManager.abort(httpPost);
			e.printStackTrace();
		} catch (IOException e) {
			HttpConnectionManager.abort(httpPost);
			e.printStackTrace();
		}

		return null;
	}

	private String entityToString(HttpEntity entity) {
		if (entity == null) {
			return null;
		}
		String result = null;
		try {
			result = EntityUtils.toString(entity, "utf-8");
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			HttpConnectionManager.releaseEntity(entity);
		}
		return result;
	}
}
