package org.knowm.xchange.coindirect.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoindirectTicker {
  public final List<CoindirectTickerData> data;
  public final CoindirectTickerMetadata metaData;

  public CoindirectTicker(
      @JsonProperty("data") List<CoindirectTickerData> data,
      @JsonProperty("metaData") CoindirectTickerMetadata metaData) {
    this.data = data;
    this.metaData = metaData;
  }

  @Override
  public String toString() {
    return "CoindirectTicker{" + "data=" + data + ", metaData=" + metaData + '}';
  }
}
