package org.knowm.xchange.ccex;

import java.io.IOException;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.ccex.service.pooling.CCEXMarketDataService;
import org.knowm.xchange.utils.nonce.AtomicLongIncrementalTime2013NonceFactory;

import si.mazi.rescu.SynchronizedValueFactory;

public class CCEXExchange extends BaseExchange implements Exchange{

	private SynchronizedValueFactory<Long> nonceFactory = new AtomicLongIncrementalTime2013NonceFactory();
	/*
	@Override
	  public StreamingExchangeService getStreamingExchangeService(ExchangeStreamingConfiguration configuration) {

	    if (configuration instanceof CCEXStreamingConfiguration) {
	      return new CCEXPusherService(this, (CCEXStreamingConfiguration) configuration);
	    }

	    throw new IllegalArgumentException("C-CEX only supports CCEXStreamingConfiguration");
	  }
	*/
	@Override
	public SynchronizedValueFactory<Long> getNonceFactory() {
		return nonceFactory;
	}

	@Override
	public ExchangeSpecification getDefaultExchangeSpecification() {
		ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
	    exchangeSpecification.setSslUri("https://c-cex.com");
	    exchangeSpecification.setHost("c-cex.com");
	    exchangeSpecification.setExchangeName("C-CEX");

	    return exchangeSpecification;
	}

	@Override
	protected void initServices() {
		this.pollingMarketDataService = new CCEXMarketDataService(this);
		this.pollingTradeService = null;
	    this.pollingAccountService = null;
	}
	
	@Override
	  public void remoteInit() throws IOException {
		
	}

}
