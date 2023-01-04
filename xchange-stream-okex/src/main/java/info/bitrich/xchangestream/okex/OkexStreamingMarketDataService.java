package info.bitrich.xchangestream.okex;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.Observable;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.FundingRate;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.okex.OkexAdapters;
import org.knowm.xchange.okex.dto.marketdata.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static info.bitrich.xchangestream.okex.OkexStreamingService.*;

public class OkexStreamingMarketDataService implements StreamingMarketDataService {

    private static final Logger LOG = LoggerFactory.getLogger(OkexStreamingMarketDataService.class);

    private final OkexStreamingService service;

    private final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();

    public OkexStreamingMarketDataService(OkexStreamingService service) {
        this.service = service;
    }

    private final Map<String, OrderBook> orderBookMap = new HashMap<>();
    @Override
    public Observable<Ticker> getTicker(Instrument instrument, Object... args) {
        String channelUniqueId = TICKERS+OkexAdapters.adaptInstrument(instrument);

        return service
                .subscribeChannel(channelUniqueId)
                .filter(message-> message.has("data"))
                .flatMap(jsonNode -> {
                    List<OkexTicker> okexTickers = mapper.treeToValue(jsonNode.get("data"), mapper.getTypeFactory().constructCollectionType(List.class, OkexTicker.class));
                    return Observable.fromIterable(okexTickers).map(OkexAdapters::adaptTicker);
                });
    }

    @Override
    public Observable<Trade> getTrades(Instrument instrument, Object... args) {
        String channelUniqueId = TRADES+OkexAdapters.adaptInstrument(instrument);

        return service
            .subscribeChannel(channelUniqueId)
            .filter(message-> message.has("data"))
            .flatMap(jsonNode -> {
                List<OkexTrade> okexTradeList = mapper.treeToValue(jsonNode.get("data"), mapper.getTypeFactory().constructCollectionType(List.class, OkexTrade.class));
                return Observable.fromIterable(OkexAdapters.adaptTrades(okexTradeList, instrument).getTrades());
            });
    }

    @Override
    public Observable<FundingRate> getFundingRate(Instrument instrument, Object... args) {
        String channelUniqueId = FUNDING_RATE+OkexAdapters.adaptInstrument(instrument);

        return service
                .subscribeChannel(channelUniqueId)
                .filter(message-> message.has("data"))
                .map(jsonNode -> {
                    List<OkexFundingRate> okexFundingRates = mapper.treeToValue(jsonNode.get("data"), mapper.getTypeFactory().constructCollectionType(List.class, OkexFundingRate.class));
                    return OkexAdapters.adaptFundingRate(okexFundingRates);
                });
    }

    @Override
    public Observable<OrderBook> getOrderBook(Instrument instrument, Object... args) {
        String instId = OkexAdapters.adaptInstrument(instrument);
        String channelName = args.length >= 1 ? args[0].toString() : "books";
        String channelUniqueId = ORDERBOOK+instId;

        return service
                .subscribeChannel(channelUniqueId)
                .filter(message-> message.has("action"))
                .flatMap(jsonNode -> {
                    // "books5" channel pushes 5 depth levels every time.
                    String action = channelName.equals(ORDERBOOK5) ? "snapshot" : jsonNode.get("action").asText();
                    if ("snapshot".equalsIgnoreCase(action)) {
                        List<OkexOrderbook> okexOrderbooks = mapper.treeToValue(jsonNode.get("data"), mapper.getTypeFactory().constructCollectionType(List.class, OkexOrderbook.class));
                        OrderBook orderBook = OkexAdapters.adaptOrderBook(okexOrderbooks, instrument);
                        orderBookMap.put(instId, orderBook);
                        return Observable.just(orderBook);
                    } else if ("update".equalsIgnoreCase(action)) {
                        OrderBook orderBook = orderBookMap.getOrDefault(instId, null);
                        if (orderBook == null) {
                            LOG.error(String.format("Failed to get orderBook, instId=%s.", instId));
                            return Observable.fromIterable(new LinkedList<>());
                        }
                        List<OkexPublicOrder> asks = mapper.treeToValue(jsonNode.get("data").get(0).get("asks"), mapper.getTypeFactory().constructCollectionType(List.class, OkexPublicOrder.class));
                        asks.forEach(okexPublicOrder -> orderBook.update(OkexAdapters.adaptLimitOrder(okexPublicOrder, instrument, Order.OrderType.ASK)));

                        List<OkexPublicOrder> bids = mapper.treeToValue(jsonNode.get("data").get(0).get("bids"), mapper.getTypeFactory().constructCollectionType(List.class, OkexPublicOrder.class));
                        bids.forEach(okexPublicOrder -> orderBook.update(OkexAdapters.adaptLimitOrder(okexPublicOrder, instrument, Order.OrderType.BID)));

                        return Observable.just(orderBook);

                    } else {
                        LOG.error(String.format("Unexpected books action=%s, message=%s", action, jsonNode));
                        return Observable.fromIterable(new LinkedList<>());
                    }
                });
    }
}
