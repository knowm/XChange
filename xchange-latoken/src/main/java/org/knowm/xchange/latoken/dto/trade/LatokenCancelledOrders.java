package org.knowm.xchange.latoken.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Response schema:
 *
 * <pre>
 * {
 * 	"pairId": 502,
 * 	"symbol": "LAETH",
 * 	"cancelledOrders": [
 * 		"1555492358.126073.126767@0502:2"
 * 	]
 * }
 * </pre>
 */
public class LatokenCancelledOrders {

  private final String pairId;
  private final String symbol;
  private final List<String> cancelledOrders;

  public LatokenCancelledOrders(
      @JsonProperty("pairId") String pairId,
      @JsonProperty("symbol") String symbol,
      @JsonProperty("cancelledOrders") List<String> cancelledOrders) {

    this.pairId = pairId;
    this.symbol = symbol;
    this.cancelledOrders = cancelledOrders;
  }

  /**
   * ID of trading pair
   *
   * @return
   */
  public String getPairId() {
    return pairId;
  }

  /**
   * Trading pair symbol
   *
   * @return
   */
  public String getSymbol() {
    return symbol;
  }

  /**
   * IDs of the cancelled orders
   *
   * @return
   */
  public List<String> getCancelledOrders() {
    return cancelledOrders;
  }
}
