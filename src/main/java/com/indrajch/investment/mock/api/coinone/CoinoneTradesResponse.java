package com.indrajch.investment.mock.api.coinone;

import java.util.List;

import com.indrajch.investment.mock.data.Currency;

public class CoinoneTradesResponse extends CoinoneResponse {

	private Currency currency;
	private List<CoinoneCompleteOrder> completeOrders;

	public CoinoneTradesResponse(CoinoneErrorCode errorCode, String timestamp, String result, Currency currency,
			List<CoinoneCompleteOrder> completeOrders) {
		super(errorCode, timestamp, result);
		this.currency = currency;
		this.completeOrders = completeOrders;
	}

	public Currency getCurrency() {
		return currency;
	}

	public List<CoinoneCompleteOrder> getCompleteOrders() {
		return completeOrders;
	}

}
