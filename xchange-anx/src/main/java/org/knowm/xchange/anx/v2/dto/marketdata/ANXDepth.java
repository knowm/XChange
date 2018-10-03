package org.knowm.xchange.anx.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;

/** Data object representing depth from ANX */
public final class ANXDepth {

  private final List<ANXOrder> asks;
  private final List<ANXOrder> bids;
  private final FilterPrice filterMinPrice;
  private final FilterPrice filterMaxPrice;
  private final Long microTime;

  /**
   * Constructor
   *
   * @param asks
   * @param bids
   */
  public ANXDepth(
      @JsonProperty("now") Long microTime,
      @JsonProperty("asks") List<ANXOrder> asks,
      @JsonProperty("bids") List<ANXOrder> bids,
      @JsonProperty("filter_min_price") FilterPrice filterMinPrice,
      @JsonProperty("filter_max_price") FilterPrice filterMaxPrice) {

    this.asks = asks;
    this.bids = bids;
    this.filterMinPrice = filterMinPrice;
    this.filterMaxPrice = filterMaxPrice;
    this.microTime = microTime;
  }

  public List<ANXOrder> getAsks() {

    return asks;
  }

  public List<ANXOrder> getBids() {

    return bids;
  }

  public FilterPrice getFilterMinPrice() {

    return filterMinPrice;
  }

  public FilterPrice getFilterMaxPrice() {

    return filterMaxPrice;
  }

  public Long getMicroTime() {

    return microTime;
  }

  @Override
  public String toString() {

    return "ANXDepth [asks=" + asks.toString() + ", bids=" + bids.toString() + "]";
  }

  public static class FilterPrice {

    private final BigDecimal value;
    private final long valueInt;
    private final String currency;

    /**
     * Constructor
     *
     * @param value
     * @param valueInt
     * @param currency
     */
    public FilterPrice(
        @JsonProperty("value") BigDecimal value,
        @JsonProperty("value_int") long valueInt,
        @JsonProperty("currency") String currency) {

      this.value = value;
      this.valueInt = valueInt;
      this.currency = currency;
    }

    public BigDecimal getValue() {

      return value;
    }

    public long getValueInt() {

      return valueInt;
    }

    public String getCurrency() {

      return currency;
    }
  }
}
