package info.bitrich.xchangestream.bitmex;

import info.bitrich.xchangestream.bitmex.dto.BitmexLimitOrder;
import info.bitrich.xchangestream.bitmex.dto.BitmexOrderbook;
import info.bitrich.xchangestream.bitmex.dto.BitmexTicker;
import info.bitrich.xchangestream.bitmex.dto.BitmexTrade;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import io.reactivex.Observable;
import org.knowm.xchange.bitmex.BitmexContract;
import org.knowm.xchange.bitmex.BitmexPrompt;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by Lukas Zaoralek on 13.11.17.
 */
public class BitmexStreamingMarketDataService implements StreamingMarketDataService {
    private static final Logger LOG = LoggerFactory.getLogger(BitmexStreamingMarketDataService.class);

    protected final BitmexStreamingService streamingService;

    private final SortedMap<String, BitmexOrderbook> orderbooks = new TreeMap<>();

    public BitmexStreamingMarketDataService(BitmexStreamingService streamingService) {
        this.streamingService = streamingService;
    }

    private String getBitmexSymbol(CurrencyPair currencyPair, Object... args) {
        if (args.length > 0) {
            BitmexPrompt prompt = (BitmexPrompt) args[0];
            BitmexContract contract = new BitmexContract(currencyPair, prompt);
            return BitmexUtils.translateBitmexContract(contract);
        } else {
            return currencyPair.base.toString() + currencyPair.counter.toString();
        }
    }

    @Override
    public Observable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
        String instrument = getBitmexSymbol(currencyPair, args);
        String channelName = String.format("orderBookL2:%s", instrument);

        return streamingService.subscribeBitmexChannel(channelName).map(s -> {
            BitmexOrderbook orderbook;
            String action = s.getAction();
            if (action.equals("partial")) {
                orderbook = s.toBitmexOrderbook();
                orderbooks.put(instrument, orderbook);
            } else {
                orderbook = orderbooks.get(instrument);
                //ignore updates until first "partial"
                if (orderbook == null) {
                    return null;
                }
                BitmexLimitOrder[] levels = s.toBitmexOrderbookLevels();
                orderbook.updateLevels(levels, action);
            }

            return orderbook.toOrderbook();
        });
    }

    public Observable<BitmexTicker> getRawTicker(CurrencyPair currencyPair, Object... args) {
        String instrument = getBitmexSymbol(currencyPair, args);
        String channelName = String.format("quote:%s", instrument);

        return streamingService.subscribeBitmexChannel(channelName).map(s -> s.toBitmexTicker());
    }

    @Override
    public Observable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
        String instrument = getBitmexSymbol(currencyPair, args);
        String channelName = String.format("quote:%s", instrument);

        return streamingService.subscribeBitmexChannel(channelName).map(s -> {
            BitmexTicker bitmexTicker = s.toBitmexTicker();
            return bitmexTicker.toTicker();
        });
    }

    @Override
    public Observable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
        String instrument = getBitmexSymbol(currencyPair, args);
        String channelName = String.format("trade:%s", instrument);

        return streamingService.subscribeBitmexChannel(channelName).flatMapIterable(s -> {
            BitmexTrade[] bitmexTrades = s.toBitmexTrades();
            List<Trade> trades = new ArrayList<>(bitmexTrades.length);
            for (BitmexTrade bitmexTrade : bitmexTrades) {
                trades.add(bitmexTrade.toTrade());
            }
            return trades;
        });
    }

}
