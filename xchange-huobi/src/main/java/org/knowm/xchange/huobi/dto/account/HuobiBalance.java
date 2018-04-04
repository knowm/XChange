package org.knowm.xchange.huobi.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;

public class HuobiBalance {

  private final long id;
  private final String state;
  private final String type;
  private final HuobiBalanceRecord[] list;

  public HuobiBalance(
      @JsonProperty("id") long id,
      @JsonProperty("state") String state,
      @JsonProperty("type") String type,
      @JsonProperty("list") HuobiBalanceRecord[] list) {
    this.id = id;
    this.state = state;
    this.type = type;
    this.list = list;
  }

  private long getId() {
    return id;
  }

  private String getState() {
    return state;
  }

  private String getType() {
    return type;
  }

  public HuobiBalanceRecord[] getList() {
    return list;
  }

  @Override
  public String toString() {
    return String.format(
        "HuobiBalance [id = %s, state = %s, type = %s, list = %s",
        getId(), getState(), getType(), Arrays.toString(getList()));
  }
}
