package org.knowm.xchange.okcoin.v3.dto.trade;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
public class SpotOrderPlacementRequest {

  // Common Request Parameters

  /**
   * optional the order ID customized by yourself , The client_oid type should be comprised of
   * alphabets + numbers or only alphabets within 1 – 32 characters， both uppercase and lowercase
   * letters are supported
   */
  @JsonProperty("client_oid")
  private String clientOid;

  /**
   * optional, limit / market(default: limit),When posting orders at market price, order_type can
   * only be 0 (regular order)
   */
  private String type;

  /** required, buy or sell */
  private Side side;

  /** required, trading pair */
  @JsonProperty("instrument_id")
  private String instrumentId;

  /** required, order type (The request value is 1) */
  @JsonProperty("margin_trading")
  private String marginTrading;

  /**
   * optional Fill in number for parameter， 0: Normal limit order (Unfilled and 0 represent normal
   * limit order) 1: Post only 2: Fill Or Kill 3: Immediatel Or Cancel
   */
  @JsonProperty("order_type")
  private OrderPlacementType orderType;

  /** required, quantity bought or sold */
  private BigDecimal size;

  // Limit Order Parameters
  /** required, price */
  private BigDecimal price;

  // Market Order Parameters
  /** required, amount bought. (for orders bought at market price only) */
  private BigDecimal notional;
}
