package com.xeiam.xchange.clevercoin.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.clevercoin.CleverCoin;
import com.xeiam.xchange.clevercoin.dto.marketdata.CleverCoinOrderBook;
import com.xeiam.xchange.clevercoin.dto.marketdata.CleverCoinTicker;
import com.xeiam.xchange.clevercoin.dto.marketdata.CleverCoinTransaction;
import com.xeiam.xchange.exceptions.ExchangeException;

import si.mazi.rescu.RestProxyFactory;

/**
 * @author gnandiga
 */
public class CleverCoinMarketDataServiceRaw extends CleverCoinBasePollingService {

  private final CleverCoin cleverCoin;

  /**
   * Constructor
   *
   * @param exchange
   */
  public CleverCoinMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
    this.cleverCoin = RestProxyFactory.createProxy(CleverCoin.class, exchange.getExchangeSpecification().getSslUri());
  }

  public CleverCoinTicker getCleverCoinTicker() throws IOException {

    return cleverCoin.getTicker();
  }

  public CleverCoinOrderBook getCleverCoinOrderBook() throws IOException {

    return cleverCoin.getOrderBook();
  }

  public CleverCoinTransaction[] getCleverCoinTransactions(Object... args) throws IOException {

    CleverCoinTransaction[] transactions = null;

    if (args.length == 0) {
      transactions = cleverCoin.getTransactions(); // default values: return all transactions
    } else if (args.length == 1) {
      throw new ExchangeException("Since call not implemented in XChange yet.");
    } else {
      throw new ExchangeException("Invalid argument length. Must be 0, or 1.");
    }
    return transactions;
  }

}
