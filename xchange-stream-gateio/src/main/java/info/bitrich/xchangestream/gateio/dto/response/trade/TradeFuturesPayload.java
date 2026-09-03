package info.bitrich.xchangestream.gateio.dto.response.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import org.knowm.xchange.gateio.config.converter.DoubleMillisecondsToInstantConverter;
import org.knowm.xchange.gateio.config.converter.LongToInstantConverter;
import org.knowm.xchange.gateio.config.converter.StringToFutureContractConverter;
import org.knowm.xchange.instrument.Instrument;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class TradeFuturesPayload {
  @JsonProperty("contract")
  @JsonDeserialize(converter = StringToFutureContractConverter.class)
  Instrument contract;
  @JsonProperty("size")
  BigDecimal size;
  @JsonProperty("id")
  Long id;
  @JsonProperty("create_time")
  @JsonDeserialize(converter = DoubleMillisecondsToInstantConverter.class)
  Instant time;
  @JsonProperty("create_time_ms")
  @JsonDeserialize(converter = LongToInstantConverter.class)
  Instant timeMs;
  @JsonProperty("price")
  BigDecimal price;
  boolean is_internal;
}
