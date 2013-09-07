/**
 * Copyright (C) 2013 David Yam
 * Copyright (C) 2013 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.btcchina.dto.account;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.btcchina.dto.BTCChinaValue;

/**
 * @author David Yam
 */
public class BTCChinaAccountInfo {

  private final BTCChinaProfile profile;
  private final Map<String, BTCChinaValue> balances;
  private final Map<String, BTCChinaValue> frozens;

  /**
   * Constructor
   * 
   * @param profile
   * @param balance
   * @param frozen
   */
  public BTCChinaAccountInfo(@JsonProperty("profile") BTCChinaProfile profile, @JsonProperty("balance") Map<String, BTCChinaValue> balances, @JsonProperty("frozen") Map<String, BTCChinaValue> frozens) {

    this.profile = profile;
    this.balances = balances;
    this.frozens = frozens;
  }

  public BTCChinaProfile getProfile() {

    return profile;
  }

  public Map<String, BTCChinaValue> getBalances() {

    return balances;
  }

  public Map<String, BTCChinaValue> getFrozens() {

    return frozens;
  }

  @Override
  public String toString() {

    return String.format("BTCChinaAccountInfo{profile=%s, balances=%s, frozens=%s}", profile, balances, frozens);
  }

}
