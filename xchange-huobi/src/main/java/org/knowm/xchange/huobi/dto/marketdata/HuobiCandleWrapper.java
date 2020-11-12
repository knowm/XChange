package org.knowm.xchange.huobi.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;
import java.util.Date;

public class HuobiCandleWrapper {

  private final long id;
  private final Date ts;
  private final HuobiCandle[] data;

  public HuobiCandleWrapper(
      @JsonProperty("id") long id,
      @JsonProperty("ts") Date ts,
      @JsonProperty("data") HuobiCandle[] data) {
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

  public HuobiCandle[] getData() {
    return data;
  }

  @Override
  public String toString() {
    return "HuobiCandleWrapper [id=" + id + ", ts=" + ts + ", data=" + Arrays.toString(data) + "]";
  }
}
