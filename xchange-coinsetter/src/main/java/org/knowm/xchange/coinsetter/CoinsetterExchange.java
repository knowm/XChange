package org.knowm.xchange.coinsetter;

import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.coinsetter.service.polling.CoinsetterAccountService;
import org.knowm.xchange.coinsetter.service.polling.CoinsetterMarketDataService;
import org.knowm.xchange.coinsetter.service.polling.CoinsetterTradeService;
import org.knowm.xchange.coinsetter.service.streaming.CoinsetterSocketIOService;
import org.knowm.xchange.coinsetter.service.streaming.CoinsetterStreamingConfiguration;
import org.knowm.xchange.service.streaming.ExchangeStreamingConfiguration;
import org.knowm.xchange.service.streaming.StreamingExchangeService;

import si.mazi.rescu.SynchronizedValueFactory;

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

  public static final String SESSION_HEARTBEAT_INTERVAL_KEY = "session.heartbeat.interval";
  public static final String SESSION_HEARTBEAT_MAX_FAILURE_TIMES_KEY = "session.failure";
  public static final String SESSION_IP_ADDRESS_KEY = "session.ipAddress";
  public static final String SESSION_LOCK_KEY = "session.lock";
  public static final String SESSION_KEY = "session";

  /**
   * XChange do not support multiple sub accounts, so specify one account using to trade. If does not specified, the first one in from the account
   * list will be used.
   */
  public static final String ACCOUNT_UUID_KEY = "account";

  @Override
  protected void initServices() {
    this.pollingMarketDataService = new CoinsetterMarketDataService(this);
    this.pollingAccountService = new CoinsetterAccountService(this);
    this.pollingTradeService = new CoinsetterTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(getClass());
    exchangeSpecification.setSslUri("https://api.coinsetter.com/v1");
    exchangeSpecification.setHost("api.coinsetter.com");
    exchangeSpecification.setExchangeName("Coinsetter");
    exchangeSpecification.setExchangeDescription(
        "Coinsetter is a New York City based, venture capital funded bitcoin exchange that is dedicated to making bitcoin safe and reliable for active users.");
    exchangeSpecification.setExchangeSpecificParametersItem(WEBSOCKET_URI_KEY, "https://plug.coinsetter.com:3000");

    // default heartbeat interval is 30 seconds.
    exchangeSpecification.setExchangeSpecificParametersItem(SESSION_HEARTBEAT_INTERVAL_KEY, 30000L);

    exchangeSpecification.setExchangeSpecificParametersItem(SESSION_HEARTBEAT_MAX_FAILURE_TIMES_KEY, 3);
    exchangeSpecification.setExchangeSpecificParametersItem(SESSION_IP_ADDRESS_KEY, "0.0.0.0");
    exchangeSpecification.setExchangeSpecificParametersItem(SESSION_LOCK_KEY, new ReentrantReadWriteLock());
    return exchangeSpecification;
  }

  @Override
  public StreamingExchangeService getStreamingExchangeService(ExchangeStreamingConfiguration configuration) {

    final CoinsetterStreamingConfiguration coinsetterStreamingConfiguration;

    if (configuration == null) {
      coinsetterStreamingConfiguration = new CoinsetterStreamingConfiguration();
      coinsetterStreamingConfiguration.addAllMarketDataEvents();
    } else if (configuration instanceof CoinsetterStreamingConfiguration) {
      coinsetterStreamingConfiguration = (CoinsetterStreamingConfiguration) configuration;
    } else {
      throw new IllegalArgumentException("Coinsetter only supports CoinsetterStreamingConfiguration");
    }

    return new CoinsetterSocketIOService(this, coinsetterStreamingConfiguration);
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    // Coinsetter uses it's own session authentication scheme and does not use a nonce
    return null;
  }
}
