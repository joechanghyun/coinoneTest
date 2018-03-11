package com.indrajch.investment.mock.api.coinone;

public class PayLoadCompleteOrder extends Payload {
	private String currency;

	public PayLoadCompleteOrder(String accessToken, String currency) {
		super(accessToken);
		this.currency = currency;
	}

	public String getCurrency() {
		return currency;
	}
}
