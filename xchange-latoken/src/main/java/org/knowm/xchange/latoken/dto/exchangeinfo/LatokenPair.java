package org.knowm.xchange.latoken.dto.exchangeinfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/**
 * Response schema:
 *
 * <pre>
 * {
 * 	"pairId": 502,
 * 	"symbol": "LAETH",
 * 	"baseCurrency": "LA",
 * 	"quotedCurrency": "ETH",
 * 	"makerFee": 0.01,
 * 	"takerFee": 0.01,
 * 	"pricePrecision": 8,
 * 	"amountPrecision": 8,
 * 	"minQty": 0.1
 * }
 * </pre>
 *
 * @author Ezer
 */
public class LatokenPair {

  private final long pairId;
  private final String symbol;
  private final String baseCurrency;
  private final String counterCurrency;
  private final BigDecimal makerFee;
  private final BigDecimal takerFee;
  private final int pricePrecision;
  private final int amountPrecision;
  private final BigDecimal minOrderAmount;

  /**
   * C'tor
   *
   * @param pairId
   * @param symbol
   * @param baseCurrency
   * @param counterCurrency
   * @param makerFee
   * @param takerFee
   * @param pricePrecision
   * @param amountPrecision
   * @param minOrderAmount
   */
  public LatokenPair(
      @JsonProperty("pairId") long pairId,
      @JsonProperty("symbol") String symbol,
      @JsonProperty("baseCurrency") String baseCurrency,
      @JsonProperty("quotedCurrency") String counterCurrency,
      @JsonProperty("makerFee") BigDecimal makerFee,
      @JsonProperty("takerFee") BigDecimal takerFee,
      @JsonProperty("pricePrecision") int pricePrecision,
      @JsonProperty("amountPrecision") int amountPrecision,
      @JsonProperty("minQty") BigDecimal minOrderAmount) {

    this.pairId = pairId;
    this.symbol = symbol;
    this.baseCurrency = baseCurrency;
    this.counterCurrency = counterCurrency;
    this.makerFee = makerFee;
    this.takerFee = takerFee;
    this.pricePrecision = pricePrecision;
    this.amountPrecision = amountPrecision;
    this.minOrderAmount = minOrderAmount;
  }

  /**
   * ID of pair
   *
   * @return
   */
  public long getPairId() {
    return pairId;
  }

  /**
   * Pair symbol
   *
   * @return
   */
  public String getSymbol() {
    return symbol;
  }

  /**
   * Symbol of base currency
   *
   * @return
   */
  public String getBaseCurrency() {
    return baseCurrency;
  }

  /**
   * Symbol of counter currency
   *
   * @return
   */
  public String getCounterCurrency() {
    return counterCurrency;
  }

  /**
   * Maker fee
   *
   * @return
   */
  public BigDecimal getMakerFee() {
    return makerFee;
  }

  /**
   * Taker fee
   *
   * @return
   */
  public BigDecimal getTakerFee() {
    return takerFee;
  }

  /**
   * Price precision
   *
   * @return
   */
  public int getPricePrecision() {
    return pricePrecision;
  }

  /**
   * Amount precision
   *
   * @return
   */
  public int getAmountPrecision() {
    return amountPrecision;
  }

  /**
   * Minimum order amount
   *
   * @return
   */
  public BigDecimal getMinOrderAmount() {
    return minOrderAmount;
  }

  @Override
  public String toString() {
    return "LatokenPair [pairId = "
        + pairId
        + ", symbol = "
        + symbol
        + ", baseCurrency = "
        + baseCurrency
        + ", counterCurrency = "
        + counterCurrency
        + ", makerFee = "
        + makerFee
        + ", takerFee = "
        + takerFee
        + ", pricePrecision = "
        + pricePrecision
        + ", amountPrecision = "
        + amountPrecision
        + ", minOrderAmount = "
        + minOrderAmount
        + "]";
  }
}
