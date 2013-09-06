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

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * @author David Yam
 */
public class BTCChinaProfile {
  
  private final String username;
  private final Boolean tradePasswordEnabled;
  private final Boolean otpEnabled;
  private final BigDecimal tradeFee;
  private final BigDecimal dailyBtcLimit;
  private final String btcDepositAddress;
  private final String btcWithdrawalAddress;
  
  /**
   * Constructor
   * @param username
   * @param tradePasswordEnabled
   * @param otpEnabled
   * @param tradeFee
   * @param dailyBtcLimit
   * @param btcDepositAddress
   * @param btcWithdrawalAddress
   */
  public BTCChinaProfile(@JsonProperty("username") String username, 
      @JsonProperty("trade_password_enabled") Boolean tradePasswordEnabled,
      @JsonProperty("otp_enabled") Boolean otpEnabled,
      @JsonProperty("trade_fee") BigDecimal tradeFee,
      @JsonProperty("daily_btc_limit") BigDecimal dailyBtcLimit,
      @JsonProperty("btc_deposit_address") String btcDepositAddress,
      @JsonProperty("btc_withdrawal_address") String btcWithdrawalAddress){
    
    this.username = username;
    this.tradePasswordEnabled = tradePasswordEnabled;
    this.otpEnabled = otpEnabled;
    this.tradeFee = tradeFee;
    this.dailyBtcLimit = dailyBtcLimit;
    this.btcDepositAddress = btcDepositAddress;
    this.btcWithdrawalAddress = btcWithdrawalAddress;
  }
  
  public String getUsername() {
    return username;
  }

  public Boolean getTradePasswordEnabled() {
    return tradePasswordEnabled;
  }

  public Boolean getOtpEnabled() {
    return otpEnabled;
  }

  public BigDecimal getTradeFee() {
    return tradeFee;
  }

  public BigDecimal getDailyBtcLimit() {
    return dailyBtcLimit;
  }

  public String getBtcDepositAddress() {
    return btcDepositAddress;
  }

  public String getBtcWithdrawalAddress() {
    return btcWithdrawalAddress;
  }

  @Override
  public String toString() {
    
    return String.format("Profile{username=%s, tradePasswordEnabled=%s, otpEnabled=%s, tradeFee=%s, dailyBtcLimit=%s, btcDepositAddress=%s, btcWithdrawalAddress=%s}", username, tradePasswordEnabled, otpEnabled, tradeFee, dailyBtcLimit, btcDepositAddress, btcWithdrawalAddress);
  }
 
  
}
