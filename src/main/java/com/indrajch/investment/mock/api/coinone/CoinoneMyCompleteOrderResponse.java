package com.indrajch.investment.mock.api.coinone;

import java.util.List;

public class CoinoneMyCompleteOrderResponse extends CoinoneResponse {
	private List<CoinoneCompleteOrder> completeOrders;

	public CoinoneMyCompleteOrderResponse(CoinoneErrorCode errorCode, String timestamp, String result,
			List<CoinoneCompleteOrder> completeOrders) {
		super(errorCode, timestamp, result);
		this.completeOrders = completeOrders;
	}

	public List<CoinoneCompleteOrder> getCompleteOrders() {
		return completeOrders;
	}
}
