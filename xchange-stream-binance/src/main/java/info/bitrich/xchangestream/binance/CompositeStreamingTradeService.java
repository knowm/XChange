package info.bitrich.xchangestream.binance;

import info.bitrich.xchangestream.core.StreamingTradeService;
import io.reactivex.Observable;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.instrument.Instrument;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CompositeStreamingTradeService implements StreamingTradeService {
    private final List<StreamingTradeService> tradeServices;

    public CompositeStreamingTradeService(StreamingTradeService... tradeServices) {
        this.tradeServices = Stream.of(tradeServices).filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public Observable<Order> getOrderChanges(CurrencyPair currencyPair, Object... args) {
        return Observable.merge(tradeServices.stream().map(s -> s.getOrderChanges(currencyPair, args))
                .collect(Collectors.toList()));
    }

    @Override
    public Observable<Order> getOrderChanges(Instrument instrument, Object... args) {
        return Observable.merge(tradeServices.stream().map(s -> s.getOrderChanges(instrument, args))
                .collect(Collectors.toList()));
    }

    @Override
    public Observable<UserTrade> getUserTrades(CurrencyPair currencyPair, Object... args) {
        return Observable.merge(tradeServices.stream().map(s -> s.getUserTrades(currencyPair, args))
                .collect(Collectors.toList()));
    }

    @Override
    public Observable<UserTrade> getUserTrades(Instrument instrument, Object... args) {
        return Observable.merge(tradeServices.stream().map(s -> s.getUserTrades(instrument, args))
                .collect(Collectors.toList()));
    }
}
