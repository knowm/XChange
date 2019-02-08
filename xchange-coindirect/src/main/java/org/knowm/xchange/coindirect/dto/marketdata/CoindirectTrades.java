package org.knowm.xchange.coindirect.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoindirectTrades {
  public final List<CoindirectTrade> data;
  public final CoindirectTradesMetadata metaData;

  public CoindirectTrades(
      @JsonProperty("data") List<CoindirectTrade> data,
      @JsonProperty("metaData") CoindirectTradesMetadata metaData) {
    this.data = data;
    this.metaData = metaData;
  }

  @Override
  public String toString() {
    return "CoindirectTrades{" + "data=" + data + ", metaData=" + metaData + '}';
  }
}
