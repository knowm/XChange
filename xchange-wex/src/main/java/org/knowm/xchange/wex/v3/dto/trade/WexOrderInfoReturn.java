package org.knowm.xchange.wex.v3.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import org.knowm.xchange.wex.v3.dto.WexReturn;

/**
 * Return value from OrderInfo.
 *
 * @author Dyorgio Nascimento Date: 4/17/15 Time: 18:05 PM
 */
public class WexOrderInfoReturn extends WexReturn<Map<Long, WexOrderInfoResult>> {

  /**
   * Constructor
   *
   * @param success
   * @param value
   * @param error
   */
  public WexOrderInfoReturn(
      @JsonProperty("success") boolean success,
      @JsonProperty("return") Map<Long, WexOrderInfoResult> value,
      @JsonProperty("error") String error) {

    super(success, value, error);
  }
}
