/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.vaultofsatoshi.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data object representing Ticker from VaultOfSatoshi
 */

public final class VaultOfSatoshiTicker {

  private final VosCurrency closing_price;
  private final VosCurrency max_price;
  private final VosCurrency min_price;
  private final VosCurrency volume_1day;
  private final long timestamp;
  
  private final VosCurrency opening_price;
  private final VosCurrency units_traded;  
  private final VosCurrency volume_7day;
  private final VosCurrency average_price;

  public VaultOfSatoshiTicker(@JsonProperty("closing_price") VosCurrency closing_price, 
		  @JsonProperty("max_price") VosCurrency max_price, 
		  @JsonProperty("min_price") VosCurrency min_price, 
		  @JsonProperty("volume_1day") VosCurrency volume_1day,
		  @JsonProperty("date") long timestamp,
		  @JsonProperty("opening_price") VosCurrency opening_price,
		  @JsonProperty("units_traded") VosCurrency units_traded,
		  @JsonProperty("volume_7day") VosCurrency volume_7day,
		  @JsonProperty("average_price") VosCurrency average_price) {

    this.closing_price = closing_price;
    this.max_price = max_price;
    this.min_price = min_price;
    this.volume_1day = volume_1day;
    this.timestamp = timestamp;
    this.units_traded = units_traded;
    this.opening_price = opening_price;
    this.volume_7day = volume_7day;
    this.average_price = average_price;
  }

  public BigDecimal getLast() {

    return closing_price.getValue();
  }

  public BigDecimal getHigh() {

    return max_price.getValue();
  }

  public BigDecimal getLow() {

    return min_price.getValue();
  }

  public BigDecimal getVolume() {

    return volume_1day.getValue();
  }
  
  public BigDecimal getVolume7Day() {

	    return volume_7day.getValue();
  }
  
  public long getTimestamp() {

	    return timestamp;
  }
  
  public BigDecimal getOpeningPrice() {
	
	  return opening_price.getValue();
  }

	public BigDecimal getUnitsTraded() {
		
		return units_traded.getValue();
	}

	public BigDecimal getAveragePrice() {
		
		return average_price.getValue();
	}


  @Override
  public String toString() {

    return "VaultOfSatoshiTicker [last=" + closing_price.getValue() + ", high=" + max_price.getValue()  + ", low=" + min_price.getValue()  + ", volume=" + volume_1day.getValue()  + ", timestamp=" + timestamp + "]";
  }


}
