package org.knowm.xchange.mexc.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.mexc.dto.MEXCResult;

import java.io.IOException;

public class MEXCTradeServiceRaw extends MEXCBaseService {
  public MEXCTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public MEXCResult<String> placeOrder(
          String symbol,
          long price,
          long qty,
          String side,
          String type) throws IOException {
    return mexcAuthenticated.placeOrder(
            apiKey,
            symbol,
            price,
            qty,
            side,
            type,
            nonceFactory,
            signatureCreator
    );
  }

}
