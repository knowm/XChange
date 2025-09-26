package org.knowm.xchange.bitso.dto.marketdata;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * @author Piotr Ładyżyński Updated for Bitso API v3
 */
@Value
@Builder
@Jacksonized
public class BitsoOrderBook {

  private Boolean success;

  private BitsoOrderBookData payload;

  @Value
  @Jacksonized
  @Builder
  public static class BitsoOrderBookData {

    private List<BitsoOrderBookEntry> asks;

    private List<BitsoOrderBookEntry> bids;

    private Instant updatedAt;

    private String sequence;
  }

  @Value
  @Jacksonized
  @Builder
  public static class BitsoOrderBookEntry {

    private String book;

    private BigDecimal price;

    private BigDecimal amount;
  }

  // Legacy methods for backwards compatibility
  public List<List<BigDecimal>> getBids() {
    if (payload == null || payload.getBids() == null) {
      return null;
    }
    return payload.getBids().stream()
        .map(entry -> Arrays.asList(entry.getPrice(), entry.getAmount()))
        .collect(Collectors.toList());
  }

  public List<List<BigDecimal>> getAsks() {
    if (payload == null || payload.getAsks() == null) {
      return null;
    }
    return payload.getAsks().stream()
        .map(entry -> Arrays.asList(entry.getPrice(), entry.getAmount()))
        .collect(Collectors.toList());
  }

  public Long getTimestamp() {
    // For backwards compatibility, try to convert updatedAt to timestamp
    // This is a simplified conversion - in practice you might want to parse the ISO date
    return payload != null ? payload.getUpdatedAt().toEpochMilli() : null;
  }
}
