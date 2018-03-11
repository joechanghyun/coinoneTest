package com.indrajch.investment.mock.api.coinone;

public class PayLoadOrder extends Payload {

	/*
	 * KRW price
	 */
	private long price;

	private float qty;

	private String currency;

	public PayLoadOrder(String accessToken, long price, float qty, String currency) {
		super(accessToken);
		this.price = price;
		this.qty = qty;
		this.currency = currency;
	}

	public long getPrice() {
		return price;
	}

	public float getQty() {
		return qty;
	}

	public String getCurrency() {
		return currency;
	}

}
