package org.knowm.xchange.lakebtc.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.lakebtc.dto.LakeBTCResponse;

/** User: cristian.lucaci Date: 10/3/2014 Time: 5:32 PM */
public class LakeBTCAccountInfoResponse extends LakeBTCResponse<LakeBTCAccount> {

  /**
   * Constructor
   *
   * @param id
   * @param result
   */
  public LakeBTCAccountInfoResponse(
      @JsonProperty("id") String id, @JsonProperty("result") LakeBTCAccount result) {
    super(id, result);
  }
}
