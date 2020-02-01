package org.knowm.xchange.dragonex.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class CoinPrepayHistory {

  private final List<CoinPrepay> list;
  private final long total;

  public CoinPrepayHistory(
      @JsonProperty("list") List<CoinPrepay> list, @JsonProperty("total") long total) {
    this.list = list;
    this.total = total;
  }

  public List<CoinPrepay> getList() {
    return list;
  }

  public long getTotal() {
    return total;
  }

  @Override
  public String toString() {
    return "CoinPrepayHistory ["
        + (list != null ? "list=" + list + ", " : "")
        + "total="
        + total
        + "]";
  }
}
