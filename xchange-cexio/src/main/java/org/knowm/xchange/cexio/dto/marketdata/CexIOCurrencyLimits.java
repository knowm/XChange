package org.knowm.xchange.cexio.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;
import org.knowm.xchange.cexio.dto.CexIOApiResponse;
import org.knowm.xchange.currency.Currency;

/** @author ujjwal on 13/02/18. */
public class CexIOCurrencyLimits extends CexIOApiResponse<CexIOCurrencyLimits.Data> {

  public CexIOCurrencyLimits(
      @JsonProperty("e") final String e,
      @JsonProperty("data") final Data data,
      @JsonProperty("ok") final String ok,
      @JsonProperty("error") final String error) {
    super(e, data, ok, error);
  }

  public static class Data {
    private final List<Pair> pairs;

    public Data(@JsonProperty("pairs") List<Pair> pairs) {
      this.pairs = pairs;
    }

    public List<Pair> getPairs() {
      return pairs;
    }
  }

  public static class Pair {
    private final Currency symbol1;
    private final Currency symbol2;
    private final BigDecimal minLotSize;
    private final BigDecimal minLotSizeS2;
    private final BigDecimal maxLotSize;
    private final BigDecimal minPrice;
    private final BigDecimal maxPrice;

    public Pair(
        @JsonProperty("symbol1") Currency symbol1,
        @JsonProperty("symbol2") Currency symbol2,
        @JsonProperty("minLotSize") BigDecimal minLotSize,
        @JsonProperty("minLotSizeS2") BigDecimal minLotSizeS2,
        @JsonProperty("maxLotSize") BigDecimal maxLotSize,
        @JsonProperty("minPrice") BigDecimal minPrice,
        @JsonProperty("maxPrice") BigDecimal maxPrice) {
      this.symbol1 = symbol1;
      this.symbol2 = symbol2;
      this.minLotSize = minLotSize;
      this.minLotSizeS2 = minLotSizeS2;
      this.maxLotSize = maxLotSize;
      this.minPrice = minPrice;
      this.maxPrice = maxPrice;
    }

    public Currency getSymbol1() {
      return symbol1;
    }

    public Currency getSymbol2() {
      return symbol2;
    }

    public BigDecimal getMinLotSize() {
      return minLotSize;
    }

    public BigDecimal getMinLotSizeS2() {
      return minLotSizeS2;
    }

    public BigDecimal getMaxLotSize() {
      return maxLotSize;
    }

    public BigDecimal getMinPrice() {
      return minPrice;
    }

    public BigDecimal getMaxPrice() {
      return maxPrice;
    }

    @Override
    public String toString() {
      return "Pair{"
          + "symbol1="
          + symbol1
          + ", symbol2="
          + symbol2
          + ", minLotSize="
          + minLotSize
          + ", minLotSizeS2="
          + minLotSizeS2
          + ", maxLotSize="
          + maxLotSize
          + ", minPrice="
          + minPrice
          + ", maxPrice="
          + maxPrice
          + '}';
    }
  }
}
