package org.knowm.xchange.abucoins.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.abucoins.dto.trade.AbucoinsOrder;
import org.knowm.xchange.abucoins.dto.trade.AbucoinsOrder.Side;
import org.knowm.xchange.abucoins.dto.trade.AbucoinsOrder.Type;

public abstract class AbucoinsBaseCreateOrderRequest {
  /** [optional] limit or market. Default limit */
  @JsonInclude(JsonInclude.Include.NON_NULL)
  AbucoinsOrder.Type type;

  /** buy or sell */
  AbucoinsOrder.Side side;

  /** Product id (ex. ZEC-BTC) */
  @JsonProperty("product_id")
  String productID;

  /** [optional] Self-trade prevention flag (co). Default stp is off */
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  String stp;

  /** [optional]* Hide your offer. Default is false */
  @JsonInclude(JsonInclude.Include.NON_NULL)
  Boolean hidden;

  /**
   * Base constructor (defaults values where optional).
   *
   * @param side
   * @param product_id
   */
  public AbucoinsBaseCreateOrderRequest(Side side, String product_id) {
    this(null, side, product_id, null, null);
  }

  /**
   * Base constructor. All values, including optional. Use <code>null</code> to use default value.
   *
   * @param type
   * @param side
   * @param productID
   * @param stp
   * @param hidden
   */
  public AbucoinsBaseCreateOrderRequest(
      Type type, Side side, String productID, String stp, Boolean hidden) {
    super();
    this.type = type;
    this.side = side;
    this.productID = productID;
    this.stp = stp;
    this.hidden = hidden;
  }

  public AbucoinsOrder.Type getType() {
    return type;
  }

  public AbucoinsOrder.Side getSide() {
    return side;
  }

  public String getProductID() {
    return productID;
  }

  public String getStp() {
    return stp;
  }

  public Boolean isHidden() {
    return hidden;
  }
}
