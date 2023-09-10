package org.knowm.xchange.bybit.dto.marketdata.ticker.spot;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.bybit.dto.marketdata.ticker.BybitTicker;

@SuperBuilder
@Jacksonized
@Value
public class BybitSpotTicker extends BybitTicker {

  @JsonProperty("usdIndexPrice")
  BigDecimal usdIndexPrice;
}
