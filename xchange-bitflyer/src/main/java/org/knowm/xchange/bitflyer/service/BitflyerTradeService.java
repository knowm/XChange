package org.knowm.xchange.bitflyer.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitflyer.BitflyerAdapters;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.service.trade.TradeService;

public class BitflyerTradeService extends BitflyerTradeServiceRaw implements TradeService {
  /**
   * Constructor
   *
   * @param exchange
   */
  public BitflyerTradeService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    return BitflyerAdapters.adaptOrderId(super.sendChildOrder(marketOrder));
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    return BitflyerAdapters.adaptOrderId(super.sendChildOrder(limitOrder));
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return BitflyerAdapters.adaptOpenOrdersFromChildOrderResults(
        // TODO not sure yet how to select which product code to use here
        super.getChildOrders("BTC_USD", "ACTIVE"));
  }
}
