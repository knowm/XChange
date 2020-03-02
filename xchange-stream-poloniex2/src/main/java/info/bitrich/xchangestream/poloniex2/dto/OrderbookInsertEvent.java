package info.bitrich.xchangestream.poloniex2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;

/** Created by Lukas Zaoralek on 11.11.17. */
public class OrderbookInsertEvent {
  public static final int ASK_SIDE = 0;
  public static final int BID_SIDE = 1;

  private final String currencyPair;
  private final JsonNode[] orderbookSides;

  public OrderbookInsertEvent(
      @JsonProperty("currencyPair") String currencyPair,
      @JsonProperty("orderBook") JsonNode[] orderbookSides) {
    this.currencyPair = currencyPair;
    this.orderbookSides = orderbookSides;
  }

  public String getCurrencyPair() {
    return currencyPair;
  }

  public JsonNode[] getOrderbookSides() {
    return orderbookSides;
  }

  public SortedMap<BigDecimal, BigDecimal> toDepthLevels(int side) {
    if (side == ASK_SIDE) return toDepthLevels(orderbookSides[ASK_SIDE], false);
    else return toDepthLevels(orderbookSides[BID_SIDE], true);
  }

  private SortedMap<BigDecimal, BigDecimal> toDepthLevels(JsonNode side, boolean reverse) {
    SortedMap<BigDecimal, BigDecimal> levels =
        new TreeMap<>(reverse ? java.util.Collections.reverseOrder() : null);
    Iterator<String> prices = side.fieldNames();
    while (prices.hasNext()) {
      String strPrice = prices.next();
      BigDecimal price = new BigDecimal(strPrice);
      BigDecimal volume = new BigDecimal(side.get(strPrice).asText());
      levels.put(price, volume);
    }

    return levels;
  }
}
