package org.knowm.xchange.bankera.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BankeraTickerResponse {

  private final BankeraTicker ticker;

  public BankeraTickerResponse(@JsonProperty("ticker") BankeraTicker ticker) {
    this.ticker = ticker;
  }

  @JsonProperty("ticker")
  public BankeraTicker getTicker() {
    return ticker;
  }
}
