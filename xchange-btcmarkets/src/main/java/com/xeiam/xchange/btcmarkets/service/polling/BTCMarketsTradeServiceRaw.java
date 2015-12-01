package com.xeiam.xchange.btcmarkets.service.polling;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import si.mazi.rescu.RestProxyFactory;
import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btcmarkets.BTCMarketsAuthenticated;
import com.xeiam.xchange.btcmarkets.dto.BTCMarketsBaseResponse;
import com.xeiam.xchange.btcmarkets.dto.trade.BTCMarketsCancelOrderRequest;
import com.xeiam.xchange.btcmarkets.dto.trade.BTCMarketsMyTradingRequest;
import com.xeiam.xchange.btcmarkets.dto.trade.BTCMarketsOrder;
import com.xeiam.xchange.btcmarkets.dto.trade.BTCMarketsOrders;
import com.xeiam.xchange.btcmarkets.dto.trade.BTCMarketsPlaceOrderResponse;
import com.xeiam.xchange.btcmarkets.dto.trade.BTCMarketsTradeHistory;
import com.xeiam.xchange.btcmarkets.service.BTCMarketsDigest;
import com.xeiam.xchange.currency.CurrencyPair;

public class BTCMarketsTradeServiceRaw extends BTCMarketsBasePollingService {

  private final BTCMarketsAuthenticated btcm;
  private final BTCMarketsDigest signer;
  private final SynchronizedValueFactory<Long> nonceFactory;

  public BTCMarketsTradeServiceRaw(Exchange exchange) {
    super(exchange);
    final ExchangeSpecification spec = exchange.getExchangeSpecification();
    this.btcm = RestProxyFactory.createProxy(BTCMarketsAuthenticated.class, spec.getSslUri());
    this.signer = new BTCMarketsDigest(spec.getSecretKey());
    this.nonceFactory = exchange.getNonceFactory();
  }

  public BTCMarketsPlaceOrderResponse placeBTCMarketsOrder(
      CurrencyPair currencyPair,
      BigDecimal amount,
      BigDecimal price,
      BTCMarketsOrder.Side side,
      BTCMarketsOrder.Type type
  ) throws IOException {
    return btcm.placeOrder(exchange.getExchangeSpecification().getApiKey(), nonceFactory, this.signer,
        new BTCMarketsOrder(
            amount, price, currencyPair.counter.getCurrencyCode(), currencyPair.base.getCurrencyCode(), side,
            type, newReqId()));
  }

  public BTCMarketsOrders getBTCMarketsOpenOrders(CurrencyPair currencyPair, Integer limit, Date since) throws IOException {
      return btcm.getOpenOrders(exchange.getExchangeSpecification().getApiKey(), nonceFactory, signer,
           new BTCMarketsMyTradingRequest(currencyPair.counter.getCurrencyCode(), currencyPair.base.getCurrencyCode(), limit, since));
  }

  public BTCMarketsBaseResponse cancelBTCMarketsOrder(Long orderId) throws IOException {
    return btcm.cancelOrder(exchange.getExchangeSpecification().getApiKey(), nonceFactory, signer,
        new BTCMarketsCancelOrderRequest(orderId));
  }

  public BTCMarketsTradeHistory getBTCMarketsUserTransactions( CurrencyPair currencyPair,
      Integer limit,
      Date since
  )
      throws IOException {
    return btcm.getTradeHistory(exchange.getExchangeSpecification().getApiKey(), nonceFactory, signer,
        new BTCMarketsMyTradingRequest(currencyPair.counter.getCurrencyCode(), currencyPair.base.getCurrencyCode(), limit, since));
  }

  private String newReqId() {
    return UUID.randomUUID().toString();
  }
}
