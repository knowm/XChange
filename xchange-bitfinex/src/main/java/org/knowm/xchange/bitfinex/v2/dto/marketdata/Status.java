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

/** see https://docs.bitfinex.com/v2/reference#status */
public class Status {

  private String symbol;
  private long timestamp;
  private Object placeHolder0;
  /** Derivative last traded price. */
  private BigDecimal derivPrice;
  /** Last traded price of the underlying Bitfinex spot trading pair */
  private BigDecimal spotPrice;

  private Object placeHolder1;
  /** The balance available to the liquidation engine to absorb losses. */
  private BigDecimal insuranceFundBalance;

  private Object placeHolder2;
  private Object placeHolder3;
  /** Current accrued funding for next 8h period */
  private BigDecimal nextFundingAccrued;
  /** Incremental accrual counter */
  private long nextFundingStep;

  private Object placeHolder4;
  /** Funding applied in the current 8h period */
  private BigDecimal currentFunding;
}
