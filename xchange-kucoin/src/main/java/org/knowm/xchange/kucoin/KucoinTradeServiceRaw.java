package org.knowm.xchange.kucoin;

import java.io.IOException;

import com.kucoin.sdk.rest.response.OrderResponse;
import com.kucoin.sdk.rest.response.Pagination;

public class KucoinTradeServiceRaw extends KucoinBaseService {

  protected KucoinTradeServiceRaw(KucoinExchange exchange) {
    super(exchange);
  }

  public Pagination<OrderResponse> getKucoinOpenOrders(String symbol, int page, int pageSize) throws IOException {
    return kucoinRestClient.orderAPI().listOrders(symbol, null, null, null, null, null, page, pageSize);
  }
}