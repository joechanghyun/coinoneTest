package com.indrajch.investment.mock.api;

import java.io.UnsupportedEncodingException;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

import com.indrajch.investment.mock.Status;
import com.indrajch.investment.mock.api.coinone.CoinoneCompleteOrder;
import com.indrajch.investment.mock.api.coinone.CoinoneErrorCode;
import com.indrajch.investment.mock.api.coinone.CoinoneMyCompleteOrderResponse;
import com.indrajch.investment.mock.api.coinone.CoinoneOrderResponse;
import com.indrajch.investment.mock.api.coinone.CoinoneResponse;
import com.indrajch.investment.mock.data.Currency;

@Aspect
public class CoinoneAspect {
	// 10분
	private static final int BLOCK_DELAY = 10 * 60 * 1000;

	@Autowired
	private API coinoneAPI;

	/**
	 * API 가 1분에 90번 호출을 막고, error의 경우 로그를 남김
	 * 
	 * @param joinPoint
	 * @param response
	 * @throws InterruptedException
	 */
	@AfterReturning(pointcut = "execution(* com.indrajch.investment.mock.api.CoinoneAPI.*(..))", returning = "response")
	public void executionAllApiAfterReturning(CoinoneResponse response) throws InterruptedException {
		if (response == null || response.getErrorCode() == null) {
			return;
		}

		System.out.println(response.getClass().getName() + " response error " + response.getErrorCode().getCode()
				+ " : " + response.getErrorCode().getDescription());

		if (CoinoneErrorCode.BlockedUserAccess == response.getErrorCode()) {
			Thread.sleep(BLOCK_DELAY);
		}
	}

	@AfterReturning(pointcut = "execution(* com.indrajch.investment.mock.api.CoinoneAPI.order*(..))", returning = "orderResponse")
	public void executionOrderApiAfterReturning(CoinoneOrderResponse orderResponse) throws InterruptedException {
		if (orderResponse.getErrorCode() != null || !Status.SUCCESS.equals(orderResponse.getResult())) {
			return;
		}
		int maxWaitCount = 50;
		try {
			for (int i = 0; i < maxWaitCount; i++) {
				CoinoneMyCompleteOrderResponse completeOrderResponse = this.coinoneAPI.myCompleteOrder(Currency.BTC);
				for (CoinoneCompleteOrder completeOrder : completeOrderResponse.getCompleteOrders()) {
					if (orderResponse.getOrderId().equals(completeOrder.getOrderId())) {
						return;
					}
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}
