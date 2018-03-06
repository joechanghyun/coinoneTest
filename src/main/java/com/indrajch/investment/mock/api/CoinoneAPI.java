package com.indrajch.investment.mock.api;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.springframework.beans.factory.annotation.Autowired;

import com.indrajch.investment.mock.api.coinone.Payload;
import com.indrajch.investment.mock.encrypt.Cryptography;
import com.indrajch.investment.mock.pool.http.HttpClient;

public class CoinoneAPI implements API {
	private static final String API_V2_URL = "https://api.coinone.co.kr/v2";

	private static final String X_COINONE_PAYLOAD = "X-COINONE-PAYLOAD";

	private static final String X_COINONE_SIGNATURE = "X-COINONE-SIGNATURE";

	@Autowired
	private HttpClient httpClient;

	@Autowired
	private Cryptography coinoneCryptography;

	private String accessToken;

	private String secretKey;

	public CoinoneAPI(String accessToken, String secretKey) {
		this.accessToken = accessToken;
		this.secretKey = secretKey.toUpperCase();
	}

	private String makeEncodedPayload(Payload payload) throws UnsupportedEncodingException {
		return payload.convertionToJsonBase64encode();
	}

	private String makeSignature(String encodedPayload) throws IllegalStateException, UnsupportedEncodingException {
		return coinoneCryptography.cryptographyData(encodedPayload);
	}

	private Map<String, String> makeHeader(String payload, String signature) {
		Map<String, String> header = new HashMap<>();
		header.put(X_COINONE_PAYLOAD, payload);
		header.put(X_COINONE_SIGNATURE, signature);
		return header;
	}

	public String getDailyBalance() throws UnsupportedEncodingException {
		String encodedPayload = makeEncodedPayload(new Payload(this.accessToken));
		String signature = makeSignature(encodedPayload);
		String response = httpClient.doPost(makeHeader(encodedPayload, signature),
				new StringBuilder().append(API_V2_URL).append("/account/daily_balance/").toString(), encodedPayload);

		return response;
	}

}
