package org.knowm.xchange.bybit.dto.marketdata.instruments;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.bybit.dto.BybitCategorizedPayload;
import org.knowm.xchange.bybit.dto.marketdata.instruments.BybitInstrumentsInfo.BybitLinearInverseInstrumentsInfo;
import org.knowm.xchange.bybit.dto.marketdata.instruments.BybitInstrumentsInfo.BybitOptionInstrumentsInfo;
import org.knowm.xchange.bybit.dto.marketdata.instruments.BybitInstrumentsInfo.BybitSpotInstrumentsInfo;
import org.knowm.xchange.bybit.dto.marketdata.instruments.linear.BybitLinearInverseInstrumentInfo;
import org.knowm.xchange.bybit.dto.marketdata.instruments.option.BybitOptionInstrumentInfo;
import org.knowm.xchange.bybit.dto.marketdata.instruments.spot.BybitSpotInstrumentInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "category", visible = true)
@JsonSubTypes({
  @Type(value = BybitLinearInverseInstrumentsInfo.class, name = "linear"),
  @Type(value = BybitLinearInverseInstrumentsInfo.class, name = "inverse"),
  @Type(value = BybitOptionInstrumentsInfo.class, name = "option"),
  @Type(value = BybitSpotInstrumentsInfo.class, name = "spot"),
})
public abstract class BybitInstrumentsInfo<T extends BybitInstrumentInfo>
    extends BybitCategorizedPayload<T> {

  @Jacksonized
  @Value
  public static class BybitLinearInverseInstrumentsInfo
      extends BybitInstrumentsInfo<BybitLinearInverseInstrumentInfo> {}

  @Jacksonized
  @Value
  public static class BybitOptionInstrumentsInfo
      extends BybitInstrumentsInfo<BybitOptionInstrumentInfo> {}

  @Jacksonized
  @Value
  public static class BybitSpotInstrumentsInfo
      extends BybitInstrumentsInfo<BybitSpotInstrumentInfo> {}
}
