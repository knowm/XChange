package org.knowm.xchange.binance.dto.account.margin;

import java.util.List;

public class LoanRecords {

  private List<LoanRecord> rows;
  private Long total;

  public LoanRecords(List<LoanRecord> rows, Long total) {
    this.rows = rows;
    this.total = total;
  }

  public List<LoanRecord> getRows() {
    return rows;
  }

  public void setRows(List<LoanRecord> rows) {
    this.rows = rows;
  }

  public Long getTotal() {
    return total;
  }

  public void setTotal(Long total) {
    this.total = total;
  }
}
