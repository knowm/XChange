package com.xeiam.xchange.atlasats;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.atlasats.services.AtlasPollingAccountService;
import com.xeiam.xchange.atlasats.services.AtlasPollingMarketDataService;
import com.xeiam.xchange.atlasats.services.AtlasPollingTradeService;
import com.xeiam.xchange.atlasats.services.AtlasStreamingExchangeService;

public class AtlasExchange extends BaseExchange implements Exchange {

	@Override
	public ExchangeSpecification getDefaultExchangeSpecification() {
		ExchangeSpecification specification = new AtlasExchangeSpecification();
		return specification;
	}

	public void applySpecification(
			AtlasExchangeSpecification exchangeSpecification) {
		super.applySpecification(exchangeSpecification);
		this.pollingAccountService = new AtlasPollingAccountService(
				exchangeSpecification);
		this.pollingTradeService = new AtlasPollingTradeService(
				exchangeSpecification);
		this.pollingMarketDataService = new AtlasPollingMarketDataService(
				exchangeSpecification);
		this.streamingExchangeService = new AtlasStreamingExchangeService(
				exchangeSpecification,
				exchangeSpecification.getStreamingConfiguration());
	}

}
