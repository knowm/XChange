package org.knowm.xchange.bitfinex.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonFormat(shape = JsonFormat.Shape.ARRAY)
@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@Getter
@ToString

/** see https://docs.bitfinex.com/reference#rest-public-status */
public class Status {

  private String symbol;
  /** Millisecond timestamp */
  private long timestamp;

  private Object placeHolder0;
  /** Derivative book mid price. */
  private BigDecimal derivPrice;
  /** Book mid price of the underlying Bitfinex spot trading pair */
  private BigDecimal spotPrice;

  private Object placeHolder1;
  /** The balance available to the liquidation engine to absorb losses. */
  private BigDecimal insuranceFundBalance;

  private Object placeHolder2;
  /** Millisecond timestamp of next funding event */
  private Long nextFundingEvtTimestampMillis;
  /** Current accrued funding for next 8h period */
  private BigDecimal nextFundingAccrued;
  /** Incremental accrual counter */
  private long nextFundingStep;

  private Object placeHolder4;
  /** Funding applied in the current 8h period */
  private BigDecimal currentFunding;

  private Object placeHolder5;
  private Object placeHolder6;
  /** Price based on the BFX Composite Index */
  private BigDecimal markPrice;

  private Object placeHolder7;
  private Object placeHolder8;
  /** Total number of outstanding derivative contracts */
  private BigDecimal openInterest;

  private Object placeHolder9;
  private Object placeHolder10;
  private Object placeHolder11;
  /** Range in the average spread that does not require a funding payment */
  private BigDecimal clampMin;
  /** Funding payment cap */
  private BigDecimal clampMax;
}
