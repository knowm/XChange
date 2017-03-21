package org.knowm.xchange.bitstamp.service;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitstamp.BitstampAuthenticated;
import org.knowm.xchange.bitstamp.BitstampAuthenticatedV2;
import org.knowm.xchange.bitstamp.BitstampV2;
import org.knowm.xchange.bitstamp.dto.trade.BitstampOrder;
import org.knowm.xchange.bitstamp.dto.trade.BitstampUserTransaction;
import org.knowm.xchange.currency.CurrencyPair;

import si.mazi.rescu.RestProxyFactory;
import si.mazi.rescu.SynchronizedValueFactory;

/**
 * @author gnandiga
 */
public class BitstampTradeServiceRaw extends BitstampBaseService {

  private final BitstampAuthenticated bitstampAuthenticated;
  private final BitstampAuthenticatedV2 bitstampAuthenticatedV2;
  private final BitstampDigest signatureCreator;
  private String apiKey;
  private SynchronizedValueFactory<Long> nonceFactory;

  public BitstampTradeServiceRaw(Exchange exchange) {
    super(exchange);
    this.bitstampAuthenticated = RestProxyFactory.createProxy(BitstampAuthenticated.class, exchange.getExchangeSpecification().getSslUri());
    this.bitstampAuthenticatedV2 = RestProxyFactory.createProxy(BitstampAuthenticatedV2.class, exchange.getExchangeSpecification().getSslUri());
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.nonceFactory = exchange.getNonceFactory();
    this.signatureCreator = BitstampDigest.createInstance(exchange.getExchangeSpecification().getSecretKey(),
        exchange.getExchangeSpecification().getUserName(), apiKey);
  }

  /**
   * @deprecated Use {@link #getBitstampOpenOrders(CurrencyPair)}.
   */
  @Deprecated
  public BitstampOrder[] getBitstampOpenOrders() throws IOException {
    return getBitstampOpenOrders(CurrencyPair.BTC_USD);
  }

  /**
   * @deprecated Use {@link #placeBitstampOrder(CurrencyPair, BitstampAuthenticatedV2.Side, BigDecimal, BigDecimal)}.
   */
  @Deprecated
  public BitstampOrder sellBitstampOrder(BigDecimal tradableAmount, BigDecimal price) throws IOException {
    return placeBitstampOrder(CurrencyPair.BTC_USD, BitstampAuthenticatedV2.Side.sell, tradableAmount, price);
  }

  /**
   * @deprecated Use {@link #placeBitstampOrder(CurrencyPair, BitstampAuthenticatedV2.Side, BigDecimal, BigDecimal)}.
   */
  @Deprecated
  public BitstampOrder buyBitStampOrder(BigDecimal tradableAmount, BigDecimal price) throws IOException {
    return placeBitstampOrder(CurrencyPair.BTC_USD, BitstampAuthenticatedV2.Side.buy, tradableAmount, price);
  }

  public BitstampOrder[] getBitstampOpenOrders(CurrencyPair pair) throws IOException {
    return bitstampAuthenticatedV2.getOpenOrders(apiKey, signatureCreator, nonceFactory, new BitstampV2.Pair(pair));
  }

  public BitstampOrder placeBitstampOrder(CurrencyPair pair, BitstampAuthenticatedV2.Side side, BigDecimal tradableAmount, BigDecimal price)
      throws IOException {
    return bitstampAuthenticatedV2.placeOrder(apiKey, signatureCreator, nonceFactory, side, new BitstampV2.Pair(pair), tradableAmount, price);
  }

  public boolean cancelBitstampOrder(int orderId) throws IOException {
    return bitstampAuthenticated.cancelOrder(apiKey, signatureCreator, nonceFactory, orderId);
  }

  public BitstampUserTransaction[] getBitstampUserTransactions(Long numberOfTransactions, CurrencyPair pair) throws IOException {
    return bitstampAuthenticatedV2.getUserTransactions(apiKey, signatureCreator, nonceFactory, new BitstampV2.Pair(pair), numberOfTransactions, null,
        null);
  }

  public BitstampUserTransaction[] getBitstampUserTransactions(Long numberOfTransactions) throws IOException {
    return bitstampAuthenticatedV2.getUserTransactions(apiKey, signatureCreator, nonceFactory, numberOfTransactions, null, null);
  }

  public BitstampUserTransaction[] getBitstampUserTransactions(Long numberOfTransactions, CurrencyPair pair, Long offset, String sort)
      throws IOException {
    return bitstampAuthenticatedV2.getUserTransactions(apiKey, signatureCreator, nonceFactory, new BitstampV2.Pair(pair), numberOfTransactions,
        offset, sort);
  }

  public BitstampUserTransaction[] getBitstampUserTransactions(Long numberOfTransactions, Long offset, String sort) throws IOException {
    return bitstampAuthenticatedV2.getUserTransactions(apiKey, signatureCreator, nonceFactory, numberOfTransactions, offset, sort);
  }

}
