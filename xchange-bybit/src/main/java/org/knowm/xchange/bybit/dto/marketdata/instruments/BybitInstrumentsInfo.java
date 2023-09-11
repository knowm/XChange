package org.knowm.xchange.bybit.dto.marketdata.instruments;

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
import org.knowm.xchange.bybit.dto.marketdata.instruments.BybitInstrumentsInfo.BybitLinearInverseInstrumentsInfo;
import org.knowm.xchange.bybit.dto.marketdata.instruments.BybitInstrumentsInfo.BybitOptionInstrumentsInfo;
import org.knowm.xchange.bybit.dto.marketdata.instruments.BybitInstrumentsInfo.BybitSpotInstrumentsInfo;
import org.knowm.xchange.bybit.dto.marketdata.instruments.linear.BybitLinearInverseInstrumentInfo;
import org.knowm.xchange.bybit.dto.marketdata.instruments.option.BybitOptionInstrumentInfo;
import org.knowm.xchange.bybit.dto.marketdata.instruments.spot.BybitSpotInstrumentInfo;

@SuperBuilder
@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "category", visible = true)
@JsonSubTypes({
  @Type(value = BybitLinearInverseInstrumentsInfo.class, name = "linear"),
  @Type(value = BybitLinearInverseInstrumentsInfo.class, name = "inverse"),
  @Type(value = BybitOptionInstrumentsInfo.class, name = "option"),
  @Type(value = BybitSpotInstrumentsInfo.class, name = "spot"),
})
public abstract class BybitInstrumentsInfo<T extends BybitInstrumentInfo> {

  @JsonProperty("category")
  BybitCategory category;

  @JsonProperty("list")
  List<T> list;

  @SuperBuilder
  @Jacksonized
  @Value
  public static class BybitLinearInverseInstrumentsInfo
      extends BybitInstrumentsInfo<BybitLinearInverseInstrumentInfo> {}

  @SuperBuilder
  @Jacksonized
  @Value
  public static class BybitOptionInstrumentsInfo
      extends BybitInstrumentsInfo<BybitOptionInstrumentInfo> {}

  @SuperBuilder
  @Jacksonized
  @Value
  public static class BybitSpotInstrumentsInfo
      extends BybitInstrumentsInfo<BybitSpotInstrumentInfo> {}
}
