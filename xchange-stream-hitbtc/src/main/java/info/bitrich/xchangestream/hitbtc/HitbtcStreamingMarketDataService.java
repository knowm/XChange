package info.bitrich.xchangestream.hitbtc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.hitbtc.dto.HitbtcWebSocketOrderBook;
import info.bitrich.xchangestream.hitbtc.dto.HitbtcWebSocketOrderBookTransaction;
import info.bitrich.xchangestream.hitbtc.dto.HitbtcWebSocketTickerTransaction;
import info.bitrich.xchangestream.hitbtc.dto.HitbtcWebSocketTradeParams;
import info.bitrich.xchangestream.hitbtc.dto.HitbtcWebSocketTradesTransaction;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.rxjava3.core.Observable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.hitbtc.v2.HitbtcAdapters;

/** Created by Pavel Chertalev on 15.03.2018. */
public class HitbtcStreamingMarketDataService implements StreamingMarketDataService {

  private final HitbtcStreamingService service;
  private Map<CurrencyPair, HitbtcWebSocketOrderBook> orderbooks = new HashMap<>();

  public HitbtcStreamingMarketDataService(HitbtcStreamingService service) {
    this.service = service;
  }

  @Override
  public Observable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
    String pair = currencyPair.getBase().toString() + currencyPair.getCounter().toString();
    String channelName = getChannelName("orderbook", pair);
    final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();

    Observable<JsonNode> jsonNodeObservable = service.subscribeChannel(channelName);
    return jsonNodeObservable
        .map(s -> mapper.treeToValue(s, HitbtcWebSocketOrderBookTransaction.class))
        .map(
            s -> {
              HitbtcWebSocketOrderBook hitbtcOrderBook =
                  s.toHitbtcOrderBook(orderbooks.getOrDefault(currencyPair, null));
              orderbooks.put(currencyPair, hitbtcOrderBook);
              return HitbtcAdapters.adaptOrderBook(
                  hitbtcOrderBook.toHitbtcOrderBook(), currencyPair);
            });
  }

  @Override
  public Observable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
    String pair = currencyPair.getBase().toString() + currencyPair.getCounter().toString();
    String channelName = getChannelName("trades", pair);
    final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();

    return service
        .subscribeChannel(channelName)
        .map(s -> mapper.treeToValue(s, HitbtcWebSocketTradesTransaction.class))
        .map(HitbtcWebSocketTradesTransaction::getParams)
        .filter(Objects::nonNull)
        .map(HitbtcWebSocketTradeParams::getData)
        .filter(Objects::nonNull)
        .map(Arrays::asList)
        .flatMapIterable(
            s -> {
              Trades adaptedTrades = HitbtcAdapters.adaptTrades(s, currencyPair);
              return adaptedTrades.getTrades();
            });
  }

  @Override
  public Observable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
    String pair = currencyPair.getBase().toString() + currencyPair.getCounter().toString();
    String channelName = getChannelName("ticker", pair);
    final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();

    return service
        .subscribeChannel(channelName)
        .map(s -> mapper.treeToValue(s, HitbtcWebSocketTickerTransaction.class))
        .map(s -> HitbtcAdapters.adaptTicker(s.getParams(), currencyPair));
  }

  private String getChannelName(String entityName, String pair) {
    return entityName + "-" + pair;
  }
}
