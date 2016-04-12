/*
 * The MIT License
 *
 * Copyright 2015 Coinmate.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.knowm.xchange.coinmate.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinmate.CoinmateAuthenticated;
import org.knowm.xchange.coinmate.dto.trade.CoinmateCancelOrderResponse;
import org.knowm.xchange.coinmate.dto.trade.CoinmateOpenOrders;
import org.knowm.xchange.coinmate.dto.trade.CoinmateTradeResponse;
import org.knowm.xchange.coinmate.dto.trade.CoinmateTransactionHistory;
import org.knowm.xchange.coinmate.service.CoinmateDigest;

import si.mazi.rescu.RestProxyFactory;

/**
 * @author Martin Stachon
 */
public class CoinmateTradeServiceRaw extends CoinmateBasePollingService {

  private final CoinmateDigest signatureCreator;
  private final CoinmateAuthenticated coinmateAuthenticated;

  public CoinmateTradeServiceRaw(Exchange exchange) {
    super(exchange);

    this.coinmateAuthenticated = RestProxyFactory.createProxy(CoinmateAuthenticated.class, exchange.getExchangeSpecification().getSslUri());
    this.signatureCreator = CoinmateDigest.createInstance(exchange.getExchangeSpecification().getSecretKey(),
        exchange.getExchangeSpecification().getUserName(), exchange.getExchangeSpecification().getApiKey());
  }

  public CoinmateTransactionHistory getCoinmateTradeHistory(int offset, int limit, String sort) throws IOException {
    CoinmateTransactionHistory tradeHistory = coinmateAuthenticated.getTransactionHistory(exchange.getExchangeSpecification().getUserName(),
        signatureCreator, exchange.getNonceFactory(), offset, limit, sort);

    throwExceptionIfError(tradeHistory);

    return tradeHistory;
  }

  public CoinmateOpenOrders getCoinmateOpenOrders(String currencyPair) throws IOException {
    CoinmateOpenOrders openOrders = coinmateAuthenticated.getOpenOrders(exchange.getExchangeSpecification().getUserName(), signatureCreator,
        exchange.getNonceFactory(), currencyPair);

    throwExceptionIfError(openOrders);

    return openOrders;

  }

  public CoinmateCancelOrderResponse cancelCoinmateOrder(String orderId) throws IOException {
    CoinmateCancelOrderResponse response = coinmateAuthenticated.cancelOder(exchange.getExchangeSpecification().getUserName(), signatureCreator,
        exchange.getNonceFactory(), orderId);

    throwExceptionIfError(response);

    return response;
  }

  public CoinmateTradeResponse buyCoinmateLimit(BigDecimal amount, BigDecimal price, String currencyPair) throws IOException {
    CoinmateTradeResponse response = coinmateAuthenticated.buyLimit(exchange.getExchangeSpecification().getUserName(), signatureCreator,
        exchange.getNonceFactory(), amount, price, currencyPair);

    throwExceptionIfError(response);

    return response;
  }

  public CoinmateTradeResponse sellCoinmateLimit(BigDecimal amount, BigDecimal price, String currencyPair) throws IOException {
    CoinmateTradeResponse response = coinmateAuthenticated.sellLimit(exchange.getExchangeSpecification().getUserName(), signatureCreator,
        exchange.getNonceFactory(), amount, price, currencyPair);

    throwExceptionIfError(response);

    return response;
  }

  public CoinmateTradeResponse buyCoinmateInstant(BigDecimal total, String currencyPair) throws IOException {
    CoinmateTradeResponse response = coinmateAuthenticated.buyInstant(exchange.getExchangeSpecification().getUserName(), signatureCreator,
        exchange.getNonceFactory(), total, currencyPair);

    throwExceptionIfError(response);

    return response;
  }

  public CoinmateTradeResponse sellCoinmateInstant(BigDecimal total, String currencyPair) throws IOException {
    CoinmateTradeResponse response = coinmateAuthenticated.sellInstant(exchange.getExchangeSpecification().getUserName(), signatureCreator,
        exchange.getNonceFactory(), total, currencyPair);

    throwExceptionIfError(response);

    return response;
  }

}
