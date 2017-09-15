package org.knowm.xchange.therock.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.therock.TheRock;
import org.knowm.xchange.therock.TheRockAuthenticated;
import org.knowm.xchange.therock.dto.TheRockException;
import org.knowm.xchange.therock.dto.trade.TheRockOrder;
import org.knowm.xchange.therock.dto.trade.TheRockOrders;
import org.knowm.xchange.therock.dto.trade.TheRockTransaction;
import org.knowm.xchange.therock.dto.trade.TheRockUserTrades;

import si.mazi.rescu.RestProxyFactory;

public class TheRockTradeServiceRaw extends TheRockBaseService {

  private final TheRockAuthenticated theRockAuthenticated;
  private final TheRockDigest signatureCreator;

  public TheRockTradeServiceRaw(Exchange exchange) {
    super(exchange);
    final ExchangeSpecification spec = exchange.getExchangeSpecification();
    this.theRockAuthenticated = RestProxyFactory.createProxy(TheRockAuthenticated.class, spec.getSslUri());
    this.signatureCreator = new TheRockDigest(spec.getSecretKey());
  }

  public TheRockOrder placeTheRockOrder(CurrencyPair currencyPair, BigDecimal amount, BigDecimal price, TheRockOrder.Side side,
      TheRockOrder.Type type) throws ExchangeException, IOException {
    return placeTheRockOrder(currencyPair, new TheRockOrder(new TheRock.Pair(currencyPair), side, type, amount, price));
  }

  public TheRockOrder placeTheRockOrder(CurrencyPair currencyPair, TheRockOrder order) throws ExchangeException, IOException {
    try {
      return theRockAuthenticated.placeOrder(new TheRock.Pair(currencyPair), exchange.getExchangeSpecification().getApiKey(), signatureCreator,
          exchange.getNonceFactory(), order);
    } catch (TheRockException e) {
      throw new ExchangeException(e);
    }
  }

  public TheRockOrder cancelTheRockOrder(CurrencyPair currencyPair, Long orderId) throws TheRockException, IOException {
    try {
      return theRockAuthenticated.cancelOrder(new TheRock.Pair(currencyPair), orderId, exchange.getExchangeSpecification().getApiKey(),
          signatureCreator, exchange.getNonceFactory());
    } catch (TheRockException e) {
      throw new ExchangeException(e);
    }
  }

  public TheRockOrders getTheRockOrders(CurrencyPair currencyPair) throws TheRockException, IOException {
    try {
      return theRockAuthenticated.orders(new TheRock.Pair(currencyPair), exchange.getExchangeSpecification().getApiKey(), signatureCreator,
          exchange.getNonceFactory());
    } catch (TheRockException e) {
      throw new ExchangeException(e);
    }
  }

  public TheRockOrder showTheRockOrder(CurrencyPair currencyPair, Long orderId) throws TheRockException, IOException {
    try {
      return theRockAuthenticated.showOrder(new TheRock.Pair(currencyPair), orderId, exchange.getExchangeSpecification().getApiKey(),
          signatureCreator, exchange.getNonceFactory());
    } catch (TheRockException e) {
      throw new ExchangeException(e);
    }
  }

  public TheRockUserTrades getTheRockUserTrades(CurrencyPair currencyPair, Long sinceTradeId, Date after, Date before, int pageSize, int page) throws IOException {
    try {

      return theRockAuthenticated.trades(new TheRock.Pair(currencyPair), exchange.getExchangeSpecification().getApiKey(), signatureCreator,
          exchange.getNonceFactory(), sinceTradeId, after, before, pageSize, page);
    } catch (Throwable e) {
      throw new ExchangeException(e);
    }
  }

  public TheRockTransaction[] getTheRockTransactions(String type, Date after, Date before) throws IOException {
    try {
      return theRockAuthenticated.transactions(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory(), type, after, before, null, 0)
          .getTransactions();
    } catch (Throwable e) {
      throw new ExchangeException(e);
    }
  }
}
