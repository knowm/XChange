package org.knowm.xchange.abucoins.dto;

import java.math.BigDecimal;

import org.knowm.xchange.abucoins.dto.trade.AbucoinsOrder;
import org.knowm.xchange.abucoins.dto.trade.AbucoinsOrder.Side;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * <p>* One of size or funds is required.</p>
 * 
 * <p>Funds will limit how much of your quote currency account balance is used and size will limit the asset
 * amount transacted.</p>
 * 
 * @author bryant_harris
 */
public class AbucoinsCreateMarketOrderRequest extends AbucoinsBaseCreateOrderRequest{
  /** [optional]* Desired amount in BTC */
  @JsonInclude(JsonInclude.Include.NON_NULL)
  BigDecimal size;
        
  /** [optional]* Desired amount of quote currency to use */
  @JsonInclude(JsonInclude.Include.NON_NULL)
  BigDecimal funds;
  
  /**
   * Constructor with skipping all optional fields.
   * @param side
   * @param product_id
   * @param size
   * @param funds
   */
  public AbucoinsCreateMarketOrderRequest(Side side, String product_id, BigDecimal size, BigDecimal funds) {
    super(side, product_id);
    this.type = AbucoinsOrder.Type.market;
    this.size = size;
    this.funds = funds;
  }
        
  /**
   * Full constructor, use <code>null</code> for any optional fields to use their default value
   * @param type
   * @param side
   * @param productID
   * @param stp
   * @param hidden
   * @param size
   * @param funds
   */
  public AbucoinsCreateMarketOrderRequest(Side side, String productID, String stp, Boolean hidden, BigDecimal size, BigDecimal funds) {
    super(AbucoinsOrder.Type.market, side, productID, stp, hidden);
    this.size = size;
    this.funds = funds;
  }

  public BigDecimal getSize() {
    return size;
  }

  public BigDecimal getFunds() {
    return funds;
  }

  @Override
  public String toString() {
    return "AbucoinsCreateMarketOrderRequest [size=" + size + ", funds=" + funds + ", type=" + type + ", side="
        + side + ", productID=" + productID + ", stp=" + stp + ", hidden=" + hidden + "]";
  }
}
