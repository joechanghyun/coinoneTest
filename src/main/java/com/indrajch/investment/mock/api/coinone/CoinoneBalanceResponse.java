package com.indrajch.investment.mock.api.coinone;

public class CoinoneBalanceResponse extends CoinoneResponse {
	private BalanceAttribute btc;

	private BalanceAttribute krw;

	public CoinoneBalanceResponse(CoinoneErrorCode errorCode, String timestamp, String result, BalanceAttribute btc,
			BalanceAttribute krw) {
		super(errorCode, timestamp, result);
		this.btc = btc;
		this.krw = krw;
	}

	public BalanceAttribute getBtc() {
		return btc;
	}

	public BalanceAttribute getKrw() {
		return krw;
	}

	public static class BalanceAttribute {
		private float avail;
		private float balance;

		/**
		 * 생성자
		 * 
		 * @param avail
		 * @param balance
		 */
		public BalanceAttribute(float avail, float balance) {
			super();
			this.avail = avail;
			this.balance = balance;
		}

		public float getAvail() {
			return avail;
		}

		public float getBalance() {
			return balance;
		}
	}

}
