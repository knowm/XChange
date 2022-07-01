package org.knowm.xchange.huobi.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

public class HuobiTradeWrapper {
  private final long id;
  private final Date ts;
  private final HuobiTrade[] data;

  public HuobiTradeWrapper(
      @JsonProperty("id") long id,
      @JsonProperty("ts") Date ts,
      @JsonProperty("data") HuobiTrade[] data) {
    this.id = id;
    this.ts = ts;
    this.data = data;
  }

  public long getId() {
    return id;
  }

  public Date getTs() {
    return ts;
  }

  public HuobiTrade[] getData() {
    return data;
  }

  @Override
  public String toString() {

    return "HuobiTradeWrapper [id="
        + id
        + ", timestamp="
        + ts
        + ", data=["
        + data[0].toString()
        + "]"
        + "]";
  }
}
