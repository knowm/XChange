package org.knowm.xchange.dragonex.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;
import org.knowm.xchange.dragonex.DragonexUtils;

/**
 * {"charge": "0.0066", "deal_type": 1, "order_id": "1540349135948523001", "order_type": 2, "price":
 * "6557.2000", "symbol_id": 101, "timestamp": 1540349180147282172, "trade_id":
 * "15403491801472794729", "volume": "0.0010"}
 */
public class Deal {
  /** commission of current trade */
  public final BigDecimal charge;
  /** deal type: 1-buy, 2-sell */
  public final int dealType;

  public final String orderId;
  /** order type: 1-buy, 2-sell */
  public final int orderType;

  public final BigDecimal price;
  public final int symbolId;
  /** timestamp is provided in nano seconds */
  public final String timestamp;

  public final String tradeId;
  public final BigDecimal volume;

  public Deal(
      @JsonProperty("charge") BigDecimal charge,
      @JsonProperty("deal_type") int dealType,
      @JsonProperty("order_id") String orderId,
      @JsonProperty("order_type") int orderType,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("symbol_id") int symbolId,
      @JsonProperty("timestamp") String timestamp,
      @JsonProperty("trade_id") String tradeId,
      @JsonProperty("volume") BigDecimal volume) {
    this.charge = charge;
    this.dealType = dealType;
    this.orderId = orderId;
    this.orderType = orderType;
    this.price = price;
    this.symbolId = symbolId;
    this.timestamp = timestamp;
    this.tradeId = tradeId;
    this.volume = volume;
  }

  public Date getTimestamp() {
    return DragonexUtils.nanos2Date(timestamp);
  }
}
