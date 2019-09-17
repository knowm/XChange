package org.knowm.xchange.binance.dto.account.margin;

import java.util.List;

public class RepayRecords {

  public RepayRecords(List<RepayRecord> rows, Long total) {
    this.rows = rows;
    this.total = total;
  }

  private List<RepayRecord> rows;
  private Long total;

  public List<RepayRecord> getRows() {
    return rows;
  }

  public void setRows(List<RepayRecord> rows) {
    this.rows = rows;
  }

  public Long getTotal() {
    return total;
  }

  public void setTotal(Long total) {
    this.total = total;
  }
}
