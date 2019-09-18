package org.knowm.xchange.binance.dto.account.margin;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Transfers {

  private List<Transfer> rows;
  private Long total;

  public Transfers(@JsonProperty("rows") List<Transfer> rows, @JsonProperty("total") Long total) {
    this.rows = rows;
    this.total = total;
  }

  public List<Transfer> getRows() {
    return rows;
  }

  public void setRows(List<Transfer> rows) {
    this.rows = rows;
  }

  public Long getTotal() {
    return total;
  }

  public void setTotal(Long total) {
    this.total = total;
  }
}
