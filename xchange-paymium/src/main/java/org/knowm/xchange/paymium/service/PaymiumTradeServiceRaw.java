package org.knowm.xchange.paymium.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.paymium.PaymiumAuthenticated;
import org.knowm.xchange.paymium.dto.account.PaymiumOrder;

public class PaymiumTradeServiceRaw extends PaymiumBaseService {

  protected PaymiumAuthenticated paymiumAuthenticated;

  public PaymiumTradeServiceRaw(Exchange exchange) {

    super(exchange);

    this.paymiumAuthenticated =
        ExchangeRestProxyBuilder.forInterface(
                org.knowm.xchange.paymium.PaymiumAuthenticated.class,
                exchange.getExchangeSpecification())
            .build();
  }

  public List<PaymiumOrder> getPaymiumOrders(Long offset, Integer limit) throws IOException {
    return paymiumAuthenticated.getOrders(
        apiKey,
        signatureCreator,
        exchange.getNonceFactory(),
        offset,
        limit,
        Arrays.asList("LimitOrder"),
        null);
  }
}
