/*
 * The MIT License
 *
 * Copyright 2015-2016 Coinmate.
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
package org.knowm.xchange.coinmate.service;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.coinmate.CoinmateAuthenticated;
import org.knowm.xchange.coinmate.dto.trade.*;

/**
 * @author Martin Stachon
 */
public class CoinmateTradeServiceRaw extends CoinmateBaseService {

  private final CoinmateDigest signatureCreator;
  private final CoinmateAuthenticated coinmateAuthenticated;

  public CoinmateTradeServiceRaw(Exchange exchange) {
    super(exchange);

    this.coinmateAuthenticated =
        ExchangeRestProxyBuilder.forInterface(
                CoinmateAuthenticated.class, exchange.getExchangeSpecification())
            .build();
    this.signatureCreator =
        CoinmateDigest.createInstance(
            exchange.getExchangeSpecification().getSecretKey(),
            exchange.getExchangeSpecification().getUserName(),
            exchange.getExchangeSpecification().getApiKey());
  }

  public CoinmateTransactionHistory getCoinmateTransactionHistory(
      int offset, Integer limit, String sort, Long timestampFrom, Long timestampTo, String orderId)
      throws IOException {
    CoinmateTransactionHistory transactionHistory =
        coinmateAuthenticated.getTransactionHistory(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getExchangeSpecification().getUserName(),
            signatureCreator,
            exchange.getNonceFactory(),
            offset,
            limit,
            sort,
            timestampFrom,
            timestampTo,
            orderId);

    throwExceptionIfError(transactionHistory);

    return transactionHistory;
  }

  public CoinmateTradeHistory getCoinmateTradeHistory(
      String currencyPair,
      int limit,
      String order,
      String startId,
      Long timestampFrom,
      Long timestampTo,
      String orderId)
      throws IOException {
    CoinmateTradeHistory tradeHistory =
        coinmateAuthenticated.getTradeHistory(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getExchangeSpecification().getUserName(),
            signatureCreator,
            exchange.getNonceFactory(),
            limit,
            startId,
            order,
            timestampFrom,
            timestampTo,
            currencyPair,
            orderId);

    throwExceptionIfError(tradeHistory);

    return tradeHistory;
  }

  public CoinmateTransferHistory getCoinmateTransferHistory() throws IOException {
    CoinmateTransferHistory transferHistory =
        coinmateAuthenticated.getTransferHistory(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getExchangeSpecification().getUserName(),
            signatureCreator,
            exchange.getNonceFactory(),
            null,
            null,
            null,
            null,
            null,
            null);

    throwExceptionIfError(transferHistory);

    return transferHistory;
  }

  public CoinmateOrderHistory getCoinmateOrderHistory(String currencyPair, Integer limit)
      throws IOException {
    CoinmateOrderHistory orderHistory =
        coinmateAuthenticated.getOrderHistory(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getExchangeSpecification().getUserName(),
            signatureCreator,
            exchange.getNonceFactory(),
            currencyPair,
            limit);

    throwExceptionIfError(orderHistory);

    return orderHistory;
  }

  public CoinmateOpenOrders getCoinmateOpenOrders(String currencyPair) throws IOException {
    CoinmateOpenOrders openOrders =
        coinmateAuthenticated.getOpenOrders(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getExchangeSpecification().getUserName(),
            signatureCreator,
            exchange.getNonceFactory(),
            currencyPair);

    throwExceptionIfError(openOrders);

    return openOrders;
  }

  public CoinmateCancelOrderResponse cancelCoinmateOrder(String orderId) throws IOException {
    CoinmateCancelOrderResponse response =
        coinmateAuthenticated.cancelOder(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getExchangeSpecification().getUserName(),
            signatureCreator,
            exchange.getNonceFactory(),
            orderId);

    throwExceptionIfError(response);

    return response;
  }

  public CoinmateOrder getCoinmateOrderById(String orderId) throws IOException {
    CoinmateOrder response =
        coinmateAuthenticated.getOrderById(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getExchangeSpecification().getUserName(),
            signatureCreator,
            exchange.getNonceFactory(),
            orderId);

    throwExceptionIfError(response);

    return response;
  }

