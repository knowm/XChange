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
public class CryptsyOpenOrders {

  private final int marketId;
  private final int orderId;
  private final CryptsyOrderType type;
  private final Date timeStamp;
  private final BigDecimal price;
  private final BigDecimal quantityRem;
  private final BigDecimal quantityOrg;
  private final BigDecimal total;

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
  public CryptsyOpenOrders(@JsonProperty("marketid") int marketId, @JsonProperty("ordertype") CryptsyOrderType type,
      @JsonProperty("orderid") int orderId, @JsonProperty("created") String timeStamp, @JsonProperty("price") BigDecimal price,
      @JsonProperty("quantity") BigDecimal quantityRem, @JsonProperty("total") BigDecimal total,
      @JsonProperty("orig_quantity") BigDecimal quantityOrg) throws ParseException {

    this.marketId = marketId;
    this.orderId = orderId;
    this.type = type;
    this.timeStamp = timeStamp == null ? null : CryptsyUtils.convertDateTime(timeStamp);
    this.price = price;
    this.quantityRem = quantityRem;
    this.total = total;
    this.quantityOrg = quantityOrg;
  }

  public int getMarketId() {

    return marketId;
  }

  public int getOrderId() {

    return orderId;
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

  public BigDecimal getQuantityRemaining() {

    return quantityRem;
  }

  public BigDecimal getOriginalQuantity() {

    return quantityOrg;
  }

  public BigDecimal getTotal() {

    return total;
  }

  @Override
  public String toString() {

    return "CryptsyOpenOrder[" + "Market ID='" + marketId + "',Order ID='" + orderId + "',Type='" + type + "',Timestamp='" + timeStamp + "',Price='"
        + price + "',Quantity Remaining='" + quantityRem + "',Total='" + total + "',Original Quantity='" + quantityOrg + "']";
  }
}
