package info.bitrich.xchangestream.kucoin;

import info.bitrich.xchangestream.kucoin.dto.KucoinOrderEventData;
import info.bitrich.xchangestream.kucoin.dto.KucoinWebSocketOrderEvent;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class KucoinStreamingAdapters {
    public static Order adaptOrder(KucoinWebSocketOrderEvent orderEvent) {
        KucoinOrderEventData data = orderEvent.data;

        Order.OrderType orderType = "buy".equals(data.side) ? Order.OrderType.BID : Order.OrderType.ASK;
        CurrencyPair currencyPair = data.getCurrencyPair();

        Order.Builder orderBuilder =
                "market".equals(data.orderType) ? new MarketOrder.Builder(orderType, currencyPair) :
                new LimitOrder.Builder(orderType, currencyPair).limitPrice(new BigDecimal(data.price));

        orderBuilder
                .id(data.orderId)
                .originalAmount(new BigDecimal(data.size))
                .timestamp(new Date(TimeUnit.NANOSECONDS.toMillis(data.orderTime)))
                .cumulativeAmount(new BigDecimal(data.filledSize))
                .orderStatus(adaptStatus(data.status))
        ;

        return orderBuilder.build();
    }

    private static Order.OrderStatus adaptStatus(String status) {
        if ("open".equals(status))
            return Order.OrderStatus.NEW;
        if ("match".equals(status))
            return Order.OrderStatus.PARTIALLY_FILLED;
        if ("done".equals(status))
            return Order.OrderStatus.FILLED;
        return null;
    }
}
