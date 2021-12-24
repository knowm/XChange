package org.knowm.xchange.binance.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public final class BinanceDustLog {
  private final Integer total;
  private final List<BinanceDribblets> dribblets;

  public BinanceDustLog(
      @JsonProperty("total") Integer total,
      @JsonProperty("userAssetDribblets") List<BinanceDribblets> dribblets) {
    this.total = total;
    this.dribblets = dribblets;
  }

  public Integer getTotal() {
    return total;
  }

  public List<BinanceDribblets> getDribblets() {
    return dribblets;
  }
}
