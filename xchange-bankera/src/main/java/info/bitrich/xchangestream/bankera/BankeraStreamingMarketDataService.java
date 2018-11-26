package info.bitrich.xchangestream.bankera;

import info.bitrich.xchangestream.core.StreamingMarketDataService;
import io.reactivex.Observable;
import org.knowm.xchange.bankera.BankeraAdapters;
import org.knowm.xchange.bankera.dto.BankeraException;
import org.knowm.xchange.bankera.dto.marketdata.*;
import org.knowm.xchange.bankera.service.BankeraMarketDataService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;


public class BankeraStreamingMarketDataService implements StreamingMarketDataService {

  private static final Logger LOGGER = LoggerFactory.getLogger(BankeraStreamingMarketDataService.class);
  private final BankeraStreamingService service;
  private final BankeraMarketDataService marketDataService;

  public BankeraStreamingMarketDataService(BankeraStreamingService service, BankeraMarketDataService marketDataService) {
    this.service = service;
    this.marketDataService = marketDataService;
  }

  @Override
  public Observable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
    BankeraMarket market = getMarketInfo(currencyPair);
    return service.subscribeChannel("market-orderbook", market.getId())
        .map(s -> {
          List<BankeraOrderBook.OrderBookOrder> listBids = new ArrayList<>();
          List<BankeraOrderBook.OrderBookOrder> listAsks = new ArrayList<>();
          s.get("data").get("buy")
              .forEach(b -> listBids.add(new BankeraOrderBook.OrderBookOrder(
                  0, b.get("price").asText(), b.get("amount").asText())));
          s.get("data").get("sell")
              .forEach(b -> listAsks.add(new BankeraOrderBook.OrderBookOrder(
                  0, b.get("price").asText(), b.get("amount").asText())));
          return BankeraAdapters.adaptOrderBook(new BankeraOrderBook(listBids, listAsks), currencyPair);
        });
  }

  @Override
  public Observable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
    BankeraMarket market = getMarketInfo(currencyPair);
    return service.subscribeChannel("market-ohlcv-candle", market.getId())
      .map(s -> {
        return BankeraAdapters.adaptTicker(new BankeraTickerResponse(new BankeraTicker(
            1, "1", "0.0001", "0.5", "0.6", "0.55", "4444", 546545L
        )), currencyPair);
      });
  }

  @Override
  public Observable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
   // Observable<JsonNode> jsonNodeObservable = service.subscribeChannel("tradesChannel");
    return null;
  }

  private BankeraMarket getMarketInfo(CurrencyPair currencyPair) {
    try {
      BankeraMarketInfo info = this.marketDataService.getMarketInfo();
      Optional<BankeraMarket> market = info.getMarkets().stream().filter(
          m -> m.getName().equals(currencyPair.toString().replace("/", "-"))
      ).findFirst();

      if (market.isPresent()) {
        return market.get();
      }
      throw new BankeraException(404, "Unable to find market.");
    } catch (IOException e) {
      throw new BankeraException(404, "Unable to find market.");
    }
  }
}
