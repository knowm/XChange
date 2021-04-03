package org.knowm.xchange.bitfinex.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;

/**
 * @see https://docs.bitfinex.com/reference#rest-public-book trading order on raw books (precision
 *     of R0)
 */
@JsonFormat(shape = JsonFormat.Shape.ARRAY)
@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class BitfinexTradingRawOrder {
  /** Order ID */
  long orderId;
  /** Price level */
  BigDecimal price;
  /** Total amount available at that price level. if AMOUNT > 0 then bid else ask. */
  BigDecimal amount;
}
