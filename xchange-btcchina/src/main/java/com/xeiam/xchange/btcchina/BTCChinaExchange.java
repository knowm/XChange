package com.xeiam.xchange.btcchina;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btcchina.service.BTCChinaTonceFactory;
import com.xeiam.xchange.btcchina.service.polling.BTCChinaAccountService;
import com.xeiam.xchange.btcchina.service.polling.BTCChinaMarketDataService;
import com.xeiam.xchange.btcchina.service.polling.BTCChinaTradeService;
import com.xeiam.xchange.btcchina.service.streaming.BTCChinaSocketIOService;
import com.xeiam.xchange.btcchina.service.streaming.BTCChinaStreamingConfiguration;
import com.xeiam.xchange.service.streaming.ExchangeStreamingConfiguration;
import com.xeiam.xchange.service.streaming.StreamingExchangeService;

/**
 * <p>
 * Exchange implementation to provide the following to applications:
 * </p>
 * <ul>
 * <li>A wrapper for the BTCChina exchange API</li>
 * </ul>
 */
public class BTCChinaExchange extends BaseExchange implements Exchange {

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

  private final BTCChinaTonceFactory tonceFactory = new BTCChinaTonceFactory();

  /**
   * Default constructor for ExchangeFactory
   */
  public BTCChinaExchange() {

  }

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);
    this.pollingTradeService = new BTCChinaTradeService(exchangeSpecification, tonceFactory);
    this.pollingAccountService = new BTCChinaAccountService(exchangeSpecification, tonceFactory);
    exchangeSpecification.setSslUri("https://data.btcchina.com");
    this.pollingMarketDataService = new BTCChinaMarketDataService(exchangeSpecification, tonceFactory);
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
    }
    else if (configuration instanceof BTCChinaStreamingConfiguration) {
      btcchinaStreamingConfiguration = (BTCChinaStreamingConfiguration) configuration;
    }
    else {
      throw new IllegalArgumentException("BTCChina only supports BTCChinaStreamingConfiguration");
    }

    return new BTCChinaSocketIOService(getExchangeSpecification(), btcchinaStreamingConfiguration);
  }

}
