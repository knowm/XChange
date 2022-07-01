package info.bitrich.xchangestream.dydx.service.v3;

import static info.bitrich.xchangestream.dydx.dydxStreamingService.CHANNEL_DATA;
import static info.bitrich.xchangestream.dydx.dydxStreamingService.SUBSCRIBED;
import static info.bitrich.xchangestream.dydx.dydxStreamingService.V3_ORDERBOOK;

import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.dydx.dto.v3.dydxInitialOrderBookMessage;
import info.bitrich.xchangestream.dydx.dto.v3.dydxUpdateOrderBookMessage;
import info.bitrich.xchangestream.dydx.dydxStreamingService;
import io.reactivex.Observable;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;

/** Author: Max Gao (gaamox@tutanota.com) Created: 20-02-2021 */
public class dydxStreamingMarketDataService implements StreamingMarketDataService {

  private final dydxStreamingService service;

  private final Map<CurrencyPair, SortedMap<BigDecimal, BigDecimal>> bids =
      new ConcurrentHashMap<>();
  private final Map<CurrencyPair, SortedMap<BigDecimal, BigDecimal>> asks =
      new ConcurrentHashMap<>();

  public dydxStreamingMarketDataService(dydxStreamingService service) {
    this.service = service;
  }

  private boolean containsPair(List<CurrencyPair> pairs, CurrencyPair pair) {
    return pairs.stream().anyMatch(p -> p.equals(pair));
  }

  @Override
  public Observable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
    if (!containsPair(service.getProduct().getOrderBook(), currencyPair))
      throw new UnsupportedOperationException(
          String.format("The currency pair %s is not subscribed for orderbook", currencyPair));

    final int maxDepth =
        (args.length > 0 && args[0] instanceof Number) ? ((Number) args[0]).intValue() : 100;

    return service
        .getRawWebsocketTransactions(currencyPair, V3_ORDERBOOK)
        .map(
            message -> {
              bids.computeIfAbsent(currencyPair, k -> new TreeMap<>(Comparator.reverseOrder()));
              asks.computeIfAbsent(currencyPair, k -> new TreeMap<>());

              switch (message.getType()) {
                case SUBSCRIBED:
                  return ((dydxInitialOrderBookMessage) message)
                      .toOrderBook(
                          bids.get(currencyPair), asks.get(currencyPair), maxDepth, currencyPair);
                case CHANNEL_DATA:
                  return ((dydxUpdateOrderBookMessage) message)
                      .toOrderBook(
                          bids.get(currencyPair), asks.get(currencyPair), maxDepth, currencyPair);
                default:
                  throw new UnsupportedOperationException(
                      String.format(
                          "Unknown message type detected in OrderBook message: %s,",
                          message.getType()));
              }
            });
  }

  @Override
  public Observable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
    throw new NotYetImplementedForExchangeException("Not yet implemented!");
  }

  @Override
  public Observable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
    throw new NotYetImplementedForExchangeException("Not yet implemented!");
  }
}
