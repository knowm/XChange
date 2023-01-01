package info.bitrich.xchangestream.krakenfutures;

import info.bitrich.xchangestream.krakenfutures.dto.KrakenFuturesStreamingOrderBookSnapshotResponse;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.krakenfutures.KrakenFuturesAdapters;

import java.util.ArrayList;
import java.util.List;

public class KrakenFuturesStreamingAdapters {

    public static OrderBook adaptKrakenFuturesSnapshot(KrakenFuturesStreamingOrderBookSnapshotResponse snapshot){
        List<LimitOrder> asks = new ArrayList<>();
        List<LimitOrder> bids = new ArrayList<>();

        snapshot.getBids().forEach(krakenFuturesSnapShotOrder -> bids.add(new LimitOrder.Builder(Order.OrderType.BID, KrakenFuturesAdapters.adaptInstrument(snapshot.getProduct_id().toLowerCase()))
                        .limitPrice(krakenFuturesSnapShotOrder.getPrice())
                        .originalAmount(krakenFuturesSnapShotOrder.getQuantity())
                .build()));
        snapshot.getAsks().forEach(krakenFuturesSnapShotOrder -> asks.add(new LimitOrder.Builder(Order.OrderType.ASK, KrakenFuturesAdapters.adaptInstrument(snapshot.getProduct_id().toLowerCase()))
                .limitPrice(krakenFuturesSnapShotOrder.getPrice())
                .originalAmount(krakenFuturesSnapShotOrder.getQuantity())
                .build()));

        return new OrderBook(snapshot.getTimestamp(), asks, bids);
    }
}
