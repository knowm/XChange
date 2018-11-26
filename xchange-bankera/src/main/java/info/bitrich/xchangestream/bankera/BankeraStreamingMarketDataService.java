package info.bitrich.xchangestream.bankera;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.Observable;
import org.knowm.xchange.bankera.BankeraAdapters;
import org.knowm.xchange.bankera.dto.marketdata.BankeraOrderBook;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.hitbtc.v2.HitbtcAdapters;

import java.util.Arrays;
import java.util.List;


public class BankeraStreamingMarketDataService implements StreamingMarketDataService {

  private final BankeraStreamingService service;

  public BankeraStreamingMarketDataService(BankeraStreamingService service) {
    this.service = service;
  }

  @Override
  public Observable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
    Observable<JsonNode> jsonNodeObservable = service.subscribeChannel("market-orderbook");
    return jsonNodeObservable
        .map(s -> {
          List<BankeraOrderBook.OrderBookOrder> ol = Arrays.asList(new BankeraOrderBook.OrderBookOrder(1, "0.1", "0.2"));
          List<BankeraOrderBook.OrderBookOrder> oll = Arrays.asList(new BankeraOrderBook.OrderBookOrder(1, "0.1", "0.2"));

          return BankeraAdapters.adaptOrderBook(new BankeraOrderBook(ol, oll), currencyPair);
        });
  }

  @Override
  public Observable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
    Observable<JsonNode> jsonNodeObservable = service.subscribeChannel("tickerChannel");
    return null;
  }

  @Override
  public Observable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
    Observable<JsonNode> jsonNodeObservable = service.subscribeChannel("tradesChannel");
    return null;
  }
}
