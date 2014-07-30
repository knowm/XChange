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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
@JsonPropertyOrder({ "asks", "bids", "isFrozen" })
public class PoloniexDepth {

  @JsonProperty("asks")
  private List<List<BigDecimal>> asks = new ArrayList<List<BigDecimal>>();
  @JsonProperty("bids")
  private List<List<BigDecimal>> bids = new ArrayList<List<BigDecimal>>();
  @JsonProperty("isFrozen")
  private String isFrozen;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  @JsonProperty("asks")
  public List<List<BigDecimal>> getAsks() {

    return asks;
  }

  @JsonProperty("asks")
  public void setAsks(List<List<BigDecimal>> asks) {

    this.asks = asks;
  }

  @JsonProperty("bids")
  public List<List<BigDecimal>> getBids() {

    return bids;
  }

  @JsonProperty("bids")
  public void setBids(List<List<BigDecimal>> bids) {

    this.bids = bids;
  }

  @JsonProperty("isFrozen")
  public String getIsFrozen() {

    return isFrozen;
  }

  @JsonProperty("isFrozen")
  public void setIsFrozen(String isFrozen) {

    this.isFrozen = isFrozen;
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

    return "PoloniexDepth [asks=" + asks + ", bids=" + bids + ", isFrozen=" + isFrozen + ", additionalProperties=" + additionalProperties + "]";
  }

}
