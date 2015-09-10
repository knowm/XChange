/*
 * The MIT License
 *
 * Copyright 2015 Xeiam, LLC.
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
package com.xeiam.xchange.okcoin.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.okcoin.dto.trade.OkCoinErrorResult;

/**
 * Withdraw result info
 * @author Ondřej Novotný
 */
public class OKCoinWithdraw extends OkCoinErrorResult{
    private final String withdrawId;
    
    
  /*
   * withdraw_id: withdrawal request ID
   * result: true means request successful
   */
  public OKCoinWithdraw(@JsonProperty("result") final boolean result, @JsonProperty("error_code") final int errorCode, 
          @JsonProperty("withdraw_id") final String withdrawId) {
    super(result, errorCode);    
    this.withdrawId = withdrawId;    
  }

  public String getWithdrawId() {
    return withdrawId;
  }

  @Override
  public String toString() {
    return "Withdraw [refid=" + withdrawId + "]";
  }

}
