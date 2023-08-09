package info.bitrich.xchangestream.gateio;

import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.gateio.dto.response.GateioOrderBookResponse;
import info.bitrich.xchangestream.gateio.dto.response.GateioTradesResponse;
import io.reactivex.Observable;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GateioStreamingMarketDataService implements StreamingMarketDataService {
  private static final Logger LOGGER =
      LoggerFactory.getLogger(GateioStreamingMarketDataService.class);
  private final GateioStreamingService service;

  public GateioStreamingMarketDataService(GateioStreamingService service) {
    this.service = service;
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
    return service
        .getRawWebSocketTransactions(currencyPair, GateioStreamingService.SPOT_ORDERBOOK_CHANNEL)
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
