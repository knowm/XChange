package org.knowm.xchange.wex.v3.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import org.knowm.xchange.wex.v3.dto.WexReturn;

/**
 * Return value from TradeHistory, including overal status and map of transaction ids to
 * TransHistoryResult.
 *
 * @author Peter N. Steinmetz Date: 3/30/15 Time: 3:19 PM
 */
public class WexTransHistoryReturn extends WexReturn<Map<Long, WexTransHistoryResult>> {

  /**
   * Constructor
   *
   * @param success
   * @param value
   * @param error
   */
  public WexTransHistoryReturn(
      @JsonProperty("success") boolean success,
      @JsonProperty("return") Map<Long, WexTransHistoryResult> value,
      @JsonProperty("error") String error) {

    super(success, value, error);
  }
}
