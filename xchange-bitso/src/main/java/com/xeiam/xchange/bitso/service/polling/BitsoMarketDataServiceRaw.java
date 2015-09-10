package com.xeiam.xchange.bitso.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitso.Bitso;
import com.xeiam.xchange.bitso.dto.marketdata.BitsoOrderBook;
import com.xeiam.xchange.bitso.dto.marketdata.BitsoTicker;
import com.xeiam.xchange.bitso.dto.marketdata.BitsoTransaction;
import com.xeiam.xchange.exceptions.ExchangeException;

import si.mazi.rescu.RestProxyFactory;

/**
 * @author Piotr Ładyżyński
 */
public class BitsoMarketDataServiceRaw extends BitsoBasePollingService {

  private final Bitso bitso;

  public BitsoMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
    this.bitso = RestProxyFactory.createProxy(Bitso.class, exchange.getExchangeSpecification().getSslUri());
  }

  public BitsoOrderBook getBitsoOrderBook() throws IOException {
    return bitso.getOrderBook();
  }

  public BitsoTransaction[] getBitsoTransactions(Object... args) throws IOException {

    BitsoTransaction[] transactions = null;

    if (args.length == 0) {
      transactions = bitso.getTransactions(); // default values: offset=0, limit=100
    } else if (args.length == 1) {
      BitsoTime bitsoTime = BitsoTime.valueOf(((String) args[0]).toUpperCase());
      transactions = bitso.getTransactions(bitsoTime.toString().toLowerCase()); // default values: limit=100
    } else {
      throw new ExchangeException("Invalid argument length. Must be 0, or 1.");
    }
    return transactions;
  }

  public enum BitsoTime {
    HOUR, MINUTE
  }

  public BitsoTicker getBitsoTicker() throws IOException {
    return bitso.getTicker();
  }
}
