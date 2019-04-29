package org.knowm.xchange.deribit.v2;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.deribit.v2.dto.marketdata.DeribitOrderBook;
import org.knowm.xchange.deribit.v2.dto.marketdata.DeribitTicker;
import org.knowm.xchange.deribit.v2.dto.marketdata.DeribitTrade;
import org.knowm.xchange.deribit.v2.dto.marketdata.DeribitTrades;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.utils.DateUtils;

import java.util.List;
import java.util.stream.Collectors;

public class DeribitAdapters {

    public static CurrencyPair adaptCurrencyPair(String instrumentName){
        String[] temp = instrumentName.split("-", 2);
        return new CurrencyPair(temp[0], temp[1]);
    }

    public static Ticker adaptTicker(DeribitTicker deribitTicker){

        return new Ticker.Builder()
                .currencyPair(adaptCurrencyPair(deribitTicker.getInstrumentName()))
                .open(deribitTicker.getOpenInterest())
                .last(deribitTicker.getLastPrice())
                .bid(deribitTicker.getBestBidPrice())
                .ask(deribitTicker.getBestAskPrice())
                .high(deribitTicker.getMaxPrice())
                .low(deribitTicker.getMinPrice())
                .volume(deribitTicker.getStats().getVolume())
                .bidSize(deribitTicker.getBestBidAmount())
                .askSize(deribitTicker.getBestAskAmount())
                .timestamp(DateUtils.fromUnixTime(deribitTicker.getTimestamp()))
                .build();
    }

    public static OrderBook adaptOrderBook(DeribitOrderBook deribitOrderBook) {

        CurrencyPair pair = adaptCurrencyPair(deribitOrderBook.getInstrumentName());

        List<LimitOrder> bids = deribitOrderBook.getBids()
                .stream()
                .map(bid -> new LimitOrder(Order.OrderType.BID, bid.get(1), pair, null, null, bid.get(0)))
                .collect(Collectors.toList());

        List<LimitOrder> asks = deribitOrderBook.getAsks()
                .stream()
                .map(ask -> new LimitOrder(Order.OrderType.ASK, ask.get(1), pair, null, null, ask.get(0)))
                .collect(Collectors.toList());

        return new OrderBook(DateUtils.fromUnixTime(deribitOrderBook.getTimestamp()), asks, bids);
    }

    public static Trade adaptTrade(DeribitTrade deribitTrade) {

        Order.OrderType type = null;
        String direction = deribitTrade.getDirection();

        if(direction.equals("buy")) {
            type = Order.OrderType.BID;
        } else if(direction.equals("sell")) {
            type = Order.OrderType.ASK;
        }

         return new Trade(
                 type,
                 deribitTrade.getAmount(),
                 adaptCurrencyPair(deribitTrade.getInstrumentName()),
                 deribitTrade.getPrice(),
                 DateUtils.fromUnixTime(deribitTrade.getTimestamp()),
                 deribitTrade.getTradeId()
        );
    }

    public static Trades adaptTrades(DeribitTrades deribitTrades) {

        return new Trades(
                deribitTrades.getTrades()
                        .stream()
                        .map(trade -> adaptTrade(trade))
                        .collect(Collectors.toList())
        );
    }

}

