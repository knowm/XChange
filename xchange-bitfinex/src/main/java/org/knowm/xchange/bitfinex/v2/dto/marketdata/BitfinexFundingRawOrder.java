package org.knowm.xchange.bitfinex.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;

/**
 * @see https://docs.bitfinex.com/reference#rest-public-book funding order on raw books (precision
 *     of R0)
 */
@JsonFormat(shape = JsonFormat.Shape.ARRAY)
@JsonPropertyOrder({"id", "period", "rate", "amount"})
@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class BitfinexFundingRawOrder {
  /** Order ID */
  long orderId;

  /** Period level */
  int period;

  /** Rate level */
  BigDecimal rate;

  /** Total amount available at that price level. if AMOUNT > 0 then ask else bid. */
  BigDecimal amount;
}
