package org.knowm.xchange.bybit.dto.marketdata.ticker;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.List;
import lombok.Data;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.bybit.dto.marketdata.ticker.BybitTickers.BybitLinearTickers;
import org.knowm.xchange.bybit.dto.marketdata.ticker.BybitTickers.BybitSpotTickers;
import org.knowm.xchange.bybit.dto.marketdata.ticker.linear.BybitLinearTicker;
import org.knowm.xchange.bybit.dto.marketdata.ticker.spot.BybitSpotTicker;

@SuperBuilder
@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "category", visible = true)
@JsonSubTypes({
  @Type(value = BybitSpotTickers.class, name = "spot"),
  @Type(value = BybitLinearTickers.class, name = "linear")
})
public abstract class BybitTickers<T extends BybitTicker> {

  @JsonProperty("category")
  BybitCategory category;

  @JsonProperty("list")
  List<T> list;

  @SuperBuilder
  @Jacksonized
  @Value
  public static class BybitSpotTickers extends BybitTickers<BybitSpotTicker> {}

  @SuperBuilder
  @Jacksonized
  @Value
  public static class BybitLinearTickers extends BybitTickers<BybitLinearTicker> {}
}
