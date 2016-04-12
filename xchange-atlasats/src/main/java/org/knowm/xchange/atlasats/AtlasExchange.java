package org.knowm.xchange.atlasats;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.atlasats.services.AtlasPollingAccountService;
import org.knowm.xchange.atlasats.services.AtlasPollingMarketDataService;
import org.knowm.xchange.atlasats.services.AtlasPollingTradeService;
import org.knowm.xchange.atlasats.services.AtlasStreamingExchangeService;

public class AtlasExchange extends BaseExchange implements Exchange {

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification specification = new AtlasExchangeSpecification();
    return specification;
  }

  public void applySpecification(AtlasExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);
    this.pollingAccountService = new AtlasPollingAccountService(exchangeSpecification);
    this.pollingTradeService = new AtlasPollingTradeService(exchangeSpecification);
    this.pollingMarketDataService = new AtlasPollingMarketDataService(exchangeSpecification);
    this.streamingExchangeService = new AtlasStreamingExchangeService(exchangeSpecification, exchangeSpecification.getStreamingConfiguration());
  }

}
