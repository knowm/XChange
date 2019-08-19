package info.bitrich.xchangestream.lgo;

import info.bitrich.xchangestream.lgo.domain.*;
import info.bitrich.xchangestream.lgo.dto.LgoAckUpdateData;
import info.bitrich.xchangestream.lgo.dto.LgoTrade;
import info.bitrich.xchangestream.lgo.dto.LgoUserUpdateData;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.UserTrade;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.SortedMap;

import static java.util.stream.Collectors.toList;

public class LgoAdapter {

    static String channelName(String name, CurrencyPair currencyPair) {
        return name + "-" + currencyPair.base.toString() + "-" + currencyPair.counter.toString();
    }

    static OrderBook adaptOrderBook(SortedMap<BigDecimal, BigDecimal> bidSide, SortedMap<BigDecimal, BigDecimal> askSide, CurrencyPair currencyPair) {
        List<LimitOrder> bidOrders =
                bidSide.entrySet().stream()
                        .map(entry -> createOrder(entry.getKey(), entry.getValue(), Order.OrderType.BID, currencyPair))
                        .collect(toList());
        List<LimitOrder> askOrders =
                askSide.entrySet().stream()
                        .map(entry -> createOrder(entry.getKey(), entry.getValue(), Order.OrderType.ASK, currencyPair))
                        .collect(toList());
        return new OrderBook(null, askOrders, bidOrders);
    }

    private static LimitOrder createOrder(BigDecimal price, BigDecimal qtt, Order.OrderType type, CurrencyPair currencyPair) {
        return new LimitOrder(type, qtt, currencyPair, "0", null, price);
    }

    static Trade adaptTrade(CurrencyPair currencyPair, LgoTrade lgoTrade) {
        return new Trade(
                parseTradeType(lgoTrade),
                lgoTrade.getQuantity(),
                currencyPair,
                lgoTrade.getPrice(),
                lgoTrade.getCreationTime(),
                lgoTrade.getId());
    }

    static List<Balance> adaptBalances(List<List<String>> data) {
        return data.stream().map(balance -> {
            Currency currency = Currency.getInstance(balance.get(0));
            BigDecimal available = new BigDecimal(balance.get(1));
            BigDecimal escrow = new BigDecimal(balance.get(2));
            BigDecimal total = available.add(escrow);
            return new Balance(currency, total, available, escrow);
        }).collect(toList());
    }

    private static Order.OrderType parseTradeType(LgoTrade lgoTrade) {
        //the XChange API requires the taker order type whereas the LGO API gives the maker order type
        return lgoTrade.getSide().equals("B") ? Order.OrderType.ASK : Order.OrderType.BID;
    }

    private static Order.OrderType parseOrderType(LgoUserUpdateData orderEvent) {
        return orderEvent.getSide().equals("B") ? Order.OrderType.BID : Order.OrderType.ASK;
    }

    private static Order.OrderType parseTradeType(LgoUserUpdateData updateData, Order order) {
        //the XChange API requires the taker order type
        return takerBuyer(updateData, order) || makerSeller(updateData, order) ? Order.OrderType.BID : Order.OrderType.ASK;
    }

    private static boolean takerBuyer(LgoUserUpdateData updateData, Order order) {
        return order.getType().equals(Order.OrderType.BID) && updateData.getLiquidity().equals("T");
    }

    private static boolean makerSeller(LgoUserUpdateData updateData, Order order) {
        return order.getType().equals(Order.OrderType.ASK) && updateData.getLiquidity().equals("M");
    }

    static Collection<LimitOrder> adaptOrdersSnapshot(List<LgoUserUpdateData> orderEvents, CurrencyPair currencyPair) {
        return orderEvents.stream().map(orderEvent -> {
            Order.OrderStatus status = orderEvent.getQuantity().equals(orderEvent.getRemainingQuantity()) ? Order.OrderStatus.NEW : Order.OrderStatus.PARTIALLY_FILLED;
            return new LimitOrder.Builder(parseOrderType(orderEvent), currencyPair)
                    .id(orderEvent.getOrderId())
                    .originalAmount(orderEvent.getQuantity())
                    .remainingAmount(orderEvent.getRemainingQuantity())
                    .limitPrice(orderEvent.getPrice())
                    .orderStatus(status)
                    .timestamp(orderEvent.getOrderCreationTime())
                    .build();
        }).collect(toList());
    }

