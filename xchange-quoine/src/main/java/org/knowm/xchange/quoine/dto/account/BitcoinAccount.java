package org.knowm.xchange.quoine.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/** @author timmolter */
public final class BitcoinAccount {

  private final Integer id;
  private final BigDecimal balance;
  private final String address;
  private final String currency;
  private final String currencySymbol;
  private final String pusherChannel;
  private final BigDecimal btcMinimumWithdraw;
  private final BigDecimal lowestOfferInterestRate;
  private final BigDecimal highestOfferInterestRate;
  private final BigDecimal freeBalance;

  /**
   * Constructor
   *
   * @param id
   * @param balance
   * @param address
   * @param currency
   * @param currencySymbol
   * @param pusherChannel
   * @param btcMinimumWithdraw
   * @param lowestOfferInterestRate
   * @param highestOfferInterestRate
   * @param freeBalance
   */
  public BitcoinAccount(
      @JsonProperty("id") Integer id,
      @JsonProperty("balance") BigDecimal balance,
      @JsonProperty("address") String address,
      @JsonProperty("currency") String currency,
      @JsonProperty("currency_symbol") String currencySymbol,
      @JsonProperty("pusher_channel") String pusherChannel,
      @JsonProperty("btc_minimum_withdraw") BigDecimal btcMinimumWithdraw,
      @JsonProperty("lowest_offer_interest_rate") BigDecimal lowestOfferInterestRate,
      @JsonProperty("highest_offer_interest_rate") BigDecimal highestOfferInterestRate,
      @JsonProperty("free_balance") BigDecimal freeBalance) {
    this.id = id;
    this.balance = balance;
    this.address = address;
    this.currency = currency;
    this.currencySymbol = currencySymbol;
    this.pusherChannel = pusherChannel;
    this.btcMinimumWithdraw = btcMinimumWithdraw;
    this.lowestOfferInterestRate = lowestOfferInterestRate;
    this.highestOfferInterestRate = highestOfferInterestRate;
    this.freeBalance = freeBalance;
  }

  public Integer getId() {
    return id;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public String getAddress() {
    return address;
  }

  public String getCurrency() {
    return currency;
  }

  public String getCurrencySymbol() {
    return currencySymbol;
  }

  public String getPusherChannel() {
    return pusherChannel;
  }

  public BigDecimal getBtcMinimumWithdraw() {
    return btcMinimumWithdraw;
  }

  public BigDecimal getLowestOfferInterestRate() {
    return lowestOfferInterestRate;
  }

  public BigDecimal getHighestOfferInterestRate() {
    return highestOfferInterestRate;
  }

  public BigDecimal getFreeBalance() {
    return freeBalance;
  }

  @Override
  public String toString() {
    return "BitcoinAccount [id="
        + id
        + ", balance="
        + balance
        + ", address="
        + address
        + ", currency="
        + currency
        + ", currencySymbol="
        + currencySymbol
        + ", pusherChannel="
        + pusherChannel
        + ", btcMinimumWithdraw="
        + btcMinimumWithdraw
        + ", lowestOfferInterestRate="
        + lowestOfferInterestRate
        + ", highestOfferInterestRate="
        + highestOfferInterestRate
        + ", freeBalance="
        + freeBalance
        + "]";
  }
}
