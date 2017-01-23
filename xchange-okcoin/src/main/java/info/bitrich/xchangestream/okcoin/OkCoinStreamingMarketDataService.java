package info.bitrich.xchangestream.okcoin;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.okcoin.dto.OkCoinWebSocketTrade;
import io.reactivex.Observable;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.okcoin.OkCoinAdapters;
import org.knowm.xchange.okcoin.dto.marketdata.OkCoinDepth;
import org.knowm.xchange.okcoin.dto.marketdata.OkCoinTicker;
import org.knowm.xchange.okcoin.dto.marketdata.OkCoinTickerResponse;

public class OkCoinStreamingMarketDataService implements StreamingMarketDataService {
    private final OkCoinStreamingService service;

    OkCoinStreamingMarketDataService(OkCoinStreamingService service) {
        this.service = service;
    }

    @Override
    public Observable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
        String channel = String.format("ok_%s%s_depth", currencyPair.base.toString().toLowerCase(), currencyPair.counter.toString().toLowerCase());

        return service.subscribeChannel(channel)
                .map(s -> {
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    OkCoinDepth okCoinDepth = mapper.treeToValue(s.get("data"), OkCoinDepth.class);
                    return OkCoinAdapters.adaptOrderBook(okCoinDepth, currencyPair);
                });
    }

    @Override
    public Observable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
        String channel = String.format("ok_%s%s_ticker", currencyPair.base.toString().toLowerCase(), currencyPair.counter.toString().toLowerCase());

        return service.subscribeChannel(channel)
                .map(s -> {
                    ObjectMapper mapper = new ObjectMapper();
                    // TODO: fix parsing of BigDecimal attribute val that has format: 1,625.23
                    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    OkCoinTicker okCoinTicker = mapper.treeToValue(s.get("data"), OkCoinTicker.class);
                    return OkCoinAdapters.adaptTicker(new OkCoinTickerResponse(okCoinTicker), currencyPair);
                });
    }

    @Override
    public Observable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
        String channel = String.format("ok_%s%s_trades", currencyPair.base.toString().toLowerCase(), currencyPair.counter.toString().toLowerCase());

        return service.subscribeChannel(channel)
                .map(s -> {
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    String[][] trades = mapper.treeToValue(s.get("data"), String[][].class);

                    // I don't know how to parse this array of arrays in Jacson.
                    OkCoinWebSocketTrade[] okCoinTrades = new OkCoinWebSocketTrade[trades.length];
                    for (int i = 0; i < trades.length; ++i) {
                        OkCoinWebSocketTrade okCoinWebSocketTrade = new OkCoinWebSocketTrade(trades[i]);
                        okCoinTrades[i] = okCoinWebSocketTrade;
                    }

                    return OkCoinAdapters.adaptTrades(okCoinTrades, currencyPair);
                }).flatMapIterable(Trades::getTrades);
    }
}
