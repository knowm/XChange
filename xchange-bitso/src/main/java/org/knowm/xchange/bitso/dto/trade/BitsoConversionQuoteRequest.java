package org.knowm.xchange.bitso.dto.trade;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * Bitso conversion quote request DTO for v4 API
 *
 * @see <a href="https://docs.bitso.com/bitso-api/docs/request-a-conversion-quote-1">Request a
 *     Conversion Quote</a>
 */
@Value
@Builder
@Jacksonized
public class BitsoConversionQuoteRequest {

  /** The currency ticker symbol to convert from */
  private String from;

  /** The currency ticker symbol to convert to */
  private String to;

  /** The amount to convert (in the source currency) */
  private BigDecimal amount;

  /**
   * Optional: The type of amount. Can be "exact_in" or "exact_out" Default is "exact_in" (exact
   * input amount)
   */
  private String amountType;
}
