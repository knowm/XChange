package org.knowm.xchange.paymium.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.paymium.PaymiumAuthenticated;
import org.knowm.xchange.paymium.dto.account.PaymiumBalance;
import si.mazi.rescu.RestProxyFactory;

public class PaymiumAccountServiceRaw extends PaymiumBaseService {

  protected PaymiumAuthenticated paymiumAuthenticated;

  /**
   * Constructor
   *
   * @param exchange
   */
  public PaymiumAccountServiceRaw(Exchange exchange) {
    super(exchange);

    this.paymiumAuthenticated =
        RestProxyFactory.createProxy(
            org.knowm.xchange.paymium.PaymiumAuthenticated.class,
            exchange.getExchangeSpecification().getSslUri(),
            getClientConfig());
  }

  public PaymiumBalance getPaymiumBalances() throws IOException {
    return paymiumAuthenticated.getBalance(apiKey, signatureCreator, exchange.getNonceFactory());
  }
}
