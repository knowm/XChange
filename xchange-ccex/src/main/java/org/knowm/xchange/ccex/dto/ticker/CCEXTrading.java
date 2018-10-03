package org.knowm.xchange.ccex.dto.ticker;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({"ticker"})
public class CCEXTrading {

  @JsonProperty("ticker")
  private Ticker ticker;

  /** No args constructor for use in serialization */
  public CCEXTrading() {}

  /** @param ticker */
  public CCEXTrading(Ticker ticker) {
    this.ticker = ticker;
  }

  /** @return The ticker */
  @JsonProperty("ticker")
  public Ticker getTicker() {
    return ticker;
  }

  /** @param ticker The ticker */
  @JsonProperty("ticker")
  public void setTicker(Ticker ticker) {
    this.ticker = ticker;
  }

  public CCEXTrading withTicker(Ticker ticker) {
    this.ticker = ticker;
    return this;
  }
}
