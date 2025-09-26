package org.knowm.xchange.bitso.dto.trade;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * Request DTO for modifying orders in Bitso API v4
 *
 * @see <a href="https://docs.bitso.com/bitso-api/docs/modify-an-order">Modify an Order</a>
 */
@Value
@Builder
@Jacksonized
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BitsoModifyOrderRequest {

  /** The amount of the major currency to update */
  private final BigDecimal major;

  /**
   * The amount of the minor currency to update, the order's value. You must specify an order in
   * terms of either major or minor, never both.
   */
  private final BigDecimal minor;

  /**
   * The price in minor at which to buy or sell the amount, the rate of the major/minor combination.
   */
  private final BigDecimal price;

  /**
   * The price per unit of major at which to stop and place the order. Use this parameter only with
   * Stop Orders.
   */
  private final BigDecimal stop;

  /**
   * A Boolean flag to indicate whether to cancel the order if it cannot be processed. Set it to "1"
   * (true) to enable cancellation or "0" (false) to keep the order active. Default value "0".
   */
  private final Boolean cancel;
}
