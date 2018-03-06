package com.indrajch.investment.mock.data;

public enum Currency {
	BTC("btc"), //
	BCH("bch"), //
	ETH("eth"), //
	ETC("etc"), //
	XRP("xrp"), //
	QTUM("qtum"), //
	LTC("ltc"), //
	IOTA("iota"), //
	BTG("btg"), //
	;

	public String name;

	private Currency(String name) {
		this.name = name;
	}

	public static Currency findOfName(String name) {
		String lowerCaseName = name.toLowerCase();
		for (Currency curr : Currency.values()) {
			if (lowerCaseName.equals(curr.name)) {
				return curr;
			}
		}

		return null;
	}
}
