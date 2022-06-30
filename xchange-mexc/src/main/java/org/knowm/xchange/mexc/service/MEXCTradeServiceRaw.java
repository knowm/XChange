package org.knowm.xchange.mexc.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.mexc.dto.MEXCResult;
import org.knowm.xchange.mexc.dto.trade.MEXCOrderRequestPayload;

import java.io.IOException;

public class MEXCTradeServiceRaw extends MEXCBaseService {
  public MEXCTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public MEXCResult<String> placeOrder(MEXCOrderRequestPayload orderRequestPayload) throws IOException {
    return mexcAuthenticated.placeOrder(
            apiKey,
            nonceFactory,
            signatureCreator,
            orderRequestPayload
    );
  }

}
