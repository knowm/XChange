package com.xeiam.xchange.okcoin.service.polling;

import java.io.IOException;
import java.util.Map;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.okcoin.FuturesContract;
import com.xeiam.xchange.okcoin.OkCoin;
import com.xeiam.xchange.okcoin.OkCoinAdapters;
import com.xeiam.xchange.okcoin.dto.marketdata.OkCoinDepth;
import com.xeiam.xchange.okcoin.dto.marketdata.OkCoinTickerResponse;
import com.xeiam.xchange.okcoin.dto.marketdata.OkCoinTrade;

public class OkCoinMarketDataServiceRaw extends OkCoinBasePollingService {

	private final OkCoin okCoin;

	/**
	 * Constructor
	 *
	 * @param exchangeSpecification
	 */
	public OkCoinMarketDataServiceRaw(ExchangeSpecification exchangeSpecification) {

		super(exchangeSpecification);

		Map<String, Object> specific = exchangeSpecification.getExchangeSpecificParameters();
		okCoin = RestProxyFactory.createProxy(OkCoin.class, useIntl ? (String) specific.get("Intl_SslUri") : exchangeSpecification.getSslUri());
	}

	public OkCoinTickerResponse getTicker(CurrencyPair currencyPair) throws IOException {

		return okCoin.getTicker("1", OkCoinAdapters.adaptSymbol(currencyPair));
	}
	
	 public OkCoinTickerResponse getFuturesTicker(CurrencyPair currencyPair, FuturesContract prompt) throws IOException {

	    return okCoin.getFuturesTicker(OkCoinAdapters.adaptSymbol(currencyPair), prompt.getName());
	  }


	public OkCoinDepth getDepth(CurrencyPair currencyPair) throws IOException {

		return okCoin.getDepth("1", OkCoinAdapters.adaptSymbol(currencyPair));
	}

	public OkCoinDepth getFuturesDepth(CurrencyPair currencyPair, FuturesContract prompt) throws IOException {

		return okCoin.getFuturesDepth("1", OkCoinAdapters.adaptSymbol(currencyPair), prompt.getName().toLowerCase());
	}

	public OkCoinTrade[] getTrades(CurrencyPair currencyPair) throws IOException {

		return okCoin.getTrades("1", OkCoinAdapters.adaptSymbol(currencyPair));
	}

	public OkCoinTrade[] getTrades(CurrencyPair currencyPair, long since) throws IOException {

		return okCoin.getTrades("1", OkCoinAdapters.adaptSymbol(currencyPair), since);
	}

	public OkCoinTrade[] getFuturesTrades(CurrencyPair currencyPair, FuturesContract prompt) throws IOException {

		return okCoin.getFuturesTrades("1", OkCoinAdapters.adaptSymbol(currencyPair), prompt.getName().toLowerCase());
	}

}