  public CoinmateOrders getCoinmateOrderByClientOrderId(String clientOrderId) throws IOException {
    CoinmateOrders response =
        coinmateAuthenticated.getOrderByClientOrderId(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getExchangeSpecification().getUserName(),
            signatureCreator,
            exchange.getNonceFactory(),
            clientOrderId);

    throwExceptionIfError(response);

    return response;
  }

  public CoinmateCancelOrderWithInfoResponse cancelCoinmateOrderWithInfo(String orderId)
      throws IOException {
    CoinmateCancelOrderWithInfoResponse response =
        coinmateAuthenticated.cancelOderWithInfo(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getExchangeSpecification().getUserName(),
            signatureCreator,
            exchange.getNonceFactory(),
            orderId);

    throwExceptionIfError(response);

    return response;
  }

  public CoinmateTradeResponse buyCoinmateLimit(
      BigDecimal amount,
      BigDecimal price,
      String currencyPair,
      BigDecimal stopPrice,
      Integer hidden,
      Integer postOnly,
      Integer immediateOrCancel,
      Integer trailing,
      String clientOrderId)
      throws IOException {
    CoinmateTradeResponse response =
        coinmateAuthenticated.buyLimit(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getExchangeSpecification().getUserName(),
            signatureCreator,
            exchange.getNonceFactory(),
            amount,
            price,
            currencyPair,
            stopPrice,
            hidden,
            postOnly,
            immediateOrCancel,
            trailing,
            clientOrderId);

    throwExceptionIfError(response);

    return response;
  }

  public CoinmateTradeResponse sellCoinmateLimit(
      BigDecimal amount,
      BigDecimal price,
      String currencyPair,
      BigDecimal stopPrice,
      Integer hidden,
      Integer postOnly,
      Integer immediateOrCancel,
      Integer trailing,
      String clientOrderId)
      throws IOException {
    CoinmateTradeResponse response =
        coinmateAuthenticated.sellLimit(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getExchangeSpecification().getUserName(),
            signatureCreator,
            exchange.getNonceFactory(),
            amount,
            price,
            currencyPair,
            stopPrice,
            hidden,
            postOnly,
            immediateOrCancel,
            trailing,
            clientOrderId);

    throwExceptionIfError(response);

    return response;
  }

  public CoinmateReplaceResponse coinmateReplaceByBuyLimit(
      String orderId,
      BigDecimal amount,
      BigDecimal price,
      String currencyPair,
      BigDecimal stopPrice,
      Integer hidden,
      Integer postOnly,
      Integer immediateOrCancel,
      Integer trailing,
      String clientOrderId)
      throws IOException {
    CoinmateReplaceResponse response =
        coinmateAuthenticated.replaceByBuyLimit(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getExchangeSpecification().getUserName(),
            signatureCreator,
            exchange.getNonceFactory(),
            amount,
            price,
            currencyPair,
            orderId,
            stopPrice,
            hidden,
            postOnly,
            immediateOrCancel,
            trailing,
            clientOrderId);

    throwExceptionIfError(response);

    return response;
  }

  public CoinmateReplaceResponse coinmateReplaceBySellLimit(
      String orderId,
      BigDecimal amount,
      BigDecimal price,
      String currencyPair,
      BigDecimal stopPrice,
      Integer hidden,
      Integer postOnly,
      Integer immediateOrCancel,
      Integer trailing,
      String clientOrderId)
      throws IOException {
    CoinmateReplaceResponse response =
        coinmateAuthenticated.replaceBySellLimit(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getExchangeSpecification().getUserName(),
            signatureCreator,
            exchange.getNonceFactory(),
            amount,
            price,
            currencyPair,
            orderId,
            stopPrice,
            hidden,
            postOnly,
            immediateOrCancel,
            trailing,
            clientOrderId);

    throwExceptionIfError(response);

    return response;
  }

  public CoinmateTradeResponse buyCoinmateInstant(
      BigDecimal total, String currencyPair, String clientOrderId) throws IOException {
    CoinmateTradeResponse response =
        coinmateAuthenticated.buyInstant(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getExchangeSpecification().getUserName(),
            signatureCreator,
            exchange.getNonceFactory(),
            total,
            currencyPair,
            clientOrderId);

    throwExceptionIfError(response);

    return response;
  }

