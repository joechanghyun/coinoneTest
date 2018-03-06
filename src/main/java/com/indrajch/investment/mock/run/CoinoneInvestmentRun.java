package com.indrajch.investment.mock.run;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;

import com.indrajch.investment.mock.api.API;

public class CoinoneInvestmentRun implements InvestmentRun {

	@Autowired
	private API coinoneAPI;

	@Override
	public void run() {
		try {
			System.out.println(this.coinoneAPI.getDailyBalance());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}
