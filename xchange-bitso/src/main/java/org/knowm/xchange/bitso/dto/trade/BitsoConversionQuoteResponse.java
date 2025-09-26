package org.knowm.xchange.bitso.dto.trade;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * Bitso conversion quote response DTO for v4 API
 *
 * @see <a href="https://docs.bitso.com/bitso-api/docs/request-a-conversion-quote-1">Request a
 *     Conversion Quote</a>
 */
@Value
@Jacksonized
@Builder
public class BitsoConversionQuoteResponse {

  /** The quote's unique identifier used for execution */
  private String id;

  /** The amount to be converted (in from_currency) */
  private BigDecimal fromAmount;

  /** The source currency ticker */
  private String fromCurrency;

  /** The amount to be received (in to_currency) */
  private BigDecimal toAmount;

  /** The target currency ticker */
  private String toCurrency;

  /** Quote creation timestamp (epoch milliseconds) */
  private Long created;

  /** Quote expiration timestamp (epoch milliseconds) */
  private Long expires;

  /** The conversion rate offered by Bitso (includes spread) */
  private BigDecimal rate;

  /** The market rate without spread */
  private BigDecimal plainRate;

  /** The currency in which the rate is expressed */
  private String rateCurrency;

  /** The book that will be used for the conversion */
  private String book;
}
