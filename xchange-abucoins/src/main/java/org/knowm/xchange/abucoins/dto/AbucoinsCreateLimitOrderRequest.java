package org.knowm.xchange.abucoins.dto;

import java.math.BigDecimal;

import org.knowm.xchange.abucoins.dto.trade.AbucoinsOrder;
import org.knowm.xchange.abucoins.dto.trade.AbucoinsOrder.Side;
import org.knowm.xchange.abucoins.dto.trade.AbucoinsOrder.TimeInForce;
import org.knowm.xchange.abucoins.dto.trade.AbucoinsOrder.Type;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AbucoinsCreateLimitOrderRequest extends AbucoinsBaseCreateOrderRequest {
  /** price per one asset */
  BigDecimal price;
        
  /** amount of assets to buy or sell */
  BigDecimal size;
        
  /** GTC       [optional] GTC, GTT, IOC or FOK */
  @JsonInclude(JsonInclude.Include.NON_NULL)
  @JsonProperty("time_in_force")
  AbucoinsOrder.TimeInForce timeInForce;
        
  /** [optional]* min, hour, day.  * Requires only with GTT */
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("cancel_after")
  String cancelAfter;   
        
  /** [optional]** create order only if maker - true, false.  ** Invalid when time_in_force is IOC or FOK */
  @JsonInclude(JsonInclude.Include.NON_NULL)
  @JsonProperty("post_only")
  Boolean postOnly;
                
  /**
   * Constructor with skipping all optional fields.
   * @param side
   * @param product_id
   * @param price
   * @param size
   */
  public AbucoinsCreateLimitOrderRequest(Side side, String product_id, BigDecimal price, BigDecimal size) {
    super(side, product_id);
    this.type = AbucoinsOrder.Type.limit; // optional but being explicit.
    this.price = price;
    this.size = size;
  }
        
  /**
   * Full constructor, use <code>null</code> for any optional fields to use their default value
   * @param type
   * @param side
   * @param product_id
   * @param stp
   * @param hidden
   * @param price
   * @param size
   * @param timeInForce
   * @param cancel_after
   * @param postOnly
   */
  public AbucoinsCreateLimitOrderRequest(Side side, String product_id, String stp, Boolean hidden, BigDecimal price, BigDecimal size,
                                        TimeInForce timeInForce, String cancel_after, Boolean postOnly) {
    super(AbucoinsOrder.Type.limit, side, product_id, stp, hidden);
    this.price = price;
    this.size = size;
    this.timeInForce = timeInForce;
    this.cancelAfter = cancel_after;
    this.postOnly = postOnly;
    
    switch ( timeInForce ) {
    case GTT:
      if ( cancelAfter == null )
        throw new IllegalArgumentException("cancel_after required if time_in_force is GTT");
      break;
        
    case IOC:
    case FOK:
      if ( postOnly != null )
        throw new IllegalArgumentException("post_only invalid if time_in_force is IOK OR FOK, use null.");
        // falls through to default checks intentionally
        
    default:
      if ( cancelAfter != null )
        throw new IllegalArgumentException("cancel_after only required for GTT. Use null");
    }                   
  }
    
  public BigDecimal getPrice() {
    return price;
  }
    
  public BigDecimal getSize() {
    return size;
  }

  public AbucoinsOrder.TimeInForce getTimeInForce() {
    return timeInForce;
  }

  public String getCancelAfter() {
    return cancelAfter;
  }

  public Boolean getPostOnly() {
    return postOnly;
  }

  @Override
  public String toString() {
    return "AbucoinsCreateLimitOrderRequest [price=" + price + ", size=" + size + ", timeInForce=" + timeInForce
        + ", cancelAfter=" + cancelAfter + ", postOnly=" + postOnly + ", type=" + type + ", side=" + side
        + ", productID=" + productID + ", stp=" + stp + ", hidden=" + hidden + "]";
  }
}
