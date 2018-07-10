package org.knowm.xchange.coindirect.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coindirect.dto.marketdata.CoindirectOrderbook;
import org.knowm.xchange.coindirect.dto.marketdata.CoindirectTicker;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.service.marketdata.MarketDataService;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class CoindirectMarketDataService extends CoindirectMarketDataServiceRaw implements MarketDataService {
    /**
     * Constructor
     *
     * @param exchange
     */
    public CoindirectMarketDataService(Exchange exchange) {
        super(exchange);
    }

    @Override
    public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
        CoindirectOrderbook coindirectOrderbook = getCoindirectOrderbook(currencyPair);

        List<LimitOrder> bids =
                coindirectOrderbook.bids
                        .stream()
                        .map(e -> new LimitOrder(Order.OrderType.BID, e.size, currencyPair, null, null, e.price))
                        .collect(Collectors.toList());
        List<LimitOrder> asks =
                coindirectOrderbook.asks
                        .stream()
                        .map(e -> new LimitOrder(Order.OrderType.ASK, e.size, currencyPair, null, null, e.price))
                        .collect(Collectors.toList());

        return new OrderBook(null, asks, bids);
    }

    @Override
    public Trades getTrades(CurrencyPair pair, Object... args) throws IOException {

        String history = "1h";

        try {
            if (args[0] != null) history = args[0].toString();
        } catch (Throwable ignored) {
        }

        CoindirectTicker coindirectTicker = getCoindirectTicker(pair, history);

        List<Trade> trades =
                coindirectTicker.data
                        .stream()
                        .map(
                                at ->
                                        new Trade(
                                                Order.OrderType.BID,
                                                at.volume,
                                                pair,
                                                at.price,
                                                new Date(at.time),
                                                Long.toString(at.time)))
                        .collect(Collectors.toList());

        return new Trades(trades, Trades.TradeSortType.SortByTimestamp);
    }
}
