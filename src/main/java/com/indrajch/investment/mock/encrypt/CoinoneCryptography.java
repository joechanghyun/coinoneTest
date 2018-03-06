package com.indrajch.investment.mock.encrypt;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class CoinoneCryptography implements Cryptography {

	private static final String HMAC_SHA512 = "HmacSHA512";

	private final Mac cipher;

	public CoinoneCryptography(String secretKey)
			throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
		SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes("UTF-8"), HMAC_SHA512);
		this.cipher = Mac.getInstance(HMAC_SHA512);
		this.cipher.init(keySpec);
	}

	@Override
	public String cryptographyData(String data) throws IllegalStateException, UnsupportedEncodingException {
		return EncryptUtils.bytesToHex(this.cipher.doFinal(data.getBytes("UTF-8")));
	}
}
