package org.knowm.xchange.mexbt;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.mexbt.service.polling.MeXBTAccountService;
import org.knowm.xchange.mexbt.service.polling.MeXBTMarketDataService;
import org.knowm.xchange.mexbt.service.polling.MeXBTTradeService;
import org.knowm.xchange.mexbt.service.streaming.MeXBTExchangeStreamingConfiguration;
import org.knowm.xchange.mexbt.service.streaming.MeXBTStreamingService;
import org.knowm.xchange.service.streaming.ExchangeStreamingConfiguration;
import org.knowm.xchange.service.streaming.StreamingExchangeService;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;

import si.mazi.rescu.SynchronizedValueFactory;

public class MeXBTExchange extends BaseExchange implements Exchange {

  public static final String PRIVATE_API_URI_KEY = "private-api";
  public static final String WSS_TICKER_URI_KEY = "wss.ticker";
  public static final String WSS_TRADES_AND_ORDERS_URI_KEY = "wss.trades-and-orders";

  private final SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  @Override
  protected void initServices() {
    this.pollingMarketDataService = new MeXBTMarketDataService(this);
    if (exchangeSpecification.getApiKey() != null && exchangeSpecification.getUserName() != null) {
      this.pollingAccountService = new MeXBTAccountService(this);
      this.pollingTradeService = new MeXBTTradeService(this);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return nonceFactory;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification spec = new ExchangeSpecification(getClass());
    spec.setExchangeName("mexbt");
    spec.setExchangeDescription("Mexican Bitcoin Exchange");
    spec.setSslUri("https://data.mexbt.com");
    spec.setExchangeSpecificParametersItem(PRIVATE_API_URI_KEY, "https://private-api.mexbt.com");
    spec.setExchangeSpecificParametersItem(WSS_TICKER_URI_KEY, "wss://ws.mexbt.com/v1/ticker");
    spec.setExchangeSpecificParametersItem(WSS_TRADES_AND_ORDERS_URI_KEY, "wss://ws.mexbt.com/v1/trades-and-orders");
    return spec;
  }

  @Override
  public StreamingExchangeService getStreamingExchangeService(ExchangeStreamingConfiguration configuration) {
    if (configuration == null || !(configuration instanceof MeXBTExchangeStreamingConfiguration)) {
      return new MeXBTStreamingService(this);
    } else {
      return new MeXBTStreamingService(this, (MeXBTExchangeStreamingConfiguration) configuration);
    }
  }
}
