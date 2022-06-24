package org.knowm.xchange.bybit.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bybit.dto.BybitResult;
import org.knowm.xchange.bybit.dto.trade.BybitOrderDetails;
import org.knowm.xchange.bybit.dto.trade.BybitOrderRequest;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.service.trade.TradeService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.knowm.xchange.bybit.BybitAdapters.*;

public class BybitTradeService extends BybitTradeServiceRaw implements TradeService {

    public BybitTradeService(Exchange exchange) {
        super(exchange);
    }

    @Override
    public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
        BybitResult<BybitOrderRequest> order = placeOrder(
                convertToBybitSymbol(marketOrder.getInstrument().toString()),
                marketOrder.getOriginalAmount().longValue(),
                getSideString(marketOrder.getType()),
                "MARKET");

        return order.getResult().getOrderId();
    }

    @Override
    public Collection<Order> getOrder(String... orderIds) throws IOException {
        List<Order> results = new ArrayList<>();

        for (String orderId : orderIds) {
            BybitResult<BybitOrderDetails> bybitOrder = getBybitOrder(orderId);
            BybitOrderDetails bybitOrderResult = bybitOrder.getResult();
            Order order = adaptBybitOrderDetails(bybitOrderResult);
            results.add(order);
        }

        return results;
    }

}
