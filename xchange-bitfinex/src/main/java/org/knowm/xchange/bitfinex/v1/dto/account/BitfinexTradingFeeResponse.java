package org.knowm.xchange.bitfinex.v1.dto.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

@JsonIgnoreProperties({"maker_fees", "taker_fees"})
public class BitfinexTradingFeeResponse {
  public static class BitfinexTradingFeeResponseRow {
    private final String currency;
    private final BigDecimal makerFee;
    private final BigDecimal takerFee;

    public BitfinexTradingFeeResponseRow(
        @JsonProperty("pairs") String pairs,
        @JsonProperty("maker_fees") BigDecimal makerFees,
        @JsonProperty("taker_fees") BigDecimal takerFees) {
      this.currency = pairs;
      this.makerFee = makerFees;
      this.takerFee = takerFees;
    }

    public String getCurrency() {
      return currency;
    }

    public BigDecimal getMakerFee() {
      return makerFee;
    }

    public BigDecimal getTakerFee() {
      return takerFee;
    }
  }

  private final BitfinexTradingFeeResponseRow[] tradingFeeResponseRows;
  /**
   * Constructor
   *
   * @param type
   * @param currency
   * @param amount
   * @param available
   */
  public BitfinexTradingFeeResponse(
      @JsonProperty("fees") BitfinexTradingFeeResponseRow[] tradingFeeResponseRows) {
    this.tradingFeeResponseRows = tradingFeeResponseRows;
  }

  public BitfinexTradingFeeResponseRow[] getTradingFees() {
    return tradingFeeResponseRows;
  }

  @Override
  public String toString() {
    return "BitfinexTradingFeeResponseRow [tradingFeeResponseRows="
        + tradingFeeResponseRows.toString()
        + "]";
  }
}
