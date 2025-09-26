package org.knowm.xchange.bitso.dto.trade;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * Response DTO for placing orders in Bitso API v3
 *
 * @see <a href="https://docs.bitso.com/bitso-api/docs/place-an-order">Place an Order</a>
 */
@Value
@Builder
@Jacksonized
public class BitsoOrderResponse {

  /** The order's unique ID */
  private final String oid;
}
