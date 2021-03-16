package info.bitrich.xchangestream.coinjar;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import info.bitrich.xchangestream.coinjar.dto.CoinjarWebSocketBookEvent;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.rxjava3.core.Flowable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class CoinjarStreamingMarketDataService implements StreamingMarketDataService {

  private static final Logger logger =
      LoggerFactory.getLogger(CoinjarStreamingMarketDataService.class);

  private final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();

  private final CoinjarStreamingService service;

  public CoinjarStreamingMarketDataService(CoinjarStreamingService service) {
    this.service = service;
  }

  private static void updateOrderbook(Map<BigDecimal, LimitOrder> book, List<LimitOrder> orders) {
    orders.forEach(
        order -> {
          if (order.getOriginalAmount().compareTo(BigDecimal.ZERO) > 0) {
            book.put(order.getLimitPrice(), order);
          } else {
            book.remove(order.getLimitPrice());
          }
        });
  }

  private static OrderBook handleOrderbookEvent(
      CoinjarWebSocketBookEvent event,
      Map<BigDecimal, LimitOrder> bids,
      Map<BigDecimal, LimitOrder> asks) {
    final CurrencyPair pairFromEvent =
        CoinjarStreamingAdapters.adaptTopicToCurrencyPair(event.topic);
    switch (event.event) {
      case CoinjarWebSocketBookEvent.UPDATE:
      case CoinjarWebSocketBookEvent.INIT:
        updateOrderbook(
            bids,
            CoinjarStreamingAdapters.toLimitOrders(
                event.payload.bids, pairFromEvent, Order.OrderType.BID));
        updateOrderbook(
            asks,
            CoinjarStreamingAdapters.toLimitOrders(
                event.payload.asks, pairFromEvent, Order.OrderType.ASK));
        break;
    }
    return new OrderBook(
        null, Lists.newArrayList(asks.values()), Lists.newArrayList(bids.values()));
  }

  @Override
  public Flowable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
    final SortedMap<BigDecimal, LimitOrder> bids =
        Maps.newTreeMap((o1, o2) -> Math.negateExact(o1.compareTo(o2)));
    final SortedMap<BigDecimal, LimitOrder> asks = Maps.newTreeMap(BigDecimal::compareTo);
    String channelName = CoinjarStreamingAdapters.adaptCurrencyPairToBookTopic(currencyPair);
    return service
        .subscribeChannel(channelName)
        .doOnError(
            throwable -> {
              logger.warn(
                  "encoutered error while subscribing to channel " + channelName, throwable);
            })
        .map(
            node -> {
              CoinjarWebSocketBookEvent orderEvent =
                  mapper.treeToValue(node, CoinjarWebSocketBookEvent.class);
              return handleOrderbookEvent(orderEvent, bids, asks);
            })
        .filter(orderbook -> !orderbook.getBids().isEmpty() && !orderbook.getAsks().isEmpty());
  }

  @Override
  public Flowable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public Flowable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
    throw new NotYetImplementedForExchangeException();
  }
}
