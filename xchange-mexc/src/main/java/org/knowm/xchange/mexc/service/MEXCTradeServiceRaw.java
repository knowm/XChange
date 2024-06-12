package org.knowm.xchange.mexc.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.mexc.dto.MEXCResult;
import org.knowm.xchange.mexc.dto.trade.MEXCOrder;
import org.knowm.xchange.mexc.dto.trade.MEXCOrderRequestPayload;

public class MEXCTradeServiceRaw extends MEXCBaseService {
  public MEXCTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public MEXCResult<String> placeOrder(MEXCOrderRequestPayload orderRequestPayload)
      throws IOException {
    return mexcAuthenticated.placeOrder(
        apiKey, nonceFactory, signatureCreator, orderRequestPayload);
  }

  public MEXCResult<List<MEXCOrder>> getOrders(List<String> orderIds) throws IOException {
    return mexcAuthenticated.getOrders(apiKey, nonceFactory, signatureCreator, orderIds);
  }
}
