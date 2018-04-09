package org.knowm.xchange.bitmarket.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/** @author kfonal */
public class BitMarketHistoryOperations {

  private final int total;
  private final int start;
  private final int count;
  private final List<BitMarketHistoryOperation> operations;

  /**
   * Constructor
   *
   * @param total
   * @param start
   * @param count
   * @param operations
   */
  public BitMarketHistoryOperations(
      @JsonProperty("total") int total,
      @JsonProperty("start") int start,
      @JsonProperty("count") int count,
      @JsonProperty("results") List<BitMarketHistoryOperation> operations) {

    this.total = total;
    this.start = start;
    this.count = count;
    this.operations = operations;
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

  public List<BitMarketHistoryOperation> getOperations() {
    return operations;
  }
}
