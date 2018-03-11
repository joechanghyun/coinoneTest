package com.indrajch.investment.mock.api.coinone;

public abstract class CoinoneResponse {
	private CoinoneErrorCode errorCode;
	private String timestamp;
	private String result;

	/**
	 * 생성자
	 * 
	 * @param errorCode
	 * @param timestamp
	 * @param result
	 */
	public CoinoneResponse(CoinoneErrorCode errorCode, String timestamp, String result) {
		super();
		this.errorCode = errorCode;
		this.timestamp = timestamp;
		this.result = result;
	}

	public CoinoneErrorCode getErrorCode() {
		return errorCode;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public String getResult() {
		return result;
	}
}
