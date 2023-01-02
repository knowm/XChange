package info.bitrich.xchangestream.krakenfutures;

import info.bitrich.xchangestream.krakenfutures.dto.KrakenFuturesStreamingOrderBookDeltaResponse;
import info.bitrich.xchangestream.krakenfutures.dto.KrakenFuturesStreamingOrderBookSnapshotResponse;
import info.bitrich.xchangestream.krakenfutures.dto.KrakenFuturesStreamingTickerResponse;
import info.bitrich.xchangestream.krakenfutures.dto.KrakenFuturesStreamingTradeResponse;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.FundingRate;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.krakenfutures.KrakenFuturesAdapters;

import java.math.BigDecimal;
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

    public static Ticker adaptTicker(KrakenFuturesStreamingTickerResponse tickerResponse) {
        return new Ticker.Builder()
                .instrument(KrakenFuturesAdapters.adaptInstrument(tickerResponse.getProduct_id().toLowerCase()))
                .ask(tickerResponse.getAsk())
                .bid(tickerResponse.getBid())
                .last(tickerResponse.getLast())
                .volume(tickerResponse.getVolume())
                .timestamp(tickerResponse.getTime())
                .quoteVolume(tickerResponse.getVolumeQuote())
                .percentageChange(tickerResponse.getChange())
                .askSize(tickerResponse.getAsk_size())
                .bidSize(tickerResponse.getBid_size())
                .build();
    }

    public static FundingRate adaptFundingRate(KrakenFuturesStreamingTickerResponse tickerResponse) {
        return new FundingRate.Builder()
                .instrument(KrakenFuturesAdapters.adaptInstrument(tickerResponse.getProduct_id().toLowerCase()))
                .fundingRate1h(tickerResponse.getRelative_funding_rate())
                .fundingRate8h(tickerResponse.getRelative_funding_rate().multiply(BigDecimal.valueOf(8)))
                .fundingRateDate(tickerResponse.getNextFundingRateTime())
                .build();
    }

    public static Trade adaptTrade(KrakenFuturesStreamingTradeResponse trade) {
        return new Trade.Builder()
                        .price(trade.getPrice())
                        .instrument(KrakenFuturesAdapters.adaptInstrument(trade.getProduct_id().toLowerCase()))
                        .timestamp(trade.getTime())
                        .type((trade.getSide().equals(KrakenFuturesStreamingOrderBookDeltaResponse.KrakenFuturesStreamingSide.sell) ? Order.OrderType.ASK : Order.OrderType.BID))
                        .id(trade.getUid())
                        .originalAmount(trade.getQty())
                .build();
    }
}
