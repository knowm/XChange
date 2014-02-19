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
package com.xeiam.xchange.coinbase.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author jamespedwards42
 */
public class CoinbaseOAuth {

  private final String accessToken;
  private final String tokenType;
  private final long expiresIn;
  private final String refreshToken;
  private final CoinbaseScope scope;

  private CoinbaseOAuth(@JsonProperty("access_token") final String accessToken, @JsonProperty("token_type") final String tokenType, @JsonProperty("expires_in") final long expiresIn,
      @JsonProperty("refresh_token") final String refreshToken, @JsonProperty("scope") final CoinbaseScope scope) {

    this.accessToken = accessToken;
    this.tokenType = tokenType;
    this.expiresIn = expiresIn;
    this.refreshToken = refreshToken;
    this.scope = scope;
  }

  public String getAccessToken() {

    return accessToken;
  }

  public String getTokenType() {

    return tokenType;
  }

  public long getExpiresIn() {

    return expiresIn;
  }

  public String getRefreshToken() {

    return refreshToken;
  }

  public CoinbaseScope getScope() {

    return scope;
  }

  @Override
  public String toString() {

    return "CoinbaseOAuthInfo [accessToken=" + accessToken + ", tokenType=" + tokenType + ", expiresIn=" + expiresIn + ", refreshToken=" + refreshToken + ", scope=" + scope + "]";
  }
}
