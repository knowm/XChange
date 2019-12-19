package org.knowm.xchange.latoken.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Response schema:
 *
 * <pre>
 * {
 * 	"orderId": "1555492358.126073.126767@0502:2",
 * 	"cliOrdId": "myNewOrder",
 * 	"pairId": 502,
 * 	"symbol": "LAETH",
 * 	"side": "buy",
 * 	"orderType": "limit",
 * 	"price": 136.2,
 * 	"amount": 0.57,
 * 	"latokenOrderStatus": "partiallyFilled",
 * 	"executedAmount": 0.27,
 * 	"reaminingAmount": 0.3,
 * 	"timeCreated": 155551580736,
 * 	"timeFilled": 0
 * }
 * </pre>
 *
 * @author Ezer
 */
public final class LatokenOrder extends LatokenNewOrder {

  private final LatokenOrderStatus latokenOrderStatus;
  private final BigDecimal executedAmount;
  private final BigDecimal reaminingAmount;
  private final Date timeCreated;
  private final Date timeFilled;

  /**
   * C'tor
   *
   * @param orderId
   * @param clientOrderId
   * @param pairId
   * @param symbol
   * @param side
   * @param type
   * @param price
   * @param amount
   */
  public LatokenOrder(
      @JsonProperty("orderId") String orderId,
      @JsonProperty("cliOrdId") String clientOrderId,
      @JsonProperty("pairId") long pairId,
      @JsonProperty("symbol") String symbol,
      @JsonProperty("side") String side,
      @JsonProperty("orderType") String type,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("orderStatus") String orderStatus,
      @JsonProperty("executedAmount") BigDecimal executedAmount,
      @JsonProperty("reaminingAmount") BigDecimal reaminingAmount,
      @JsonProperty("timeCreated") long timeCreated,
      @JsonProperty("timeFilled") long timeFilled) {

    super(orderId, clientOrderId, pairId, symbol, side, type, price, amount);
    this.latokenOrderStatus = LatokenOrderStatus.parse(orderStatus);
    this.executedAmount = executedAmount;
    this.reaminingAmount = reaminingAmount;
    this.timeCreated = new Date(timeCreated);
    this.timeFilled = new Date(timeFilled);
  }

  /**
   * Order status
   *
   * @return
   */
  public LatokenOrderStatus getOrderStatus() {
    return latokenOrderStatus;
  }

  /**
   * Amount of order filled
   *
   * @return
   */
  public BigDecimal getExecutedAmount() {
    return executedAmount;
  }

  /**
   * Amount of order available for execution
   *
   * @return
   */
  public BigDecimal getReaminingAmount() {
    return reaminingAmount;
  }

  /**
   * Time of order creation
   *
   * @return
   */
  public Date getTimeCreated() {
    return timeCreated;
  }

  /**
   * Time when order is filled (or {@code null})
   *
   * @return
   */
  public Date getTimeFilled() {
    return timeFilled;
  }

  @Override
  public String toString() {
    return "LatokenOrder [latokenOrderStatus = "
        + latokenOrderStatus
        + ", executedAmount = "
        + executedAmount
        + ", reaminingAmount = "
        + reaminingAmount
        + ", timeCreated = "
        + timeCreated
        + ", timeFilled = "
        + timeFilled
        + super.toString()
        + "]";
  }
}
