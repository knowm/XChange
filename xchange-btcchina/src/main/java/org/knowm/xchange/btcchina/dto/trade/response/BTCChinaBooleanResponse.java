package org.knowm.xchange.btcchina.dto.trade.response;

import org.knowm.xchange.btcchina.dto.BTCChinaError;
import org.knowm.xchange.btcchina.dto.BTCChinaResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author David Yam
 */
public class BTCChinaBooleanResponse extends BTCChinaResponse<Boolean> {

  /**
   * Constructor
   *
   * @param id
   * @param result
   */
  public BTCChinaBooleanResponse(@JsonProperty("id") String id, @JsonProperty("result") Boolean result, @JsonProperty("error") BTCChinaError error) {

    super(id, result, error);
  }

}
