package info.bitrich.xchangestream.dydx.dto.v3;

import static info.bitrich.xchangestream.dydx.dydxStreamingAdapters.dydxOrderBookChanges;

import com.fasterxml.jackson.annotation.JsonProperty;
import info.bitrich.xchangestream.dydx.dto.dydxWebSocketTransaction;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.SortedMap;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.trade.LimitOrder;

/** Author: Max Gao (gaamox@tutanota.com) Created: 08-03-2021 */
@NoArgsConstructor
@Getter
@Setter
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
    @JsonProperty("price")
    private String price;

    @JsonProperty("size")
    private String size;
  }

  public OrderBook toOrderBook(
      SortedMap<BigDecimal, BigDecimal> bids,
      SortedMap<BigDecimal, BigDecimal> asks,
      int maxDepth,
      CurrencyPair currencyPair) {
    String[][] bidsData = null;
    String[][] asksData = null;

    if (this.contents.getBids() != null) {
      bidsData =
          Arrays.stream(this.contents.getBids())
              .map(b -> new String[] {b.price, b.size})
              .toArray(String[][]::new);
    }
    if (this.contents.getAsks() != null) {
      asksData =
          Arrays.stream(this.contents.getAsks())
              .map(a -> new String[] {a.price, a.size})
              .toArray(String[][]::new);
    }

    List<LimitOrder> dydxBids =
        dydxOrderBookChanges(
            org.knowm.xchange.dto.Order.OrderType.BID,
            currencyPair,
            bidsData,
            bids,
            maxDepth,
            false);

    List<LimitOrder> dydxAsks =
        dydxOrderBookChanges(
            org.knowm.xchange.dto.Order.OrderType.ASK,
            currencyPair,
            asksData,
            asks,
            maxDepth,
            false);

    return new OrderBook(null, dydxBids, dydxAsks, false);
  }
}
