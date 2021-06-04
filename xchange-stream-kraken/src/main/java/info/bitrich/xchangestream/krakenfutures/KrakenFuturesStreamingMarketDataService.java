package info.bitrich.xchangestream.krakenfutures;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Lists;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.kraken.dto.enums.KrakenSubscriptionName;
import io.reactivex.Observable;
import org.apache.commons.lang3.ArrayUtils;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.krakenfutures.service.KrakenFuturesMarketDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author makarid, pchertalev
 */
public class KrakenFuturesStreamingMarketDataService implements StreamingMarketDataService {

    private static final Logger LOG = LoggerFactory.getLogger(KrakenFuturesStreamingMarketDataService.class);

    private static final int ORDER_BOOK_SIZE_DEFAULT = 25;
    private static final int[] KRAKEN_VALID_ORDER_BOOK_SIZES = {10, 25, 100, 500, 1000};
    private static final int MIN_DATA_ARRAY_SIZE = 4;

    public static final String KRAKEN_CHANNEL_DELIMITER = "-";

    private final KrakenFuturesStreamingService service;
    private final KrakenFuturesMarketDataService marketDataService;

    public KrakenFuturesStreamingMarketDataService(KrakenFuturesStreamingService service, KrakenFuturesMarketDataService marketDataService) {
        this.service = service;
        this.marketDataService = marketDataService;
    }

    @Override
    public Observable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
        String channelName = getChannelName(KrakenSubscriptionName.book, currencyPair);
        int depth = parseOrderBookSize(args);
        Observable<ObjectNode> subscribe = subscribe(channelName, MIN_DATA_ARRAY_SIZE, depth);
        OrderbookSubscription orderbookSubscription = new OrderbookSubscription(subscribe);
        AtomicInteger x = new AtomicInteger();
        return orderbookSubscription.stream

                .doOnNext(objectNode -> orderbookSubscription.initSnapshotIfInvalid(currencyPair))

                .filter(objectNode -> {

                    Integer seqNumber = objectNode.get("seq").asInt();

                    if (orderbookSubscription.lastSequenceNumber + 1 == seqNumber || orderbookSubscription.lastSequenceNumber == 0) {
                        if(x.get() == 100) orderbookSubscription.lastSequenceNumber = seqNumber -10;

                        else orderbookSubscription.lastSequenceNumber = seqNumber;
                        x.incrementAndGet();

                        return true;
                    }
                    orderbookSubscription.lastSequenceNumber = null;
                    return false;
                })

                .map(objectNode -> KrakenFuturesStreamingAdapters.adaptFuturesOrderbookMessage(orderbookSubscription.orderBook, currencyPair, objectNode));
    }

    @Override
    public Observable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
        throw new UnsupportedOperationException("getTicker operation not currently supported");
    }

    @Override
    public Observable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
        throw new NotAvailableFromExchangeException("getTrades operation not supported by Kraken Futures API");
    }

    public Observable<ObjectNode> subscribe(String channelName, int maxItems, Integer depth) {
        return service
                .subscribeChannel(channelName, depth)
                .filter(node -> node instanceof ObjectNode)
                .map(node -> (ObjectNode) node)
                .filter(
                        list -> {
                            if (list.size() < maxItems) {
                                LOG.warn(
                                        "Invalid message in channel {}. It contains {} array items but expected at least {}",
                                        channelName,
                                        list.size(),
                                        maxItems);
                                return false;
                            }
                            return true;
                        });
    }

    public String getChannelName(KrakenSubscriptionName subscriptionName, CurrencyPair currencyPair) {
        String pair = currencyPair.base.toString() + currencyPair.counter.toString();
        return subscriptionName + KRAKEN_CHANNEL_DELIMITER + pair;
    }

    private int parseOrderBookSize(Object[] args) {
        if (args != null && args.length > 0) {
            Object obSizeParam = args[0];
            LOG.debug("Specified Kraken order book size: {}", obSizeParam);
            if (Number.class.isAssignableFrom(obSizeParam.getClass())) {
                int obSize = ((Number) obSizeParam).intValue();
                if (ArrayUtils.contains(KRAKEN_VALID_ORDER_BOOK_SIZES, obSize)) {
                    return obSize;
                }
                LOG.error(
                        "Invalid order book size {}. Valid values: {}. Default order book size has been used: {}",
                        obSize,
                        ArrayUtils.toString(KRAKEN_VALID_ORDER_BOOK_SIZES),
                        ORDER_BOOK_SIZE_DEFAULT);
                return ORDER_BOOK_SIZE_DEFAULT;
            }
            LOG.error(
                    "Order book size param type {} is invalid. Expected: {}. Default order book size has been used {}",
                    obSizeParam.getClass().getName(),
                    Number.class,
                    ORDER_BOOK_SIZE_DEFAULT);
            return ORDER_BOOK_SIZE_DEFAULT;
        }

        LOG.debug(
                "Order book size param has not been specified. Default order book size has been used: {}",
                ORDER_BOOK_SIZE_DEFAULT);
        return ORDER_BOOK_SIZE_DEFAULT;
    }

    private final class OrderbookSubscription {
        final Observable<ObjectNode> stream;
        Integer lastSequenceNumber = 0;
        OrderBook orderBook = new OrderBook(null, Lists.newArrayList(), Lists.newArrayList());

        private OrderbookSubscription(Observable<ObjectNode> stream) {
            this.stream = stream;
        }

        void initSnapshotIfInvalid(CurrencyPair currencyPair) {
            if (lastSequenceNumber != null) return;
            try {
                LOG.info("Fetching orderbook snapshot for {} ", currencyPair);
                orderBook = marketDataService.getOrderBook(currencyPair);

            } catch (Exception e) {
                LOG.error("Failed to fetch order book snapshot for " + currencyPair, e);
                orderBook.getAsks().clear();
                orderBook.getBids().clear();

            } finally {
                lastSequenceNumber = 0;
            }
        }
    }
}