    static Order adaptPendingOrder(LgoUserUpdateData orderEvent, CurrencyPair currencyPair) {
        if (orderEvent.getOrderType().equals("L")) {
            return adaptPendingLimitOrder(orderEvent, currencyPair);
        }
        return adaptPendingMarketOrder(orderEvent, currencyPair);
    }

    static LimitOrder adaptPendingLimitOrder(LgoUserUpdateData orderEvent, CurrencyPair currencyPair) {
        return new LimitOrder.Builder(parseOrderType(orderEvent), currencyPair)
                .id(orderEvent.getOrderId())
                .originalAmount(orderEvent.getQuantity())
                .remainingAmount(orderEvent.getQuantity())
                .limitPrice(orderEvent.getPrice())
                .orderStatus(Order.OrderStatus.PENDING_NEW)
                .timestamp(orderEvent.getTime())
                .build();
    }

    private static MarketOrder adaptPendingMarketOrder(LgoUserUpdateData orderEvent, CurrencyPair currencyPair) {
        return new MarketOrder.Builder(parseOrderType(orderEvent), currencyPair)
                .id(orderEvent.getOrderId())
                .originalAmount(orderEvent.getQuantity())
                .cumulativeAmount(BigDecimal.ZERO)
                .orderStatus(Order.OrderStatus.PENDING_NEW)
                .timestamp(orderEvent.getTime())
                .build();
    }

    static List<LgoOrderEvent> adaptOrderEvent(List<LgoUserUpdateData> data, Long batchId, List<Order> updatedOrders) {
        List<LgoOrderEvent> events = new ArrayList<>();
        for (LgoUserUpdateData orderEvent : data) {
            switch (orderEvent.getType()) {
                case "pending":
                    events.add(new LgoPendingOrderEvent(batchId, orderEvent.getType(), orderEvent.getOrderId(), orderEvent.getTime(), orderEvent.getOrderType(), orderEvent.getPrice(), parseOrderType(orderEvent), orderEvent.getQuantity()));
                    break;
                case "open":
                    events.add(new LgoOpenOrderEvent(batchId, orderEvent.getType(), orderEvent.getOrderId(), orderEvent.getTime()));
                    break;
                case "match":
                    Order.OrderType tradeType = updatedOrders.stream().filter(order -> order.getId().equals(orderEvent.getOrderId())).findFirst().map(order -> parseTradeType(orderEvent, order)).orElse(null);
                    events.add(new LgoMatchOrderEvent(batchId, orderEvent.getType(), orderEvent.getOrderId(), orderEvent.getTime(), orderEvent.getTradeId(), orderEvent.getPrice(), orderEvent.getFilledQuantity(), orderEvent.getRemainingQuantity(), orderEvent.getFees(), orderEvent.getLiquidity(), tradeType));
                    break;
                case "invalid":
                    events.add(new LgoInvalidOrderEvent(batchId, orderEvent.getType(), orderEvent.getOrderId(), orderEvent.getTime(), orderEvent.getReason()));
                    break;
                case "done":
                    events.add(new LgoDoneOrderEvent(batchId, orderEvent.getType(), orderEvent.getOrderId(), orderEvent.getTime(), orderEvent.getReason(), orderEvent.getCanceled()));
                    break;
            }
        }
        return events;
    }

    static LgoOrderEvent adaptReceivedEvent(LgoAckUpdateData data) {
        if (data.getType().equals("received")) {
            return new LgoReceivedOrderEvent(data.getType(), data.getOrderId(), data.getTime(), data.getReference());
        }
        return new LgoFailedOrderEvent(data.getType(), data.getOrderId(), data.getTime(), data.getReference(), data.getReason());
    }

    static UserTrade adaptUserTrade(CurrencyPair currencyPair, LgoMatchOrderEvent event) {
        return new UserTrade(event.getOrderType(), event.getFilledQuantity(), currencyPair, event.getTradePrice(), event.getTime(), event.getTradeId(), event.getOrderId(), event.getFees(), currencyPair.counter);
    }
}
