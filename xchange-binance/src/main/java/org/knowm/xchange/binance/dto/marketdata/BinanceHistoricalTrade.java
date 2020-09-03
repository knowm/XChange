package org.knowm.xchange.binance.dto.marketdata;

import java.math.BigDecimal;

import org.knowm.xchange.currency.CurrencyPair;

public class BinanceHistoricalTrade {

	private final long id;
	private final BigDecimal price;
	private final BigDecimal qty;
	private final BigDecimal quoteQty;
	private final long time;
	private final boolean isBuyerMaker;
	private final boolean isBestMatch;
	private final CurrencyPair pair;

	public BinanceHistoricalTrade(CurrencyPair pair, Object[] obj) {
		this.pair = pair;
		this.id = Long.valueOf(obj[0].toString());
		this.price = new BigDecimal(obj[1].toString());
		this.qty = new BigDecimal(obj[2].toString());
		this.quoteQty = new BigDecimal(obj[3].toString());
		this.time = Long.valueOf(obj[4].toString());
		this.isBuyerMaker = Boolean.valueOf(obj[5].toString());
		this.isBestMatch = Boolean.valueOf(obj[6].toString());
	}

	public long getId() {
		return id;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public BigDecimal getQty() {
		return qty;
	}

	public BigDecimal getQuoteQty() {
		return quoteQty;
	}

	public long getTime() {
		return time;
	}

	public boolean isBuyerMaker() {
		return isBuyerMaker;
	}

	public boolean isBestMatch() {
		return isBestMatch;
	}

	public CurrencyPair getPair() {
		return pair;
	}

}
