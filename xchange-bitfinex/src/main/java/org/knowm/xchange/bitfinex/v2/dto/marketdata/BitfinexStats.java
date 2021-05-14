package org.knowm.xchange.bitfinex.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;

/** @see https://docs.bitfinex.com/reference#rest-public-stats1 */
@JsonFormat(shape = JsonFormat.Shape.ARRAY)
@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class BitfinexStats {
  long millisecondTimestamp;
  BigDecimal value;

  public Date getTimestamp() {
    return new Date(millisecondTimestamp);
  }
}
