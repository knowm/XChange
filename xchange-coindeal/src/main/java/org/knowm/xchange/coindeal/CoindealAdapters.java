package org.knowm.xchange.coindeal;

import org.knowm.xchange.coindeal.dto.marketdata.CoindealOrderBook;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.trade.LimitOrder;

import java.util.ArrayList;
import java.util.List;

public final class CoindealAdapters {

    /** private Constructor */
    private CoindealAdapters() {}

    public static OrderBook adaptOrderBook(CoindealOrderBook coindealOrderBook, CurrencyPair currencyPair){
        List<LimitOrder> asks = new ArrayList<>();
            coindealOrderBook.getAsks().forEach(coindealOrderBookEntry -> {
            asks.add(new LimitOrder(Order.OrderType.ASK,coindealOrderBookEntry.getAmount(),
                    currencyPair,null,null,coindealOrderBookEntry.getPrice()));
        });
        List<LimitOrder> bids = new ArrayList<>();
        coindealOrderBook.getBids().forEach(coindealOrderBookEntry -> {
            bids.add(new LimitOrder(Order.OrderType.BID,coindealOrderBookEntry.getAmount(),
                    currencyPair,null,null,coindealOrderBookEntry.getPrice()));
        });
        return new OrderBook(null,asks,bids,true);
    }
    public static String adaptCurrencyPair(CurrencyPair currencyPair){
        return currencyPair.toString().replace("/","");
    }

    public static String adaptOrderType(Order.OrderType orderType){
        return orderType.equals(Order.OrderType.ASK) ? "sell" : "buy";
    }
}
