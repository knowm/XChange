package org.knowm.xchange.therock.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.therock.TheRock;
import org.knowm.xchange.therock.TheRockAuthenticated;
import org.knowm.xchange.therock.dto.TheRockException;
import org.knowm.xchange.therock.dto.trade.TheRockOrder;
import org.knowm.xchange.therock.dto.trade.TheRockOrders;
import org.knowm.xchange.therock.service.TheRockDigest;

import si.mazi.rescu.RestProxyFactory;

public class TheRockTradeServiceRaw extends TheRockBasePollingService {

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

  /*
   * public TheRockUserTrade[] getTheRockUserTransactions(CurrencyPair currencyPair, Integer offset, Integer limit, Long sinceTradeId) throws
   * IOException { TheRockTradeHistoryResponse result = theRockAuthenticated.getTradeHistory( exchange.getExchangeSpecification().getApiKey(),
   * signatureCreator, new TheRock.Pair(currencyPair), new TheRockTradeHistoryRequest(limit, offset, sinceTradeId)); return result.getData(); }
   */

}
