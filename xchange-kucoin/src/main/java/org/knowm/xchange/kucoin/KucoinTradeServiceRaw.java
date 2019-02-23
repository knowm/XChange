package org.knowm.xchange.kucoin;

import java.io.IOException;

import com.kucoin.sdk.rest.response.OrderCancelResponse;
import com.kucoin.sdk.rest.response.OrderResponse;
import com.kucoin.sdk.rest.response.Pagination;

public class KucoinTradeServiceRaw extends KucoinBaseService {

  protected KucoinTradeServiceRaw(KucoinExchange exchange) {
    super(exchange);
  }

  public Pagination<OrderResponse> getKucoinOpenOrders(String symbol, int page, int pageSize) throws IOException {
    return kucoinRestClient.orderAPI().listOrders(symbol, null, null, "active", null, null, pageSize, page);
  }

  public OrderCancelResponse kucoinCancelAllOrders(String symbol) throws IOException {
    return kucoinRestClient.orderAPI().cancelAllOrders(symbol);
  }
}