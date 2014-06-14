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
package com.xeiam.xchange.justcoin.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author jamespedwards42
 */
public class JustcoinDepositAddress {

  private final String address;

  /**
   * Constructor
   * 
   * @param address
   */
  public JustcoinDepositAddress(final @JsonProperty("address") String address) {

    this.address = address;
  }

  public String getAddress() {

    return address;
  }

  @Override
  public String toString() {

    return "JustcoinDepositAddress [address=" + address + "]";
  }

  @Override
  public int hashCode() {

    final int prime = 31;
    int result = 1;
    result = prime * result + ((address == null) ? 0 : address.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    JustcoinDepositAddress other = (JustcoinDepositAddress) obj;
    if (address == null) {
      if (other.address != null) {
        return false;
      }
    }
    else if (!address.equals(other.address)) {
      return false;
    }
    return true;
  }
}
