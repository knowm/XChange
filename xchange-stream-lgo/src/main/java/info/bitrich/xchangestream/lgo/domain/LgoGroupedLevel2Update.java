package info.bitrich.xchangestream.lgo.domain;

import info.bitrich.xchangestream.lgo.dto.LgoLevel2Data;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.OrderBookUpdate;

public class LgoGroupedLevel2Update {

  private final OrderBook orderBook = new OrderBook(null, new ArrayList<>(), new ArrayList<>());
  private long lastBatchId;
  private boolean dirty = true;

  public void applySnapshot(long batchId, CurrencyPair currencyPair, LgoLevel2Data data) {
    dirty = false;
    applyUpdate(batchId, currencyPair, data);
  }

  public void applyUpdate(long batchId, CurrencyPair currencyPair, LgoLevel2Data data) {
    lastBatchId = batchId;
    updateL2Book(currencyPair, data);
  }

  private void updateL2Book(CurrencyPair currencyPair, LgoLevel2Data data) {
    Stream<OrderBookUpdate> asksUpdates =
        adaptLevel2Update(data, Order.OrderType.ASK, currencyPair);
    Stream<OrderBookUpdate> bidsUpdate = adaptLevel2Update(data, Order.OrderType.BID, currencyPair);
    asksUpdates.forEach(orderBook::update);
    bidsUpdate.forEach(orderBook::update);
  }

  private Stream<OrderBookUpdate> adaptLevel2Update(
      LgoLevel2Data data, Order.OrderType type, CurrencyPair currencyPair) {
    switch (type) {
      case BID:
        return data.getBids().stream().map(value -> toBookUpdate(type, currencyPair, value));
      case ASK:
        return data.getAsks().stream().map(value -> toBookUpdate(type, currencyPair, value));
      default:
        return Stream.empty();
    }
  }

  private OrderBookUpdate toBookUpdate(
      Order.OrderType type, CurrencyPair currencyPair, List<String> value) {
    BigDecimal price = new BigDecimal(value.get(0));
    BigDecimal quantity = new BigDecimal(value.get(1));
    return new OrderBookUpdate(type, null, currencyPair, price, null, quantity);
  }

  public void markDirty() {
    dirty = true;
  }

  public boolean isValid() {
    return !dirty;
  }

  public long getLastBatchId() {
    return lastBatchId;
  }

  public OrderBook orderBook() {
    return orderBook;
  }
}
