package info.bitrich.xchangestream.hitbtc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.hitbtc.dto.*;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.rxjava3.core.Flowable;
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
  public Flowable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
    String pair = currencyPair.base.toString() + currencyPair.counter.toString();
    String channelName = getChannelName("orderbook", pair);
    final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();

    Flowable<JsonNode> jsonNodeFlowable = service.subscribeChannel(channelName);
    return jsonNodeFlowable
        .map(s -> mapper.readValue(s.toString(), HitbtcWebSocketOrderBookTransaction.class))
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
  public Flowable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
    String pair = currencyPair.base.toString() + currencyPair.counter.toString();
    String channelName = getChannelName("trades", pair);
    final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();

    return service
        .subscribeChannel(channelName)
        .map(s -> mapper.readValue(s.toString(), HitbtcWebSocketTradesTransaction.class))
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
  public Flowable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
    String pair = currencyPair.base.toString() + currencyPair.counter.toString();
    String channelName = getChannelName("ticker", pair);
    final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();

    return service
        .subscribeChannel(channelName)
        .map(s -> mapper.readValue(s.toString(), HitbtcWebSocketTickerTransaction.class))
        .map(s -> HitbtcAdapters.adaptTicker(s.getParams(), currencyPair));
  }

  private String getChannelName(String entityName, String pair) {
    return entityName + "-" + pair;
  }
}
