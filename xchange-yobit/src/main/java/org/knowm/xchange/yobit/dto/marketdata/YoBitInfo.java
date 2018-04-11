package org.knowm.xchange.yobit.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

public class YoBitInfo {
  private Long server_time;
  private YoBitPairs pairs;

  public YoBitInfo(
      @JsonProperty("server_time") Long server_time, @JsonProperty("pairs") YoBitPairs pairs) {
    super();
    this.server_time = server_time;
    this.pairs = pairs;
  }

  public Long getServer_time() {
    return server_time;
  }

  public YoBitPairs getPairs() {
    return pairs;
  }

  @Override
  public String toString() {
    return "YoBitInfo [server_time=" + server_time + ", pairs=" + pairs + "]";
  }
}
