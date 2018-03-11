package com.indrajch.investment.mock.api.coinone;

public class CoinoneCompleteOrder {
	private String timestamp;
	private long price;
	private double qty;
	private String type;
	private float feeRate;
	private float fee;
	private String orderId;

	/**
	 * 생성자
	 * 
	 * @param timestamp
	 * @param price
	 * @param qty
	 */
	public CoinoneCompleteOrder(String timestamp, long price, double qty) {
		this(timestamp, price, qty, "", 0f, 0f, "");
	}

	/**
	 * 생성자
	 * 
	 * @param timestamp
	 * @param price
	 * @param qty
	 * @param type
	 * @param feeRate
	 * @param fee
	 * @param orderId
	 */
	public CoinoneCompleteOrder(String timestamp, long price, double qty, String type, float feeRate, float fee,
			String orderId) {
		super();
		this.timestamp = timestamp;
		this.price = price;
		this.qty = qty;
		this.type = type;
		this.feeRate = feeRate;
		this.fee = fee;
		this.orderId = orderId;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public long getPrice() {
		return price;
	}

	public double getQty() {
		return qty;
	}

	public String getType() {
		return type;
	}

	public float getFeeRate() {
		return feeRate;
	}

	public float getFee() {
		return fee;
	}

	public String getOrderId() {
		return orderId;
	}

}
