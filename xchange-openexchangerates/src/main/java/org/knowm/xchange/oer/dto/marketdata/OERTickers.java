package org.knowm.xchange.oer.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * *
 * <p>
 * Data object representing a Exchange Rates from Open ExchangeRates
 * </p>
 * Auto-generated using the simplest types possible with conversion delegated to the adapter
 *
 * @author timmolter
 */
public final class OERTickers {

  private final OERRates rates;
  private final Long timestamp;

  /**
   * Constructor
   *
   * @param rates
   * @param timestamp
   */
  public OERTickers(@JsonProperty("rates") OERRates rates, @JsonProperty("timestamp") Long timestamp) {

    this.rates = rates;
    this.timestamp = timestamp;
  }

  public OERRates getRates() {

    return this.rates;
  }

  public Long getTimestamp() {

    return this.timestamp;
  }

  @Override
  public String toString() {

    return "OERTickers [rates=" + rates + ", timestamp=" + timestamp + "]";
  }

}
