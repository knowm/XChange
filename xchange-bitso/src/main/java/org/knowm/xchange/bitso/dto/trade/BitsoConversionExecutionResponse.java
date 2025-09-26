package org.knowm.xchange.bitso.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * Bitso conversion execution response DTO for v4 API
 *
 * @see <a href="https://docs.bitso.com/bitso-api/docs/execute-a-conversion-quote">Execute a
 *     Conversion Quote</a>
 */
@Value
@Jacksonized
@Builder
public class BitsoConversionExecutionResponse {

  /** The conversion's unique identifier for tracking */
  @JsonProperty("oid")
  private String conversionId;
}
