package org.knowm.xchange.cryptsy.dto.trade;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.cryptsy.CryptsyUtils;
import org.knowm.xchange.cryptsy.dto.CryptsyOrder.CryptsyOrderType;

/**
 * @author ObsessiveOrange
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptsyTradeHistory {

  private final int marketId;
  private final int tradeId;
  private final CryptsyOrderType type;
  private final Date timeStamp;
  private final BigDecimal price;
  private final BigDecimal quantity;
  private final BigDecimal total;
  private final BigDecimal fee;
  private final CryptsyOrderType init_type;
  private final int orderId;

  /**
   * Constructor
   * 
   * @param timestamp
   * @param isYourOrder
   * @param orderId
   * @param rate
   * @param amount
   * @param type
   * @param pair
   * @throws ParseException
   */
  public CryptsyTradeHistory(@JsonProperty("marketid") int marketId, @JsonProperty("tradeid") int tradeId,
      @JsonProperty("tradetype") CryptsyOrderType type, @JsonProperty("datetime") String timeStamp, @JsonProperty("tradeprice") BigDecimal price,
      @JsonProperty("quantity") BigDecimal quantity, @JsonProperty("total") BigDecimal total, @JsonProperty("fee") BigDecimal fee,
      @JsonProperty("initiate_ordertype") CryptsyOrderType init_type, @JsonProperty("order_id") int orderId) throws ParseException {

    this.marketId = marketId;
    this.tradeId = tradeId;
    this.type = type;
    this.timeStamp = timeStamp == null ? null : CryptsyUtils.convertDateTime(timeStamp);
    this.price = price;
    this.quantity = quantity;
    this.total = total;
    this.fee = fee;
    this.init_type = init_type;
    this.orderId = orderId;
  }

  public int getMarketId() {

    return marketId;
  }

  public int getTradeId() {

    return tradeId;
  }

  public CryptsyOrderType getTradeType() {

    return type;
  }

  public Date getTimestamp() {

    return timeStamp;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public BigDecimal getQuantity() {

    return quantity;
  }

  public BigDecimal getTotal() {

    return total;
  }

  public BigDecimal getFee() {

    return fee;
  }

  public CryptsyOrderType getInitiatingOrderType() {

    return init_type;
  }

  public int getOrderId() {

    return orderId;
  }

  @Override
  public String toString() {

    return "CryptsyTrade[" + "Market ID='" + marketId + "',Trade ID='" + tradeId + "',Type='" + type + "',Timestamp='" + timeStamp + "',Price='"
        + price + "',Quantity='" + quantity + "',Total='" + total + "',Fee='" + fee + "',InitiatingOrderType='" + init_type + "',Order ID='" + orderId
        + "']";
  }
}
