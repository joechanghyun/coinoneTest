package com.indrajch.investment.mock.pool.http;

import java.util.Map;

import org.apache.http.HttpEntity;

public interface HttpClient {
	String doPost(Map<String, String> header, String url, String jsonContent);

	String doGet(String url);
}
