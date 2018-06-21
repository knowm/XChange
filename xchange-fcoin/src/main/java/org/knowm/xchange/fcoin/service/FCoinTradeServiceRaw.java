package org.knowm.xchange.fcoin.service;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.fcoin.dto.trade.FCoinOrder;
import org.knowm.xchange.fcoin.dto.trade.FCoinOrderResult;
import org.knowm.xchange.fcoin.dto.trade.FCoinSide;
import org.knowm.xchange.fcoin.dto.trade.FCoinType;

public class FCoinTradeServiceRaw extends FCoinBaseService {

  public FCoinTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public String placeLimitOrder(String symbol, BigDecimal amount, BigDecimal price, FCoinSide side)
      throws IOException {
    FCoinOrder order = new FCoinOrder(symbol, side, FCoinType.limit, price, amount);
    FCoinOrderResult result =
        fcoin.placeOrder(apiKey, System.currentTimeMillis(), signatureCreator, order);
    if (result.getStatus() == 0) {
      return result.getData();
    } else {
      throw new ExchangeException("Error status: " + result.getStatus() + ", " + result.getMsg());
    }
  }
}
