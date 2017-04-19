package org.knowm.xchange.btcchina.dto.account.response;

import org.knowm.xchange.btcchina.dto.BTCChinaError;
import org.knowm.xchange.btcchina.dto.BTCChinaID;
import org.knowm.xchange.btcchina.dto.BTCChinaResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author David Yam
 */
public class BTCChinaRequestWithdrawalResponse extends BTCChinaResponse<BTCChinaID> {

  /**
   * Constructor
   *
   * @param id
   * @param result
   */
  public BTCChinaRequestWithdrawalResponse(@JsonProperty("id") String id, @JsonProperty("result") BTCChinaID result,
      @JsonProperty("error") BTCChinaError error) {

    super(id, result, error);
  }

}
