package org.knowm.xchange.cryptocom.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptoComPublicTrade {

  @JsonProperty("s")
  private String side;

  @JsonProperty("p")
  private String price;

  @JsonProperty("q")
  private String quantity;

  @JsonProperty("t")
  private Long timestamp;

  @JsonProperty("d")
  private String tradeId;

  @JsonProperty("i")
  private String instrumentName;

  @JsonProperty("m")
  private String tradeMatchId;
}
