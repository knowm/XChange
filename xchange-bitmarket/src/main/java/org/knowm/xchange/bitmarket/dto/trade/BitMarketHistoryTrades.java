package org.knowm.xchange.bitmarket.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/** @author kfonal */
public class BitMarketHistoryTrades {

  private final int total;
  private final int start;
  private final int count;
  private final List<BitMarketHistoryTrade> trades;

  /**
   * Constructor
   *
   * @param total
   * @param start
   * @param count
   * @param trades
   */
  public BitMarketHistoryTrades(
      @JsonProperty("total") int total,
      @JsonProperty("start") int start,
      @JsonProperty("count") int count,
      @JsonProperty("results") List<BitMarketHistoryTrade> trades) {

    this.total = total;
    this.start = start;
    this.count = count;
    this.trades = trades;
  }

  public int getTotal() {
    return total;
  }

  public int getStart() {
    return start;
  }

  public int getCount() {
    return count;
  }

  public List<BitMarketHistoryTrade> getTrades() {
    return trades;
  }
}
