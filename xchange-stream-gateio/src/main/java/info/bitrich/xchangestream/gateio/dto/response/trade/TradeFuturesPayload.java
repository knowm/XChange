package info.bitrich.xchangestream.gateio.dto.response.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import org.knowm.xchange.gateio.config.converter.DoubleToInstantConverter;
import org.knowm.xchange.gateio.config.converter.StringToCurrencyPairConverter;
import org.knowm.xchange.gateio.config.converter.TimestampSecondsToInstantConverter;
import org.knowm.xchange.instrument.Instrument;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class TradeFuturesPayload {
  @JsonProperty("contract")
  @JsonDeserialize(converter = StringToCurrencyPairConverter.class)
  Instrument contract;
  @JsonProperty("size")
  BigDecimal size;
  @JsonProperty("id")
  Long id;
  @JsonProperty("create_time")
  @JsonDeserialize(converter = TimestampSecondsToInstantConverter.class)
  Instant time;
  @JsonProperty("create_time_ms")
  @JsonDeserialize(converter = DoubleToInstantConverter.class)
  Instant timeMs;
  @JsonProperty("price")
  BigDecimal price;
  boolean is_internal;
}
