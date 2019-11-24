package info.bitrich.xchangestream.poloniex2;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.poloniex2.dto.*;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.Observable;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.*;

import static org.knowm.xchange.poloniex.PoloniexAdapters.adaptPoloniexDepth;
import static org.knowm.xchange.poloniex.PoloniexAdapters.adaptPoloniexTicker;

/**
 * Created by Lukas Zaoralek on 10.11.17.
 */
public class PoloniexStreamingMarketDataService implements StreamingMarketDataService {
    private static final Logger LOG = LoggerFactory.getLogger(PoloniexStreamingMarketDataService.class);

    private final PoloniexStreamingService service;
    private final Map<CurrencyPair, Integer> currencyPairMap;

    public PoloniexStreamingMarketDataService(PoloniexStreamingService service, Map<CurrencyPair, Integer> currencyPairMap) {
        this.service = service;
        this.currencyPairMap = currencyPairMap;
    }

    @Override
    public Observable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
        Observable<PoloniexOrderbook> subscribedOrderbook = service.subscribeCurrencyPairChannel(currencyPair)
                .scan(
                        Optional.empty(),
                        (Optional<PoloniexOrderbook> orderbook, List<PoloniexWebSocketEvent> poloniexWebSocketEvents) ->
                                poloniexWebSocketEvents.stream()
                                        .filter(s ->
                                                s instanceof PoloniexWebSocketOrderbookInsertEvent
                                                        || s instanceof PoloniexWebSocketOrderbookModifiedEvent
                                        )
                                        .reduce(
                                                orderbook,
                                                (poloniexOrderbook, s) -> getPoloniexOrderbook(orderbook, s),
                                                (o1, o2) -> {
                                                    throw new UnsupportedOperationException("No parallel execution");
                                                }
                                        )
                )
                .filter(Optional::isPresent)
                .map(Optional::get);

        return subscribedOrderbook.map(s -> adaptPoloniexDepth(s.toPoloniexDepth(), currencyPair));
    }

    @Override
    public Observable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
        final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();

        int currencyPairId = currencyPairMap.getOrDefault(currencyPair, 0);
        Observable<PoloniexWebSocketTickerTransaction> subscribedChannel = service.subscribeChannel("1002")
                .map(s -> mapper.readValue(s.toString(), PoloniexWebSocketTickerTransaction.class));

        return subscribedChannel
                .filter(s -> s.getPairId() == currencyPairId)
                .map(s -> adaptPoloniexTicker(s.toPoloniexTicker(currencyPair), currencyPair));
    }

    @Override
    public Observable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
        Observable<PoloniexWebSocketTradeEvent> subscribedTrades = service.subscribeCurrencyPairChannel(currencyPair)
                .flatMapIterable(poloniexWebSocketEvents -> poloniexWebSocketEvents)
                .filter(PoloniexWebSocketTradeEvent.class::isInstance)
                .map(PoloniexWebSocketTradeEvent.class::cast)
                .share();

        return subscribedTrades
                .map(s -> PoloniexWebSocketAdapter.convertPoloniexWebSocketTradeEventToTrade(s, currencyPair));
    }

    private Optional<PoloniexOrderbook> getPoloniexOrderbook(final Optional<PoloniexOrderbook> orderbook,
                                                             final PoloniexWebSocketEvent s) {
        if (s.getEventType().equals("i")) {
            OrderbookInsertEvent insertEvent = ((PoloniexWebSocketOrderbookInsertEvent) s).getInsert();
            SortedMap<BigDecimal, BigDecimal> asks = insertEvent.toDepthLevels(OrderbookInsertEvent.ASK_SIDE);
            SortedMap<BigDecimal, BigDecimal> bids = insertEvent.toDepthLevels(OrderbookInsertEvent.BID_SIDE);
            return Optional.of(new PoloniexOrderbook(asks, bids));
        } else {
            OrderbookModifiedEvent modifiedEvent = ((PoloniexWebSocketOrderbookModifiedEvent) s).getModifiedEvent();
            orderbook.orElseThrow(() -> new IllegalStateException("Orderbook update received before initial snapshot"))
                    .modify(modifiedEvent);
            return orderbook;
        }
    }
}
