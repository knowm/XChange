package org.knowm.xchange.bybit.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.time.Instant;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.bybit.config.converter.TimestampNanoToInstantConverter;
import org.knowm.xchange.bybit.config.converter.TimestampSecondsToInstantConverter;

@Data
@Builder
@Jacksonized
public class BybitServerTime {

  @JsonProperty("timeSecond")
  @JsonDeserialize(converter = TimestampSecondsToInstantConverter.class)
  Instant timestamp;

  @JsonProperty("timeNano")
  @JsonDeserialize(converter = TimestampNanoToInstantConverter.class)
  Instant timestampNano;

}
