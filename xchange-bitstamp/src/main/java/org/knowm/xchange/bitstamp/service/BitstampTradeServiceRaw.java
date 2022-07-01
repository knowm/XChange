package org.knowm.xchange.bitstamp.service;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitstamp.BitstampAuthenticatedV2;
import org.knowm.xchange.bitstamp.BitstampExchange;
import org.knowm.xchange.bitstamp.BitstampV2;
import org.knowm.xchange.bitstamp.dto.BitstampException;
import org.knowm.xchange.bitstamp.dto.trade.BitstampCancelAllOrdersResponse;
import org.knowm.xchange.bitstamp.dto.trade.BitstampOrder;
import org.knowm.xchange.bitstamp.dto.trade.BitstampOrderCancelResponse;
import org.knowm.xchange.bitstamp.dto.trade.BitstampOrderStatusResponse;
import org.knowm.xchange.bitstamp.dto.trade.BitstampUserTransaction;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.currency.CurrencyPair;
import si.mazi.rescu.SynchronizedValueFactory;

/** @author gnandiga */
public class BitstampTradeServiceRaw extends BitstampBaseService {

  private static final String API_VERSION = "v2";
  private final BitstampAuthenticatedV2 bitstampAuthenticatedV2;
  private final BitstampDigestV2 signatureCreatorV2;
  private final String apiKeyForV2Requests;
  private final SynchronizedValueFactory<String> uuidNonceFactory;
  private final SynchronizedValueFactory<String> timestampFactory;

  public BitstampTradeServiceRaw(Exchange exchange) {

    super(exchange);

    this.bitstampAuthenticatedV2 =
        ExchangeRestProxyBuilder.forInterface(
                BitstampAuthenticatedV2.class, exchange.getExchangeSpecification())
            .build();
    this.apiKeyForV2Requests = "BITSTAMP " + exchange.getExchangeSpecification().getApiKey();
    this.signatureCreatorV2 =
        BitstampDigestV2.createInstance(
            exchange.getExchangeSpecification().getSecretKey(),
            exchange.getExchangeSpecification().getApiKey());

    BitstampExchange bitstampExchange = (BitstampExchange) exchange;
    this.uuidNonceFactory = bitstampExchange.getUuidNonceFactory();
    this.timestampFactory = bitstampExchange.getTimestampFactory();
  }

  public BitstampOrder[] getBitstampOpenOrders() throws IOException {
    try {
      return bitstampAuthenticatedV2.getOpenOrders(
          apiKeyForV2Requests, signatureCreatorV2, uuidNonceFactory, timestampFactory, API_VERSION);
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public BitstampOrder[] getBitstampOpenOrders(CurrencyPair pair) throws IOException {
    try {
      return bitstampAuthenticatedV2.getOpenOrders(
          apiKeyForV2Requests,
          signatureCreatorV2,
          uuidNonceFactory,
          timestampFactory,
          API_VERSION,
          new BitstampV2.Pair(pair));
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public BitstampOrder placeBitstampMarketOrder(
      CurrencyPair pair, BitstampAuthenticatedV2.Side side, BigDecimal originalAmount)
      throws IOException {

    try {
      return bitstampAuthenticatedV2.placeMarketOrder(
          apiKeyForV2Requests,
          signatureCreatorV2,
          uuidNonceFactory,
          timestampFactory,
          API_VERSION,
          side,
          new BitstampV2.Pair(pair),
          originalAmount);
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
          apiKeyForV2Requests,
          signatureCreatorV2,
          uuidNonceFactory,
          timestampFactory,
          API_VERSION,
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
          bitstampAuthenticatedV2.cancelOrder(
              apiKeyForV2Requests,
              signatureCreatorV2,
              uuidNonceFactory,
              timestampFactory,
              API_VERSION,
              orderId);
      return cancelResponse.getError() == null;
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public boolean cancelAllBitstampOrders() throws IOException {

    try {
      BitstampCancelAllOrdersResponse response =
          bitstampAuthenticatedV2.cancelAllOrders(
              apiKeyForV2Requests,
              signatureCreatorV2,
              uuidNonceFactory,
              timestampFactory,
              API_VERSION);
      return response.success;
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public BitstampUserTransaction[] getBitstampUserTransactions(
      Long numberOfTransactions, CurrencyPair pair) throws IOException {

    try {
      return bitstampAuthenticatedV2.getUserTransactions(
          apiKeyForV2Requests,
          signatureCreatorV2,
          uuidNonceFactory,
          timestampFactory,
          API_VERSION,
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
          apiKeyForV2Requests,
          signatureCreatorV2,
          uuidNonceFactory,
          timestampFactory,
          API_VERSION,
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
          apiKeyForV2Requests,
          signatureCreatorV2,
          uuidNonceFactory,
          timestampFactory,
          API_VERSION,
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
          apiKeyForV2Requests,
          signatureCreatorV2,
          uuidNonceFactory,
          timestampFactory,
          API_VERSION,
          orderId);
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }
}
