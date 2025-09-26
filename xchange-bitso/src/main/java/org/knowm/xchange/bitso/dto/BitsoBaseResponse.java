package org.knowm.xchange.bitso.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * Base response wrapper for Bitso API v3 responses All API v3 responses follow the structure: {
 * "success": boolean, "payload": T }
 *
 * @param <T> The type of the payload
 * @see <a href="https://docs.bitso.com/bitso-api/docs/api-overview">Bitso API v3 Documentation</a>
 */
@Value
@Builder
@Jacksonized
public class BitsoBaseResponse<T> {

  /** Indicates whether the request was successful */
  private final Boolean success;

  /** The response payload containing the actual data */
  private final T payload;

  /** Error message (present when success is false) */
  private final BitsoError error;

  /** Error details for Bitso API responses */
  @Value
  @Builder
  @Jacksonized
  public static class BitsoError {

    /** Error code */
    private final String code;

    /** Error message */
    private final String message;
  }
}
