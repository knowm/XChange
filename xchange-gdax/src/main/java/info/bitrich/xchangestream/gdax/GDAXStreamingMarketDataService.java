package info.bitrich.xchangestream.gdax;

import static io.netty.util.internal.StringUtil.isNullOrEmpty;
import static org.knowm.xchange.gdax.GDAXAdapters.adaptOrderBook;
import static org.knowm.xchange.gdax.GDAXAdapters.adaptTicker;
import static org.knowm.xchange.gdax.GDAXAdapters.adaptTradeHistory;
import static org.knowm.xchange.gdax.GDAXAdapters.adaptTrades;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.gdax.dto.marketdata.GDAXProductBook;
import org.knowm.xchange.gdax.dto.marketdata.GDAXProductTicker;
import org.knowm.xchange.gdax.dto.marketdata.GDAXTrade;
import org.knowm.xchange.gdax.dto.trade.GDAXFill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.gdax.dto.GDAXWebSocketTransaction;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.Observable;

/**
 * Created by luca on 4/3/17.
 */
public class GDAXStreamingMarketDataService implements StreamingMarketDataService {
    private static final Logger LOG = LoggerFactory.getLogger(GDAXStreamingMarketDataService.class);

    private final GDAXStreamingService service;
    private final Map<CurrencyPair, SortedMap<BigDecimal, String>> bids = new HashMap<>();
    private final Map<CurrencyPair, SortedMap<BigDecimal, String>> asks = new HashMap<>();

    GDAXStreamingMarketDataService(GDAXStreamingService service) {
        this.service = service;
    }

    private boolean containsPair(List<CurrencyPair> pairs, CurrencyPair pair) {
        for (CurrencyPair item : pairs) {
            if (item.compareTo(pair) == 0) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Observable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
        if (!containsPair(service.getProduct().getOrderBook(), currencyPair))
            throw new UnsupportedOperationException(String.format("The currency pair %s is not subscribed for orderbook", currencyPair));

        String channelName = currencyPair.base.toString() + "-" + currencyPair.counter.toString();

        final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();

        final int maxDepth = (args.length > 0 && args[0] instanceof Integer) ? (int) args[0] : 100;

        Observable<GDAXWebSocketTransaction> subscribedChannel = service.subscribeChannel(channelName)
                .map(s -> mapper.readValue(s.toString(), GDAXWebSocketTransaction.class));

        return subscribedChannel
                .filter(message -> !isNullOrEmpty(message.getType()) &&
                        (message.getType().equals("snapshot") || message.getType().equals("l2update")) &&
                        message.getProductId().equals(channelName))
                .map(s -> {
                    if (s.getType().equals("snapshot")) {
                        bids.put(currencyPair, new TreeMap<>(java.util.Collections.reverseOrder()));
                        asks.put(currencyPair, new TreeMap<>());
                    }

                    GDAXProductBook productBook = s.toGDAXProductBook(bids.get(currencyPair), asks.get(currencyPair), maxDepth);
                    return adaptOrderBook(productBook, currencyPair);
                });
    }

    /**
     * Returns an Observable of {@link GDAXProductTicker}, not converted to {@link Ticker}
     *
     * @param currencyPair the currency pair.
     * @param args         optional arguments.
     * @return an Observable of {@link GDAXProductTicker}.
     */
    public Observable<GDAXProductTicker> getRawTicker(CurrencyPair currencyPair, Object... args) {
        if (!containsPair(service.getProduct().getTicker(), currencyPair))
            throw new UnsupportedOperationException(String.format("The currency pair %s is not subscribed for ticker", currencyPair));

        String channelName = currencyPair.base.toString() + "-" + currencyPair.counter.toString();

        final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();

        Observable<GDAXWebSocketTransaction> subscribedChannel = service.subscribeChannel(channelName)
                .map(s -> mapper.readValue(s.toString(), GDAXWebSocketTransaction.class));

        return subscribedChannel
                .filter(message -> !isNullOrEmpty(message.getType()) && message.getType().equals("match") &&
                        message.getProductId().equals(channelName))
                .map(GDAXWebSocketTransaction::toGDAXProductTicker);
    }

    /**
     * Returns the GDAX ticker converted to the normalized XChange object.
     * GDAX does not directly provide ticker data via web service.
     * As stated by: https://docs.gdax.com/#get-product-ticker, we can just listen for 'match' messages.
     *
     * @param currencyPair Currency pair of the ticker
     * @param args         optional parameters.
     * @return an Observable of normalized Ticker objects.
     */
    @Override
    public Observable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
        if (!containsPair(service.getProduct().getTicker(), currencyPair))
            throw new UnsupportedOperationException(String.format("The currency pair %s is not subscribed for ticker", currencyPair));

        String channelName = currencyPair.base.toString() + "-" + currencyPair.counter.toString();

        final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();

        Observable<GDAXWebSocketTransaction> subscribedChannel = service.subscribeChannel(channelName)
                .map(s -> mapper.readValue(s.toString(), GDAXWebSocketTransaction.class));

        return subscribedChannel
                .filter(message -> !isNullOrEmpty(message.getType()) && message.getType().equals("ticker") &&
                        message.getProductId().equals(channelName))
                .map(s -> adaptTicker(s.toGDAXProductTicker(), s.toGDAXProductStats(), currencyPair));
    }

    @Override
    public Observable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
        if (!containsPair(service.getProduct().getTrades(), currencyPair))
            throw new UnsupportedOperationException(String.format("The currency pair %s is not subscribed for trades", currencyPair));

        String channelName = currencyPair.base.toString() + "-" + currencyPair.counter.toString();

        final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();

        Observable<GDAXWebSocketTransaction> subscribedChannel = service.subscribeChannel(channelName)
                .map(s -> mapper.readValue(s.toString(), GDAXWebSocketTransaction.class));

        return subscribedChannel
                .filter(message -> !isNullOrEmpty(message.getType()) && message.getType().equals("match") &&
                        message.getProductId().equals(channelName))
                .map(s -> {
                            Trades adaptedTrades = null;
                            if ( s.getUserId() != null )
                                adaptedTrades = adaptTradeHistory(new GDAXFill[]{s.toGDAXFill()});
                            else
                                adaptedTrades = adaptTrades(new GDAXTrade[]{s.toGDAXTrade()}, currencyPair);
                            return adaptedTrades.getTrades().get(0);
                        }
                );
    }
}
