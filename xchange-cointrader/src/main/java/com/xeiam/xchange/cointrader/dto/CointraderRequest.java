package com.xeiam.xchange.cointrader.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CointraderRequest {

  @JsonProperty private String t;

  // todo: use a nonce factory to avoid clashes
  public CointraderRequest() {
    t = String.format("%tc", new Date());
  }
}
