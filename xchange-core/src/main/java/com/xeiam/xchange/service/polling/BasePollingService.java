package com.xeiam.xchange.service.polling;

import java.io.IOException;
import java.util.Collection;

import com.xeiam.xchange.currency.CurrencyPair;

/**
 * <p>
 * Interface to provide the following to exchange services:
 * </p>
 * <ul>
 * <li>Provision of standard specification parsing</li>
 * </ul>
 */
public interface BasePollingService {

  /**
   * <p>
   * Which currency pairs the exchange supports
   * </p>
   *
   * @return The symbol pairs supported by this exchange (e.g. BTC/USD), null if some sort of error occurred. Implementers should log the error.
   */
  public abstract Collection<CurrencyPair> getExchangeSymbols() throws IOException;

}
