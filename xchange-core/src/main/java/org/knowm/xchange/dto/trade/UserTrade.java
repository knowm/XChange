package org.knowm.xchange.dto.trade;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.marketdata.Trade;

/** Data object representing a user trade */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UserTrade extends Trade {

  private static final long serialVersionUID = -3021617981214969292L;

  /** The id of the order responsible for execution of this trade */
  private String orderId;

  /** The fee that was charged by the exchange for this trade. */
  private BigDecimal feeAmount;

  /** The currency in which the fee was charged. */
  private Currency feeCurrency;

  /** The order reference id which has been added by the user on the order creation */
  private String orderUserReference;
}
