package org.knowm.xchange.coinex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import java.util.Map;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class CoinexAllMarketStatisticsV1 {

  @JsonProperty("date")
  private Instant timestamp;

  @JsonProperty("ticker")
  private Map<String, CoinexTickerV1> tickers;
}
