package com.xeiam.xchange.dto.meta;

import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.currency.CurrencyPair;

/**
 * This class is loaded during creation of the Exchange and is intended to hold both data that is readily available from an HTTP API request at an
 * exchange extended by semi-static data that is not available from an HTTP API, but is still important information to have. Examples include currency
 * pairs, max polling rates, scaling factors, etc.
 * <p/>
 * This class is used only in the API by the classes that merge metadata stored in custom JSON file and online info from the remote exchange.
 */
public class ExchangeMetaData {

  private Map<CurrencyPair, MarketMetaData> currencyPairs;

  private Map<Currency, CurrencyMetaData> currency;

  private Set<RateLimit> publicRateLimits;
  private Set<RateLimit> privateRateLimits;

  /**
   * If true, both public and private calls use single rate limit policy, which is described in {@link #privateRateLimits}.
   */
  private boolean shareRateLimits = true;

  /**
   * @param currencyPairs Map of {@link CurrencyPair} -> {@link MarketMetaData}
   * @param currency Map of currency -> {@link CurrencyMetaData}
   */
  public ExchangeMetaData(@JsonProperty("currencyPair") Map<CurrencyPair, MarketMetaData> currencyPairs,
      @JsonProperty("currency") Map<Currency, CurrencyMetaData> currency, @JsonProperty("publicRateLimits") Set<RateLimit> publicRateLimits,
      @JsonProperty("privateRateLimits") Set<RateLimit> privateRateLimits, @JsonProperty("shareRateLimits") Boolean shareRateLimits) {

    this.currencyPairs = currencyPairs;
    this.currency = currency;

    this.publicRateLimits = publicRateLimits;
    this.privateRateLimits = privateRateLimits;

    this.shareRateLimits = shareRateLimits != null ? shareRateLimits : false;
  }

  public Map<CurrencyPair, MarketMetaData> getMarketMetaDataMap() {
    return currencyPairs;
  }

  public Map<Currency, CurrencyMetaData> getCurrencyMetaDataMap() { return currency; }

  public Set<RateLimit> getPublicRateLimits() {
    return publicRateLimits;
  }

  public Set<RateLimit> getPrivateRateLimits() {
    return privateRateLimits;
  }

  public boolean isShareRateLimits() {
    return shareRateLimits;
  }

  /**
   * @return minimum number of milliseconds required between any two remote calls, assuming the client makes consecutive calls without any bursts or
   *         breaks for an infinite period of time. Returns null if the rateLimits collection is null or empty
   */
  public static Long getPollDelayMillis(Set<RateLimit> rateLimits) {
    if (rateLimits == null || rateLimits.isEmpty())
      return null;
    long result = 0;
    for (RateLimit rateLimit : rateLimits) {
      // this is the delay between calls, we want max, any smaller number is for burst calls
      result = Math.max(result, rateLimit.getPollDelayMillis());
    }
    return result;
  }

  @Override
  public String toString() {
    return "ExchangeMetaData{" + "currencyPairs=" + currencyPairs + ", currency=" + currency + '}';
  }
}
