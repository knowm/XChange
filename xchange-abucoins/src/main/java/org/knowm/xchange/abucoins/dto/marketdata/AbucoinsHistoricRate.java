package org.knowm.xchange.abucoins.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;

/**
 * POJO representing the output JSON for the Abucoins <code>
 * GET GET /products/&lt;product-id&gt;/candles?granularity=[granularity]&start=[UTC time of start]&end=[UTC time of end]
 * </code> endpoint. Example: <code><pre>
 * [
 *     [ time, low, high, open, close, volume ],
 *     [
 *         "1505984400",
 *         "14209.92500000",
 *         "14209.92500000",
 *         "14209.92500000",
 *         "14209.92500000",
 *         "0.001"
 *     ],
 *     [
 *         "1505984460",
 *         "14209.92500000",
 *         "14209.92500000",
 *         "14209.92500000",
 *         "14209.92500000",
 *         "0.00052"
 *     ],
 *     [
 *         "1505984520",
 *         "14209.92500000",
 *         "14209.92500000",
 *         "14209.92500000",
 *         "14209.92500000",
 *         "0.00068"
 *     ]
 * ]
 * </pre></code>
 *
 * @author bryant_harris
 */
@JsonFormat(shape = JsonFormat.Shape.ARRAY)
public class AbucoinsHistoricRate {
  BigDecimal time;
  BigDecimal low;
  BigDecimal high;
  BigDecimal open;
  BigDecimal close;
  BigDecimal volume;

  public BigDecimal getTime() {
    return time;
  }

  public BigDecimal getLow() {
    return low;
  }

  public BigDecimal getHigh() {
    return high;
  }

  public BigDecimal getOpen() {
    return open;
  }

  public BigDecimal getClose() {
    return close;
  }

  public BigDecimal getVolume() {
    return volume;
  }

  @Override
  public String toString() {
    return "AbucoinsHistoricRate [time="
        + time
        + ", low="
        + low
        + ", high="
        + high
        + ", open="
        + open
        + ", close="
        + close
        + ", volume="
        + volume
        + "]";
  }
}
