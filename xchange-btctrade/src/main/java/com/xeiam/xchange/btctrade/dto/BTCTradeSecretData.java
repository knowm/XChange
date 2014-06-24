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
package com.xeiam.xchange.btctrade.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BTCTradeSecretData {

  private final String secret;
  private final String id;
  private final String uid;
  private final String level;
  private final String expires;

  public BTCTradeSecretData(
      @JsonProperty("secret") String secret,
      @JsonProperty("id") String id,
      @JsonProperty("uid") String uid,
      @JsonProperty("level") String level,
      @JsonProperty("expires") String expires) {
    this.secret = secret;
    this.id = id;
    this.uid = uid;
    this.level = level;
    this.expires = expires;
  }

  public String getSecret() {
    return secret;
  }

  public String getId() {
    return id;
  }

  public String getUid() {
    return uid;
  }

  public String getLevel() {
    return level;
  }

  public String getExpires() {
    return expires;
  }

}
