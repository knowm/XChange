package org.knowm.xchange.lgo.dto.marketdata;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import org.knowm.xchange.utils.DateUtils;

public class LgoPriceHistory {

  private final List<LgoCandlestick> prices;

  private LgoPriceHistory(List<LgoCandlestick> prices) {
    this.prices = prices;
  }

  public static LgoPriceHistory fromRawValues(List<List<Object>> rawPriceHistory)
      throws IOException {
    List<LgoCandlestick> result = new ArrayList<>();
    for (List<Object> price : rawPriceHistory) {
      String time = (String) price.get(0);
      String low = (String) price.get(1);
      String high = (String) price.get(2);
      String open = (String) price.get(3);
      String close = (String) price.get(4);
      String volume = (String) price.get(5);
      LgoCandlestick candlestick =
          new LgoCandlestick(
              DateUtils.fromISO8601DateString(time),
              open == null ? null : new BigDecimal(open),
              high == null ? null : new BigDecimal(high),
              low == null ? null : new BigDecimal(low),
              close == null ? null : new BigDecimal(close),
              volume == null ? null : new BigDecimal(volume));
      result.add(candlestick);
    }
    return new LgoPriceHistory(result);
  }

  public List<LgoCandlestick> getPrices() {
    return prices;
  }

  @Override
  public String toString() {
    return prices.toString();
  }
}
