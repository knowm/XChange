package org.knowm.xchange.quoine.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/** @author timmolter */
public final class FiatAccount {

  private final Integer id;
  private final String currency;
  private final String currencySymbol;
  private final BigDecimal balance;
  private final String sendToBtcAddress;
  private final String pusherChannel;
  private final BigDecimal lowestOfferInterestRate;
  private final BigDecimal highestOfferInterestRate;

  /**
   * Constructor
   *
   * @param id
   * @param currency
   * @param currencySymbol
   * @param balance
   * @param sendToBtcAddress
   * @param pusherChannel
   * @param lowestOfferInterestRate
   * @param highestOfferInterestRate
   */
  public FiatAccount(
      @JsonProperty("id") Integer id,
      @JsonProperty("currency") String currency,
      @JsonProperty("currency_symbol") String currencySymbol,
      @JsonProperty("balance") BigDecimal balance,
      @JsonProperty("send_to_btc_address") String sendToBtcAddress,
      @JsonProperty("pusher_channel") String pusherChannel,
      @JsonProperty("lowest_offer_interest_rate") BigDecimal lowestOfferInterestRate,
      @JsonProperty("highest_offer_interest_rate") BigDecimal highestOfferInterestRate) {
    this.id = id;
    this.currency = currency;
    this.currencySymbol = currencySymbol;
    this.balance = balance;
    this.sendToBtcAddress = sendToBtcAddress;
    this.pusherChannel = pusherChannel;
    this.lowestOfferInterestRate = lowestOfferInterestRate;
    this.highestOfferInterestRate = highestOfferInterestRate;
  }

  public Integer getId() {
    return id;
  }

  public String getCurrency() {
    return currency;
  }

  public String getCurrencySymbol() {
    return currencySymbol;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public String getSendToBtcAddress() {
    return sendToBtcAddress;
  }

  public String getPusherChannel() {
    return pusherChannel;
  }

  public BigDecimal getLowestOfferInterestRate() {
    return lowestOfferInterestRate;
  }

  public BigDecimal getHighestOfferInterestRate() {
    return highestOfferInterestRate;
  }

  @Override
  public String toString() {
    return "FiatAccount [id="
        + id
        + ", currency="
        + currency
        + ", currencySymbol="
        + currencySymbol
        + ", balance="
        + balance
        + ", sendToBtcAddress="
        + sendToBtcAddress
        + ", pusherChannel="
        + pusherChannel
        + ", lowestOfferInterestRate="
        + lowestOfferInterestRate
        + ", highestOfferInterestRate="
        + highestOfferInterestRate;
  }
}
