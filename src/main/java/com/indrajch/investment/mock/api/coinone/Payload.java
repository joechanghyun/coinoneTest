package com.indrajch.investment.mock.api.coinone;

import java.io.UnsupportedEncodingException;
import java.time.ZonedDateTime;

import org.springframework.util.Base64Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

public class Payload {

	private static final Gson GSON = new GsonBuilder().create();

	@SerializedName("access_token")
	private String accessToken;

	@SerializedName("nonce")
	private int nonce;

	public Payload(String accessToken) {
		this.accessToken = accessToken;
		this.nonce = (int) ZonedDateTime.now().toEpochSecond();
	}

	public String convertionToJsonBase64encode() throws UnsupportedEncodingException {
		return new String(Base64Utils.encode(GSON.toJson(this).getBytes("UTF-8")), "UTF-8");
	}
}
