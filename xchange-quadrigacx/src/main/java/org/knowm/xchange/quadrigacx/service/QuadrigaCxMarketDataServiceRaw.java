package org.knowm.xchange.quadrigacx.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.quadrigacx.QuadrigaCx;
import org.knowm.xchange.quadrigacx.dto.marketdata.QuadrigaCxOrderBook;
import org.knowm.xchange.quadrigacx.dto.marketdata.QuadrigaCxTicker;
import org.knowm.xchange.quadrigacx.dto.marketdata.QuadrigaCxTransaction;
import si.mazi.rescu.RestProxyFactory;

public class QuadrigaCxMarketDataServiceRaw extends QuadrigaCxBaseService {

  private final QuadrigaCx quadrigacx;

  public QuadrigaCxMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
    this.quadrigacx =
        RestProxyFactory.createProxy(
            QuadrigaCx.class, exchange.getExchangeSpecification().getSslUri(), getClientConfig());
  }

  public QuadrigaCxOrderBook getQuadrigaCxOrderBook(CurrencyPair currencyPair) throws IOException {
    return quadrigacx.getOrderBook(
        currencyPair.base.getCurrencyCode().toLowerCase(),
        currencyPair.counter.getCurrencyCode().toLowerCase());
  }

  public QuadrigaCxTransaction[] getQuadrigaCxTransactions(
      CurrencyPair currencyPair, Object... args) throws IOException {

    QuadrigaCxTransaction[] transactions = null;

    if (args.length == 0) {
      transactions =
          quadrigacx.getTransactions(
              currencyPair.base.getCurrencyCode().toLowerCase(),
              currencyPair
                  .counter
                  .getCurrencyCode()
                  .toLowerCase()); // default values: offset=0, limit=100
    } else if (args.length == 1) {
      QuadrigaCxTime quadrigacxTime = QuadrigaCxTime.valueOf(((String) args[0]).toUpperCase());
      transactions =
          quadrigacx.getTransactions(
              currencyPair.base.getCurrencyCode(),
              currencyPair.counter.getCurrencyCode(),
              quadrigacxTime.toString().toLowerCase()); // default values: limit=100
    } else {
      throw new ExchangeException("Invalid argument length. Must be 0, or 1.");
    }
    return transactions;
  }

  public QuadrigaCxTicker getQuadrigaCxTicker(CurrencyPair currencyPair) throws IOException {
    return quadrigacx.getTicker(
        currencyPair.base.getCurrencyCode().toLowerCase(),
        currencyPair.counter.getCurrencyCode().toLowerCase());
  }

  public enum QuadrigaCxTime {
    HOUR,
    MINUTE
  }
}
