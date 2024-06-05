package org.knowm.xchange.bybit.service;

import static org.knowm.xchange.bybit.BybitAdapters.adaptBybitOrderDetails;
import static org.knowm.xchange.bybit.BybitAdapters.convertToBybitSymbol;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bybit.BybitAdapters;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.bybit.dto.BybitResult;
import org.knowm.xchange.bybit.dto.marketdata.tickers.BybitTicker;
import org.knowm.xchange.bybit.dto.marketdata.tickers.BybitTickers;
import org.knowm.xchange.bybit.dto.trade.BybitOrderResponse;
import org.knowm.xchange.bybit.dto.trade.details.BybitOrderDetail;
import org.knowm.xchange.bybit.dto.trade.details.BybitOrderDetails;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.StopOrder;
import org.knowm.xchange.service.trade.TradeService;

public class BybitTradeService extends BybitTradeServiceRaw implements TradeService {

  public BybitTradeService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    BybitResult<BybitOrderResponse> orderResponseBybitResult =
        placeMarketOrder(
            BybitAdapters.getCategory(marketOrder.getInstrument()),
            BybitAdapters.convertToBybitSymbol(marketOrder.getInstrument()),
            BybitAdapters.getSideString(marketOrder.getType()),
            marketOrder.getOriginalAmount(),
            marketOrder.getUserReference());

    return orderResponseBybitResult.getResult().getOrderId();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    BybitResult<BybitOrderResponse> orderResponseBybitResult =
        placeLimitOrder(
            BybitAdapters.getCategory(limitOrder.getInstrument()),
            BybitAdapters.convertToBybitSymbol(limitOrder.getInstrument()),
            BybitAdapters.getSideString(limitOrder.getType()),
            limitOrder.getOriginalAmount(),
            limitOrder.getLimitPrice(),
            limitOrder.getUserReference());

    return orderResponseBybitResult.getResult().getOrderId();
  }

  @Override
  public Collection<Order> getOrder(String... orderIds) throws IOException {
    List<Order> results = new ArrayList<>();

    for (String orderId : orderIds) {
      for (BybitCategory category : BybitCategory.values()) {

        BybitResult<BybitOrderDetails<BybitOrderDetail>> bybitOrder =
            getBybitOrder(category, orderId);

        if (bybitOrder.getResult().getCategory().equals(category)
            && bybitOrder.getResult().getList().size() > 0) {
          BybitOrderDetail bybitOrderDetail = bybitOrder.getResult().getList().get(0);
          Order order = adaptBybitOrderDetails(bybitOrderDetail);
          results.add(order);
        }
      }
    }

    return results;
  }


  public String amendOrder(Order order) throws IOException {
    BybitCategory category = BybitAdapters.getCategory(order.getInstrument());
    BybitResult<BybitOrderResponse> response = null;
    if (order instanceof LimitOrder) {
      response = amendOrder(category,
          convertToBybitSymbol(order.getInstrument()), order.getId(), order.getUserReference(),
          null, order.getOriginalAmount().toString(), ((LimitOrder) order)
              .getLimitPrice().toString(), null, null, null,
          null, null, null, null, null);
    }
    //Todo order instanceof StopOrder
    if (response != null) {
      return response.getResult().getOrderId();
    }
    return "";
  }

}
