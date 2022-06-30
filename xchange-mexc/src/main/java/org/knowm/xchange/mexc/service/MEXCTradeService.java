package org.knowm.xchange.mexc.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.mexc.MEXCExchange;
import org.knowm.xchange.service.trade.TradeService;

import java.io.IOException;

import static org.knowm.xchange.mexc.MEXCAdapters.convertToMEXCSymbol;

public class MEXCTradeService extends MEXCTradeServiceRaw implements TradeService {

  public MEXCTradeService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    // placeOrder(
    //          String symbol,
    //          long price,
    //          long qty,
    //          String side,
    //          String type)
    return placeOrder(
            convertToMEXCSymbol(limitOrder.getInstrument().toString()),
            limitOrder.getLimitPrice().longValue(),
            limitOrder.getOriginalAmount().longValue(),
            limitOrder.getType().toString(),
            "LIMIT_ORDER"
    ).getData();

  }

}
