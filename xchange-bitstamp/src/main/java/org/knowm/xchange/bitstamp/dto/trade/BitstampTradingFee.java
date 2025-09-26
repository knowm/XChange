package org.knowm.xchange.bitstamp.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BitstampTradingFee {

  @JsonProperty("currency_pair")
  private String currencyPair;

  @JsonProperty("fees")
  private List<FeeDto> fees;

  @JsonProperty("market")
  private String market;

  @Data
  @NoArgsConstructor
  public static class FeeDto {
    @JsonProperty("maker")
    private String maker;

    @JsonProperty("taker")
    private String taker;
  }
}
