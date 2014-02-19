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

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.coinbase.dto.CoinbaseBaseResponse;

/**
 * @author jamespedwards42
 */
public class CoinbaseToken extends CoinbaseBaseResponse {

  private final CoinbaseTokenInfo token;
  
  private CoinbaseToken(@JsonProperty("token") final CoinbaseTokenInfo token, @JsonProperty("success") final boolean success, @JsonProperty("errors") final List<String> errors) {
    
    super(success, errors);
    this.token = token;
  }
  
  public String getTokenId() {

    return token.getTokenId();
  }

  public String getAddress() {

    return token.getAddress();
  }
  
  @Override
  public String toString() {

    return "CoinbaseToken [token=" + token + "]";
  }

  private static class CoinbaseTokenInfo {
    
    private final String tokenId;
    private final String address;
    
    private CoinbaseTokenInfo(@JsonProperty("token_id") final String tokenId, @JsonProperty("address") final String address) {
      
      this.tokenId = tokenId;
      this.address = address;
    }

    private String getTokenId() {

      return tokenId;
    }

    private String getAddress() {

      return address;
    }

    @Override
    public String toString() {

      return "CoinbaseTokenInfo [tokenId=" + tokenId + ", address=" + address + "]";
    }
    
  }
}
