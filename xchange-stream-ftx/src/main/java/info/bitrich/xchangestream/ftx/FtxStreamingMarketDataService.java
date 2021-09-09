package info.bitrich.xchangestream.ftx;

import com.google.common.collect.Lists;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import io.reactivex.Observable;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.ftx.FtxAdapters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FtxStreamingMarketDataService implements StreamingMarketDataService {

  private static final Logger LOG = LoggerFactory.getLogger(FtxStreamingMarketDataService.class);

  private final FtxStreamingService service;

  public FtxStreamingMarketDataService(FtxStreamingService service) {
    this.service = service;
  }

  @Override
  public Observable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
    OrderBook orderBook = new OrderBook(null, Lists.newArrayList(), Lists.newArrayList());
    String channelName = "orderbook:" + FtxAdapters.adaptCurrencyPairToFtxMarket(currencyPair);

    return service
        .subscribeChannel(channelName)
        .map(
            res -> {
              try {
                return FtxStreamingAdapters.adaptOrderbookMessage(orderBook, currencyPair, res);
              } catch (IllegalStateException e) {
                LOG.warn(
                    "Resubscribing {} channel after adapter error {}",
                    currencyPair,
                    e.getMessage());
                orderBook.getBids().clear();
                orderBook.getAsks().clear();
                // Resubscribe to the channel
                this.service.sendMessage(service.getUnsubscribeMessage(channelName, args));
                this.service.sendMessage(service.getSubscribeMessage(channelName, args));
                return new OrderBook(null, Lists.newArrayList(), Lists.newArrayList(), false);
              }
            })
        .filter(ob -> ob.getBids().size() > 0 && ob.getAsks().size() > 0);
  }

  @Override
  public Observable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
    return service
        .subscribeChannel("ticker:" + FtxAdapters.adaptCurrencyPairToFtxMarket(currencyPair))
        .map(res -> FtxStreamingAdapters.adaptTickerMessage(currencyPair, res))
        .filter(ticker -> ticker != FtxStreamingAdapters.NULL_TICKER); // lets not send these backs
  }

  @Override
  public Observable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
    return service
        .subscribeChannel("trades:" + FtxAdapters.adaptCurrencyPairToFtxMarket(currencyPair))
        .flatMapIterable(res -> FtxStreamingAdapters.adaptTradesMessage(currencyPair, res));
  }
}
