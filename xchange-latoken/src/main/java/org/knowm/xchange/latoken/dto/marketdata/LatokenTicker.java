package org.knowm.xchange.latoken.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response schema:
 *
 * <pre>
 * {
 * 	"pairId": 502,
 * 	"symbol": "LAETH",
 * 	"volume": 1023314.3202,
 * 	"open": 134.82,
 * 	"low": 133.95,
 * 	"high": 136.22,
 * 	"close": 135.12,
 * 	"priceChange": 0.22
 * }
 * </pre>
 *
 * @author Ezer
 */
public final class LatokenTicker {

  private final long pairId;
  private final String symbol;
  private final double volume;
  private final double open;
  private final double low;
  private final double high;
  private final double close;
  private final double priceChange;

  /**
   * C'tor
   *
   * @param pairId
   * @param symbol
   * @param volume
   * @param open
   * @param low
   * @param high
   * @param close
   * @param priceChange
   */
  public LatokenTicker(
      @JsonProperty("pairId") long pairId,
      @JsonProperty("symbol") String symbol,
      @JsonProperty("volume") double volume,
      @JsonProperty("open") double open,
      @JsonProperty("low") double low,
      @JsonProperty("high") double high,
      @JsonProperty("close") double close,
      @JsonProperty("priceChange") double priceChange) {

    this.pairId = pairId;
    this.symbol = symbol;
    this.volume = volume;
    this.open = open;
    this.low = low;
    this.high = high;
    this.close = close;
    this.priceChange = priceChange;
  }

  /**
   * ID of trading pair
   *
   * @return
   */
  public long getPairId() {
    return pairId;
  }

  /**
   * Trading pair symbol
   *
   * @return
   */
  public String getSymbol() {
    return symbol;
  }

  /**
   * Traded volume in last 24h
   *
   * @return
   */
  public double getVolume() {
    return volume;
  }

  /**
   * Open price of ticker
   *
   * @return
   */
  public double getOpen() {
    return open;
  }

  /**
   * Lowest price in last 24h
   *
   * @return
   */
  public double getLow() {
    return low;
  }

  /**
   * Highest price in last 24h
   *
   * @return
   */
  public double getHigh() {
    return high;
  }

  /**
   * Close price of ticker
   *
   * @return
   */
  public double getClose() {
    return close;
  }

  /**
   * Change of price in last 24h (in percentage)
   *
   * @return
   */
  public double getPriceChange() {
    return priceChange;
  }

  @Override
  public String toString() {
    return "LatokenTicker [pairId = "
        + pairId
        + ", symbol = "
        + symbol
        + ", volume = "
        + volume
        + ", open = "
        + open
        + ", low = "
        + low
        + ", high = "
        + high
        + ", close = "
        + close
        + ", priceChange = "
        + priceChange
        + "]";
  }
}
