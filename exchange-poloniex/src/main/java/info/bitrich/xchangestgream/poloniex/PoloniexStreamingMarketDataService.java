package info.bitrich.xchangestgream.poloniex;

import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.wamp.WampStreamingService;
import io.reactivex.Observable;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;

public class PoloniexStreamingMarketDataService implements StreamingMarketDataService {
    private final WampStreamingService streamingService;

    public PoloniexStreamingMarketDataService(WampStreamingService streamingService) {
        this.streamingService = streamingService;
    }

    @Override
    public Observable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
        throw new NotYetImplementedForExchangeException();
    }

    @Override
    public Observable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
        return streamingService.subscribeChannel("ticker")
                .map(pubSubData -> {
                    // TODO parse ticket data in pubSubData.arguments();
                    return new Ticker.Builder().build();
                });
    }

    @Override
    public Observable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
        throw new NotYetImplementedForExchangeException();
    }
}
