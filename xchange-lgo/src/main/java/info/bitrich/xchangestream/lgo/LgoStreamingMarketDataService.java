package info.bitrich.xchangestream.lgo;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.lgo.dto.LgoLevel2Update;
import info.bitrich.xchangestream.lgo.dto.LgoTradesUpdate;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.Observable;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;

import java.math.BigDecimal;
import java.util.*;

import static java.util.stream.Collectors.toList;

public class LgoStreamingMarketDataService implements StreamingMarketDataService {

    private final LgoStreamingService service;
    private final Map<CurrencyPair, SortedMap<BigDecimal, BigDecimal>> bids = new HashMap<>();
    private final Map<CurrencyPair, SortedMap<BigDecimal, BigDecimal>> asks = new HashMap<>();
    private final Map<CurrencyPair, Observable<OrderBook>> level2Subscriptions = new HashMap<>();
    private final Map<CurrencyPair, Observable<Trade>> tradeSubscriptions = new HashMap<>();

    public LgoStreamingMarketDataService(LgoStreamingService lgoStreamingService) {
        service = lgoStreamingService;
    }

    @Override
    public Observable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
        if (!level2Subscriptions.containsKey(currencyPair)) {
            createLevel2Subscription(currencyPair);
        }
        return level2Subscriptions.get(currencyPair).share();
    }

    private void createLevel2Subscription(CurrencyPair currencyPair) {
        final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();

        Observable<OrderBook> observable = service
                .subscribeChannel(LgoAdapter.channelName("level2", currencyPair))
                .map(s -> mapper.readValue(s.toString(), LgoLevel2Update.class))
                .map(s -> {
                    if (s.getType().equals("snapshot")) {
                        bids.put(currencyPair, new TreeMap<>(Collections.reverseOrder()));
                        asks.put(currencyPair, new TreeMap<>());
                    }

                    updateL2Book(s, bids.get(currencyPair), asks.get(currencyPair));
                    return LgoAdapter.adaptOrderBook(bids.get(currencyPair), asks.get(currencyPair), currencyPair);
                });
        level2Subscriptions.put(currencyPair, observable);
    }

    private void updateL2Book(LgoLevel2Update data, SortedMap<BigDecimal, BigDecimal> bidSide, SortedMap<BigDecimal, BigDecimal> askSide) {
        data.getData().getAsks().forEach(ask -> updateSide(askSide, ask));
        data.getData().getBids().forEach(bid -> updateSide(bidSide, bid));
    }

    private void updateSide(SortedMap<BigDecimal, BigDecimal> side, List<String> value) {
        BigDecimal price = new BigDecimal(value.get(0));
        String quantity = value.get(1);
        if (isZero(quantity)) {
            side.remove(price);
            return;
        }
        BigDecimal qtt = new BigDecimal(quantity);
        side.put(price, side.getOrDefault(price, BigDecimal.ZERO).add(qtt));
    }

    private boolean isZero(String quantity) {
        return quantity.replaceAll("[\\.0]", "").isEmpty();
    }

    @Override
    public Observable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
        if (!tradeSubscriptions.containsKey(currencyPair)) {
            createTradeSubscription(currencyPair);
        }
        return tradeSubscriptions.get(currencyPair).share();
    }

    private void createTradeSubscription(CurrencyPair currencyPair) {
        final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();
        Observable<Trade> observable = service
                .subscribeChannel(LgoAdapter.channelName("trades", currencyPair))
                .map(s -> mapper.readValue(s.toString(), LgoTradesUpdate.class))
                .map(LgoTradesUpdate::getTrades)
                .flatMap(list -> Observable.fromIterable(list.stream()
                        .map(lgoTrade -> LgoAdapter.adaptTrade(currencyPair, lgoTrade))
                        .collect(toList())));
        tradeSubscriptions.put(currencyPair, observable);
    }

    @Override
    public Observable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
        throw new NotYetImplementedForExchangeException();
    }
}
