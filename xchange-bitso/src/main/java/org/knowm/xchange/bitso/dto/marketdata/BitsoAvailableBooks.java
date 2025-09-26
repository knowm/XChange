package org.knowm.xchange.bitso.dto.marketdata;

import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/** DTO for Bitso API v3 available books endpoint */
@Value
@Jacksonized
@Builder
public class BitsoAvailableBooks {

  private Boolean success;

  private List<BitsoBook> payload;

  @Value
  @Jacksonized
  @Builder
  public static class BitsoBook {

    private String book;

    private BigDecimal minimumAmount;

    private BigDecimal maximumAmount;

    private BigDecimal minimumPrice;

    private BigDecimal maximumPrice;

    private BigDecimal minimumValue;

    private BigDecimal maximumValue;

    private BigDecimal tickSize;

    // Additional fields that may be present in real API responses

    private String defaultChart;

    private Object fees; // Complex fee structure, using Object for flexibility
  }
}
