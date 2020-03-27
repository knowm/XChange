package org.knowm.xchange.deribit.v2.dto.trade;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(SnakeCaseStrategy.class)
@Data
public class Settlement {

  /** The type of settlement. settlement, delivery or bankruptcy. */
  private SettlementType type;
  /** The timestamp (seconds since the Unix epoch, with millisecond precision) */
  private long timestamp;
  /** total value of session profit and losses (in base currency) */
  private BigDecimal sessionProfitLoss;
  /** profit and loss (in base currency; settlement and delivery only) */
  private BigDecimal profitLoss;
  /** position size (in quote currency; settlement and delivery only) */
  private BigDecimal position;
  /** mark price for at the settlement time (in quote currency; settlement and delivery only) */
  private BigDecimal markPrice;
  /** instrument name (settlement and delivery only) */
  private String instrumentName;
  /** underlying index price at time of event (in quote currency; settlement and delivery only) */
  private BigDecimal indexPrice;
  /** funded amount (bankruptcy only) */
  private BigDecimal funded;
  /** funding (in base currency ; settlement for perpetual product only) */
  private BigDecimal funding;
  /** value of session bankrupcy (in base currency; bankruptcy only) */
  private BigDecimal sessionBankrupcy;
  /** total amount of paid taxes/fees (in base currency; bankruptcy only) */
  private BigDecimal sessionTax;
  /** rate of paid texes/fees (in base currency; bankruptcy only) */
  private BigDecimal sessionTaxRate;
  /** the amount of the socialized losses (in base currency; bankruptcy only) */
  private BigDecimal socialized;

  public Date getTimestamp() {
    return new Date(timestamp);
  }
}
