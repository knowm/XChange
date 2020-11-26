package org.knowm.xchange.coingi.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.coingi.CoingiAuthenticated;
import org.knowm.xchange.coingi.dto.trade.CoingiCancelOrderRequest;
import org.knowm.xchange.coingi.dto.trade.CoingiGetOrderHistoryRequest;
import org.knowm.xchange.coingi.dto.trade.CoingiGetOrderRequest;
import org.knowm.xchange.coingi.dto.trade.CoingiOrder;
import org.knowm.xchange.coingi.dto.trade.CoingiOrdersList;
import org.knowm.xchange.coingi.dto.trade.CoingiPlaceLimitOrderRequest;
import org.knowm.xchange.coingi.dto.trade.CoingiPlaceOrderResponse;

public class CoingiTradeServiceRaw extends CoingiBaseService {
  private final CoingiAuthenticated coingiAuthenticated;

  public CoingiTradeServiceRaw(Exchange exchange) {
    super(exchange);
    this.coingiAuthenticated =
        ExchangeRestProxyBuilder.forInterface(
                CoingiAuthenticated.class, exchange.getExchangeSpecification())
            .build();

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
