package org.knowm.xchange.binance.dto.account.margin;

import java.util.List;

public class Interests {

  private List<Interest> rows;
  private Long total;

  public Interests(List<Interest> rows, Long total) {
    this.rows = rows;
    this.total = total;
  }

  public List<Interest> getRows() {
    return rows;
  }

  public void setRows(List<Interest> rows) {
    this.rows = rows;
  }

  public Long getTotal() {
    return total;
  }

  public void setTotal(Long total) {
    this.total = total;
  }
}
