package org.knowm.xchange.binance.dto.account.margin;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class LoanRecords {

  private List<LoanRecord> rows;
  private Long total;

  public LoanRecords(
          @JsonProperty("rows") List<LoanRecord> rows, @JsonProperty("total") Long total) {
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
