package org.knowm.xchange.bitso.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * @author Piotr Ładyżyński Updated for Bitso API v3
 */
@Value
@Jacksonized
@Builder
public class BitsoTicker {

  private Boolean success;

  private BitsoTickerData payload;

  @Value
  @Jacksonized
  @Builder
  public static class BitsoTickerData {

    private String book;

    private BigDecimal volume;

    private BigDecimal high;

    private BigDecimal last;

    private BigDecimal low;

    private BigDecimal vwap;

    private BigDecimal ask;

    private BigDecimal bid;

    private Instant createdAt;

    @JsonProperty("change_24")
    private BigDecimal change24;

    private Object rollingAverageChange;
  }

  // Legacy methods for backwards compatibility
  public BigDecimal getLast() {
    return payload != null ? payload.getLast() : null;
  }

  public BigDecimal getHigh() {
    return payload != null ? payload.getHigh() : null;
  }

  public BigDecimal getLow() {
    return payload != null ? payload.getLow() : null;
  }

  public BigDecimal getVwap() {
    return payload != null ? payload.getVwap() : null;
  }

  public BigDecimal getVolume() {
    return payload != null ? payload.getVolume() : null;
  }

  public BigDecimal getBid() {
    return payload != null ? payload.getBid() : null;
  }

  public BigDecimal getAsk() {
    return payload != null ? payload.getAsk() : null;
  }
}
