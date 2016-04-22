package org.knowm.xchange.btcchina;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.btcchina.service.polling.BTCChinaAccountService;
import org.knowm.xchange.btcchina.service.polling.BTCChinaMarketDataService;
import org.knowm.xchange.btcchina.service.polling.BTCChinaTradeService;
import org.knowm.xchange.btcchina.service.streaming.BTCChinaSocketIOService;
import org.knowm.xchange.btcchina.service.streaming.BTCChinaStreamingConfiguration;
import org.knowm.xchange.service.streaming.ExchangeStreamingConfiguration;
import org.knowm.xchange.service.streaming.StreamingExchangeService;
import org.knowm.xchange.utils.nonce.CurrentNanosecondTimeIncrementalNonceFactory;

import si.mazi.rescu.SynchronizedValueFactory;

public class BTCChinaExchange extends BaseExchange implements Exchange {

  // move to metadata

  public static final String WEBSOCKET_URI_KEY = "websocket.uri";

  public static final String ALL_MARKET = "ALL";
  public static final String DEFAULT_MARKET = "BTCCNY";

  /**
   * 2 decimals for BTC/CNY and LTC/CNY markets.
   */
  public static final int CNY_SCALE = 2;

  /**
   * 4 decimals for LTC/BTC market.
   */
  public static final int BTC_SCALE = 4;

  private SynchronizedValueFactory<Long> nonceFactory = new CurrentNanosecondTimeIncrementalNonceFactory();

  @Override
  protected void initServices() {
    this.pollingTradeService = new BTCChinaTradeService(this);
    this.pollingAccountService = new BTCChinaAccountService(this);

    // TODO use exchangeSpecificParameters
    exchangeSpecification.setSslUri("https://data.btcchina.com");
    this.pollingMarketDataService = new BTCChinaMarketDataService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.btcchina.com");
    exchangeSpecification.setHost("api.btcchina.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("BTCChina");
    exchangeSpecification.setExchangeDescription("BTCChina is a Bitcoin exchange located in China.");
    exchangeSpecification.setExchangeSpecificParametersItem(WEBSOCKET_URI_KEY, "https://websocket.btcchina.com");
    return exchangeSpecification;
  }

  @Override
  public StreamingExchangeService getStreamingExchangeService(ExchangeStreamingConfiguration configuration) {

    final BTCChinaStreamingConfiguration btcchinaStreamingConfiguration;

    if (configuration == null) {
      btcchinaStreamingConfiguration = new BTCChinaStreamingConfiguration();
    } else if (configuration instanceof BTCChinaStreamingConfiguration) {
      btcchinaStreamingConfiguration = (BTCChinaStreamingConfiguration) configuration;
    } else {
      throw new IllegalArgumentException("BTCChina only supports BTCChinaStreamingConfiguration");
    }

    return new BTCChinaSocketIOService(this, btcchinaStreamingConfiguration);
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }
}
