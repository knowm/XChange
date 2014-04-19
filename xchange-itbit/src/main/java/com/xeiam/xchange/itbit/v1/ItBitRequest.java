package com.xeiam.xchange.itbit.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.currency.Currencies;

public class ItBitRequest {
	  @JsonProperty("id")
	  protected long id = 100;

	  @JsonProperty("method")
	  protected String method = "[\"" + Currencies.BTC + "\"," + 100 + "]";
}
