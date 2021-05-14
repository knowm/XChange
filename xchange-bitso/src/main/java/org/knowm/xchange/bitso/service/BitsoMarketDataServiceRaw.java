package org.knowm.xchange.bitso.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitso.Bitso;
import org.knowm.xchange.bitso.dto.BitsoException;
import org.knowm.xchange.bitso.dto.marketdata.BitsoOrderBook;
import org.knowm.xchange.bitso.dto.marketdata.BitsoTicker;
import org.knowm.xchange.bitso.dto.marketdata.BitsoTransaction;
import org.knowm.xchange.bitso.dto.trade.BitsoTrades;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;

/** @author Piotr Ładyżyński */
public class BitsoMarketDataServiceRaw extends BitsoBaseService {

  private final Bitso bitso;

  public BitsoMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
    this.bitso =
        ExchangeRestProxyBuilder.forInterface(Bitso.class, exchange.getExchangeSpecification())
            .build();
  }

  public BitsoOrderBook getBitsoOrderBook(CurrencyPair pair) throws IOException {
    String symbol = pair.base.getCurrencyCode() + "_" + pair.counter.getCurrencyCode();
    return bitso.getOrderBook(symbol.toLowerCase());
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
    String symbol =
        pair.base.toString().toLowerCase() + "_" + pair.counter.toString().toLowerCase();
    return bitso.getTicker(symbol);
  }

  public BitsoTrades getBitsoTrades(CurrencyPair pair) throws BitsoException, IOException {
    String symbol =
        pair.base.toString().toLowerCase() + "_" + pair.counter.toString().toLowerCase();
    return bitso.getTrades(symbol);
  }

  public enum BitsoTime {
    HOUR,
    MINUTE
  }
}
