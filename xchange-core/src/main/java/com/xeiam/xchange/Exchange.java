package com.xeiam.xchange;

import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.dto.MetaData;
import com.xeiam.xchange.service.polling.account.PollingAccountService;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;
import com.xeiam.xchange.service.streaming.ExchangeStreamingConfiguration;
import com.xeiam.xchange.service.streaming.StreamingExchangeService;

/**
 * <p>
 * Interface to provide the following to applications:
 * </p>
 * <ul>
 * <li>Entry point to the XChange APIs</li>
 * </ul>
 * <p>
 * The consumer is given a choice of a default (no-args) or configured accessor
 * </p>
 */
public interface Exchange {

  /**
   * @return The ExchangeSpecification in use for this exchange
   */
  ExchangeSpecification getExchangeSpecification();

  /**
   * The MetaData defining some semi-static properties of an exchange such as currency pairs, trading fees, etc.
   *
   * @return
   */
  MetaData getMetaData();

  /**
   * The nonce factory used to create a nonce value. Allows services to accept a placeholder that is replaced with generated value just before message
   * is serialized and sent. If a method of a rest accepts ValueFactory as a parameter, it's evaluated, the message is serialized and sent in a single
   * synchronized block.
   *
   * @return
   */
  SynchronizedValueFactory<Long> getNonceFactory();

  /**
   * @return A default ExchangeSpecification to use during the creation process if one is not supplied
   */
  ExchangeSpecification getDefaultExchangeSpecification();

  /**
   * Applies any exchange specific parameters
   *
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  void applySpecification(ExchangeSpecification exchangeSpecification);

  /**
   * <p>
   * A market data service typically consists of a regularly updated list of the available prices for the various symbols
   * </p>
   * <p>
   * This is the non-streaming (blocking) version of the service
   * </p>
   *
   * @return The exchange's market data service
   */
  PollingMarketDataService getPollingMarketDataService();

  /**
   * <p>
   * A market data service typically consists of a regularly updated list of the available prices for the various symbols
   * </p>
   * <p>
   * This is the streaming (non-blocking and event driven) version of the service, and requires an application to provide a suitable implementation of
   * the listener to allow event callbacks to take place.
   * </p>
   *
   * @param configuration The exchange-specific configuration to be applied after creation
   * @return The exchange's "push" market data service
   */
  StreamingExchangeService getStreamingExchangeService(ExchangeStreamingConfiguration configuration);

  /**
   * <p>
   * An trade service typically provides access to trading functionality
   * </p>
   * <p>
   * Typically access is restricted by a secret API key and/or username password authentication which are usually provided in the
   * {@link ExchangeSpecification}
   * </p>
   *
   * @return The exchange's polling trade service
   */
  PollingTradeService getPollingTradeService();

  /**
   * <p>
   * An account service typically provides access to the user's private exchange data
   * </p>
   * <p>
   * Typically access is restricted by a secret API key and/or username password authentication which are usually provided in the
   * {@link ExchangeSpecification}
   * </p>
   *
   * @return The exchange's polling account service
   */
  PollingAccountService getPollingAccountService();

}
