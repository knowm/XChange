package org.knowm.xchange.bybit.dto.marketdata.tickers.spot;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.bybit.dto.marketdata.tickers.BybitTicker;

@SuperBuilder
@Jacksonized
@Value
public class BybitSpotTicker extends BybitTicker {

  @JsonProperty("prevPrice24h")
  BigDecimal prevPrice24h;

  @JsonProperty("price24hPcnt")
  BigDecimal price24hPcnt;

  @JsonProperty("usdIndexPrice")
  BigDecimal usdIndexPrice;
}
