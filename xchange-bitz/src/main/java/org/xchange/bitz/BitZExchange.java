package org.xchange.bitz;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.xchange.bitz.service.BitZMarketDataService;

import si.mazi.rescu.SynchronizedValueFactory;

public class BitZExchange extends BaseExchange implements Exchange {

	@Override
	protected void initServices() {
		this.marketDataService = new BitZMarketDataService(this);
	}

	@Override
	public ExchangeSpecification getDefaultExchangeSpecification() {

	    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
	    exchangeSpecification.setSslUri("https://www.bit-z.com");
	    exchangeSpecification.setHost("http://www.bit-z.com");
	    exchangeSpecification.setPort(80);
	    exchangeSpecification.setExchangeName("Bit-Z");
	    exchangeSpecification.setExchangeDescription("Bit-Z is a Bitcoin exchange registered in Hong Kong.");
	    return exchangeSpecification;
	}
	
	//TODO: Implement Method
	@Override
	public SynchronizedValueFactory<Long> getNonceFactory() {
	    return null;
	}
}
