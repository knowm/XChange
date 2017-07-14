package info.bitrich.xchangestream.bitstamp;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.bitstamp.dto.BitstampOrderBook;
import info.bitrich.xchangestream.bitstamp.dto.BitstampWebSocketTransaction;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.service.pusher.PusherStreamingService;
import io.reactivex.Observable;
import org.knowm.xchange.bitstamp.BitstampAdapters;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;

import java.util.Date;

public class BitstampStreamingMarketDataService implements StreamingMarketDataService {
    private final PusherStreamingService service;

    BitstampStreamingMarketDataService(PusherStreamingService service) {
        this.service = service;
    }

    @Override
    public Observable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
        String channelName = "order_book" + getChannelPostfix(currencyPair);

        return service.subscribeChannel(channelName, "data")
                .map(s -> {
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    BitstampOrderBook orderBook = mapper.readValue(s, BitstampOrderBook.class);
                    org.knowm.xchange.bitstamp.dto.marketdata.BitstampOrderBook bitstampOrderBook = new org.knowm.xchange.bitstamp.dto.marketdata.BitstampOrderBook(new Date().getTime(), orderBook.getBids(), orderBook.getAsks());

                    return BitstampAdapters.adaptOrderBook(bitstampOrderBook, currencyPair);
                });
    }

    @Override
    public Observable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
        // BitStamp has no live ticker, only trades.
        throw new NotAvailableFromExchangeException();
    }

    @Override
    public Observable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
        String channelName = "live_trades" + getChannelPostfix(currencyPair);

        return service.subscribeChannel(channelName, "trade")
                .map(s -> {
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    BitstampWebSocketTransaction transactions = mapper.readValue(s, BitstampWebSocketTransaction.class);
                    transactions = new BitstampWebSocketTransaction(new Date().getTime()/1000L, transactions.getTid(),
                            transactions.getPrice(), transactions.getAmount(), transactions.getType());
                    return BitstampAdapters.adaptTrade(transactions, currencyPair, 1000);
                });
    }

    private String getChannelPostfix(CurrencyPair currencyPair) {
        if (currencyPair.equals(CurrencyPair.BTC_USD)) {
            return "";
        }
        return "_" + currencyPair.base.toString().toLowerCase() + currencyPair.counter.toString().toLowerCase();
    }
}
