package com.xeiam.xchange.cryptsy.service.polling;

import com.xeiam.xchange.currency.CurrencyPair;

public class CryptsyCurrencyMarket {

	public CurrencyPair pair;

	public CryptsyCurrencyMarket(CurrencyPair pair, int marketId) {
		super();
		this.pair = pair;
		this.marketId = marketId;
	}

	public int marketId;

	public CurrencyPair getPair() {
		return pair;
	}

	public void setPair(CurrencyPair pair) {
		this.pair = pair;
	}

	public int getMarketId() {
		return marketId;
	}

	public void setMarketId(int marketId) {
		this.marketId = marketId;
	}

}
