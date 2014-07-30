/**
 * The MIT License
 * Copyright (c) 2012 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.xeiam.xchange.poloniex.dto.marketdata;

/**
 * @author Zach Holmes
 */

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "last", "lowestAsk", "highestBid", "percentChange", "baseVolume", "quoteVolume" })
public class PoloniexMarketData {

  @JsonProperty("last")
  private BigDecimal last;
  @JsonProperty("lowestAsk")
  private BigDecimal lowestAsk;
  @JsonProperty("highestBid")
  private BigDecimal highestBid;
  @JsonProperty("percentChange")
  private BigDecimal percentChange;
  @JsonProperty("baseVolume")
  private BigDecimal baseVolume;
  @JsonProperty("quoteVolume")
  private BigDecimal quoteVolume;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  @JsonProperty("last")
  public BigDecimal getLast() {

    return last;
  }

  @JsonProperty("last")
  public void setLast(BigDecimal last) {

    this.last = last;
  }

  @JsonProperty("lowestAsk")
  public BigDecimal getLowestAsk() {

    return lowestAsk;
  }

  @JsonProperty("lowestAsk")
  public void setLowestAsk(BigDecimal lowestAsk) {

    this.lowestAsk = lowestAsk;
  }

  @JsonProperty("highestBid")
  public BigDecimal getHighestBid() {

    return highestBid;
  }

  @JsonProperty("highestBid")
  public void setHighestBid(BigDecimal highestBid) {

    this.highestBid = highestBid;
  }

  @JsonProperty("percentChange")
  public BigDecimal getPercentChange() {

    return percentChange;
  }

  @JsonProperty("percentChange")
  public void setPercentChange(BigDecimal percentChange) {

    this.percentChange = percentChange;
  }

  @JsonProperty("baseVolume")
  public BigDecimal getBaseVolume() {

    return baseVolume;
  }

  @JsonProperty("baseVolume")
  public void setBaseVolume(BigDecimal baseVolume) {

    this.baseVolume = baseVolume;
  }

  @JsonProperty("quoteVolume")
  public BigDecimal getQuoteVolume() {

    return quoteVolume;
  }

  @JsonProperty("quoteVolume")
  public void setQuoteVolume(BigDecimal quoteVolume) {

    this.quoteVolume = quoteVolume;
  }

  @JsonAnyGetter
  public Map<String, Object> getAdditionalProperties() {

    return this.additionalProperties;
  }

  @JsonAnySetter
  public void setAdditionalProperty(String name, Object value) {

    this.additionalProperties.put(name, value);
  }

  @Override
  public String toString() {

    return "PoloniexMarketData [last=" + last + ", lowestAsk=" + lowestAsk + ", highestBid=" + highestBid + ", percentChange=" + percentChange + ", baseVolume=" + baseVolume + ", quoteVolume="
        + quoteVolume + ", additionalProperties=" + additionalProperties + "]";
  }

}
