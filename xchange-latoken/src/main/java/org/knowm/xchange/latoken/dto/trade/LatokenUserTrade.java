package org.knowm.xchange.latoken.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Response schema:
 *
 * <pre>
 * {
 * 	"id": "1555492358.126073.126767@0502:2",
 * 	"orderId": "1555492358.126073.126767@0502:2",
 * 	"commission": 0.012,
 * 	"side": "buy",
 * 	"price": 136.2,
 * 	"amount": 0.7,
 * 	"time": 1555515807369
 * }
 * </pre>
 *
 * @author Ezer
 */
public class LatokenUserTrade {

  private final String id;
  private final String orderId;
  private final BigDecimal fee;
  private final LatokenOrderSide side;
  private final BigDecimal price;
  private final BigDecimal amount;
  private final Date time;

  /**
   * C'tor
   *
   * @param id
   * @param orderId
   * @param fee
   * @param side
   * @param price
   * @param amount
   * @param time
   */
  public LatokenUserTrade(
      @JsonProperty("id") String id,
      @JsonProperty("orderId") String orderId,
      @JsonProperty("commission") BigDecimal fee,
      @JsonProperty("side") String side,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("low") BigDecimal amount,
      @JsonProperty("time") long time) {

    this.id = id;
    this.orderId = orderId;
    this.fee = fee;
    this.side = LatokenOrderSide.parse(side);
    this.price = price;
    this.amount = amount;
    this.time = new Date(time);
  }

  /**
   * ID of trade
   *
   * @return
   */
  public String getId() {
    return id;
  }

  /**
   * Id of order
   *
   * @return
   */
  public String getOrderId() {
    return orderId;
  }

  /**
   * Trade fee
   *
   * @return
   */
  public BigDecimal getFee() {
    return fee;
  }

  /**
   * Order side
   *
   * @return
   */
  public LatokenOrderSide getSide() {
    return side;
  }

  /**
   * Price of trade
   *
   * @return
   */
  public BigDecimal getPrice() {
    return price;
  }

  /**
   * Amount of trade
   *
   * @return
   */
  public BigDecimal getAmount() {
    return amount;
  }

  /**
   * Trade time
   *
   * @return
   */
  public Date getTime() {
    return time;
  }

  @Override
  public String toString() {
    return "LatokenUserTrade [id = "
        + id
        + ", orderId = "
        + orderId
        + ", fee = "
        + fee
        + ", side = "
        + side
        + ", price = "
        + price
        + ", amount = "
        + amount
        + ", time = "
        + time
        + "]";
  }
}
