package org.knowm.xchange.coinex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class CoinexSingleMarketStatisticsV1 {

  @JsonProperty("date")
  private Instant timestamp;

  @JsonProperty("ticker")
  private CoinexTickerV1 ticker;

}
