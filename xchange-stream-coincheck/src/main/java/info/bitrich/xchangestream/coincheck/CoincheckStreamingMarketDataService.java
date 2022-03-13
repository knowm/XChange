package info.bitrich.xchangestream.coincheck;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.coincheck.dto.CoincheckSubscriptionNames;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.stream.Stream;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.coincheck.CoincheckExchange;
import org.knowm.xchange.coincheck.dto.marketdata.CoincheckPair;
import org.knowm.xchange.coincheck.service.CoincheckMarketDataService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.OrderBookUpdate;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.trade.LimitOrder;

@Slf4j
@RequiredArgsConstructor
public class CoincheckStreamingMarketDataService implements StreamingMarketDataService {

  private final CoincheckStreamingService service;
  private final Runnable onApiCall;

  private final CoincheckMarketDataService marketDataService =
      (CoincheckMarketDataService)
          ExchangeFactory.INSTANCE.createExchange(CoincheckExchange.class).getMarketDataService();
  private final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();

  @Override
  public Observable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
    String channelName = getChannelName(CoincheckSubscriptionNames.ORDERBOOK, currencyPair);

    return Single.fromCallable(() -> marketDataService.getOrderBook(currencyPair))
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.computation())
        .flatMapObservable(
            orderBook -> {
              return service
                  .subscribeChannel(channelName)
                  .map(
                      json -> {
                        Stream<OrderBookUpdate> updates =
                            CoincheckStreamingAdapter.parseOrderBookUpdates(json);
                        updates.forEach(orderBook::update);
                        return new OrderBook(null, orderBook.getAsks(), orderBook.getBids());
                      });
            });
  }

  @Override
  public Observable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
    return getOrderBook(currencyPair, args)
        .map(
            book -> {
              LimitOrder lowestAsk = book.getAsks().get(0);
              LimitOrder highestBid = book.getBids().get(0);
              return new Ticker.Builder()
                  .instrument(currencyPair)
                  .timestamp(book.getTimeStamp())
                  .ask(lowestAsk == null ? null : lowestAsk.getLimitPrice())
                  .bid(highestBid == null ? null : highestBid.getLimitPrice())
                  .build();
            });
  }

  @Override
  public Observable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
    String channelName = getChannelName(CoincheckSubscriptionNames.TRADES, currencyPair);

    return service
        .subscribeChannel(channelName)
        .map(json -> CoincheckStreamingAdapter.parseTrade(json));
  }

  private String getChannelName(
      @NonNull CoincheckSubscriptionNames name, @NonNull CurrencyPair currencyPair) {
    return CoincheckPair.pairToString(new CoincheckPair(currencyPair)) + "-" + name.getName();
  }
}
