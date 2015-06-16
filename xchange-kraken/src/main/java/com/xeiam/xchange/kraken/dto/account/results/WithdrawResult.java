package com.xeiam.xchange.kraken.dto.account.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.kraken.dto.KrakenResult;
import com.xeiam.xchange.kraken.dto.account.Withdraw;

public class WithdrawResult extends KrakenResult<Withdraw> {

	public WithdrawResult(@JsonProperty("result") Withdraw result, @JsonProperty("error") String[] error) {
		super(result, error);
	}

}
