package org.knowm.xchange.coinfloor.dto.streaming.trade;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.coinfloor.CoinfloorUtils;
import org.knowm.xchange.coinfloor.CoinfloorUtils.CoinfloorCurrency;

/**
 * @author obsessiveOrange
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoinfloorEstimateMarketOrder {

  private final int tag;
  private final int errorCode;
  private final BigDecimal baseQty;
  private final BigDecimal counterQty;

  public CoinfloorEstimateMarketOrder(@JsonProperty("tag") int tag, @JsonProperty("errorCode") int errorCode, @JsonProperty("quantity") int baseQty,
      @JsonProperty("total") int counterQty) {

    this.tag = tag;
    this.errorCode = errorCode;
    this.baseQty = CoinfloorUtils.scaleToBigDecimal(CoinfloorCurrency.BTC, baseQty);
    this.counterQty = CoinfloorUtils.scaleToBigDecimal(CoinfloorCurrency.GBP, counterQty);
  }

  public int getTag() {

    return tag;
  }

  public int getErrorCode() {

    return errorCode;
  }

  public BigDecimal getBaseQty() {

    return baseQty;
  }

  public BigDecimal getCounterQty() {

    return counterQty;
  }

  @Override
  public String toString() {

    return "CoinfloorEstimateMarketOrderReturn{tag='" + tag + "', errorcode='" + errorCode + "', baseQty='" + baseQty + "', counterQty='" + counterQty
        + "'}";
  }
}
