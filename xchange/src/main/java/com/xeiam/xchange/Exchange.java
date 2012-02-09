package com.xeiam.xchange;

import com.xeiam.xchange.trade.dto.AccountService;

/**
 * <p>Interface to provide the following to applications:</p>
 * <ul>
 * <li>Entry point to the XChange APIs</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public interface Exchange {


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
   * <p>A market data service typically consists of a regularly updated list of the
   * available prices for the various symbols</p>
   * <p>This is the non-streaming (blocking) version of the service</p>
   *
   * @return The exchange's market data service
   */
  MarketDataService getMarketDataService();

  /**
   * <p>An account service typically provides access to the user's private exchange data</p>
   * <p>Typically access to the account is restricted by a secret API key and/or username password authentication
   * which are usually provided in the {@link ExchangeSpecification}</p>
   *
   * @return The exchange's account service
   */
  AccountService getAccountService();
}
