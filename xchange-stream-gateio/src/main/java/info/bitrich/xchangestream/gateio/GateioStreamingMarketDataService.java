package info.bitrich.xchangestream.gateio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import info.bitrich.xchangestream.gateio.dto.response.GateioOrderBookResponse;
import info.bitrich.xchangestream.gateio.dto.response.GateioTickerResponse;
import info.bitrich.xchangestream.gateio.dto.response.GateioTradesResponse;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.Observable;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.instrument.Instrument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/** Author: Max Gao (gaamox@tutanota.com) Created: 05-05-2021 */
public class GateioStreamingMarketDataService implements StreamingMarketDataService {
  private static final Logger LOGGER =
      LoggerFactory.getLogger(GateioStreamingMarketDataService.class);
  private final GateioStreamingService service;
  private final Supplier<Observable<Ticker>> streamingTickers;
  private final Map<CurrencyPair, Integer> numberOfTickerObservers = new ConcurrentHashMap<>();

  public GateioStreamingMarketDataService(GateioStreamingService service, List<CurrencyPair> currencyPairs) {
    this.service = service;
    final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();
      streamingTickers = Suppliers.memoize(() -> service
        .subscribeChannel(GateioStreamingService.SPOT_TICKERS_CHANNEL, currencyPairs.toArray(new Object[0]))
          .map(s -> {
            GateioTickerResponse ticker = mapper.treeToValue(s, GateioTickerResponse.class);
              return ticker.toTicker();
          })
          .share());
  }

  private boolean containsPair(List<Instrument> pairs, CurrencyPair pair) {
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

    return service
        .getRawWebSocketTransactions(currencyPair, GateioStreamingService.SPOT_ORDERBOOK_CHANNEL)
        .map(msg -> ((GateioOrderBookResponse) msg).toOrderBook(currencyPair));
  }
  @Override
  public Observable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
    numberOfTickerObservers.compute(currencyPair, (k, v) -> (v == null) ? 1 : ++v);
    return streamingTickers.get().filter(ticker -> ticker.getInstrument().equals(currencyPair)).doOnDispose(() -> {
      if (numberOfTickerObservers.computeIfPresent(currencyPair, (k, v) -> (v > 1) ? --v : null) == null) {
        service.sendMessage(service.getUnsubscribeMessage(GateioStreamingService.SPOT_TICKERS_CHANNEL, currencyPair));
        if(numberOfTickerObservers.isEmpty()) {
          service.removeTickerChannel();
        }
      }
    });
  }

  @Override
  public Observable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
    return service
        .getRawWebSocketTransactions(currencyPair, GateioStreamingService.SPOT_TRADES_CHANNEL)
        .map(msg -> ((GateioTradesResponse) msg).toTrade());
  }
}
