package org.knowm.xchange.binance.dto.account.margin;

import java.util.List;

public class Transfers {

  private List<Transfer> rows;
  private Long total;

  public Transfers(List<Transfer> rows, Long total) {
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
