package com.indrajch.investment.mock.api;

import java.io.UnsupportedEncodingException;

import com.indrajch.investment.mock.api.coinone.CoinoneBalanceResponse;
import com.indrajch.investment.mock.api.coinone.CoinoneMyCompleteOrderResponse;
import com.indrajch.investment.mock.api.coinone.CoinoneOrderResponse;
import com.indrajch.investment.mock.api.coinone.CoinoneTradesResponse;
import com.indrajch.investment.mock.data.Currency;

public interface API {
	/**
	 * 잔액 조회
	 * 
	 * @return #CoinoneDailyBalanceResponse
	 * @throws UnsupportedEncodingException
	 */
	CoinoneBalanceResponse getBalance() throws UnsupportedEncodingException;

	/**
	 * 최근 완료 주문
	 * 
	 * @param currency
	 * @return #CoinoneTradesResponse
	 */
	CoinoneTradesResponse getTrades(Currency currency);

	/**
	 * 매수 주문
	 * 
	 * @param price
	 * @param qty
	 * @param currency
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	CoinoneOrderResponse orderBuy(long price, float qty, Currency currency) throws UnsupportedEncodingException;

	/**
	 * 매도 주문
	 * 
	 * @param price
	 * @param qty
	 * @param currency
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	CoinoneOrderResponse orderSell(long price, float qty, Currency currency) throws UnsupportedEncodingException;

	/**
	 * 나의 주문 완료 확인
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	CoinoneMyCompleteOrderResponse myCompleteOrder(Currency currency) throws UnsupportedEncodingException;

}
