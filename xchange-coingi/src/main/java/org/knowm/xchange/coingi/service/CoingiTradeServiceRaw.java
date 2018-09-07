package org.knowm.xchange.coingi.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coingi.CoingiAuthenticated;
import org.knowm.xchange.coingi.dto.trade.*;
import si.mazi.rescu.RestProxyFactory;

public class CoingiTradeServiceRaw extends CoingiBaseService {
  private final CoingiAuthenticated coingiAuthenticated;

  public CoingiTradeServiceRaw(Exchange exchange) {
    super(exchange);
    this.coingiAuthenticated =
        RestProxyFactory.createProxy(
            CoingiAuthenticated.class,
            exchange.getExchangeSpecification().getSslUri(),
            getClientConfig());

    String apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator =
        CoingiDigest.createInstance(
            exchange.getExchangeSpecification().getSecretKey(),
            exchange.getExchangeSpecification().getUserName(),
            apiKey);
  }

  public CoingiPlaceOrderResponse placeCoingiLimitOrder(CoingiPlaceLimitOrderRequest request)
      throws IOException {
    handleAuthentication(request);
    return coingiAuthenticated.placeLimitOrder(request);
  }

  public CoingiOrder cancelCoingiOrder(CoingiCancelOrderRequest request) throws IOException {
    handleAuthentication(request);
    return coingiAuthenticated.cancelOrder(request);
  }

  public CoingiOrder getCoingiOrder(CoingiGetOrderRequest request) throws IOException {
    handleAuthentication(request);
    return coingiAuthenticated.getOrderStatus(request);
  }

  public CoingiOrdersList getCoingiOrderHistory(CoingiGetOrderHistoryRequest request)
      throws IOException {
    handleAuthentication(request);
    return coingiAuthenticated.getOrderHistory(request);
  }
}
