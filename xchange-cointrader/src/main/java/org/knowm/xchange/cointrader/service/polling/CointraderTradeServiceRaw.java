package org.knowm.xchange.cointrader.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.cointrader.Cointrader;
import org.knowm.xchange.cointrader.CointraderAuthenticated;
import org.knowm.xchange.cointrader.dto.CointraderBaseResponse;
import org.knowm.xchange.cointrader.dto.CointraderException;
import org.knowm.xchange.cointrader.dto.CointraderRequest;
import org.knowm.xchange.cointrader.dto.trade.CointraderCancelOrderRequest;
import org.knowm.xchange.cointrader.dto.trade.CointraderOpenOrdersResponse;
import org.knowm.xchange.cointrader.dto.trade.CointraderOrder;
import org.knowm.xchange.cointrader.dto.trade.CointraderOrderRequest;
import org.knowm.xchange.cointrader.dto.trade.CointraderSubmitOrderResponse;
import org.knowm.xchange.cointrader.dto.trade.CointraderTradeHistoryRequest;
import org.knowm.xchange.cointrader.dto.trade.CointraderTradeHistoryResponse;
import org.knowm.xchange.cointrader.dto.trade.CointraderUserTrade;
import org.knowm.xchange.cointrader.service.CointraderDigest;
import org.knowm.xchange.currency.CurrencyPair;

import si.mazi.rescu.RestProxyFactory;

public class CointraderTradeServiceRaw extends CointraderBasePollingService {

  private final CointraderAuthenticated cointraderAuthenticated;
  private final CointraderDigest signatureCreator;

  public CointraderTradeServiceRaw(Exchange exchange) {
    super(exchange);
    final ExchangeSpecification spec = exchange.getExchangeSpecification();
    this.cointraderAuthenticated = RestProxyFactory.createProxy(CointraderAuthenticated.class, spec.getSslUri());
    this.signatureCreator = new CointraderDigest(spec.getSecretKey());
  }

  public CointraderSubmitOrderResponse placeCointraderBuyOrder(CurrencyPair currencyPair, BigDecimal amount, BigDecimal price) throws IOException {
    return cointraderAuthenticated.placeBuyOrder(exchange.getExchangeSpecification().getApiKey(), this.signatureCreator,
        new Cointrader.Pair(currencyPair), CointraderOrderRequest.order(amount, price));
  }

  public CointraderSubmitOrderResponse placeCointraderSellOrder(CurrencyPair currencyPair, BigDecimal amount, BigDecimal price) throws IOException {
    return cointraderAuthenticated.placeSellOrder(exchange.getExchangeSpecification().getApiKey(), this.signatureCreator,
        new Cointrader.Pair(currencyPair), CointraderOrderRequest.order(amount, price));
  }

  public CointraderOrder[] getCointraderOpenOrders(CurrencyPair currencyPair) throws IOException {
    CointraderOpenOrdersResponse response = null;
    try {
      response = cointraderAuthenticated.getOpenOrders(exchange.getExchangeSpecification().getApiKey(), signatureCreator,
          new Cointrader.Pair(currencyPair), new CointraderRequest());
    } catch (CointraderException e) {
      if (e.getErrorData().errorCode == 801) {
        // no open orders found
        return new CointraderOrder[] {};
      } else {
        throw e;
      }
    }
    return response.getData();
  }

  public CointraderBaseResponse cancelCointraderOrder(CurrencyPair currencyPair, Long orderId) throws IOException {
    return cointraderAuthenticated.deleteOrder(exchange.getExchangeSpecification().getApiKey(), signatureCreator, new Cointrader.Pair(currencyPair),
        new CointraderCancelOrderRequest(orderId));
  }

  public CointraderUserTrade[] getCointraderUserTransactions(CurrencyPair currencyPair, Integer offset, Integer limit, Long sinceTradeId)
      throws IOException {
    CointraderTradeHistoryResponse result = cointraderAuthenticated.getTradeHistory(exchange.getExchangeSpecification().getApiKey(), signatureCreator,
        new Cointrader.Pair(currencyPair), new CointraderTradeHistoryRequest(limit, offset, sinceTradeId));
    return result.getData();
  }
}
