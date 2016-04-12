package org.knowm.xchange.hitbtc.dto.trade;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HitbtcOwnTrade {

  private final long tradeId;
  private final BigDecimal execPrice;
  private final long timestamp;
  private final long originalOrderId;
  private final BigDecimal fee;
  private final String clientOrderId;
  private final String symbol;
  private final String side;
  private final BigDecimal execQuantity;

  public HitbtcOwnTrade(@JsonProperty("tradeId") long tradeId, @JsonProperty("execPrice") BigDecimal execPrice,
      @JsonProperty("timestamp") long timestamp, @JsonProperty("originalOrderId") long originalOrderId, @JsonProperty("fee") BigDecimal fee,
      @JsonProperty("clientOrderId") String clientOrderId, @JsonProperty("symbol") String symbol, @JsonProperty("side") String side,
      @JsonProperty("execQuantity") BigDecimal execQuantity) {

    super();
    this.tradeId = tradeId;
    this.execPrice = execPrice;
    this.timestamp = timestamp;
    this.originalOrderId = originalOrderId;
    this.fee = fee;
    this.clientOrderId = clientOrderId;
    this.symbol = symbol;
    this.side = side;
    this.execQuantity = execQuantity;
  }

  public long getTradeId() {

    return tradeId;
  }

  public BigDecimal getExecPrice() {

    return execPrice;
  }

  public long getTimestamp() {

    return timestamp;
  }

  public long getOriginalOrderId() {

    return originalOrderId;
  }

  public BigDecimal getFee() {

    return fee;
  }

  public String getClientOrderId() {

    return clientOrderId;
  }

  public String getSymbol() {

    return symbol;
  }

  public String getSide() {

    return side;
  }

  public BigDecimal getExecQuantity() {

    return execQuantity;
  }

  @Override
  public String toString() {

    StringBuilder builder = new StringBuilder();
    builder.append("HitbtcTrade [tradeId=");
    builder.append(tradeId);
    builder.append(", execPrice=");
    builder.append(execPrice);
    builder.append(", timestamp=");
    builder.append(timestamp);
    builder.append(", originalOrderId=");
    builder.append(originalOrderId);
    builder.append(", fee=");
    builder.append(fee);
    builder.append(", clientOrderId=");
    builder.append(clientOrderId);
    builder.append(", symbol=");
    builder.append(symbol);
    builder.append(", side=");
    builder.append(side);
    builder.append(", execQuantity=");
    builder.append(execQuantity);
    builder.append("]");
    return builder.toString();
  }
}
