package com.knowm.xchange.vertex.dto;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import lombok.Getter;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.instrument.Instrument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
public class VertexOrderBookStream extends Observable<OrderBook> implements Consumer<VertexMarketDataUpdateMessage> {
  private static final Logger logger = LoggerFactory.getLogger(VertexOrderBookStream.class);

  private final Subject<OrderBook> orderBookSubject = PublishSubject.<OrderBook>create().toSerialized();

  private final Map<BigInteger, LimitOrder> bidPriceToBidQuantity = new ConcurrentSkipListMap<>(Comparator.reverseOrder());
  private final Map<BigInteger, LimitOrder> offerPriceToOfferQuantity = new ConcurrentSkipListMap<>();
  private final Instrument instrument;
  private final int maxDepth;

  public VertexOrderBookStream(Instrument instrument, int maxDepth) {
    this.instrument = instrument;
    this.maxDepth = maxDepth;
  }

  @Override
  protected void subscribeActual(Observer<? super OrderBook> observer) {
    orderBookSubject.subscribe(observer);
  }

  @Override
  public synchronized void accept(VertexMarketDataUpdateMessage updateMessage) {
    if (updateMessage.getLastMaxTime() == null) {
      handleSnapshot(updateMessage);
    } else {
      handleIncrement(updateMessage);
    }

    publishOrderBookFromUpdate(updateMessage.getMaxTime());
  }

  private void processSnapshotOrders(List<PriceAndQuantity> orders, Map<BigInteger, LimitOrder> mapForInsert, Order.OrderType type, Instant timestamp) {
    mapForInsert.clear();

    if (orders != null) {
      for (PriceAndQuantity order : orders) {

        BigInteger price = order.getPrice();
        BigInteger quantity = order.getQuantity();

        mapForInsert.put(price, getLimitOrder(type, instrument, timestamp, price, VertexModelUtils.convertToDecimal(quantity)));
      }
    }
  }

  private void handleIncrement(VertexMarketDataUpdateMessage updateMessage) {
    processIncrementOrders(updateMessage.getBids(), bidPriceToBidQuantity, Order.OrderType.BID, updateMessage.getMaxTime());
    processIncrementOrders(updateMessage.getAsks(), offerPriceToOfferQuantity, Order.OrderType.ASK, updateMessage.getMaxTime());
  }

  private void processIncrementOrders(List<PriceAndQuantity> orders, Map<BigInteger, LimitOrder> mapForInsert, Order.OrderType type, Instant timestamp) {
    if (orders != null) {
      for (PriceAndQuantity order : orders) {

        BigInteger price = order.getPrice();
        BigInteger quantityAsInt = order.getQuantity();

        if (isZero(quantityAsInt)) {
          mapForInsert.remove(price);
        } else {
          LimitOrder exising = mapForInsert.get(price);
          BigDecimal quantity = VertexModelUtils.convertToDecimal(quantityAsInt);
          if (exising != null && exising.getOriginalAmount().equals(quantity)) {
            continue;
          }
          LimitOrder limitOrder = getLimitOrder(type, instrument, timestamp, price, quantity);
          mapForInsert.put(price, limitOrder);
        }

      }
    }
  }

  private static boolean isZero(BigInteger quantityAsInt) {
    return quantityAsInt.compareTo(BigInteger.ZERO) == 0;
  }

  private void publishOrderBookFromUpdate(Instant timestamp) {
    OrderBook book = generateOrderBook(timestamp);

    orderBookSubject.onNext(book);
  }

  private void populateOrders(List<LimitOrder> orders, Map<BigInteger, LimitOrder> priceToOrder) {
    int currentDepth = 0;
    for (Map.Entry<BigInteger, LimitOrder> bigDecimalBigDecimalEntry : priceToOrder.entrySet()) {
      LimitOrder order = bigDecimalBigDecimalEntry.getValue();
      orders.add(order);

      currentDepth += 1;

      if (currentDepth == maxDepth) break;
    }
  }

  private static LimitOrder getLimitOrder(Order.OrderType type, Instrument instrument, Instant timestamp, BigInteger priceAsInt, BigDecimal quantity) {
    BigDecimal price = VertexModelUtils.convertToDecimal(priceAsInt);

    return new LimitOrder(type, quantity, instrument, null, new Date(timestamp.toEpochMilli()), price);
  }

  private void handleSnapshot(VertexMarketDataUpdateMessage updateMessage) {
    logger.info("{} - depth {}: Received snapshot, clearing order book and repopulating.", instrument, maxDepth);
    processSnapshotOrders(updateMessage.getBids(), bidPriceToBidQuantity, Order.OrderType.BID, updateMessage.getMaxTime());
    processSnapshotOrders(updateMessage.getAsks(), offerPriceToOfferQuantity, Order.OrderType.ASK, updateMessage.getMaxTime());
  }


  private OrderBook generateOrderBook(Instant instant) {
    int capacity = maxDepth != Integer.MAX_VALUE ? maxDepth : 50;
    List<LimitOrder> bids = new ArrayList<>(capacity);
    List<LimitOrder> offers = new ArrayList<>(capacity);

    Date timestamp = new Date(instant == null ? Instant.now().toEpochMilli() : instant.toEpochMilli());
    populateOrders(bids, bidPriceToBidQuantity);
    populateOrders(offers, offerPriceToOfferQuantity);

    return new OrderBook(
        new Date(),
        timestamp,
        offers,
        bids,
        false
    );
  }

  @Override
  public String toString() {
    return "VertexOrderBookStream{" +
        "instrument=" + instrument +
        ", maxDepth=" + maxDepth +
        '}';
  }
}
