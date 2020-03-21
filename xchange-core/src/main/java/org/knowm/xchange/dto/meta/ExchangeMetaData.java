package org.knowm.xchange.dto.meta;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.instrument.InstrumentDeserializer;
import org.knowm.xchange.utils.ObjectMapperHelper;

/**
 * This class is loaded during creation of the Exchange and is intended to hold both data that is
 * readily available from an HTTP API request at an exchange extended by semi-static data that is
 * not available from an HTTP API, but is still important information to have. Examples include
 * currency pairs, max polling rates, scaling factors, etc. For more info see:
 * https://github.com/timmolter/XChange/wiki/Design-Notes
 *
 * <p>This class is used only in the API by the classes that merge metadata stored in custom JSON
 * file and online info from the remote exchange.
 */
// @JsonDeserialize(using = ExchangeMetaDataDeserializer.class)
public class ExchangeMetaData implements Serializable {

  private static final long serialVersionUID = -1495610469981534977L;

  @JsonProperty("instruments")
  @JsonDeserialize(keyUsing = InstrumentDeserializer.class)
  private Map<Instrument, InstrumentMetaData> instruments;

  @JsonProperty("currencies")
  private Map<Currency, CurrencyMetaData> currencies;

  @JsonProperty("public_rate_limits")
  private RateLimit[] publicRateLimits;

  @JsonProperty("private_rate_limits")
  private RateLimit[] privateRateLimits;

  /**
   * If true, both public and private calls use single rate limit policy, which is described in
   * {@link #privateRateLimits}.
   */
  @JsonProperty("share_rate_limits")
  private boolean shareRateLimits = true;

  /**
   * Constructor
   *
   * @param instruments Map of {@link instruments} -> {@link InstrumentMetaData}
   * @param currency Map of currency -> {@link CurrencyMetaData}
   */
  public ExchangeMetaData(
      @JsonProperty("instruments") Map<Instrument, InstrumentMetaData> instruments,
      @JsonProperty("currencies") Map<Currency, CurrencyMetaData> currency,
      @JsonProperty("public_rate_limits") RateLimit[] publicRateLimits,
      @JsonProperty("private_rate_limits") RateLimit[] privateRateLimits,
      @JsonProperty("share_rate_limits") Boolean shareRateLimits) {

    this.instruments = instruments;
    this.currencies = currency;

    this.publicRateLimits = publicRateLimits;
    this.privateRateLimits = privateRateLimits;

    this.shareRateLimits = shareRateLimits != null ? shareRateLimits : false;
  }

  /**
   * @return minimum number of milliseconds required between any two remote calls, assuming the
   *     client makes consecutive calls without any bursts or breaks for an infinite period of time.
   *     Returns null if the rateLimits collection is null or empty
   */
  @JsonIgnore
  public static Long getPollDelayMillis(RateLimit[] rateLimits) {

    if (rateLimits == null || rateLimits.length == 0) {
      return null;
    }
    long result = 0;
    for (RateLimit rateLimit : rateLimits) {
      // this is the delay between calls, we want max, any smaller number is for burst calls
      result = Math.max(result, rateLimit.getPollDelayMillis());
    }
    return result;
  }

  //
  public Map<Instrument, InstrumentMetaData> getInstruments() {
    return instruments;
  }

  /**
   * @deprecated CurrencyPair is a subtype of instrument <br>
   *     use {@link #getInstruments()} instead like this:
   *     <blockquote>
   *     <pre>
   * getInstruments()
   * </pre>
   *     </blockquote>
   */
  @Deprecated
  @JsonIgnore
  public Map<CurrencyPair, InstrumentMetaData> getCurrencyPairs() {
    Map<CurrencyPair, InstrumentMetaData> currencyPairs =
        new HashMap<CurrencyPair, InstrumentMetaData>();
    for (Instrument instrument : instruments.keySet()) {
      if (instrument instanceof CurrencyPair) {
        currencyPairs.put((CurrencyPair) instrument, instruments.get(instrument));
      }
    }
    return currencyPairs;
  }

  public Map<Currency, CurrencyMetaData> getCurrencies() {

    return currencies;
  }

  public RateLimit[] getPublicRateLimits() {

    return publicRateLimits;
  }

  public RateLimit[] getPrivateRateLimits() {

    return privateRateLimits;
  }

  public boolean isShareRateLimits() {

    return shareRateLimits;
  }

  @JsonIgnore
  public String toJSONString() {

    return ObjectMapperHelper.toJSON(this);
  }

  @Override
  public String toString() {

    return "ExchangeMetaData [instruments="
        + instruments
        + ", currencies="
        + currencies
        + ", publicRateLimits="
        + Arrays.toString(publicRateLimits)
        + ", privateRateLimits="
        + Arrays.toString(privateRateLimits)
        + ", shareRateLimits="
        + shareRateLimits
        + "]";
  }
}
