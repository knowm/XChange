package org.knowm.xchange.bybit.service;

import static org.knowm.xchange.bybit.BybitAdapters.*;
import static org.knowm.xchange.bybit.BybitAdapters.adaptBybitOrderDetails;
import static org.knowm.xchange.bybit.BybitAdapters.convertToBybitSymbol;
import static org.knowm.xchange.bybit.BybitAdapters.getSideString;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.bybit.dto.BybitResult;
import org.knowm.xchange.bybit.dto.trade.BybitOrderResponse;
import org.knowm.xchange.bybit.dto.trade.BybitOrderType;
import org.knowm.xchange.bybit.dto.trade.details.BybitOrderDetail;
import org.knowm.xchange.bybit.dto.trade.details.BybitOrderDetails;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.service.trade.TradeService;

public class BybitTradeService extends BybitTradeServiceRaw implements TradeService {

  public BybitTradeService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    BybitResult<BybitOrderResponse> order =
        placeOrder(
            BybitCategory.SPOT,
            convertToBybitSymbol(marketOrder.getInstrument().toString()),
            getSideString(marketOrder.getType()),
            BybitOrderType.MARKET,
            marketOrder.getOriginalAmount());

    return order.getResult().getOrderId();
  }

  @Override
  public Collection<Order> getOrder(String... orderIds) throws IOException {
    List<Order> results = new ArrayList<>();

    for (String orderId : orderIds) {
      BybitResult<BybitOrderDetails<BybitOrderDetail>> bybitOrder =
          getBybitOrder(BybitCategory.SPOT, orderId);
      BybitOrderDetail bybitOrderResult = bybitOrder.getResult().getList().get(0);
      Order order = adaptBybitOrderDetails(bybitOrderResult);
      results.add(order);
    }

    return results;
  }
}
