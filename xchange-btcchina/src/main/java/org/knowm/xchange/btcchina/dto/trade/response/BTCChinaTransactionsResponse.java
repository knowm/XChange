package org.knowm.xchange.btcchina.dto.trade.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.btcchina.dto.BTCChinaError;
import org.knowm.xchange.btcchina.dto.BTCChinaResponse;
import org.knowm.xchange.btcchina.dto.trade.BTCChinaTransactions;

public class BTCChinaTransactionsResponse extends BTCChinaResponse<BTCChinaTransactions> {

  /**
   * Constructor
   * 
   * @param id
   * @param result
   */
  public BTCChinaTransactionsResponse(@JsonProperty("id") String id, @JsonProperty("result") BTCChinaTransactions result,
      @JsonProperty("error") BTCChinaError error) {

    super(id, result, error);
  }

}
