package org.knowm.xchange.dragonex.dto.trade;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class OrderHistoryRequest {

  @JsonProperty("symbol_id")
  public final long symbolId;
  /**
   * search direction: 1-consult forwardly from start time, 2-consult backwardly from start time.
   * default 2
   */
  @JsonProperty("direction")
  public final Integer direction;
  /** transmit nothing or 0 from current time, or transmit Unix timestamp (ns) */
  @JsonProperty("start")
  public final Long start;
  /** counts of records, in default 10 */
  @JsonProperty("count")
  public final Integer count;
  /** order status: 0-all states, 1-pending trade, 2-successful trade, 3-canceled, 4-failed */
  @JsonProperty("status")
  public final int status;

  public OrderHistoryRequest(
      long symbolId, Integer direction, Long start, Integer count, int status) {
    this.symbolId = symbolId;
    this.direction = direction;
    this.start = start;
    this.count = count;
    this.status = status;
  }

  public OrderHistoryRequest(long symbolId, int status) {
    this(symbolId, null, null, null, status);
  }
}
