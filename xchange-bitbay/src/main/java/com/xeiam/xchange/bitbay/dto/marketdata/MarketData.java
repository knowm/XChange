package com.xeiam.xchange.bitbay.dto.marketdata;

import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;

/**
 * Created with IntelliJ IDEA.
 * User: Yaroslav
 * Date: 16/12/14
 * Time: 14:56
 */
public class MarketData {

    Ticker ticker;
    OrderBook orderBook;
    Trades trades;

    public MarketData(Ticker ticker, OrderBook orderBook, Trades trades) {
        this.ticker = ticker;
        this.orderBook = orderBook;
        this.trades = trades;
    }

    public Ticker getTicker() {
        return ticker;
    }

    public OrderBook getOrderBook() {
        return orderBook;
    }

    public Trades getTrades() {
        return trades;
    }
}
