package org.knowm.xchange.cryptonit2.service;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.cryptonit2.CryptonitAuthenticated;
import org.knowm.xchange.cryptonit2.CryptonitAuthenticatedV2;
import org.knowm.xchange.cryptonit2.CryptonitV2;
import org.knowm.xchange.cryptonit2.dto.CryptonitException;
import org.knowm.xchange.cryptonit2.dto.trade.CryptonitOrder;
import org.knowm.xchange.cryptonit2.dto.trade.CryptonitOrderStatusResponse;
import org.knowm.xchange.cryptonit2.dto.trade.CryptonitUserTransaction;
import org.knowm.xchange.currency.CurrencyPair;
import si.mazi.rescu.RestProxyFactory;
import si.mazi.rescu.SynchronizedValueFactory;

/** @author gnandiga */
public class CryptonitTradeServiceRaw extends CryptonitBaseService {

  private final CryptonitAuthenticated CryptonitAuthenticated;
  private final CryptonitAuthenticatedV2 CryptonitAuthenticatedV2;
  private final CryptonitDigest signatureCreator;
  private String apiKey;
  private SynchronizedValueFactory<Long> nonceFactory;

  public CryptonitTradeServiceRaw(Exchange exchange) {

    super(exchange);
    this.CryptonitAuthenticated =
        RestProxyFactory.createProxy(
            CryptonitAuthenticated.class,
            exchange.getExchangeSpecification().getSslUri(),
            getClientConfig());
    this.CryptonitAuthenticatedV2 =
        RestProxyFactory.createProxy(
            CryptonitAuthenticatedV2.class,
            exchange.getExchangeSpecification().getSslUri(),
            getClientConfig());
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.nonceFactory = exchange.getNonceFactory();
    this.signatureCreator =
        CryptonitDigest.createInstance(
            exchange.getExchangeSpecification().getSecretKey(),
            exchange.getExchangeSpecification().getUserName(),
            apiKey);
  }

  public CryptonitOrder[] getCryptonitOpenOrders(CurrencyPair pair) throws IOException {
    try {
      return CryptonitAuthenticatedV2.getOpenOrders(
          apiKey, signatureCreator, nonceFactory, new CryptonitV2.Pair(pair));
    } catch (CryptonitException e) {
      throw handleError(e);
    }
  }

  public CryptonitOrder placeCryptonitMarketOrder(
      CurrencyPair pair, CryptonitAuthenticatedV2.Side side, BigDecimal originalAmount)
      throws IOException {

    try {
      return CryptonitAuthenticatedV2.placeMarketOrder(
          apiKey, signatureCreator, nonceFactory, side, new CryptonitV2.Pair(pair), originalAmount);
    } catch (CryptonitException e) {
      throw handleError(e);
    }
  }

  public CryptonitOrder placeCryptonitOrder(
      CurrencyPair pair,
      CryptonitAuthenticatedV2.Side side,
      BigDecimal originalAmount,
      BigDecimal price)
      throws IOException {

    try {
      return CryptonitAuthenticatedV2.placeOrder(
          apiKey,
          signatureCreator,
          nonceFactory,
          side,
          new CryptonitV2.Pair(pair),
          originalAmount,
          price);
    } catch (CryptonitException e) {
      throw handleError(e);
    }
  }

  public boolean cancelCryptonitOrder(int orderId) throws IOException {

    try {
      return CryptonitAuthenticated.cancelOrder(apiKey, signatureCreator, nonceFactory, orderId);
    } catch (CryptonitException e) {
      throw handleError(e);
    }
  }

  public boolean cancelAllCryptonitOrders() throws IOException {

    try {
      return CryptonitAuthenticated.cancelAllOrders(apiKey, signatureCreator, nonceFactory);
    } catch (CryptonitException e) {
      throw handleError(e);
    }
  }

  public CryptonitUserTransaction[] getCryptonitUserTransactions(
      Long numberOfTransactions, CurrencyPair pair) throws IOException {

    try {
      return CryptonitAuthenticatedV2.getUserTransactions(
          apiKey,
          signatureCreator,
          nonceFactory,
          new CryptonitV2.Pair(pair),
          numberOfTransactions,
          null,
          null);
    } catch (CryptonitException e) {
      throw handleError(e);
    }
  }

  public CryptonitUserTransaction[] getCryptonitUserTransactions(Long numberOfTransactions)
      throws IOException {
    return getCryptonitUserTransactions(numberOfTransactions, null, null);
  }

  public CryptonitUserTransaction[] getCryptonitUserTransactions(
      Long numberOfTransactions, CurrencyPair pair, Long offset, String sort) throws IOException {

    try {
      return CryptonitAuthenticatedV2.getUserTransactions(
          apiKey,
          signatureCreator,
          nonceFactory,
          new CryptonitV2.Pair(pair),
          numberOfTransactions,
          offset,
          sort);
    } catch (CryptonitException e) {
      throw handleError(e);
    }
  }

  public CryptonitUserTransaction[] getCryptonitUserTransactions(
      Long numberOfTransactions, Long offset, String sort) throws IOException {
    try {
      return CryptonitAuthenticatedV2.getUserTransactions(
          apiKey, signatureCreator, nonceFactory, numberOfTransactions, offset, sort);
    } catch (CryptonitException e) {
      throw handleError(e);
    }
  }

  public CryptonitOrderStatusResponse getCryptonitOrder(Long orderId) throws IOException {
    try {
      return CryptonitAuthenticated.getOrderStatus(
          exchange.getExchangeSpecification().getApiKey(),
          signatureCreator,
          exchange.getNonceFactory(),
          orderId);
    } catch (CryptonitException e) {
      throw handleError(e);
    }
  }
}
