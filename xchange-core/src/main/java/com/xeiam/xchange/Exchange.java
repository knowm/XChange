/**
 * Copyright (C) 2012 - 2013 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange;

import com.xeiam.xchange.service.ExchangeStreamingConfiguration;
import com.xeiam.xchange.service.StreamingExchangeService;
import com.xeiam.xchange.service.account.polling.PollingAccountService;
import com.xeiam.xchange.service.marketdata.polling.PollingMarketDataService;
import com.xeiam.xchange.service.trade.polling.PollingTradeService;

/**
 * <p>
 * Interface to provide the following to applications:
 * </p>
 * <ul>
 * <li>Entry point to the XChange APIs</li>
 * <p>
 * The consumer is given a choice of a default (no-args) or configured accessor
 * </p>
 * </ul>
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
   * @param exchangeSpecification The exchange specification
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
   * This is the non-streaming (blocking) version of the service
   * </p>
   * 
   * @param configuration The exchange-specific configuration to be applied after creation
   * @return The exchange's market data service
   */
  PollingMarketDataService getPollingMarketDataService(ExchangeStreamingConfiguration configuration);

  /**
   * <p>
   * A market data service typically consists of a regularly updated list of the available prices for the various symbols
   * </p>
   * <p>
   * This is the streaming (non-blocking and event driven) version of the service, and requires an application to provide a suitable implementation of the listener to allow event callbacks to take
   * place.
   * </p>
   * 
   * @return The exchange's "push" market data service
   */
  StreamingExchangeService getStreamingExchangeService();

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
   * An trade service typically provides access to trading functionality
   * </p>
   * <p>
   * Typically access is restricted by a secret API key and/or username password authentication which are usually provided in the {@link ExchangeSpecification}
   * </p>
   * 
   * @param configuration The exchange-specific configuration to be applied after creation
   * @return The exchange's polling trade service
   */
  PollingTradeService getPollingTradeService(ExchangeStreamingConfiguration configuration);

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
   * <p>
   * An account service typically provides access to the user's private exchange data
   * </p>
   * <p>
   * Typically access is restricted by a secret API key and/or username password authentication which are usually provided in the {@link ExchangeSpecification}
   * </p>
   * 
   * @param configuration The exchange-specific configuration to be applied after creation
   * @return The exchange's polling account service
   */
  PollingAccountService getPollingAccountService(ExchangeStreamingConfiguration configuration);

}
