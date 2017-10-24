package org.knowm.xchange.btcchina.dto.account.response;

import org.knowm.xchange.btcchina.dto.BTCChinaError;
import org.knowm.xchange.btcchina.dto.BTCChinaResponse;
import org.knowm.xchange.btcchina.dto.account.BTCChinaAccountInfo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author David Yam
 */
public class BTCChinaGetAccountInfoResponse extends BTCChinaResponse<BTCChinaAccountInfo> {

  /**
   * Constructor
   *
   * @param id
   * @param result
   */
  public BTCChinaGetAccountInfoResponse(@JsonProperty("id") String id, @JsonProperty("result") BTCChinaAccountInfo result,
      @JsonProperty("error") BTCChinaError error) {

    super(id, result, error);
  }

}
