package org.knowm.xchange.ascendex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AscendexBarDto {
  @JsonProperty("ts")
  private Long timestamp;
  @JsonProperty("i")
  private String interval;
  @JsonProperty("o")
  private String openPrice;
  @JsonProperty("c")
  private String closePrice;
  @JsonProperty("h")
  private String highPrice;
  @JsonProperty("l")
  private String lowPrice;
  @JsonProperty("v")
  private String volume;
}
