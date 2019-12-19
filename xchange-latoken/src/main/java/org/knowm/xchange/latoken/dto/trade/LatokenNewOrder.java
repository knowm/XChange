package org.knowm.xchange.latoken.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

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
 * }
 * </pre>
 *
 * @author Ezer
 */
public class LatokenNewOrder {

  private final String orderId;
  private final String clientOrderId;
  private final long pairId;
  private final String symbol;
  private final LatokenOrderSide side;
  private final OrderSubclass type;
  private final BigDecimal price;
  private final BigDecimal amount;

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
  public LatokenNewOrder(
      @JsonProperty("orderId") String orderId,
      @JsonProperty("clientOrderId") String clientOrderId,
      @JsonProperty("pairId") long pairId,
      @JsonProperty("symbol") String symbol,
      @JsonProperty("side") String side,
      @JsonProperty("orderType") String type,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("amount") BigDecimal amount) {

    this.orderId = orderId;
    this.clientOrderId = clientOrderId;
    this.pairId = pairId;
    this.symbol = symbol;
    this.side = LatokenOrderSide.parse(side);
    this.type = OrderSubclass.parse(type);
    this.price = price;
    this.amount = amount;
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
   * Id of order provided by client (optional)
   *
   * @return
   */
  public String getClientOrderId() {
    return clientOrderId;
  }

  /**
   * ID of trading pair
   *
   * @return
   */
  public long getPairId() {
    return pairId;
  }

  /**
   * Trading pair symbol
   *
   * @return
   */
  public String getSymbol() {
    return symbol;
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
   * Order type
   *
   * @return
   */
  public OrderSubclass getType() {
    return type;
  }

  /**
   * Order price
   *
   * @return
   */
  public BigDecimal getPrice() {
    return price;
  }

  /**
   * Order amount
   *
   * @return
   */
  public BigDecimal getAmount() {
    return amount;
  }

  @Override
  public String toString() {
    return "LatokenNewOrder [orderId = "
        + orderId
        + ", clientOrderId = "
        + clientOrderId
        + ", symbol = "
        + symbol
        + ", side = "
        + side
        + ", type = "
        + type
        + ", price = "
        + price
        + ", amount = "
        + amount
        + "]";
  }
}
