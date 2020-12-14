package org.knowm.xchange.bitstamp.service;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitstamp.BitstampAuthenticated;
import org.knowm.xchange.bitstamp.BitstampAuthenticatedV2;
import org.knowm.xchange.bitstamp.BitstampV2;
import org.knowm.xchange.bitstamp.dto.BitstampException;
import org.knowm.xchange.bitstamp.dto.trade.BitstampOrder;
import org.knowm.xchange.bitstamp.dto.trade.BitstampOrderCancelResponse;
import org.knowm.xchange.bitstamp.dto.trade.BitstampOrderStatusResponse;
import org.knowm.xchange.bitstamp.dto.trade.BitstampUserTransaction;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.currency.CurrencyPair;
import si.mazi.rescu.SynchronizedValueFactory;

/** @author gnandiga */
public class BitstampTradeServiceRaw extends BitstampBaseService {

  private final BitstampAuthenticated bitstampAuthenticated;
  private final BitstampAuthenticatedV2 bitstampAuthenticatedV2;
  private final BitstampDigest signatureCreator;
  private final String apiKey;
  private final SynchronizedValueFactory<Long> nonceFactory;

  public BitstampTradeServiceRaw(Exchange exchange) {

    super(exchange);
    this.bitstampAuthenticated =
        ExchangeRestProxyBuilder.forInterface(
                BitstampAuthenticated.class, exchange.getExchangeSpecification())
            .build();
    this.bitstampAuthenticatedV2 =
        ExchangeRestProxyBuilder.forInterface(
                BitstampAuthenticatedV2.class, exchange.getExchangeSpecification())
            .build();
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.nonceFactory = exchange.getNonceFactory();
    this.signatureCreator =
        BitstampDigest.createInstance(
            exchange.getExchangeSpecification().getSecretKey(),
            exchange.getExchangeSpecification().getUserName(),
            apiKey);
  }

  public BitstampOrder[] getBitstampOpenOrders() throws IOException {
    try {
      return bitstampAuthenticatedV2.getOpenOrders(apiKey, signatureCreator, nonceFactory);
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public BitstampOrder[] getBitstampOpenOrders(CurrencyPair pair) throws IOException {
    try {
      return bitstampAuthenticatedV2.getOpenOrders(
          apiKey, signatureCreator, nonceFactory, new BitstampV2.Pair(pair));
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public BitstampOrder placeBitstampMarketOrder(
      CurrencyPair pair, BitstampAuthenticatedV2.Side side, BigDecimal originalAmount)
      throws IOException {

    try {
      return bitstampAuthenticatedV2.placeMarketOrder(
          apiKey, signatureCreator, nonceFactory, side, new BitstampV2.Pair(pair), originalAmount);
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public BitstampOrder placeBitstampOrder(
      CurrencyPair pair,
      BitstampAuthenticatedV2.Side side,
      BigDecimal originalAmount,
      BigDecimal price)
      throws IOException {

    try {
      return bitstampAuthenticatedV2.placeOrder(
          apiKey,
          signatureCreator,
          nonceFactory,
          side,
          new BitstampV2.Pair(pair),
          originalAmount,
          price);
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public boolean cancelBitstampOrder(long orderId) throws IOException {

    try {
      BitstampOrderCancelResponse cancelResponse =
          bitstampAuthenticatedV2.cancelOrder(apiKey, signatureCreator, nonceFactory, orderId);
      return cancelResponse.getError() == null;
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public boolean cancelAllBitstampOrders() throws IOException {

    try {
      return bitstampAuthenticated.cancelAllOrders(apiKey, signatureCreator, nonceFactory);
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public BitstampUserTransaction[] getBitstampUserTransactions(
      Long numberOfTransactions, CurrencyPair pair) throws IOException {

    try {
      return bitstampAuthenticatedV2.getUserTransactions(
          apiKey,
          signatureCreator,
          nonceFactory,
          new BitstampV2.Pair(pair),
          numberOfTransactions,
          null,
          null,
          null,
          null);
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public BitstampUserTransaction[] getBitstampUserTransactions(Long numberOfTransactions)
      throws IOException {
    return getBitstampUserTransactions(numberOfTransactions, null, null, null, null);
  }

  public BitstampUserTransaction[] getBitstampUserTransactions(
      Long numberOfTransactions,
      CurrencyPair pair,
      Long offset,
      String sort,
      Long sinceTimestamp,
      String sinceId)
      throws IOException {
    try {
      return bitstampAuthenticatedV2.getUserTransactions(
          apiKey,
          signatureCreator,
          nonceFactory,
          new BitstampV2.Pair(pair),
          numberOfTransactions,
          offset,
          sort,
          sinceTimestamp,
          sinceId);
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public BitstampUserTransaction[] getBitstampUserTransactions(
      Long numberOfTransactions, Long offset, String sort, Long sinceTimestamp, String sinceId)
      throws IOException {
    try {
      return bitstampAuthenticatedV2.getUserTransactions(
          apiKey,
          signatureCreator,
          nonceFactory,
          numberOfTransactions,
          offset,
          sort,
          sinceTimestamp,
          sinceId);
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public BitstampOrderStatusResponse getBitstampOrder(Long orderId) throws IOException {
    try {
      return bitstampAuthenticatedV2.getOrderStatus(
          exchange.getExchangeSpecification().getApiKey(),
          signatureCreator,
          exchange.getNonceFactory(),
          orderId);
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }
}
