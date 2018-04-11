package org.knowm.xchange.anx.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/** @author timmolter */
public class ANXTradesWrapper {

  private final String result;
  private final List<ANXTrade> anxTrades;
  private final String error;

  /**
   * Constructor
   *
   * @param result
   * @param anxTrades
   * @param error
   */
  public ANXTradesWrapper(
      @JsonProperty("result") String result,
      @JsonProperty("data") List<ANXTrade> anxTrades,
      @JsonProperty("error") String error) {

    this.result = result;
    this.anxTrades = anxTrades;
    this.error = error;
  }

  public String getResult() {

    return result;
  }

  public List<ANXTrade> getANXTrades() {

    return anxTrades;
  }

  public String getError() {

    return error;
  }
}
