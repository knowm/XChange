package org.knowm.xchange.coindirect.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coindirect.CoindirectAdapters;
import org.knowm.xchange.coindirect.dto.trade.CoindirectOrder;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CoindirectTradeService extends CoindirectTradeServiceRaw implements TradeService {
    /**
     * Constructor
     *
     * @param exchange
     */
    public CoindirectTradeService(Exchange exchange) {
        super(exchange);
    }

    @Override
    public OpenOrders getOpenOrders() throws IOException {
        List<CoindirectOrder> coindirectOrders = listExchangeOrders(null, false, 0, 1000);
        List<LimitOrder> limitOrders = new ArrayList<>();
        List<Order> otherOrders = new ArrayList<>();
        coindirectOrders.forEach(
                coindirectOrder -> {
                    Order order = CoindirectAdapters.adaptOrder(coindirectOrder);
                    if (order instanceof LimitOrder) {
                        limitOrders.add((LimitOrder) order);
                    } else {
                        otherOrders.add(order);
                    }
                });
        return new OpenOrders(limitOrders, otherOrders);
    }

    @Override
    public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
        List<CoindirectOrder> coindirectOrders = listExchangeOrders(null, true, 0, 1000);

        List<UserTrade> trades =
                coindirectOrders
                        .stream()
                        .map(
                                t -> {
                                        if(t.executedAmount == null || t.executedAmount.signum() == 0) {
                                            return null;
                                        }
                                        return new UserTrade(
                                                CoindirectAdapters.convert(t.side),
                                                t.executedAmount,
                                                CoindirectAdapters.toCurrencyPair(t.symbol),
                                                t.executedPrice,
                                                t.dateCreated,
                                                t.uuid,
                                                t.uuid,
                                                BigDecimal.ZERO,
                                                CoindirectAdapters.toCurrencyPair(t.symbol).counter
                                        );
                                })
                        .filter(t -> t != null)
                        .collect(Collectors.toList());

        return new UserTrades(trades, Trades.TradeSortType.SortByTimestamp);
    }
}
