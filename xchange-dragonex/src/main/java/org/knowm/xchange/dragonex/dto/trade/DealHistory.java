package org.knowm.xchange.dragonex.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class DealHistory {

  private final List<Deal> list;

  public DealHistory(@JsonProperty("list") List<Deal> list) {
    this.list = list;
  }

  public List<Deal> getList() {
    return list;
  }

  @Override
  public String toString() {
    return "DealHistory [" + (list != null ? "list=" + list : "") + "]";
  }
}
