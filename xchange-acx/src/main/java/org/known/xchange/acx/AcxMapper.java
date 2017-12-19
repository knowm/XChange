package org.known.xchange.acx;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.known.xchange.acx.dto.AcxTrade;
import org.known.xchange.acx.dto.marketdata.AcxOrder;
import org.known.xchange.acx.dto.marketdata.AcxOrderBook;
import org.known.xchange.acx.dto.marketdata.AcxTicker;
import org.known.xchange.acx.dto.marketdata.AcxMarket;

import java.util.List;
import java.util.stream.Collectors;

public class AcxMapper {
    public Ticker mapTicker(CurrencyPair currencyPair, AcxMarket tickerData) {
        AcxTicker ticker = tickerData.ticker;
        return new Ticker.Builder()
                .currencyPair(currencyPair)
                .timestamp(tickerData.at)
                .ask(ticker.sell)
                .bid(ticker.buy)
                .open(ticker.open)
                .low(ticker.low)
                .high(ticker.high)
                .last(ticker.last)
                .volume(ticker.vol)
                .build();
    }

    public OrderBook mapOrderBook(CurrencyPair currencyPair, AcxOrderBook orderBook) {
        return new OrderBook(null,
                mapLimitOrders(currencyPair, OrderType.ASK, orderBook.asks),
                mapLimitOrders(currencyPair, OrderType.BID, orderBook.bids));
    }

    private List<LimitOrder> mapLimitOrders(CurrencyPair currencyPair, OrderType orderType, List<AcxOrder> orders) {
        return orders.stream()
                .map(o -> mapOrder(orderType, currencyPair, o))
                .collect(Collectors.toList());
    }

    private LimitOrder mapOrder(OrderType orderType, CurrencyPair currencyPair, AcxOrder order) {
        return new LimitOrder.Builder(orderType, currencyPair)
                .id(order.id)
                .limitPrice(order.price)
                .averagePrice(order.avgPrice)
                .timestamp(order.createdAt)
                .originalAmount(order.volume)
                .remainingAmount(order.remainingVolume)
                .cumulativeAmount(order.executedVolume)
                .orderStatus(mapOrderStatus(order.state))
                .build();
    }

    private OrderStatus mapOrderStatus(String state) {
        switch (state) {
            case "wait":
                return OrderStatus.PENDING_NEW;
            case "done":
                return OrderStatus.FILLED;
            case "cancel":
                return OrderStatus.CANCELED;
        }
        return null;
    }

    public Trades mapTrades(CurrencyPair currencyPair, List<AcxTrade> trades) {
        return new Trades(trades.stream()
                .map(t -> mapTrade(currencyPair, t))
                .collect(Collectors.toList()), TradeSortType.SortByTimestamp);
    }

    private Trade mapTrade(CurrencyPair currencyPair, AcxTrade trade) {
        return new Trade.Builder()
                .currencyPair(currencyPair)
                .id(trade.id)
                .price(trade.price)
                .originalAmount(trade.volume)
                .timestamp(trade.createdAt)
                .type(mapTradeType(trade.side))
                .build();
    }

    private OrderType mapTradeType(String side) {
        if ("sell".equals(side)) {
            return OrderType.ASK;
        } else if ("buy".equals(side)) {
            return OrderType.BID;
        }
        return null;
    }
}
