package info.bitrich.xchangestream.gdax.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.gdax.dto.marketdata.GDAXProductTicker;
import org.knowm.xchange.gdax.dto.marketdata.GDAXTrade;

import java.math.BigDecimal;

/**
 * Domain object mapping a GDAX web socket message.
 */
public class GDAXWebSocketTransaction {
  private final String type;
  private final String orderId;
  private final String orderType;
  private final BigDecimal size;
  private final BigDecimal remainingSize;
  private final BigDecimal price;
  private final String side;
  private final String clientOid;
  private final String productId;
  private final long sequence;
  private final String time;
  private final String reason;
  private final long tradeId;
  private final String makerOrderId;
  private final String takenOrderId;

  public GDAXWebSocketTransaction(
    @JsonProperty("type") String type,
    @JsonProperty("order_id") String orderId,
    @JsonProperty("order_type") String orderType,
    @JsonProperty("size") BigDecimal size,
    @JsonProperty("remaining_size") BigDecimal remainingSize,
    @JsonProperty("price") BigDecimal price,
    @JsonProperty("side") String side,
    @JsonProperty("client_oid") String clientOid,
    @JsonProperty("product_id") String productId,
    @JsonProperty("sequence") long sequence,
    @JsonProperty("time") String time,
    @JsonProperty("reason") String reason,
    @JsonProperty("trade_id") long tradeId,
    @JsonProperty("maker_order_id") String makerOrderId,
    @JsonProperty("taken_order_id") String takenOrderId) {
    
    this.remainingSize = remainingSize;
    this.reason = reason;
    this.tradeId = tradeId;
    this.makerOrderId = makerOrderId;
    this.takenOrderId = takenOrderId;
    this.type = type;
    this.orderId = orderId;
    this.orderType = orderType;
    this.size = size;
    this.price = price;
    this.side = side;
    this.clientOid = clientOid;
    this.productId = productId;
    this.sequence = sequence;
    this.time = time;
  }
  
  public GDAXProductTicker toGDAXProductTicker(){
    return new GDAXProductTicker(String.valueOf(tradeId), price, size, null, null, null, time);
  }
  
  public GDAXTrade toGDAXTrade(){
    return new GDAXTrade(time, tradeId, price, size, side);
  }

  public String getType() {
    return type;
  }

  public String getOrderId() {
    return orderId;
  }

  public String getOrderType() {
    return orderType;
  }

  public BigDecimal getSize() {
    return size;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public String getSide() {
    return side;
  }

  public String getClientOid() {
    return clientOid;
  }

  public String getProductId() {
    return productId;
  }

  public Long getSequence() {
    return sequence;
  }

  public String getTime() {
    return time;
  }

  public BigDecimal getRemainingSize() {
    return remainingSize;
  }

  public String getReason() {
    return reason;
  }

  public long getTradeId() {
    return tradeId;
  }

  public String getMakerOrderId() {
    return makerOrderId;
  }

  public String getTakenOrderId() {
    return takenOrderId;
  }

  @Override
  public String toString() {
    final StringBuffer sb = new StringBuffer("GDAXWebSocketTransaction{");
    sb.append("type='").append(type).append('\'');
    sb.append(", orderId='").append(orderId).append('\'');
    sb.append(", orderType='").append(orderType).append('\'');
    sb.append(", size=").append(size);
    sb.append(", price=").append(price);
    sb.append(", side='").append(side).append('\'');
    sb.append(", clientOid='").append(clientOid).append('\'');
    sb.append(", productId='").append(productId).append('\'');
    sb.append(", sequence=").append(sequence);
    sb.append(", time='").append(time).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
