package info.bitrich.xchangestream.gemini.dto;

import static org.knowm.xchange.gemini.v1.GeminiAdapters.adaptOrders;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.trade.LimitOrder;

/** Created by Lukas Zaoralek on 15.11.17. */
public class GeminiOrderbook {
  private final Map<BigDecimal, GeminiLimitOrder> asks;
  private final Map<BigDecimal, GeminiLimitOrder> bids;

  private final CurrencyPair currencyPair;

  public GeminiOrderbook(CurrencyPair currencyPair) {
    asks = new ConcurrentHashMap<>();
    bids = new ConcurrentHashMap<>();
    this.currencyPair = currencyPair;
  }

  public void createFromLevels(GeminiLimitOrder[] levels) {
    for (GeminiLimitOrder level : levels) {
      Map<BigDecimal, GeminiLimitOrder> orderBookSide =
          level.getSide() == Order.OrderType.ASK ? asks : bids;
      orderBookSide.put(level.getPrice().stripTrailingZeros(), level);
    }
  }

  public void updateLevel(GeminiLimitOrder level) {
    Map<BigDecimal, GeminiLimitOrder> orderBookSide =
        level.getSide() == Order.OrderType.ASK ? asks : bids;
    boolean shouldDelete = level.getAmount().compareTo(BigDecimal.ZERO) == 0;
    // BigDecimal is immutable type (thread safe naturally),
    // strip the trailing zeros to ensure the hashCode is same when two BigDecimal are equal but
    // decimal scale are different, such as "1.1200" & "1.12"
    BigDecimal price = level.getPrice().stripTrailingZeros();
    if (shouldDelete) {
      orderBookSide.remove(price);
    } else {
      orderBookSide.put(price, level);
    }
  }

  public void updateLevels(GeminiLimitOrder[] levels) {
    for (GeminiLimitOrder level : levels) {
      updateLevel(level);
    }
  }

  public OrderBook toOrderbook() {
    GeminiLimitOrder[] askLevels = asks.values().toArray(new GeminiLimitOrder[asks.size()]);
    GeminiLimitOrder[] bidLevels = bids.values().toArray(new GeminiLimitOrder[bids.size()]);
    List<LimitOrder> askOrders =
        adaptOrders(askLevels, currencyPair, Order.OrderType.ASK).getLimitOrders();
    List<LimitOrder> bidOrders =
        adaptOrders(bidLevels, currencyPair, Order.OrderType.BID).getLimitOrders();
    Collections.sort(askOrders);
    Collections.sort(bidOrders);
    return new OrderBook(null, askOrders, bidOrders);
  }
}
