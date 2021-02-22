package org.knowm.xchange.bitfinex.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

/** @author cyrus13 * */
@JsonFormat(shape = JsonFormat.Shape.ARRAY)
@Jacksonized
@Data
@Builder
public class BitfinexCandle {
  private final long millisecondTimestamp;
  private final double open;
  private final double close;
  private final double high;
  private final double low;
  private final double volume;

  public ZonedDateTime getCandleDateTime() {
    final Instant instant = Instant.ofEpochMilli(millisecondTimestamp);
    return ZonedDateTime.ofInstant(instant, ZoneOffset.UTC);
  }
}
