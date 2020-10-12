package org.knowm.xchange.bitbns;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitbns.service.BitbnsMarketDataService;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;

import lombok.Value;
import si.mazi.rescu.SynchronizedValueFactory;

public class BitbnsExchange extends BaseExchange implements Exchange  {

	private final SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();
	
	@Override
	public SynchronizedValueFactory<Long> getNonceFactory() {
		 return nonceFactory;
	}

	@Override
	public ExchangeSpecification getDefaultExchangeSpecification() {
	   ExchangeSpecification exchangeSpecification =
		        new ExchangeSpecification(this.getClass().getCanonicalName());
		    exchangeSpecification.setSslUri("https://api.bitbns.com/api/trade");
		    exchangeSpecification.setHost("api.bitbns.com");
		    exchangeSpecification.setPort(80);
		    exchangeSpecification.setExchangeName("bitbns");
		    exchangeSpecification.setExchangeSpecificParametersItem("X-BITBNS-APIKEY", "F4D525935E2FC19900C89DB649537F0E");
		    exchangeSpecification.setExchangeDescription(
		        "Bitbns Exchange custom Exchange");
		    return exchangeSpecification;
	}

	@Override
	protected void initServices() {
		 this.marketDataService = new BitbnsMarketDataService(this);
	}

}
