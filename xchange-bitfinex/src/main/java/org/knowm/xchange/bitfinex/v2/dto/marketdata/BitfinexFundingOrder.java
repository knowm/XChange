package org.knowm.xchange.bitfinex.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;

/**
 * @see https://docs.bitfinex.com/reference#rest-public-book
 */
@JsonFormat(shape = JsonFormat.Shape.ARRAY)
@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class BitfinexFundingOrder {
  /** Rate level */
  BigDecimal rate;

  /** Period level */
  int period;

  /** Number of orders at that price level */
  int count;

  /** Total amount available at that price level. if AMOUNT > 0 then ask else bid. */
  BigDecimal amount;
}
