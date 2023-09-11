package org.knowm.xchange.bybit.dto.marketdata.tickers;

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
import org.knowm.xchange.bybit.dto.marketdata.tickers.BybitTickers.BybitLinearInverseTickers;
import org.knowm.xchange.bybit.dto.marketdata.tickers.BybitTickers.BybitOptionTickers;
import org.knowm.xchange.bybit.dto.marketdata.tickers.BybitTickers.BybitSpotTickers;
import org.knowm.xchange.bybit.dto.marketdata.tickers.linear.BybitLinearInverseTicker;
import org.knowm.xchange.bybit.dto.marketdata.tickers.option.BybitOptionTicker;
import org.knowm.xchange.bybit.dto.marketdata.tickers.spot.BybitSpotTicker;

@SuperBuilder
@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "category", visible = true)
@JsonSubTypes({
  @Type(value = BybitLinearInverseTickers.class, name = "linear"),
  @Type(value = BybitLinearInverseTickers.class, name = "inverse"),
  @Type(value = BybitOptionTickers.class, name = "option"),
  @Type(value = BybitSpotTickers.class, name = "spot")
})
public abstract class BybitTickers<T extends BybitTicker> {

  @JsonProperty("category")
  BybitCategory category;

  @JsonProperty("list")
  List<T> list;

  @SuperBuilder
  @Jacksonized
  @Value
  public static class BybitLinearInverseTickers extends BybitTickers<BybitLinearInverseTicker> {}

  @SuperBuilder
  @Jacksonized
  @Value
  public static class BybitOptionTickers extends BybitTickers<BybitOptionTicker> {}

  @SuperBuilder
  @Jacksonized
  @Value
  public static class BybitSpotTickers extends BybitTickers<BybitSpotTicker> {}
}
