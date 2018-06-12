package org.knowm.xchange.coingi.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coingi.CoingiAuthenticated;
import org.knowm.xchange.coingi.dto.request.CancelOrderRequest;
import org.knowm.xchange.coingi.dto.request.GetOrderHistoryRequest;
import org.knowm.xchange.coingi.dto.request.GetOrderRequest;
import org.knowm.xchange.coingi.dto.request.PlaceLimitOrderRequest;
import org.knowm.xchange.coingi.dto.trade.CoingiOrder;
import org.knowm.xchange.coingi.dto.trade.CoingiOrdersList;
import org.knowm.xchange.coingi.dto.trade.CoingiPlaceOrderResponse;
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

  public CoingiPlaceOrderResponse placeCoingiLimitOrder(PlaceLimitOrderRequest request)
      throws IOException {
    handleAuthentication(request);
    return coingiAuthenticated.placeLimitOrder(request);
  }

  public CoingiOrder cancelCoingiOrder(CancelOrderRequest request) throws IOException {
    handleAuthentication(request);
    return coingiAuthenticated.cancelOrder(request);
  }

  public CoingiOrder getCoingiOrder(GetOrderRequest request) throws IOException {
    handleAuthentication(request);
    return coingiAuthenticated.getOrderStatus(request);
  }

  public CoingiOrdersList getCoingiOrderHistory(GetOrderHistoryRequest request) throws IOException {
    handleAuthentication(request);
    return coingiAuthenticated.getOrderHistory(request);
  }
}
