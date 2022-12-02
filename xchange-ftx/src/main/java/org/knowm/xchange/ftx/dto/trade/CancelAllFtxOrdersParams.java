package org.knowm.xchange.ftx.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.service.trade.params.CancelOrderParams;

public class CancelAllFtxOrdersParams implements CancelOrderParams {

  private final String market;

  private final boolean conditionalOrdersOnly;

  public CancelAllFtxOrdersParams(
          @JsonProperty("market") String market
  ) {
    this.market = market;
    this.conditionalOrdersOnly = false;
  }

  public CancelAllFtxOrdersParams(
          @JsonProperty("market") String market,
          @JsonProperty("conditionalOrdersOnly") boolean conditionalOrdersOnly
  ) {
    this.market = market;
    this.conditionalOrdersOnly = conditionalOrdersOnly;
  }

  public String getMarket() {
    return market;
  }

  public boolean isConditionalOrdersOnly() {
    return conditionalOrdersOnly;
  }

  @Override
  public String toString() {
    return "CancelAllFtxOrdersParams{" +
            "market='" + market + '\'' +
            ", conditionalOrdersOnly=" + conditionalOrdersOnly +
            '}';
  }
}
