package info.bitrich.xchangestream.gateio;

import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.gateio.config.Config;
import info.bitrich.xchangestream.gateio.dto.response.orderbook.GateioOrderBookNotification;
import info.bitrich.xchangestream.gateio.dto.response.ticker.GateioTickerNotification;
import info.bitrich.xchangestream.gateio.dto.response.trade.GateioTradeNotification;
import io.reactivex.Observable;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;

public class GateioStreamingMarketDataService implements StreamingMarketDataService {

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
   */

  @Override
  public Observable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
    return service
        .subscribeChannel(Config.SPOT_ORDERBOOK_CHANNEL, currencyPair, args)
        .map(GateioOrderBookNotification.class::cast)
        .map(GateioStreamingAdapters::toOrderBook);
  }


  @Override
  public Observable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
    return service
        .subscribeChannel(Config.SPOT_TICKERS_CHANNEL, currencyPair, args)
        .map(GateioTickerNotification.class::cast)
        .map(GateioStreamingAdapters::toTicker);
  }


  @Override
  public Observable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
    return service
        .subscribeChannel(Config.SPOT_TRADES_CHANNEL, currencyPair)
        .map(GateioTradeNotification.class::cast)
        .map(GateioStreamingAdapters::toTrade);
  }
}
