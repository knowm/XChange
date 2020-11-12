package org.knowm.xchange.wazirx;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;
import org.knowm.xchange.wazirx.service.WazirxMarketDataService;

import si.mazi.rescu.SynchronizedValueFactory;

public class WazirxExchange extends BaseExchange implements Exchange  {

	private final SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();
	
	@Override
	public SynchronizedValueFactory<Long> getNonceFactory() {
		 return nonceFactory;
	}

	@Override
	public ExchangeSpecification getDefaultExchangeSpecification() {
	   ExchangeSpecification exchangeSpecification =
		        new ExchangeSpecification(this.getClass().getCanonicalName());
		    exchangeSpecification.setSslUri("https://api.wazirx.com/api");
		    exchangeSpecification.setHost("api.wazirx.com");
		    exchangeSpecification.setPort(80);
		    exchangeSpecification.setExchangeName("Wazirx");
		    exchangeSpecification.setExchangeDescription(
		        "Wazirx Exchange custom Exchange");
		    return exchangeSpecification;
	}

	@Override
	protected void initServices() {
		 this.marketDataService = new WazirxMarketDataService(this);
	}

}
