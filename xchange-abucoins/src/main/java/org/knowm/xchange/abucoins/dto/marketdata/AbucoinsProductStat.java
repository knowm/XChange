package org.knowm.xchange.abucoins.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/**
 * POJO representing the output JSON for the Abucoins <code>GET /products/stats</code> endpoint.
 * Example: <code><pre>
 * [
 *   {
 *     "product_id":"BTC-PLN",
 *     "last":"14734.55000000",
 *     "open":"14734.55000000",
 *     "high":"0.00000000",
 *     "low":"0.00000000",
 *     "volume":"1.00000000",
 *     "volume_BTC":"1.00000000",
 *     "volume_USD":"4086.69",
 *     "volume_7d":"0.00000000",
 *     "volume_30d":"149.45718835",
 *     "change":"0.00"
 *   },
 *   {
 *     "product_id":"BTC-USD",
 *     "last":"4086.69000000",
 *     "open":"4086.69000000",
 *     "high":"0.00000000",
 *     "low":"0.00000000",
 *     "volume":"1.00000000",
 *     "volume_BTC":"1.00000000",
 *     "volume_USD":"4086.69",
 *     "volume_7d":"0.00000000",
 *     "volume_30d":"53.95784974",
 *     "change":"0.00"
 *   }
 * ]
 * </pre></code>
 *
 * @author bryant_harris
 */
public class AbucoinsProductStat {
  String productID;
  BigDecimal last;
  BigDecimal open;
  BigDecimal high;
  BigDecimal low;
  BigDecimal volume;
  BigDecimal volumeBTC;
  BigDecimal volumeUSD;
  BigDecimal volume7d;
  BigDecimal volume30d;
  BigDecimal change;
  String message;

  public AbucoinsProductStat(
      @JsonProperty("product_id") String productID,
      @JsonProperty("last") BigDecimal last,
      @JsonProperty("open") BigDecimal open,
      @JsonProperty("high") BigDecimal high,
      @JsonProperty("low") BigDecimal low,
      @JsonProperty("volume") BigDecimal volume,
      @JsonProperty("volume_BTC") BigDecimal volumeBTC,
      @JsonProperty("volume_USD") BigDecimal volumeUSD,
      @JsonProperty("volume_7d") BigDecimal volume7d,
      @JsonProperty("volume_30d") BigDecimal volume30d,
      @JsonProperty("change") BigDecimal change,
      @JsonProperty("message") String message) {
    this.productID = productID;
    this.last = last;
    this.open = open;
    this.high = high;
    this.low = low;
    this.volume = volume;
    this.volumeBTC = volumeBTC;
    this.volumeUSD = volumeUSD;
    this.volume7d = volume7d;
    this.volume30d = volume30d;
    this.change = change;
    this.message = message;
  }

  public String getProductID() {
    return productID;
  }

  public BigDecimal getLast() {
    return last;
  }

  public BigDecimal getOpen() {
    return open;
  }

  public BigDecimal getHigh() {
    return high;
  }

  public BigDecimal getLow() {
    return low;
  }

  public BigDecimal getVolume() {
    return volume;
  }

  public BigDecimal getVolumeBTC() {
    return volumeBTC;
  }

  public BigDecimal getVolumeUSD() {
    return volumeUSD;
  }

  public BigDecimal getVolume7d() {
    return volume7d;
  }

  public BigDecimal getVolume30d() {
    return volume30d;
  }

  public BigDecimal getChange() {
    return change;
  }

  public String getMessage() {
    return message;
  }

  @Override
  public String toString() {
    return "AbucoinsProductStat [productID="
        + productID
        + ", last="
        + last
        + ", open="
        + open
        + ", high="
        + high
        + ", low="
        + low
        + ", volume="
        + volume
        + ", volumeBTC="
        + volumeBTC
        + ", volumeUSD="
        + volumeUSD
        + ", volume7d="
        + volume7d
        + ", volume30d="
        + volume30d
        + ", change="
        + change
        + "]";
  }
}
