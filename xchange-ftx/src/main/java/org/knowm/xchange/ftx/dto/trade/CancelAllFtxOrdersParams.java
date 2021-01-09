package org.knowm.xchange.ftx.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.service.trade.params.CancelOrderParams;

public class CancelAllFtxOrdersParams implements CancelOrderParams {

  private final String market;

  public CancelAllFtxOrdersParams(@JsonProperty("market") String market) {
    this.market = market;
  }

  public String getMarket() {
    return market;
  }

  @Override
  public String toString() {
    return "CancelAllFtxOrdersParams{" + "market='" + market + '\'' + '}';
  }
}
