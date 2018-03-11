package com.indrajch.investment.mock.api.coinone;

public class CoinoneOrderResponse extends CoinoneResponse {

	private String orderId;

	public CoinoneOrderResponse(CoinoneErrorCode errorCode, String timestamp, String result, String orderId) {
		super(errorCode, timestamp, result);
		this.orderId = orderId;
	}

	public String getOrderId() {
		return this.orderId;
	}

}
