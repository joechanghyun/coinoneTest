package com.indrajch.investment.mock;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.indrajch.investment.mock.api.API;
import com.indrajch.investment.mock.api.CoinoneAPI;
import com.indrajch.investment.mock.encrypt.CoinoneCryptography;
import com.indrajch.investment.mock.encrypt.Cryptography;
import com.indrajch.investment.mock.pool.http.HttpClient;
import com.indrajch.investment.mock.pool.http.HttpClientImpl;
import com.indrajch.investment.mock.run.CoinoneInvestmentRun;
import com.indrajch.investment.mock.run.InvestmentRun;

@Configuration
public class MockInvestmentConfiguration {
	/**
	 * Bean HttpClient
	 * 
	 * @param connectionTime
	 * @param socketTime
	 * @return
	 */
	@Bean
	public HttpClient httpClient(@Value("${http.request.connectionTime}") int connectionTime,
			@Value("${http.request.socketTime}") int socketTime) {
		return new HttpClientImpl(connectionTime, socketTime);
	}

	@Bean(name = "coinoneInvestmentRun")
	public InvestmentRun coinoneInvestmentRun() {
		return new CoinoneInvestmentRun();
	}

	@Bean(name = "coinoneAPI")
	public API coinoneAPI(@Value("${coinone.access.token}") String accessToken,
			@Value("${coinone.secret.key}") String secretKey) {
		return new CoinoneAPI(accessToken, secretKey);
	}

	@Bean(name = "coinoneCryptography")
	public Cryptography coinoneCryptography(@Value("${coinone.secret.key}") String secretKey)
			throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
		return new CoinoneCryptography(secretKey);
	}

}
