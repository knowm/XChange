package org.knowm.xchange;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.trade.TradeService;
import si.mazi.rescu.SynchronizedValueFactory;

/**
 * Interface to provide the following to applications:
 *
 * <ul>
 *   <li>Entry point to the XChange APIs
 * </ul>
 *
 * <p>The consumer is given a choice of a default (no-args) or configured accessor
 */
public interface Exchange {

  /** @return The ExchangeSpecification in use for this exchange */
  ExchangeSpecification getExchangeSpecification();

  /**
   * The Meta Data defining some semi-static properties of an exchange such as currency pairs,
   * trading fees, etc.
   *
   * @return The exchange's meta data
   */
  ExchangeMetaData getExchangeMetaData();

  /**
   * Returns a list of CurrencyPair objects. This list can either come originally from a loaded json
   * file or from a remote call if the implementation override's the `remoteInit` method.
   *
   * @return The exchange's symbols
   */
  List<CurrencyPair> getExchangeSymbols();

  /**
   * The nonce factory used to create a nonce value. Allows services to accept a placeholder that is
   * replaced with generated value just before message is serialized and sent. If a method of a rest
   * accepts ValueFactory as a parameter, it's evaluated, the message is serialized and sent in a
   * single synchronized block.
   *
   * @return Synchronized value factory
   */
  SynchronizedValueFactory<Long> getNonceFactory();

  /**
   * resilience4j registries with retry strategies, rate limiters, etc. used for this exchange.
   *
   * @return resilience4j registries
   * @throws NotYetImplementedForExchangeException if the exchange module does not support
   *     resilience features
   */
  default ResilienceRegistries getResilienceRegistries() {
    throw new NotYetImplementedForExchangeException();
  }

  /**
   * @return A default ExchangeSpecification to use during the creation process if one is not
   *     supplied
   */
  ExchangeSpecification getDefaultExchangeSpecification();

  /**
   * Applies any exchange specific parameters
   *
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  void applySpecification(ExchangeSpecification exchangeSpecification);

  /**
   * A market data service typically consists of a regularly updated list of the available prices
   * for the various symbols
   *
   * <p>This is the non-streaming (blocking) version of the service
   *
   * @return The exchange's market data service
   */
  MarketDataService getMarketDataService();

  /**
   * An trade service typically provides access to trading functionality
   *
   * <p>Typically access is restricted by a secret API key and/or username password authentication
   * which are usually provided in the {@link ExchangeSpecification}
   *
   * @return The exchange's trade service
   */
  TradeService getTradeService();

  /**
   * An account service typically provides access to the user's private exchange data
   *
   * <p>Typically access is restricted by a secret API key and/or username password authentication
   * which are usually provided in the {@link ExchangeSpecification}
   *
   * @return The exchange's account service
   */
  AccountService getAccountService();

  /**
   * Initialize this instance with the remote meta data. Most exchanges require this method to be
   * called before {@link #getExchangeMetaData()}. Some exchanges require it before using some of
   * their services.
   */
  void remoteInit() throws IOException, ExchangeException;
}
