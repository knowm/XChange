package org.knowm.xchange.therock.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.therock.TheRock;
import org.knowm.xchange.therock.TheRockAuthenticated;
import org.knowm.xchange.therock.dto.TheRockException;
import org.knowm.xchange.therock.dto.trade.TheRockOrder;
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

  public TheRockOrder placeOrder(CurrencyPair currencyPair, BigDecimal amount, BigDecimal price, TheRockOrder.Side side, TheRockOrder.Type type)
      throws TheRockException, IOException {
    return theRockAuthenticated.placeOrder(new TheRock.Pair(currencyPair), exchange.getExchangeSpecification().getApiKey(), this.signatureCreator,
        exchange.getNonceFactory(), new TheRockOrder(new TheRock.Pair(currencyPair), side, type, amount, price));
  }

  /*
   * public TheRockOrder[] getTheRockOpenOrders(CurrencyPair currencyPair) throws IOException { TheRockOpenOrdersResponse response = null; try {
   * response = theRockAuthenticated.getOpenOrders( exchange.getExchangeSpecification().getApiKey(), signatureCreator, new TheRock.Pair(currencyPair),
   * new TheRockRequest()); } catch (TheRockException e) { if (e.getErrorData().errorCode == 801) { // no open orders found return new
   * TheRockOrder[]{}; } else { throw e; } } return response.getData(); } public TheRockBaseResponse cancelTheRockOrder(CurrencyPair currencyPair,
   * Long orderId) throws IOException { return theRockAuthenticated.deleteOrder( exchange.getExchangeSpecification().getApiKey(), signatureCreator,
   * new TheRock.Pair(currencyPair), new TheRockCancelOrderRequest(orderId)); } public TheRockUserTrade[] getTheRockUserTransactions(CurrencyPair
   * currencyPair, Integer offset, Integer limit, Long sinceTradeId) throws IOException { TheRockTradeHistoryResponse result =
   * theRockAuthenticated.getTradeHistory( exchange.getExchangeSpecification().getApiKey(), signatureCreator, new TheRock.Pair(currencyPair), new
   * TheRockTradeHistoryRequest(limit, offset, sinceTradeId)); return result.getData(); }
   */
}
