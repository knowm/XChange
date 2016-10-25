package org.knowm.xchange.livecoin;

import java.io.IOException;
import java.util.List;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.livecoin.dto.marketdata.LIVECOINRestriction;
import org.knowm.xchange.livecoin.service.LIVECOINMarketDataService;
import org.knowm.xchange.livecoin.service.LIVECOINMarketDataServiceRaw;
import org.knowm.xchange.utils.ObjectMapperHelper;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;

import si.mazi.rescu.SynchronizedValueFactory;

public class LIVECOINExchange extends BaseExchange implements Exchange {

	private SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

	@Override
	public SynchronizedValueFactory<Long> getNonceFactory() {
		return nonceFactory;
	}

	@Override
	public ExchangeSpecification getDefaultExchangeSpecification() {
		ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
	    exchangeSpecification.setSslUri("https://api.livecoin.net");
	    exchangeSpecification.setHost("api.livecoin.net");
	    exchangeSpecification.setPort(80);
	    exchangeSpecification.setExchangeName("Livecoin");
	    exchangeSpecification.setExchangeDescription("Livecoin - A convenient way to buy and sell Bitcoin");
	    return exchangeSpecification;
	}

	@Override
	protected void initServices() {
		this.pollingMarketDataService = new LIVECOINMarketDataService(this);
	    this.pollingAccountService = null; // new LIVECOINAccountService(this);
	    this.pollingTradeService = null; // new LIVECOINTradeService(this);
	}
	
	@Override
	  public void remoteInit() throws IOException {
	    List<LIVECOINRestriction> products = ((LIVECOINMarketDataServiceRaw) pollingMarketDataService).getConbaseExProducts();
	    exchangeMetaData = LIVECOINAdapters.adaptToExchangeMetaData(exchangeMetaData, products);
	        System.out.println("JSON: " + ObjectMapperHelper.toJSON(exchangeMetaData));
	  }
}
