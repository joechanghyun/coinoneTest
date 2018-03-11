package com.indrajch.investment.mock.api;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.indrajch.investment.mock.api.coinone.CoinoneBalanceResponse;
import com.indrajch.investment.mock.api.coinone.CoinoneErrorCode;
import com.indrajch.investment.mock.api.coinone.CoinoneMyCompleteOrderResponse;
import com.indrajch.investment.mock.api.coinone.CoinoneOrderResponse;
import com.indrajch.investment.mock.api.coinone.CoinoneTradesResponse;
import com.indrajch.investment.mock.api.coinone.PayLoadCompleteOrder;
import com.indrajch.investment.mock.api.coinone.PayLoadOrder;
import com.indrajch.investment.mock.api.coinone.Payload;
import com.indrajch.investment.mock.data.Currency;
import com.indrajch.investment.mock.encrypt.Cryptography;
import com.indrajch.investment.mock.pool.http.HttpClient;

public class CoinoneAPI implements API {
	private static final String API_URL = "https://api.coinone.co.kr";
	
	private static final String API_V2_URL = API_URL + "/v2";

	private static final String X_COINONE_PAYLOAD = "X-COINONE-PAYLOAD";

	private static final String X_COINONE_SIGNATURE = "X-COINONE-SIGNATURE";

	private static final Gson GSON = new GsonBuilder() //
			.registerTypeAdapter(Currency.class, new CurrencyDeserializer()) //
			.registerTypeAdapter(CoinoneErrorCode.class, new CoinoneErrorCodeDeserializer()) //
			.create();

	@Autowired
	private HttpClient httpClient;

	@Autowired
	private Cryptography coinoneCryptography;

	private String accessToken;

	public CoinoneAPI(String accessToken) {
		this.accessToken = accessToken;
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

	@Override
	public CoinoneTradesResponse getTrades(Currency currency) {
		String response = httpClient.doGet(new StringBuilder().append(API_URL).append("/trades?").append("currency=")
				.append(currency.name).append("&period=hour").toString());
		return GSON.fromJson(response, CoinoneTradesResponse.class);
	}

	@Override
	public CoinoneBalanceResponse getBalance() throws UnsupportedEncodingException {
		String encodedPayload = makeEncodedPayload(new Payload(this.accessToken));
		String signature = makeSignature(encodedPayload);
		String response = httpClient.doPost(makeHeader(encodedPayload, signature),
				new StringBuilder().append(API_V2_URL).append("/account/balance/").toString(), encodedPayload);

		return GSON.fromJson(response, CoinoneBalanceResponse.class);
	}

	private CoinoneOrderResponse commonOrder(String location, long price, float qty, Currency currency)
			throws UnsupportedEncodingException {
		String encodedPayload = makeEncodedPayload(
				new PayLoadOrder(this.accessToken, price, qty, currency.name));
		String signature = makeSignature(encodedPayload);
		String response = httpClient.doPost(makeHeader(encodedPayload, signature),
				new StringBuilder().append(API_V2_URL).append(location).toString(), encodedPayload);

		return GSON.fromJson(response, CoinoneOrderResponse.class);
	}

	@Override
	public CoinoneOrderResponse orderBuy(long price, float qty, Currency currency) throws UnsupportedEncodingException {
		return commonOrder("/order/limit_buy/", price, qty, currency);
	}

	@Override
	public CoinoneOrderResponse orderSell(long price, float qty, Currency currency)
			throws UnsupportedEncodingException {
		return commonOrder("/order/limit_sell/", price, qty, currency);
	}

	@Override
	public CoinoneMyCompleteOrderResponse myCompleteOrder(Currency currency) throws UnsupportedEncodingException {
		String encodedPayload = makeEncodedPayload(
				new PayLoadCompleteOrder(this.accessToken, currency.name.toUpperCase()));
		String signature = makeSignature(encodedPayload);
		String response = httpClient.doPost(makeHeader(encodedPayload, signature),
				new StringBuilder().append(API_V2_URL).append("/order/complete_orders").toString(), encodedPayload);

		return GSON.fromJson(response, CoinoneMyCompleteOrderResponse.class);
	}

}
