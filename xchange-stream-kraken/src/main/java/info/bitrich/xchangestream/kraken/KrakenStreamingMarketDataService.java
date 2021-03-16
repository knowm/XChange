package info.bitrich.xchangestream.kraken;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.common.collect.Lists;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.kraken.dto.enums.KrakenSubscriptionName;
import io.reactivex.Flowable;
import org.apache.commons.lang3.ArrayUtils;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author makarid, pchertalev
 */
public class KrakenStreamingMarketDataService implements StreamingMarketDataService {

    private static final Logger LOG = LoggerFactory.getLogger(KrakenStreamingMarketDataService.class);

    private static final int ORDER_BOOK_SIZE_DEFAULT = 25;
    private static final int[] KRAKEN_VALID_ORDER_BOOK_SIZES = {10, 25, 100, 500, 1000};
    private static final int MIN_DATA_ARRAY_SIZE = 4;

    public static final String KRAKEN_CHANNEL_DELIMITER = "-";

    private final KrakenStreamingService service;

    public KrakenStreamingMarketDataService(KrakenStreamingService service) {
        this.service = service;
    }

    @Override
    public Flowable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
        String channelName = getChannelName(KrakenSubscriptionName.book, currencyPair);
        OrderBook orderBook = new OrderBook(null, Lists.newArrayList(), Lists.newArrayList());
        int depth = parseOrderBookSize(args);
        return subscribe(channelName, MIN_DATA_ARRAY_SIZE, depth)
                .map(arrayNode -> KrakenStreamingAdapters.adaptOrderbookMessage(orderBook, currencyPair, arrayNode));
    }

    @Override
    public Flowable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
        String channelName = getChannelName(KrakenSubscriptionName.ticker, currencyPair);
        return subscribe(channelName, MIN_DATA_ARRAY_SIZE, null)
                .map(arrayNode -> KrakenStreamingAdapters.adaptTickerMessage(currencyPair, arrayNode));
    }

    @Override
    public Flowable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
        String channelName = getChannelName(KrakenSubscriptionName.trade, currencyPair);
        return subscribe(channelName, MIN_DATA_ARRAY_SIZE, null)
                .flatMap(arrayNode -> Flowable.fromIterable(KrakenStreamingAdapters.adaptTrades(currencyPair, arrayNode)));
    }


    public Flowable<ArrayNode> subscribe(String channelName, int maxItems, Integer depth) {
        return service
                .subscribeChannel(channelName, depth)
                .filter(node -> node instanceof ArrayNode)
                .map(node -> (ArrayNode) node)
                .filter(
                        list -> {
                            if (list.size() < maxItems) {
                                LOG.warn("Invalid message in channel {}. It contains {} array items but expected at least {}", channelName, list.size(), maxItems);
                                return false;
                            }
                            return true;
                        });
    }

    public String getChannelName(KrakenSubscriptionName subscriptionName, CurrencyPair currencyPair) {
        String pair = currencyPair.base.toString() + "/" + currencyPair.counter.toString();
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
}
