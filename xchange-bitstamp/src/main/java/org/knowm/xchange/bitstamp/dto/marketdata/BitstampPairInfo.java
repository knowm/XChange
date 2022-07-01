package org.knowm.xchange.bitstamp.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

/** @author Matej Spiller Muys */
public final class BitstampPairInfo {

  private final String name;
  private final String urlSymbol;
  private final Integer baseDecimals;
  private final Integer counterDecimals;
  private final String minimumOrder;
  private final String trading;
  private final String description;

  /**
   * Constructor
   *
   * @param name
   * @param urlSymbol
   * @param baseDecimals
   * @param counterDecimals
   * @param minimumOrder
   * @param trading
   * @param description
   */
  public BitstampPairInfo(
      @JsonProperty("name") String name,
      @JsonProperty("url_symbol") String urlSymbol,
      @JsonProperty("base_decimals") Integer baseDecimals,
      @JsonProperty("counter_decimals") Integer counterDecimals,
      @JsonProperty("minimum_order") String minimumOrder,
      @JsonProperty("trading") String trading,
      @JsonProperty("description") String description) {
    this.name = name;
    this.urlSymbol = urlSymbol;
    this.baseDecimals = baseDecimals;
    this.counterDecimals = counterDecimals;
    this.minimumOrder = minimumOrder;
    this.trading = trading;
    this.description = description;
  }

  public String getName() {
    return name;
  }

  public String getUrlSymbol() {
    return urlSymbol;
  }

  public Integer getBaseDecimals() {
    return baseDecimals;
  }

  public Integer getCounterDecimals() {
    return counterDecimals;
  }

  public String getMinimumOrder() {
    return minimumOrder;
  }

  public String getTrading() {
    return trading;
  }

  public String getDescription() {
    return description;
  }

  @Override
  public String toString() {

    return "BitstampTicker [name="
        + name
        + ", urlSymbol="
        + urlSymbol
        + ", baseDecimals="
        + baseDecimals
        + ", counterDecimals="
        + counterDecimals
        + ", minimumOrder="
        + minimumOrder
        + ", trading="
        + trading
        + ", description="
        + description
        + "]";
  }
}
