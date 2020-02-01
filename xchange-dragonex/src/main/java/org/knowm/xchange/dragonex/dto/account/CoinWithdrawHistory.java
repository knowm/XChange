package org.knowm.xchange.dragonex.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class CoinWithdrawHistory {

  private final List<CoinWithdraw> list;
  private final long total;

  public CoinWithdrawHistory(
      @JsonProperty("list") List<CoinWithdraw> list, @JsonProperty("total") long total) {
    this.list = list;
    this.total = total;
  }

  public List<CoinWithdraw> getList() {
    return list;
  }

  public long getTotal() {
    return total;
  }

  @Override
  public String toString() {
    return "CoinWithdrawHistory ["
        + (list != null ? "list=" + list + ", " : "")
        + "total="
        + total
        + "]";
  }
}
