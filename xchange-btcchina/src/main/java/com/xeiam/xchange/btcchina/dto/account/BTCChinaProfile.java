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
   * 
   * @param username
   * @param tradePasswordEnabled
   * @param otpEnabled
   * @param tradeFee
   * @param dailyBtcLimit
   * @param btcDepositAddress
   * @param btcWithdrawalAddress
   */
  public BTCChinaProfile(@JsonProperty("username") String username, @JsonProperty("trade_password_enabled") Boolean tradePasswordEnabled, @JsonProperty("otp_enabled") Boolean otpEnabled,
      @JsonProperty("trade_fee") BigDecimal tradeFee, @JsonProperty("daily_btc_limit") BigDecimal dailyBtcLimit, @JsonProperty("btc_deposit_address") String btcDepositAddress,
      @JsonProperty("btc_withdrawal_address") String btcWithdrawalAddress) {

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

    return String.format("Profile{username=%s, tradePasswordEnabled=%s, otpEnabled=%s, tradeFee=%s, dailyBtcLimit=%s, btcDepositAddress=%s, btcWithdrawalAddress=%s}", username, tradePasswordEnabled,
        otpEnabled, tradeFee, dailyBtcLimit, btcDepositAddress, btcWithdrawalAddress);
  }

}
