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
package com.xeiam.xchange.bittrex.v1.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BittrexSymbol {

  private String baseCurrency;
  private String baseCurrencyLong;
  private String created;
  private boolean isActive;
  private String marketCurrency;
  private String marketCurrencyLong;
  private String marketName;
  private Number minTradeSize;

  public BittrexSymbol(@JsonProperty("BaseCurrency") String baseCurrency, @JsonProperty("BaseCurrencyLong") String baseCurrencyLong, @JsonProperty("Created") String created,
      @JsonProperty("IsActive") boolean isActive, @JsonProperty("MarketCurrency") String marketCurrency, @JsonProperty("MarketCurrencyLong") String marketCurrencyLong,
      @JsonProperty("MarketName") String marketName, @JsonProperty("MinTradeSize") Number minTradeSize) {

    this.baseCurrency = baseCurrency;
    this.baseCurrencyLong = baseCurrencyLong;
    this.created = created;
    this.isActive = isActive;
    this.marketCurrency = marketCurrency;
    this.marketCurrencyLong = marketCurrencyLong;
    this.marketName = marketName;
    this.minTradeSize = minTradeSize;

  }

  public String getBaseCurrency() {

    return this.baseCurrency;
  }

  public void setBaseCurrency(String baseCurrency) {

    this.baseCurrency = baseCurrency;
  }

  public String getBaseCurrencyLong() {

    return this.baseCurrencyLong;
  }

  public void setBaseCurrencyLong(String baseCurrencyLong) {

    this.baseCurrencyLong = baseCurrencyLong;
  }

  public String getCreated() {

    return this.created;
  }

  public void setCreated(String created) {

    this.created = created;
  }

  public boolean getIsActive() {

    return this.isActive;
  }

  public void setIsActive(boolean isActive) {

    this.isActive = isActive;
  }

  public String getMarketCurrency() {

    return this.marketCurrency;
  }

  public void setMarketCurrency(String marketCurrency) {

    this.marketCurrency = marketCurrency;
  }

  public String getMarketCurrencyLong() {

    return this.marketCurrencyLong;
  }

  public void setMarketCurrencyLong(String marketCurrencyLong) {

    this.marketCurrencyLong = marketCurrencyLong;
  }

  public String getMarketName() {

    return this.marketName;
  }

  public void setMarketName(String marketName) {

    this.marketName = marketName;
  }

  public Number getMinTradeSize() {

    return this.minTradeSize;
  }

  public void setMinTradeSize(Number minTradeSize) {

    this.minTradeSize = minTradeSize;
  }

  @Override
  public String toString() {

    return "BittrexSymbol [baseCurrency=" + baseCurrency + ", baseCurrencyLong=" + baseCurrencyLong + ", created=" + created + ", isActive=" + isActive + ", marketCurrency=" + marketCurrency
        + ", marketCurrencyLong=" + marketCurrencyLong + ", marketName=" + marketName + ", minTradeSize=" + minTradeSize + "]";
  }

}
