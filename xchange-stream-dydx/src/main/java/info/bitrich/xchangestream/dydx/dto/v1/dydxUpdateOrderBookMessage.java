package info.bitrich.xchangestream.dydx.dto.v1;

import static info.bitrich.xchangestream.dydx.dydxStreamingAdapters.dydxOrderBookChanges;

import com.fasterxml.jackson.annotation.JsonProperty;
import info.bitrich.xchangestream.dydx.dto.dydxWebSocketTransaction;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.SortedMap;
import lombok.Getter;
import lombok.Setter;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.trade.LimitOrder;

/** Author: Max Gao (gaamox@tutanota.com) Created: 08-03-2021 */
public class dydxUpdateOrderBookMessage extends dydxWebSocketTransaction {
  @JsonProperty("contents")
  private Contents contents;

  @Getter
  @Setter
  public static class Contents {
    @JsonProperty("updates")
    private Update updates[];
  }

  @Getter
  @Setter
  public static class Update {
    @JsonProperty("type")
    private String type;

    @JsonProperty("id")
    private String id;

    @JsonProperty("side")
    private String side;

    @JsonProperty("amount")
    private String amount;

    @JsonProperty("price")
    private String price;
  }

  private String[][] buildChangeData(String side, Map<String, String> idMap) {
    return Arrays.stream(this.contents.getUpdates())
        .filter(update -> (side).equals(update.side))
        .map(
            update -> {
              switch (update.type) {
                case "NEW":
                  idMap.put(update.id, update.price);
                  return new String[] {update.price, update.amount};
                case "REMOVED":
                  String removedPriceLevel = idMap.get(update.id);
                  idMap.remove(update.id);
                  if (removedPriceLevel != null) {
                    return new String[] {removedPriceLevel, "0"};
                  } else {
                    return null; // Occasionally the websocket will return duplicate removal
                    // messages.
                  }
                case "UPDATED":
                  String updatedPriceLevel = idMap.get(update.id);
                  if (updatedPriceLevel != null) {
                    return new String[] {updatedPriceLevel, update.amount};
                  } else {
                    return null; // Occasionally the websocket will return duplicate removal
                    // messages.
                  }
                default:
                  return null;
              }
            })
        .filter(Objects::nonNull)
        .toArray(String[][]::new);
  }

  public OrderBook toOrderBook(
      SortedMap<BigDecimal, BigDecimal> bids,
      SortedMap<BigDecimal, BigDecimal> asks,
      Map<String, String> bidIds,
      Map<String, String> askIds,
      int maxDepth,
      CurrencyPair currencyPair) {

    String[][] bidsData = buildChangeData("BUY", bidIds);
    String[][] asksData = buildChangeData("SELL", askIds);

    List<LimitOrder> dydxBids =
        dydxOrderBookChanges(
            org.knowm.xchange.dto.Order.OrderType.BID,
            currencyPair,
            bidsData,
            bids,
            maxDepth,
            true);

    List<LimitOrder> dydxAsks =
        dydxOrderBookChanges(
            org.knowm.xchange.dto.Order.OrderType.ASK,
            currencyPair,
            asksData,
            asks,
            maxDepth,
            true);

    return new OrderBook(null, dydxAsks, dydxBids, false);
  }
}
