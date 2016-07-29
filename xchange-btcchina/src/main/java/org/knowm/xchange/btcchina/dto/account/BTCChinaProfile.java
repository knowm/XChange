package org.knowm.xchange.btcchina.dto.account;

import java.math.BigDecimal;
import java.util.LinkedHashMap;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author David Yam
 */
public class BTCChinaProfile extends LinkedHashMap<String, String> {

  private static final long serialVersionUID = 2014080701L;

  private final String username;
  private final Boolean tradePasswordEnabled;
  private final Boolean otpEnabled;
  private final BigDecimal tradeFee;
  private final int apiKeyPermission;

  /**
   * Constructor
   *
   * @param username
   * @param tradePasswordEnabled
   * @param otpEnabled
   * @param tradeFee
   * @param apiKeyPermission
   */
  public BTCChinaProfile(@JsonProperty("username") String username, @JsonProperty("trade_password_enabled") Boolean tradePasswordEnabled,
      @JsonProperty("otp_enabled") Boolean otpEnabled, @JsonProperty("trade_fee") BigDecimal tradeFee,
      @JsonProperty("api_key_permission") int apiKeyPermission) {

    this.username = username;
    this.tradePasswordEnabled = tradePasswordEnabled;
    this.otpEnabled = otpEnabled;
    this.tradeFee = tradeFee;
    this.apiKeyPermission = apiKeyPermission;
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

  /**
   * @param currencyPair cnyltc, btcltc
   * @return the trade fee of the specified market.
   */
  public BigDecimal getTradeFee(String currencyPair) {

    return new BigDecimal(get(String.format("trade_fee_%s", currencyPair)));
  }

  public BigDecimal getDailyLimit(String currency) {

    return new BigDecimal(get(String.format("daily_%s_limit", currency)));
  }

  public String getDepositAddress(String currency) {

    return get(String.format("%s_deposit_address", currency.toLowerCase()));
  }

  public String getWithdrawalAddress(String currency) {

    return get(String.format("%s_withdrawal_address", currency.toLowerCase()));
  }

  public int getApiKeyPermission() {

    return apiKeyPermission;
  }

  @Override
  public String toString() {

    return String.format("Profile{username=%s, tradePasswordEnabled=%s, otpEnabled=%s, tradeFee=%s, apiKeyPermission=%d}", username,
        tradePasswordEnabled, otpEnabled, tradeFee, apiKeyPermission);
  }

}
