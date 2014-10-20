package com.xeiam.xchange.coinsetter;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.coinsetter.service.polling.CoinsetterMarketDataService;
import com.xeiam.xchange.coinsetter.service.streaming.CoinsetterSocketIOService;
import com.xeiam.xchange.coinsetter.service.streaming.CoinsetterStreamingConfiguration;
import com.xeiam.xchange.service.streaming.ExchangeStreamingConfiguration;
import com.xeiam.xchange.service.streaming.StreamingExchangeService;

/**
 * Coinsetter exchange.
 */
public class CoinsetterExchange extends BaseExchange implements Exchange {

  public static final String EXCHANGE_SMART = "SMART";
  public static final String EXCHANGE_COINSETTER = "COINSETTER";
  public static final String DEFAULT_EXCHANGE = EXCHANGE_SMART;

  public static final int DEFAULT_DEPTH = 10;

  public static final String DEPTH_FORMAT_PAIRED = "PAIRED";
  public static final String DEPTH_FORMAT_LIST = "LIST";
  public static final String DEFAULT_DEPTH_FORMAT = DEPTH_FORMAT_PAIRED;

  public static final String WEBSOCKET_URI_KEY = "websocket.uri";

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);
    this.pollingMarketDataService = new CoinsetterMarketDataService(exchangeSpecification);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(getClass());
    exchangeSpecification.setSslUri("https://api.coinsetter.com/v1");
    exchangeSpecification.setHost("api.coinsetter.com");
    exchangeSpecification.setExchangeName("Coinsetter");
    exchangeSpecification.setExchangeDescription("Coinsetter is a New York City based, venture capital funded bitcoin exchange that is dedicated to making bitcoin safe and reliable for active users.");
    exchangeSpecification.setExchangeSpecificParametersItem(WEBSOCKET_URI_KEY, "https://plug.coinsetter.com:3000");
    return exchangeSpecification;
  }

  @Override
  public StreamingExchangeService getStreamingExchangeService(ExchangeStreamingConfiguration configuration) {

    final CoinsetterStreamingConfiguration coinsetterStreamingConfiguration;

    if (configuration == null) {
      coinsetterStreamingConfiguration = new CoinsetterStreamingConfiguration();
      coinsetterStreamingConfiguration.addAllMarketDataEvents();
    }
    else if (configuration instanceof CoinsetterStreamingConfiguration) {
      coinsetterStreamingConfiguration = (CoinsetterStreamingConfiguration) configuration;
    }
    else {
      throw new IllegalArgumentException("Coinsetter only supports CoinsetterStreamingConfiguration");
    }

    return new CoinsetterSocketIOService(getExchangeSpecification(), coinsetterStreamingConfiguration);
  }

}
