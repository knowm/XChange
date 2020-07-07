package info.bitrich.xchangestream.gemini.dto;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.trade.LimitOrder;

/** Created by Lukas Zaoralek on 15.11.17. */
public class GeminiOrderbook {
  private final Map<Double, GeminiLimitOrder> asks;
  private final Map<Double, GeminiLimitOrder> bids;

  private final CurrencyPair currencyPair;

  public GeminiOrderbook(CurrencyPair currencyPair) {
    this.bids = new TreeMap<>(Comparator.reverseOrder());
    this.asks = new TreeMap<>();
    this.currencyPair = currencyPair;
  }

  public void createFromLevels(GeminiLimitOrder[] levels) {
    for (GeminiLimitOrder level : levels) {
      Map<Double, GeminiLimitOrder> orderBookSide =
          level.getSide() == Order.OrderType.ASK ? asks : bids;
      orderBookSide.put(level.getPrice().doubleValue(), level);
    }
  }

  public void updateLevel(GeminiLimitOrder level) {
    Map<Double, GeminiLimitOrder> orderBookSide =
        level.getSide() == Order.OrderType.ASK ? asks : bids;
    boolean shouldDelete = level.getAmount().compareTo(BigDecimal.ZERO) == 0;
    // BigDecimal is immutable type (thread safe naturally),
    // strip the trailing zeros to ensure the hashCode is same when two BigDecimal are equal but
    // decimal scale are different, such as "1.1200" & "1.12"
    BigDecimal price = level.getPrice().stripTrailingZeros();
    if (shouldDelete) {
      orderBookSide.remove(price.doubleValue());
    } else {
      orderBookSide.put(price.doubleValue(), level);
    }
  }

  public void updateLevels(GeminiLimitOrder[] levels) {
    for (GeminiLimitOrder level : levels) {
      updateLevel(level);
    }
  }

  public OrderBook toOrderbook(int maxLevels, Date timestamp) {
    List<LimitOrder> askOrders =
        asks.values().stream()
            .limit(maxLevels)
            .map(
                (GeminiLimitOrder geminiLimitOrder) ->
                    toLimitOrder(geminiLimitOrder, Order.OrderType.ASK))
            .collect(Collectors.toList());

    List<LimitOrder> bidOrders =
        bids.values().stream()
            .limit(maxLevels)
            .map(
                (GeminiLimitOrder geminiLimitOrder) ->
                    toLimitOrder(geminiLimitOrder, Order.OrderType.BID))
            .collect(Collectors.toList());

    return new OrderBook(timestamp, askOrders, bidOrders);
  }

  private LimitOrder toLimitOrder(GeminiLimitOrder geminiLimitOrder, Order.OrderType side) {
    return new LimitOrder.Builder(side, currencyPair)
        .limitPrice(geminiLimitOrder.getPrice())
        .originalAmount(geminiLimitOrder.getAmount())
        .timestamp(convertBigDecimalTimestampToDate(geminiLimitOrder.getTimestamp()))
        .build();
  }

  static Date convertBigDecimalTimestampToDate(BigDecimal timestampInSeconds) {
    return new Date((long) Math.floor(timestampInSeconds.doubleValue() * 1000));
  }
}
