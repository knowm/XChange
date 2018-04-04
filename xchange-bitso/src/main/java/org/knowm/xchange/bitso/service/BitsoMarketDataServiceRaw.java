package org.knowm.xchange.bitso.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitso.Bitso;
import org.knowm.xchange.bitso.dto.marketdata.BitsoOrderBook;
import org.knowm.xchange.bitso.dto.marketdata.BitsoTicker;
import org.knowm.xchange.bitso.dto.marketdata.BitsoTransaction;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;
import si.mazi.rescu.RestProxyFactory;

/** @author Piotr Ładyżyński */
public class BitsoMarketDataServiceRaw extends BitsoBaseService {

  private final Bitso bitso;

  public BitsoMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
    this.bitso =
        RestProxyFactory.createProxy(
            Bitso.class, exchange.getExchangeSpecification().getSslUri(), getClientConfig());
  }

  public BitsoOrderBook getBitsoOrderBook(CurrencyPair pair) throws IOException {
    return bitso.getOrderBook();
  }

  public BitsoTransaction[] getBitsoTransactions(Object... args) throws IOException {

    BitsoTransaction[] transactions = null;

    if (args.length == 0) {
      transactions = bitso.getTransactions(); // default values: offset=0, limit=100
    } else if (args.length == 1) {
      BitsoTime bitsoTime = BitsoTime.valueOf(((String) args[0]).toUpperCase());
      transactions =
          bitso.getTransactions(bitsoTime.toString().toLowerCase()); // default values: limit=100
    } else {
      throw new ExchangeException("Invalid argument length. Must be 0, or 1.");
    }
    return transactions;
  }

  public BitsoTicker getBitsoTicker(CurrencyPair pair) throws IOException {
    return bitso.getTicker(pair.base + "_" + pair.counter);
  }

  public enum BitsoTime {
    HOUR,
    MINUTE
  }
}
