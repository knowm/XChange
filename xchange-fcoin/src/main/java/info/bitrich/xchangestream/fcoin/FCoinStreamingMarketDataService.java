package info.bitrich.xchangestream.fcoin;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import io.reactivex.Observable;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.fcoin.FCoinAdapters;
import org.knowm.xchange.fcoin.dto.marketdata.FCoinDepth;

public class FCoinStreamingMarketDataService implements StreamingMarketDataService {
    protected final FCoinStreamingService service;

    private final ObjectMapper mapper = new ObjectMapper();

    FCoinStreamingMarketDataService(FCoinStreamingService service) {
        this.service = service;
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public Observable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
        String channel = String.format("depth.L20.%s%s", currencyPair.base.toString().toLowerCase(), currencyPair.counter.toString().toLowerCase());
        return this.service.subscribeChannel(channel).map((s) -> {
            FCoinDepth fCoinDepth = this.mapper.treeToValue(s, FCoinDepth.class);
            return FCoinAdapters.adaptOrderBook(fCoinDepth, currencyPair);
        });
    }

    @Override
    public Observable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
        throw new NotYetImplementedForExchangeException();
    }

    @Override
    public Observable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
        throw new NotYetImplementedForExchangeException();
    }

}
