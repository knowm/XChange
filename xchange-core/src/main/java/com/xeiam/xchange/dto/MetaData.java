package com.xeiam.xchange.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.currency.CurrencyPair;

/**
 * This class holds data "hardcoded" in a json file found in each module's "resources" folder. It is loaded during creation of the Exchange and is
 * intended to hold semi-static data that is not readily available from an HTTP API request at an exchange, but is still important information to
 * have. Examples include currency pairs, max polling rates, scaling factors, etc.
 * <p>
 * This data may be directly accessed or it can be used by the service classes to enhance or supplement data that may not be directly obtained from
 * the exchange.
 *
 * @author timmolter
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MetaData {

  @JsonProperty("currency_pairs")
  private List<CurrencyPair> currencyPairs;

  private Integer maxPrivatePollRatePerSecond;
  private Integer maxPrivatePollRatePer10Second;
  private Integer maxPrivatePollRatePerMinute;
  private Integer maxPrivatePollRatePerHour;

  private Integer maxPublicPollRatePerSecond;
  private Integer maxPublicPollRatePer10Second;
  private Integer maxPublicPollRatePerMinute;
  private Integer maxPublicPollRatePerHour;

  private Integer fiatAmountMultiplier;
  private Integer cryptoAmountMultiplier;

  /**
   * Constructor
   *
   * @param currencyPairs
   * @param maxPrivatePollRatePerSecond
   * @param maxPrivatePollRatePer10Second
   * @param maxPrivatePollRatePerMinute
   * @param maxPrivatePollRatePerHour
   * @param maxPublicPollRatePerSecond
   * @param maxPublicPollRatePer10Second
   * @param maxPublicPollRatePerMinute
   * @param maxPublicPollRatePerHour
   * @param fiatAmountMultiplier
   * @param cryptoAmountMultiplier
   */
  public MetaData(@JsonProperty("currency_pairs") List<CurrencyPair> currencyPairs,
      @JsonProperty("max_private_poll_rate_per_second") int maxPrivatePollRatePerSecond,
      @JsonProperty("max_private_poll_rate_per_10_second") int maxPrivatePollRatePer10Second,
      @JsonProperty("max_private_poll_rate_per_minute") int maxPrivatePollRatePerMinute,
      @JsonProperty("max_private_poll_rate_per_hour") int maxPrivatePollRatePerHour,
      @JsonProperty("max_public_poll_rate_per_second") int maxPublicPollRatePerSecond,
      @JsonProperty("max_public_poll_rate_per_10_second") int maxPublicPollRatePer10Second,
      @JsonProperty("max_public_poll_rate_per_minute") int maxPublicPollRatePerMinute,
      @JsonProperty("max_public_poll_rate_per_hour") int maxPublicPollRatePerHour, @JsonProperty("fiat_amount_multiplier") int fiatAmountMultiplier,
      @JsonProperty("crypto_amount_multiplier") int cryptoAmountMultiplier) {
    this.currencyPairs = currencyPairs;
    this.maxPrivatePollRatePerSecond = maxPrivatePollRatePerSecond;
    this.maxPrivatePollRatePer10Second = maxPrivatePollRatePer10Second;
    this.maxPrivatePollRatePerMinute = maxPrivatePollRatePerMinute;
    this.maxPrivatePollRatePerHour = maxPrivatePollRatePerHour;
    this.maxPublicPollRatePerSecond = maxPublicPollRatePerSecond;
    this.maxPublicPollRatePer10Second = maxPublicPollRatePer10Second;
    this.maxPublicPollRatePerMinute = maxPublicPollRatePerMinute;
    this.maxPublicPollRatePerHour = maxPublicPollRatePerHour;
    this.fiatAmountMultiplier = fiatAmountMultiplier;
    this.cryptoAmountMultiplier = cryptoAmountMultiplier;
  }

  public List<CurrencyPair> getCurrencyPairs() {
    return currencyPairs;
  }

  public void setCurrencyPairs(List<CurrencyPair> currencyPairs) {
    this.currencyPairs = currencyPairs;
  }

  public Integer getMaxPrivatePollRatePerSecond() {
    return maxPrivatePollRatePerSecond;
  }

  public void setMaxPrivatePollRatePerSecond(Integer maxPrivatePollRatePerSecond) {
    this.maxPrivatePollRatePerSecond = maxPrivatePollRatePerSecond;
  }

  public Integer getMaxPrivatePollRatePer10Second() {
    return maxPrivatePollRatePer10Second;
  }

  public void setMaxPrivatePollRatePer10Second(Integer maxPrivatePollRatePer10Second) {
    this.maxPrivatePollRatePer10Second = maxPrivatePollRatePer10Second;
  }

  public Integer getMaxPrivatePollRatePerMinute() {
    return maxPrivatePollRatePerMinute;
  }

  public void setMaxPrivatePollRatePerMinute(Integer maxPrivatePollRatePerMinute) {
    this.maxPrivatePollRatePerMinute = maxPrivatePollRatePerMinute;
  }

  public Integer getMaxPrivatePollRatePerHour() {
    return maxPrivatePollRatePerHour;
  }

  public void setMaxPrivatePollRatePerHour(Integer maxPrivatePollRatePerHour) {
    this.maxPrivatePollRatePerHour = maxPrivatePollRatePerHour;
  }

  public Integer getMaxPublicPollRatePerSecond() {
    return maxPublicPollRatePerSecond;
  }

  public void setMaxPublicPollRatePerSecond(Integer maxPublicPollRatePerSecond) {
    this.maxPublicPollRatePerSecond = maxPublicPollRatePerSecond;
  }

  public Integer getMaxPublicPollRatePer10Second() {
    return maxPublicPollRatePer10Second;
  }

  public void setMaxPublicPollRatePer10Second(Integer maxPublicPollRatePer10Second) {
    this.maxPublicPollRatePer10Second = maxPublicPollRatePer10Second;
  }

  public Integer getMaxPublicPollRatePerMinute() {
    return maxPublicPollRatePerMinute;
  }

  public void setMaxPublicPollRatePerMinute(Integer maxPublicPollRatePerMinute) {
    this.maxPublicPollRatePerMinute = maxPublicPollRatePerMinute;
  }

  public Integer getMaxPublicPollRatePerHour() {
    return maxPublicPollRatePerHour;
  }

  public void setMaxPublicPollRatePerHour(Integer maxPublicPollRatePerHour) {
    this.maxPublicPollRatePerHour = maxPublicPollRatePerHour;
  }

  public Integer getFiatAmountMultiplier() {
    return fiatAmountMultiplier;
  }

  public void setFiatAmountMultiplier(Integer fiatAmountMultiplier) {
    this.fiatAmountMultiplier = fiatAmountMultiplier;
  }

  public Integer getCryptoAmountMultiplier() {
    return cryptoAmountMultiplier;
  }

  public void setCryptoAmountMultiplier(Integer cryptoAmountMultiplier) {
    this.cryptoAmountMultiplier = cryptoAmountMultiplier;
  }

  @Override
  public String toString() {
    return "MetaData [currencyPairs=" + currencyPairs + ", maxPrivatePollRatePerSecond=" + maxPrivatePollRatePerSecond
        + ", maxPrivatePollRatePer10Second=" + maxPrivatePollRatePer10Second + ", maxPrivatePollRatePerMinute=" + maxPrivatePollRatePerMinute
        + ", maxPrivatePollRatePerHour=" + maxPrivatePollRatePerHour + ", maxPublicPollRatePerSecond=" + maxPublicPollRatePerSecond
        + ", maxPublicPollRatePer10Second=" + maxPublicPollRatePer10Second + ", maxPublicPollRatePerMinute=" + maxPublicPollRatePerMinute
        + ", maxPublicPollRatePerHour=" + maxPublicPollRatePerHour + ", fiatAmountMultiplier=" + fiatAmountMultiplier + ", cryptoAmountMultiplier="
        + cryptoAmountMultiplier + "]";
  }

}
