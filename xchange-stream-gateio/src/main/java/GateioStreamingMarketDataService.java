import dto.response.GateioOrderBookResponse;
import dto.response.GateioTradesResponse;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import io.reactivex.Observable;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/** Author: Max Gao (gaamox@tutanota.com) Created: 05-05-2021 */
public class GateioStreamingMarketDataService implements StreamingMarketDataService {
  private static final Logger LOGGER =
      LoggerFactory.getLogger(GateioStreamingMarketDataService.class);
  private static final int MAX_DEPTH_DEFAULT = 5;
  private static final int UPDATE_INTERVAL_DEFAULT = 100;

  private final GateioStreamingService service;

  public GateioStreamingMarketDataService(GateioStreamingService service) {
    this.service = service;
  }

  private boolean containsPair(List<CurrencyPair> pairs, CurrencyPair pair) {
    return pairs.stream().anyMatch(p -> p.equals(pair));
  }

  /**
   * Uses the limited-level snapshot method:
   * https://www.gate.io/docs/apiv4/ws/index.html#limited-level-full-order-book-snapshot
   *
   * @param currencyPair Currency pair of the order book
   * @param args Optional maxDepth, Optional msgInterval
   * @return
   */
  @Override
  public Observable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
    if (!containsPair(service.getProduct().getOrderBook(), currencyPair))
      throw new UnsupportedOperationException(
          String.format("The currency pair %s is not subscribed for orderbook", currencyPair));

    final int maxDepth =
        (args.length > 0 && args[0] instanceof Number)
            ? ((Number) args[0]).intValue()
            : MAX_DEPTH_DEFAULT;
    final int msgInterval =
        (args.length > 1 && args[1] instanceof Number)
            ? ((Number) args[1]).intValue()
            : UPDATE_INTERVAL_DEFAULT;

    return service
        .getRawWebSocketTransactions(
            currencyPair,
            GateioStreamingService.SPOT_ORDERBOOK_CHANNEL,
            new Integer(msgInterval),
            new Integer(maxDepth))
        .map(msg -> ((GateioOrderBookResponse) msg).toOrderBook(currencyPair));
  }

  @Override
  public Observable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
    throw new NotYetImplementedForExchangeException("Not yet implemented!");
  }

  @Override
  public Observable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
    return service
        .getRawWebSocketTransactions(currencyPair, GateioStreamingService.SPOT_TRADES_CHANNEL)
        .map(msg -> ((GateioTradesResponse) msg).toTrade());
  }
}
