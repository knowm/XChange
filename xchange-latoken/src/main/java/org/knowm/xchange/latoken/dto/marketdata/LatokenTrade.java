package org.knowm.xchange.latoken.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;
import org.knowm.xchange.latoken.dto.trade.LatokenOrderSide;

/**
 * Response schema:
 *
 * <pre>
 * {
 * 	"side": "sell",
 * 	"price": 136.2,
 * 	"amount": 0.57,
 * 	"timestamp": 1555515807369
 * }
 * </pre>
 *
 * @author Ezer
 */
public class LatokenTrade {

  private final LatokenOrderSide side;
  private final BigDecimal price;
  private final BigDecimal amount;
  private final Date timestamp;

  public LatokenTrade(
      @JsonProperty("side") String side,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("timestamp") long timestamp) {

    this.side = LatokenOrderSide.parse(side);
    this.price = price;
    this.amount = amount;
    this.timestamp = new Date(timestamp);
  }

  /**
   * {@link LatokenOrderSide Side} of trade
   *
   * @return
   */
  public LatokenOrderSide getSide() {
    return side;
  }

  /**
   * Trade price
   *
   * @return
   */
  public BigDecimal getPrice() {
    return price;
  }

  /**
   * Trade amount
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
  public Date getTimestamp() {
    return timestamp;
  }

  @Override
  public String toString() {
    return "LatokenTrade [side = "
        + side
        + ", price = "
        + price
        + ", amount = "
        + amount
        + ", timestamp = "
        + timestamp
        + "]";
  }
}
