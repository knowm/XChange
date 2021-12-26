package info.bitrich.xchangestream.dydx.dto.v1;

import static info.bitrich.xchangestream.dydx.dydxStreamingAdapters.dydxOrderBookChanges;

import com.fasterxml.jackson.annotation.JsonProperty;
import info.bitrich.xchangestream.dydx.dto.dydxWebSocketTransaction;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import lombok.Getter;
import lombok.Setter;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.trade.LimitOrder;

/** Author: Max Gao (gaamox@tutanota.com) Created: 08-03-2021 */
public class dydxInitialOrderBookMessage extends dydxWebSocketTransaction {
  @JsonProperty("contents")
  private Contents contents;

  @Getter
  @Setter
  public static class Contents {
    @JsonProperty("offset")
    private String offset;

    @JsonProperty("bids")
    private Order bids[];

    @JsonProperty("asks")
    private Order asks[];
  }

  @Getter
  @Setter
  public static class Order {
    @JsonProperty("id")
    private String id;

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("price")
    private String price;

    @JsonProperty("amount")
    private String amount;
  }

  public OrderBook toOrderBook(
      SortedMap<BigDecimal, BigDecimal> bids,
      SortedMap<BigDecimal, BigDecimal> asks,
      Map<String, String> bidIds,
      Map<String, String> askIds,
      int maxDepth,
      CurrencyPair currencyPair) {
    String[][] bidsData = null;
    String[][] asksData = null;

    if (this.contents.getBids() != null) {
      bidsData =
          Arrays.stream(this.contents.getBids())
              .map(
                  b -> {
                    bidIds.put(b.id, b.price);
                    return new String[] {b.price, b.amount};
                  })
              .toArray(String[][]::new);
    }
    if (this.contents.getAsks() != null) {
      asksData =
          Arrays.stream(this.contents.getAsks())
              .map(
                  a -> {
                    askIds.put(a.id, a.price);
                    return new String[] {a.price, a.amount};
                  })
              .toArray(String[][]::new);
    }

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

    return new OrderBook(null, dydxBids, dydxAsks, false);
  }
}
