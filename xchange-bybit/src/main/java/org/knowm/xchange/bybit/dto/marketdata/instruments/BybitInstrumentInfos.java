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
import org.knowm.xchange.bybit.dto.marketdata.instruments.BybitInstrumentInfos.BybitLinearInstrumentInfos;
import org.knowm.xchange.bybit.dto.marketdata.instruments.BybitInstrumentInfos.BybitSpotInstrumentInfos;
import org.knowm.xchange.bybit.dto.marketdata.instruments.linear.BybitLinearInstrumentInfo;
import org.knowm.xchange.bybit.dto.marketdata.instruments.spot.BybitSpotInstrumentInfo;

@SuperBuilder
@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "category", visible = true)
@JsonSubTypes({
  @Type(value = BybitSpotInstrumentInfos.class, name = "spot"),
  @Type(value = BybitLinearInstrumentInfos.class, name = "linear"),
  @Type(value = BybitLinearInstrumentInfos.class, name = "inverse")
})
public abstract class BybitInstrumentInfos<T extends BybitInstrumentInfo> {

  @JsonProperty("category")
  BybitCategory category;

  @JsonProperty("list")
  List<T> list;

  @SuperBuilder
  @Jacksonized
  @Value
  public static class BybitSpotInstrumentInfos
      extends BybitInstrumentInfos<BybitSpotInstrumentInfo> {}

  @SuperBuilder
  @Jacksonized
  @Value
  public static class BybitLinearInstrumentInfos
      extends BybitInstrumentInfos<BybitLinearInstrumentInfo> {}
}