  public CoinmateTradeResponse sellCoinmateInstant(
      BigDecimal total, String currencyPair, String clientOrderId) throws IOException {
    CoinmateTradeResponse response =
        coinmateAuthenticated.sellInstant(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getExchangeSpecification().getUserName(),
            signatureCreator,
            exchange.getNonceFactory(),
            total,
            currencyPair,
            clientOrderId);

    throwExceptionIfError(response);

    return response;
  }

  public CoinmateTradeResponse buyCoinmateQuick(
      BigDecimal total, String currencyPair, String clientOrderId) throws IOException {
    CoinmateTradeResponse response =
        coinmateAuthenticated.buyQuick(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getExchangeSpecification().getUserName(),
            clientOrderId,
            signatureCreator,
            exchange.getNonceFactory(),
            total,
            currencyPair);

    throwExceptionIfError(response);

    return response;
  }

  public CoinmateTradeResponse sellCoinmateQuick(
      BigDecimal amount, String currencyPair, String clientOrderId) throws IOException {
    CoinmateTradeResponse response =
        coinmateAuthenticated.sellQuick(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getExchangeSpecification().getUserName(),
            clientOrderId,
            signatureCreator,
            exchange.getNonceFactory(),
            amount,
            currencyPair);

    throwExceptionIfError(response);

    return response;
  }

  // quick fix rate methods

  public CoinmateBuyFixRateResponse coinmateBuyQuickFixRate(
      BigDecimal total, BigDecimal amountReceived, String currencyPair) throws IOException {
    CoinmateBuyFixRateResponse response =
        coinmateAuthenticated.buyQuickFixRate(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getExchangeSpecification().getUserName(),
            signatureCreator,
            exchange.getNonceFactory(),
            total,
            amountReceived,
            currencyPair);

    throwExceptionIfError(response);

    return response;
  }

  public CoinmateSellFixRateResponse coinmateSellQuickFixRate(
      BigDecimal amount, BigDecimal totalReceived, String currencyPair) throws IOException {
    CoinmateSellFixRateResponse response =
        coinmateAuthenticated.sellQuickFixRate(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getExchangeSpecification().getUserName(),
            signatureCreator,
            exchange.getNonceFactory(),
            amount,
            totalReceived,
            currencyPair);

    throwExceptionIfError(response);

    return response;
  }

  public CoinmateTradeResponse buyCoinmateQuickFix(String rateId, String clientOrderId)
      throws IOException {
    CoinmateTradeResponse response =
        coinmateAuthenticated.buyQuickFix(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getExchangeSpecification().getUserName(),
            signatureCreator,
            exchange.getNonceFactory(),
            clientOrderId,
            rateId);

    throwExceptionIfError(response);

    return response;
  }

  public CoinmateTradeResponse sellCoinmateQuickFix(String rateId, String clientOrderId)
      throws IOException {
    CoinmateTradeResponse response =
        coinmateAuthenticated.sellQuickFix(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getExchangeSpecification().getUserName(),
            signatureCreator,
            exchange.getNonceFactory(),
            clientOrderId,
            rateId);

    throwExceptionIfError(response);

    return response;
  }

  public CoinmateReplaceResponse coinmateReplaceByBuyInstant(
      String orderId, BigDecimal total, String currencyPair, String clientOrderId)
      throws IOException {
    CoinmateReplaceResponse response =
        coinmateAuthenticated.replaceByBuyInstant(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getExchangeSpecification().getUserName(),
            signatureCreator,
            exchange.getNonceFactory(),
            total,
            currencyPair,
            orderId,
            clientOrderId);

    throwExceptionIfError(response);

    return response;
  }

  public CoinmateReplaceResponse coinmateReplaceBySellInstant(
      String orderId, BigDecimal total, String currencyPair, String clientOrderId)
      throws IOException {
    CoinmateReplaceResponse response =
        coinmateAuthenticated.replaceBySellInstant(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getExchangeSpecification().getUserName(),
            signatureCreator,
            exchange.getNonceFactory(),
            total,
            currencyPair,
            orderId,
            clientOrderId);

    throwExceptionIfError(response);

    return response;
  }
}
