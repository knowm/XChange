package org.knowm.xchange.deribit.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DerbitCandles {

  @JsonProperty("jsonrpc")
  private Double jsonrpc;

  @JsonProperty("result")
  private DerbitCandle derbitCandle;

  @JsonProperty("usIn")
  private Long usIn;

  @JsonProperty("usOut")
  private Long usOut;

  @JsonProperty("usDiff")
  private Long usDiff;

  @JsonProperty("testnet")
  private Boolean testnet;

  public Double getJsonrpc() {
    return jsonrpc;
  }

  public void setJsonrpc(Double jsonrpc) {
    this.jsonrpc = jsonrpc;
  }

  public DerbitCandle getDerbitCandle() {
    return derbitCandle;
  }

  public void setDerbitCandle(DerbitCandle derbitCandle) {
    this.derbitCandle = derbitCandle;
  }

  public Long getUsIn() {
    return usIn;
  }

  public void setUsIn(Long usIn) {
    this.usIn = usIn;
  }

  public Long getUsOut() {
    return usOut;
  }

  public void setUsOut(Long usOut) {
    this.usOut = usOut;
  }

  public Long getUsDiff() {
    return usDiff;
  }

  public void setUsDiff(Long usDiff) {
    this.usDiff = usDiff;
  }

  public Boolean getTestnet() {
    return testnet;
  }

  public void setTestnet(Boolean testnet) {
    this.testnet = testnet;
  }
}
