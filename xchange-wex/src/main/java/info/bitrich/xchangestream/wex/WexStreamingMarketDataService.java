package info.bitrich.xchangestream.wex;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import info.bitrich.xchangestream.service.pusher.PusherStreamingService;
import info.bitrich.xchangestream.wex.dto.WexOrderbook;
import info.bitrich.xchangestream.wex.dto.WexWebSocketTransaction;
import io.reactivex.Observable;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lukas Zaoralek on 16.11.17.
 */
public class WexStreamingMarketDataService implements StreamingMarketDataService {
    private static final Logger LOG = LoggerFactory.getLogger(WexStreamingMarketDataService.class);

    private final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();

    private final PusherStreamingService service;
    private final MarketDataService marketDataService;

    private final Map<CurrencyPair, WexOrderbook> orderbooks = new HashMap<>();

    WexStreamingMarketDataService(PusherStreamingService service, MarketDataService marketDataService) {
        this.service = service;
        this.marketDataService = marketDataService;
    }

    @Override
    public Observable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
        String channelName = currencyPair.base.toString().toLowerCase() + "_" +
                currencyPair.counter.toString().toLowerCase() + ".depth";
        Observable<OrderBook> orderbookSnapshot = Observable.create(e -> {
            OrderBook orderbook = marketDataService.getOrderBook(currencyPair);
            orderbooks.put(currencyPair, new WexOrderbook(orderbook));
            e.onNext(orderbook);
        });

        return service.subscribeChannel(channelName, "depth").filter(s -> orderbooks.containsKey(currencyPair))
                .map(s -> {
                    WexWebSocketTransaction transaction = mapper.readValue(s, WexWebSocketTransaction.class);
                    WexOrderbook orderbook = orderbooks.get(currencyPair);
                    LimitOrder[] levels = transaction.toOrderbookUpdate(currencyPair);
                    orderbook.updateLevels(levels);
                    return orderbook.toOrderbook();
                }).mergeWith(orderbookSnapshot);
    }

    @Override
    public Observable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
        return null;
    }

    @Override
    public Observable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
        String channelName = currencyPair.base.toString().toLowerCase() + "_" +
                currencyPair.counter.toString().toLowerCase() + ".trades";

        return service.subscribeChannel(channelName, "trades")
                .flatMapIterable(s -> {
                    JsonNode trades = mapper.readTree(s);
                    return Arrays.asList(WexWebSocketTransaction.toTrades(trades, currencyPair));
                });
    }
}
