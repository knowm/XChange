package info.bitrich.xchangestream.krakenfutures;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.krakenfutures.dto.KrakenFuturesStreamingOrderBookDeltaResponse;
import info.bitrich.xchangestream.krakenfutures.dto.KrakenFuturesStreamingOrderBookSnapshotResponse;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.Observable;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.krakenfutures.KrakenFuturesAdapters;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class KrakenFuturesStreamingMarketDataService implements StreamingMarketDataService {

    private final ObjectMapper objectMapper = StreamingObjectMapperHelper.getObjectMapper();
    private final KrakenFuturesStreamingService service;

    private final Map<Instrument, OrderBook> orderBookMap = new HashMap<>();

    public KrakenFuturesStreamingMarketDataService(KrakenFuturesStreamingService service) {
        this.service = service;
    }

    @Override
    public Observable<OrderBook> getOrderBook(Instrument instrument, Object... args) {
        String channelName = service.ORDERBOOK + KrakenFuturesAdapters.adaptKrakenFuturesSymbol(instrument);
        return service.subscribeChannel(channelName)
                .map(message-> {
                    if(message.has("feed")){
                        if(message.get("feed").asText().contains("book_snapshot")){
                            orderBookMap.put(instrument, KrakenFuturesStreamingAdapters.adaptKrakenFuturesSnapshot(objectMapper.treeToValue(message, KrakenFuturesStreamingOrderBookSnapshotResponse.class)));
                        } else if(message.get("feed").asText().contains("book")){
                            KrakenFuturesStreamingOrderBookDeltaResponse delta = objectMapper.treeToValue(message, KrakenFuturesStreamingOrderBookDeltaResponse.class);
                            orderBookMap.get(instrument).update(new LimitOrder.Builder((delta.getSide().equals(KrakenFuturesStreamingOrderBookDeltaResponse.KrakenFuturesStreamingSide.sell))
                                    ? Order.OrderType.ASK
                                    : Order.OrderType.BID,instrument)
                                            .limitPrice(delta.getPrice())
                                            .originalAmount(delta.getQty())
                                    .build());
                        }
                    }
                    if(orderBookMap.get(instrument).getBids().get(0).getLimitPrice().compareTo(orderBookMap.get(instrument).getAsks().get(0).getLimitPrice()) > 0){
                        throw new IOException("OrderBook crossed!!!");
                    }

                    return orderBookMap.get(instrument);
                });
    }
}
