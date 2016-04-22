package org.knowm.xchange.cryptsy.dto;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.cryptsy.CryptsyUtils;

/**
 * @author ObsessiveOrange
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptsyOrder {

  private final int tradeId;
  private final CryptsyOrderType type;
  private final Date time;
  private final BigDecimal price;
  private final BigDecimal quantity;
  private final BigDecimal total;

  /**
   * Constructor
   * 
   * @throws ParseException
   */
  @JsonCreator
  public CryptsyOrder(@JsonProperty("id") Integer tradeId, @JsonProperty("tradeid") Integer tradeId2,
      @JsonProperty("initiate_ordertype") CryptsyOrderType type, @JsonProperty("datetime") String time, @JsonProperty("tradeprice") BigDecimal price,
      @JsonProperty("quantity") BigDecimal quantity, @JsonProperty("total") BigDecimal total) throws ParseException {

    this.tradeId = tradeId == null ? tradeId2 : tradeId;
    this.type = type;
    this.time = time == null ? null : CryptsyUtils.convertDateTime(time);
    this.price = price;
    this.quantity = quantity;
    this.total = total;
  }

  public int getTradeId() {

    return tradeId;
  }

  public CryptsyOrderType getType() {

    return type;
  }

  public Date getTime() {

    return time;
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

  @Override
  public String toString() {

    return "CryptsyOrder [Trade ID='" + tradeId + "',Time='" + time + "',Price='" + price + "',Quantity='" + quantity + "',Total='" + total + "]";
  }

  public static enum CryptsyOrderType {
    Buy, Sell
  }
}
