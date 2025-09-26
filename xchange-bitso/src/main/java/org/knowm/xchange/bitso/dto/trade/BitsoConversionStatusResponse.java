package org.knowm.xchange.bitso.dto.trade;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * Bitso conversion status response DTO for v4 API with enhanced states
 *
 * @see <a href="https://docs.bitso.com/bitso-api/docs/get-a-conversion-status">Get a Conversion
 *     Status</a>
 */
@Value
@Jacksonized
@Builder
public class BitsoConversionStatusResponse {

  /** The conversion's unique identifier */
  private String id;

  /** The amount converted (in from_currency) */
  private BigDecimal fromAmount;

  /** The source currency ticker */
  private String fromCurrency;

  /** The amount received (in to_currency) */
  private BigDecimal toAmount;

  /** The target currency ticker */
  private String toCurrency;

  /** Conversion creation timestamp (epoch milliseconds) */
  private Long created;

  /** Conversion expiration timestamp (epoch milliseconds) */
  private Long expires;

  /** The conversion rate offered by Bitso (includes spread) */
  private BigDecimal rate;

  /** The market rate without spread */
  private BigDecimal plainRate;

  /** The currency in which the rate is expressed */
  private String rateCurrency;

  /** The book used for the conversion */
  private String book;

  /**
   * The conversion's current state. Possible values: - "open": Initial state, awaiting execution -
   * "queued": Being executed by Bitso's Conversion Engine (NEW in v4) - "completed": Successfully
   * completed - "failed": Failed, funds remain in original currency
   */
  private String status;

  /** Conversion status enumeration for type safety */
  public enum ConversionStatus {
    OPEN("open"),
    QUEUED("queued"), // NEW in v4
    COMPLETED("completed"),
    FAILED("failed");

    private final String apiValue;

    ConversionStatus(String apiValue) {
      this.apiValue = apiValue;
    }

    public String getApiValue() {
      return apiValue;
    }

    public static ConversionStatus fromString(String status) {
      for (ConversionStatus conversionStatus : values()) {
        if (conversionStatus.apiValue.equals(status)) {
          return conversionStatus;
        }
      }
      throw new IllegalArgumentException("Unknown conversion status: " + status);
    }
  }

  /** Get the conversion status as an enum */
  public ConversionStatus getStatusEnum() {
    return ConversionStatus.fromString(status);
  }
}
