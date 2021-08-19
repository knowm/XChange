package info.bitrich.xchangestream.dydx.dto.v3;

import static info.bitrich.xchangestream.dydx.dydxStreamingAdapters.dydxOrderBookChanges;

import com.fasterxml.jackson.annotation.JsonProperty;
import info.bitrich.xchangestream.dydx.dto.dydxWebSocketTransaction;
import java.math.BigDecimal;
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
public class dydxUpdateOrderBookMessage extends dydxWebSocketTransaction {
  @JsonProperty("contents")
  private Contents contents;

  @Getter
  @Setter
  public static class Contents {
    @JsonProperty("offset")
    private String offset;

    @JsonProperty("bids")
    private String bids[][];

    @JsonProperty("asks")
    private String asks[][];
  }

  public OrderBook toOrderBook(
      SortedMap<BigDecimal, BigDecimal> bids,
      SortedMap<BigDecimal, BigDecimal> asks,
      int maxDepth,
      CurrencyPair currencyPair) {
    List<LimitOrder> dydxBids =
        dydxOrderBookChanges(
            org.knowm.xchange.dto.Order.OrderType.BID,
            currencyPair,
            this.contents.getBids() != null ? this.contents.getBids() : null,
            bids,
            maxDepth,
            false);

    List<LimitOrder> dydxAsks =
        dydxOrderBookChanges(
            org.knowm.xchange.dto.Order.OrderType.ASK,
            currencyPair,
            this.contents.getAsks() != null ? this.contents.getAsks() : null,
            asks,
            maxDepth,
            false);

    return new OrderBook(null, dydxAsks, dydxBids, false);
  }
}
