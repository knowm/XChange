/**
 * Copyright (C) 2012 - 2013 Xeiam LLC http://xeiam.com
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.mtgox.v1.dto.marketdata;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * <p>
 * Data object representing a Market Depth Change from Mt Gox
 * </p>
 * Auto-generated using the simplest types possible with conversion delegated to the adapter
 */
public final class MtGoxDepthStream {

  private final double price;
  private final int tradeTypeInt;
  private final String tradeType;
  private final double deltaVolume;
  private final long priceInt;
  private final long deltaVolumeInt;
  private final String item;
  private final String priceCurrency;
  private final long now;
  private final long newVolumeInt;

  /**
   * Constructor
   * 
   * @param priceCurrency
   * @param item
   * @param tradeType
   * @param price_int
   * @param total_volume_int
   * @param volume
   */

  public MtGoxDepthStream(@JsonProperty("price") double price, @JsonProperty("type") int tradeTypeInt, @JsonProperty("type_str") String tradeType, @JsonProperty("volume") double deltaVolume,
      @JsonProperty("price_int") long priceInt, @JsonProperty("volume_int") long deltaVolumeInt, @JsonProperty("item") String item, @JsonProperty("currency") String priceCurrency,
      @JsonProperty("now") long now, @JsonProperty("total_volume_int") long newVolumeInt) {

    // json:
    // {"price":"20.67345","type":2,"type_str":"bid","volume":"-28.74849577","price_int":"2067345","volume_int":"-2874849577","item":"BTC","currency":"USD","now":"1359713940483815","total_volume_int":"0"}
    this.price = price;
    this.tradeTypeInt = tradeTypeInt;
    this.tradeType = tradeType;
    this.deltaVolume = deltaVolume;
    this.priceInt = priceInt;
    this.deltaVolumeInt = deltaVolumeInt;
    this.item = item;
    this.priceCurrency = priceCurrency;
    this.now = now;
    this.newVolumeInt = newVolumeInt;

  }

  public double getVolume() {

    return deltaVolume;
  }

  public long getNewVolume() {

    return newVolumeInt;
  }

  public long getDate() {

    return now;
  }

  public String getItem() {

    return item;
  }

  public String getPriceCurrency() {

    return priceCurrency;
  }

  public long getPriceInt() {

    return priceInt;
  }

  public String getTradeType() {

    return tradeType;
  }

  @Override
  public String toString() {

    return "MtGoxDepth Change [At Price=" + priceInt + ", Vol Change=" + deltaVolume + ", New Vol=" + newVolumeInt + ", date=" + now + ", item=" + item + ", tradeType=" + tradeType + "]";
  }

}
