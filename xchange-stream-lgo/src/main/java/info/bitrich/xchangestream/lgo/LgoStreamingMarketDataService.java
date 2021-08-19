package info.bitrich.xchangestream.lgo;

import info.bitrich.xchangestream.core.StreamingMarketDataService;
import io.reactivex.Observable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.*;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;

public class LgoStreamingMarketDataService implements StreamingMarketDataService {

  private final LgoStreamingService service;
  private final Map<CurrencyPair, LgoLevel2BatchSubscription> level2Subscriptions =
      new ConcurrentHashMap<>();
  private final Map<CurrencyPair, LgoTradeBatchSubscription> tradeSubscriptions =
      new ConcurrentHashMap<>();

  LgoStreamingMarketDataService(LgoStreamingService lgoStreamingService) {
    service = lgoStreamingService;
  }

  @Override
  public Observable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
    return level2Subscriptions
        .computeIfAbsent(currencyPair, this::createLevel2Subscription)
        .getSubscription();
  }

  private LgoLevel2BatchSubscription createLevel2Subscription(CurrencyPair currencyPair) {
    return LgoLevel2BatchSubscription.create(service, currencyPair);
  }

  @Override
  public Observable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
    return tradeSubscriptions
        .computeIfAbsent(currencyPair, this::createTradeSubscription)
        .getSubscription();
  }

  private LgoTradeBatchSubscription createTradeSubscription(CurrencyPair currencyPair) {
    return LgoTradeBatchSubscription.create(service, currencyPair);
  }

  @Override
  public Observable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
    throw new NotYetImplementedForExchangeException();
  }
}
