package info.bitrich.xchangestream.okcoin;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.okcoin.dto.OkCoinOrderbook;
import info.bitrich.xchangestream.okcoin.dto.OkCoinWebSocketTrade;
import io.reactivex.Observable;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.okcoin.OkCoinAdapters;
import org.knowm.xchange.okcoin.dto.marketdata.OkCoinDepth;
import org.knowm.xchange.okcoin.dto.marketdata.OkCoinTicker;
import org.knowm.xchange.okcoin.dto.marketdata.OkCoinTickerResponse;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class OkCoinStreamingMarketDataService implements StreamingMarketDataService {
    private final OkCoinStreamingService service;

    private final ObjectMapper mapper = new ObjectMapper();
    private final Map<CurrencyPair, OkCoinOrderbook> orderbooks = new HashMap<>();

    OkCoinStreamingMarketDataService(OkCoinStreamingService service) {
        this.service = service;
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public Observable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
        String channel = String.format("ok_sub_spot_%s_%s_depth", currencyPair.base.toString().toLowerCase(), currencyPair.counter.toString().toLowerCase());

        return service.subscribeChannel(channel)
                .map(s -> {
                    OkCoinOrderbook okCoinOrderbook;
                    if (!orderbooks.containsKey(currencyPair)) {
                        OkCoinDepth okCoinDepth = mapper.treeToValue(s.get("data"), OkCoinDepth.class);
                        okCoinOrderbook = new OkCoinOrderbook(okCoinDepth);
                        orderbooks.put(currencyPair, okCoinOrderbook);
                    } else {
                        okCoinOrderbook = orderbooks.get(currencyPair);
                        if (s.get("data").has("asks")) {
                          if (s.get("data").get("asks").size() > 0) {
                            BigDecimal[][] askLevels = mapper.treeToValue(s.get("data").get("asks"), BigDecimal[][].class);
                            okCoinOrderbook.updateLevels(askLevels, Order.OrderType.ASK);
                          }
                        }

                        if (s.get("data").has("bids")) {
                          if (s.get("data").get("bids").size() > 0) {
                            BigDecimal[][] bidLevels = mapper.treeToValue(s.get("data").get("bids"), BigDecimal[][].class);
                            okCoinOrderbook.updateLevels(bidLevels, Order.OrderType.BID);
                          }
                        }
                    }

                    return OkCoinAdapters.adaptOrderBook(okCoinOrderbook.toOkCoinDepth(s.get("data").get("timestamp").asLong()), currencyPair);
                });
    }

    @Override
    public Observable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
        String channel = String.format("ok_sub_spot_%s_%s_ticker", currencyPair.base.toString().toLowerCase(), currencyPair.counter.toString().toLowerCase());

        return service.subscribeChannel(channel)
                .map(s -> {
                    // TODO: fix parsing of BigDecimal attribute val that has format: 1,625.23
                    OkCoinTicker okCoinTicker = mapper.treeToValue(s.get("data"), OkCoinTicker.class);
                    return OkCoinAdapters.adaptTicker(new OkCoinTickerResponse(okCoinTicker), currencyPair);
                });
    }

    @Override
    public Observable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
        String channel = String.format("ok_sub_spot_%s_%s_deals", currencyPair.base.toString().toLowerCase(), currencyPair.counter.toString().toLowerCase());

        return service.subscribeChannel(channel)
                .map(s -> {
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
