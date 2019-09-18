package org.knowm.xchange.binance.dto.account.margin;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Interests {

  private List<Interest> rows;
  private Long total;

  public Interests(@JsonProperty("rows") List<Interest> rows, @JsonProperty("total") Long total) {
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
