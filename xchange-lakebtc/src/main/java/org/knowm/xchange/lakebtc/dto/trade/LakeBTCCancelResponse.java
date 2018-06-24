package org.knowm.xchange.lakebtc.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.lakebtc.dto.LakeBTCResponse;

/** Created by cristian.lucaci on 12/19/2014. */
public class LakeBTCCancelResponse extends LakeBTCResponse<String> {

  /**
   * Constructor
   *
   * @param id
   * @param result
   */
  public LakeBTCCancelResponse(
      @JsonProperty("id") String id, @JsonProperty("result") String result) {
    super(id, result);
  }
}
