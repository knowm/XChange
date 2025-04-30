package org.knowm.xchange.bitmex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.bitmex.config.converter.StringToInstrumentConverter;
import org.knowm.xchange.bitmex.config.converter.StringToOrderTypeConverter;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.instrument.Instrument;

@Data
@Builder
@Jacksonized
public class BitmexPublicOrder {

  @JsonProperty("symbol")
  @JsonDeserialize(converter = StringToInstrumentConverter.class)
  private Instrument instrument;

  @JsonProperty("id")
  private String id;

  @JsonProperty("side")
  @JsonDeserialize(converter = StringToOrderTypeConverter.class)
  private OrderType orderType;

  @JsonProperty("size")
  private BigDecimal size;

  @JsonProperty("price")
  private BigDecimal price;

  @JsonProperty("transactTime")
  private ZonedDateTime createdAt;

  @JsonProperty("timestamp")
  private ZonedDateTime updatedAt;
}
