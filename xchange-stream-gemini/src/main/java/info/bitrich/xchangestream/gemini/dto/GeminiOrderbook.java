package info.bitrich.xchangestream.gemini.dto;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;

/** Created by Lukas Zaoralek on 15.11.17. */
public class GeminiOrderbook {
  private final Map<BigDecimal, GeminiLimitOrder> asks;
  private final Map<BigDecimal, GeminiLimitOrder> bids;

  private final CurrencyPair currencyPair;

  public GeminiOrderbook(CurrencyPair currencyPair) {
    this.bids = new TreeMap<>(Comparator.reverseOrder());
    this.asks = new TreeMap<>();
    this.currencyPair = currencyPair;
  }

  public void createFromLevels(GeminiLimitOrder[] levels) {
    for (GeminiLimitOrder level : levels) {
      Map<BigDecimal, GeminiLimitOrder> orderBookSide =
          level.getSide() == Order.OrderType.ASK ? asks : bids;
      orderBookSide.put(level.getPrice(), level);
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

  public Collection<GeminiLimitOrder> getAsks() {
    return asks.values();
  }

  public Collection<GeminiLimitOrder> getBids() {
    return bids.values();
  }

  public CurrencyPair getCurrencyPair() {
    return currencyPair;
  }
}
