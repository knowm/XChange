package org.knowm.xchange.binance.dto.account.margin;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ForceLiquidationRecs {

  private List<ForceLiquidationRec> rows;
  private Long total;

  public ForceLiquidationRecs(
          @JsonProperty("rows") List<ForceLiquidationRec> rows, @JsonProperty("total") Long total) {
    this.rows = rows;
    this.total = total;
  }

  public List<ForceLiquidationRec> getRows() {
    return rows;
  }

  public void setRows(List<ForceLiquidationRec> rows) {
    this.rows = rows;
  }

  public Long getTotal() {
    return total;
  }

  public void setTotal(Long total) {
    this.total = total;
  }
}
