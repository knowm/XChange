package com.xeiam.xchange.btcchina.dto.trade.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.btcchina.dto.BTCChinaResponse;
import com.xeiam.xchange.btcchina.dto.trade.BTCChinaTransactions;

public class BTCChinaTransactionsResponse extends BTCChinaResponse<BTCChinaTransactions> {

	  /**
	   * Constructor
	   * 
	   * @param id
	   * @param result
	   */
	  public BTCChinaTransactionsResponse(@JsonProperty("id") String id, @JsonProperty("result") BTCChinaTransactions result) {

	    super(id, result);
	  }

}
