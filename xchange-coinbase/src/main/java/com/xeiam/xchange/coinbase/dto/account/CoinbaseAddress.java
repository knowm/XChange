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
package com.xeiam.xchange.coinbase.dto.account;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.xeiam.xchange.coinbase.dto.CoinbaseBaseResponse;
import com.xeiam.xchange.utils.jackson.ISO8601DateDeserializer;

/**
 * @author jamespedwards42
 */
public class CoinbaseAddress extends CoinbaseBaseResponse {

  private final String address;
  private final String callbackUrl;
  private final String label;
  private final Date createdAt;
  
  CoinbaseAddress(final String address, final String callbackUrl, final String label, final Date createdAt) {
    
    super(true, null);
    this.address = address;
    this.callbackUrl = callbackUrl;
    this.label = label;
    this.createdAt = createdAt;
  }
  
  private CoinbaseAddress(@JsonProperty("address") final String address, @JsonProperty("callback_url") final String callbackUrl,
      @JsonProperty("label") final String label, @JsonProperty("created_at") @JsonDeserialize(using=ISO8601DateDeserializer.class) final Date createdAt, 
      @JsonProperty("success") final boolean success, @JsonProperty("errors") final List<String> errors) {

    super(success, errors);
    this.address = address;
    this.callbackUrl = callbackUrl;
    this.label = label;
    this.createdAt = createdAt;
  }

  public String getAddress() {

    return address;
  }

  public String getCallbackUrl() {

    return callbackUrl;
  }

  public String getLabel() {

    return label;
  }

  public Date getCreatedAt() {

    return createdAt;
  }

  @Override
  public String toString() {

    return "CoinbaseAddress [address=" + address + ", callbackUrl=" + callbackUrl + ", label=" + label + ", createdAt=" + createdAt + "]";
  }

}
