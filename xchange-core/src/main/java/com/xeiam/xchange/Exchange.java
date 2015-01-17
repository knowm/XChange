package com.xeiam.xchange;

import com.xeiam.xchange.service.polling.PollingAccountService;
import com.xeiam.xchange.service.polling.PollingMarketDataService;
import com.xeiam.xchange.service.polling.PollingTradeService;
import com.xeiam.xchange.service.streaming.ExchangeStreamingConfiguration;
import com.xeiam.xchange.service.streaming.StreamingExchangeService;

import java.io.IOException;

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
   * This is the streaming (non-blocking and event driven) version of the service, and requires an application to provide a suitable implementation of the listener to allow event callbacks to take
   * place.
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
   * Typically access is restricted by a secret API key and/or username password authentication which are usually provided in the {@link ExchangeSpecification}
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
   * Typically access is restricted by a secret API key and/or username password authentication which are usually provided in the {@link ExchangeSpecification}
   * </p>
   * 
   * @return The exchange's polling account service
   */
  PollingAccountService getPollingAccountService();

  /**
   * Initialize the services if necessary. Implementations may call the remote service.
   *
   * @throws IOException - Indication that a networking error occurred while fetching JSON data
   * @throws ExchangeException - Indication that the exchange reported some kind of error with the request or response
   */
  void init() throws IOException, ExchangeException;
}
