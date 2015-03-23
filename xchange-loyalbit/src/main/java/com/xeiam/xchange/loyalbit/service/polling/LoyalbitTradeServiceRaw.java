package com.xeiam.xchange.loyalbit.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.loyalbit.LoyalbitAuthenticated;
import com.xeiam.xchange.loyalbit.dto.LoyalbitBaseResponse;
import com.xeiam.xchange.loyalbit.dto.trade.LoyalbitOrder;
import com.xeiam.xchange.loyalbit.dto.trade.LoyalbitSubmitOrderResponse;
import com.xeiam.xchange.loyalbit.dto.trade.LoyalbitUserTransaction;
import com.xeiam.xchange.loyalbit.service.LoyalbitDigest;

public class LoyalbitTradeServiceRaw extends LoyalbitBasePollingService {

  private final LoyalbitAuthenticated loyalbitAuthenticated;
  private final LoyalbitDigest signatureCreator;

  public LoyalbitTradeServiceRaw(Exchange exchange) {
    super(exchange);
    final ExchangeSpecification spec = exchange.getExchangeSpecification();
    this.loyalbitAuthenticated = RestProxyFactory.createProxy(LoyalbitAuthenticated.class, spec.getSslUri());
    this.signatureCreator = LoyalbitDigest.createInstance(spec.getSecretKey(), spec.getUserName(), spec.getApiKey());
  }

  public LoyalbitOrder[] getLoyalbitOpenOrders() throws IOException {
    return loyalbitAuthenticated.getOpenOrders(exchange.getExchangeSpecification().getApiKey(), exchange.getNonceFactory(), signatureCreator);
  }

  public LoyalbitSubmitOrderResponse placeLoyalbitOrder(LoyalbitOrder.Type type, BigDecimal amount, BigDecimal price) throws IOException {
    return loyalbitAuthenticated.submitOrder(
        exchange.getExchangeSpecification().getApiKey(), exchange.getNonceFactory(), this.signatureCreator, type, amount,
        price, LoyalbitAuthenticated.Pair.BTCUSD);
  }

  public LoyalbitBaseResponse cancelLoyalbitOrder(Long orderId) throws IOException {
    return loyalbitAuthenticated.deleteOrder(exchange.getExchangeSpecification().getApiKey(), exchange.getNonceFactory(), signatureCreator, orderId);
  }

  public LoyalbitUserTransaction[] getLoyalbitUserTransactions(Integer offset, Integer limit, LoyalbitAuthenticated.Sort sort)
      throws IOException {
    return loyalbitAuthenticated.getTransactions(exchange.getExchangeSpecification().getApiKey(), exchange.getNonceFactory(), signatureCreator,
        offset, limit, sort);
  }
}
