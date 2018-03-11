package com.indrajch.investment.mock.run;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;

import com.indrajch.investment.mock.Status;
import com.indrajch.investment.mock.api.API;
import com.indrajch.investment.mock.api.coinone.CoinoneBalanceResponse;
import com.indrajch.investment.mock.api.coinone.CoinoneCompleteOrder;
import com.indrajch.investment.mock.api.coinone.CoinoneTradesResponse;
import com.indrajch.investment.mock.data.Currency;

public class CoinoneInvestmentRun implements InvestmentRun {

	private static final int DELAY = 1000;

	@Autowired
	private API coinoneAPI;

	@Override
	public void run() {
		try {
			while (true) {
				InvestmentResolve result = investmentResolvor(this.coinoneAPI.getTrades(Currency.BTC));
				if (result == null) {
					continue;
				}

				CoinoneBalanceResponse balanceResponse = this.coinoneAPI.getBalance();
				if (0 < balanceResponse.getKrw().getAvail()) {
					this.coinoneAPI.orderBuy(result.getMinimum(),
							balanceResponse.getKrw().getAvail() / result.getMinimum(), Currency.BTC);
				} else if (0 < balanceResponse.getBtc().getAvail()) {
					this.coinoneAPI.orderSell(result.getMaximum(), balanceResponse.getBtc().getAvail(), Currency.BTC);
				} else {
					break;
				}

				Thread.sleep(DELAY);
			}
		} catch (UnsupportedEncodingException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	private InvestmentResolve investmentResolvor(CoinoneTradesResponse coinoneTradesResponse) {
		if (coinoneTradesResponse.getErrorCode() != null || !Status.SUCCESS.equals(coinoneTradesResponse.getResult())) {
			return null;
		}

		long maximum = 0l;
		long minimum = 0l;

		for (CoinoneCompleteOrder order : coinoneTradesResponse.getCompleteOrders()) {
			if (maximum == 0l) {
				maximum = order.getPrice();
				minimum = order.getPrice();
			} else if (maximum < order.getPrice()) {
				maximum = order.getPrice();
			} else if (order.getPrice() < minimum) {
				minimum = order.getPrice();
			}
		}

		return new InvestmentResolve(maximum, minimum);
	}

	private static class InvestmentResolve {
		private long maximum;
		private long minimum;

		public InvestmentResolve(long maximum, long minimum) {
			this.maximum = maximum;
			this.minimum = minimum;
		}

		public long getMaximum() {
			return maximum;
		}

		public long getMinimum() {
			return minimum;
		}
	}

}
