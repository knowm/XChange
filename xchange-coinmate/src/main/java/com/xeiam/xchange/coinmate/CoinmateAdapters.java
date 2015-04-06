package com.xeiam.xchange.coinmate;

import com.xeiam.xchange.coinmate.dto.marketdata.CoinmateOrderBook;
import com.xeiam.xchange.coinmate.dto.marketdata.CoinmateOrderBookEntry;
import com.xeiam.xchange.coinmate.dto.marketdata.CoinmateTicker;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.trade.LimitOrder;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Martin Stachon
 */
public class CoinmateAdapters {

    /**
     * Adapts a CoinmateTicker to a Ticker Object
     *
     * @param coinmateTicker The exchange specific ticker
     * @param currencyPair (e.g. BTC/USD)
     * @return The ticker
     */
    public static Ticker adaptTicker(CoinmateTicker coinmateTicker, CurrencyPair currencyPair) {

        BigDecimal last = coinmateTicker.getData().getLast();
        BigDecimal bid = coinmateTicker.getData().getBid();
        BigDecimal ask = coinmateTicker.getData().getAsk();
        BigDecimal high = coinmateTicker.getData().getHigh();
        BigDecimal low = coinmateTicker.getData().getLow();
        BigDecimal volume = coinmateTicker.getData().getAmount();

        return new Ticker.Builder().currencyPair(currencyPair).last(last).bid(bid).ask(ask).high(high).low(low).volume(volume)
                .build();

    }

    public static List<LimitOrder> createOrders(List<CoinmateOrderBookEntry> coinmateOrders, Order.OrderType type, CurrencyPair currencyPair) {
        List<LimitOrder> orders = new ArrayList<LimitOrder>(coinmateOrders.size());
        for (CoinmateOrderBookEntry entry : coinmateOrders) {
            LimitOrder order = new LimitOrder(type, entry.getAmount(), currencyPair, null, null, entry.getPrice());
            orders.add(order);
        }
        return orders;
    }

    public static OrderBook adaptOrderBook(CoinmateOrderBook coinmateOrderBook, CurrencyPair currencyPair) {
        List<LimitOrder> asks = createOrders(coinmateOrderBook.getData().getAsks(), Order.OrderType.ASK, currencyPair);
        List<LimitOrder> bids = createOrders(coinmateOrderBook.getData().getBids(), Order.OrderType.BID, currencyPair);

        Date date = new Date(); //TODO the api does not provide time
        return new OrderBook(date, asks, bids);
    }

}
