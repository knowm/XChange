package com.xeiam.xchange.dto.meta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.currency.CurrencyPair;

/**
 * This class is loaded during creation of the Exchange and is
 * intended to hold both data that is readily available from an HTTP API request at an exchange extended by semi-static data that is not available from an HTTP API,
 * but is still important information to
 * have. Examples include currency pairs, max polling rates, scaling factors, etc.
 *
 * <p>
 * Note that this class extends SimpleMetaData (formerly named MetaData) as a temporary measure.
 * SimpleMetaData is used both as a generic JSON-mapped object and the type returned to users.
 * This class is used only in the API by the classes that merge metadata stored in custom JSON file and online info from the remote exchange.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeMetaData extends SimpleMetaData {

  @JsonProperty("currency_pairs")
  private Map<CurrencyPair, MarketMetaData> currencyPairs;

  @JsonProperty("currency")
  private Map<String, CurrencyMetaData> currency;

  @JsonProperty("min_poll_delay")
  private final Integer minPollDelay;

  /**
   * @param currencyPairs  Map of {@link CurrencyPair} -> {@link MarketMetaData}
   * @param currency       Map of currency -> {@link CurrencyMetaData}
   * @param minPollDelay   Minimum time between remote (polling) requests required by the exchange
   */
  public ExchangeMetaData(Map<CurrencyPair, MarketMetaData> currencyPairs, Map<String, CurrencyMetaData> currency, Integer minPollDelay) {

    // superclass and the call to super c-tor kept only for compatibility during the transition from SimpleMetaData to ExchangeMetaData
    super(new ArrayList<CurrencyPair>(currencyPairs.keySet()), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);

    this.currencyPairs = currencyPairs;
    this.currency = currency;
    this.minPollDelay = minPollDelay;
  }

  @JsonIgnore
  public List<CurrencyPair> getCurrencyPairs() {
    return super.getCurrencyPairs();
  }

  public Map<CurrencyPair, MarketMetaData>getMarketMetaDataMap(){
    return currencyPairs;
  }

  public Map<String, CurrencyMetaData> getCurrencyMetaDataMap() {
    return currency;
  }

  public Integer getMinPollDelay() {
    return minPollDelay;
  }
}
