package org.knowm.xchange.paymium.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.paymium.PaymiumAuthenticated;
import org.knowm.xchange.paymium.dto.account.PaymiumBalance;
import org.knowm.xchange.paymium.dto.account.PaymiumOrder;
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

  public List<PaymiumOrder> getPaymiumFundingOrders(Long offset, Integer limit) throws IOException {
    return paymiumAuthenticated.getOrders(
        apiKey,
        signatureCreator,
        exchange.getNonceFactory(),
        offset,
        limit,
        Arrays.asList("WireDeposit", "BitcoinDeposit", "Transfer"),
        null);
  }
}
