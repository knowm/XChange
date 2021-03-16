package info.bitrich.xchangestream.gemini;

import com.google.common.base.MoreObjects;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import io.reactivex.rxjava3.core.Flowable;
import java.math.BigDecimal;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;

/** Adapted from V1 by Max Gao on 01-09-2021 */
public class GeminiStreamingMarketDataService implements StreamingMarketDataService {
  private static final String L2_UPDATES = "l2_updates";
  private final GeminiStreamingService service;

  private final Map<CurrencyPair, SortedMap<BigDecimal, BigDecimal>> bids =
      new ConcurrentHashMap<>();
  private final Map<CurrencyPair, SortedMap<BigDecimal, BigDecimal>> asks =
      new ConcurrentHashMap<>();

  public GeminiStreamingMarketDataService(GeminiStreamingService service) {
    this.service = service;
  }

  @Override
  public Flowable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
    if (!service.getProduct().getOrderBook().stream()
        .anyMatch(pair -> pair.compareTo(currencyPair) == 0)) {
      throw new UnsupportedOperationException(
          String.format("The currency pair %s is not subscribed for orderbook", currencyPair));
    }

    int maxDepth = (int) MoreObjects.firstNonNull(args.length > 0 ? args[0] : null, 1);

    return service
        .getRawWebSocketTransactions(currencyPair, false)
        .filter(message -> (L2_UPDATES).equals(message.getType()))
        .map(
            message -> {
              bids.computeIfAbsent(
                  currencyPair, k -> new TreeMap<>(java.util.Collections.reverseOrder()));
              asks.computeIfAbsent(currencyPair, k -> new TreeMap<>());
              return message.toOrderBook(
                  bids.get(currencyPair), asks.get(currencyPair), maxDepth, currencyPair);
            });
  }

  @Override
  public Flowable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
    throw new NotYetImplementedForExchangeException("Not Yet Implemented!");
  }

  @Override
  public Flowable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
    throw new NotYetImplementedForExchangeException("Not Yet Implemented!");
  }
}
