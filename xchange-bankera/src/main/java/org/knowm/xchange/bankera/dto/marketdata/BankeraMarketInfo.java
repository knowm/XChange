package org.knowm.xchange.bankera.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BankeraMarketInfo {

  private List<BankeraMarket> markets;

  public BankeraMarketInfo(@JsonProperty("markets") List<BankeraMarket> markets) {
    this.markets = markets;
  }

  public List<BankeraMarket> getMarkets() {
    return markets;
  }
}
