package org.knowm.xchange.coinjar.dto.trading;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoinjarProduct {

  public final String id;

  public final String name;

  public final Currency baseCurrency;

  public final Currency counterCurrency;

  public final String tickValue;

  public final Integer tickValueExponent;

  public CoinjarProduct(
      @JsonProperty("id") String id,
      @JsonProperty("name") String name,
      @JsonProperty("base_currency") Currency baseCurrency,
      @JsonProperty("counter_currency") Currency counterCurrency,
      @JsonProperty("tick_value") String tickValue,
      @JsonProperty("tick_value_exponent") Integer tickValueExponent) {
    this.id = id;
    this.name = name;
    this.baseCurrency = baseCurrency;
    this.counterCurrency = counterCurrency;
    this.tickValue = tickValue;
    this.tickValueExponent = tickValueExponent;
  }

  public class Currency {

    public final Integer subunitToUnit;

    public final String isoCode;

    public final String name;

    public final String subunit;

    /**
     * @param subunit
     * @param subunitToUnit
     * @param name
     * @param isoCode
     */
    public Currency(
        @JsonProperty("subunit_to_unit") Integer subunitToUnit,
        @JsonProperty("iso_code") String isoCode,
        @JsonProperty("name") String name,
        @JsonProperty("subunit") String subunit) {
      this.subunitToUnit = subunitToUnit;
      this.isoCode = isoCode;
      this.name = name;
      this.subunit = subunit;
    }
  }
}
