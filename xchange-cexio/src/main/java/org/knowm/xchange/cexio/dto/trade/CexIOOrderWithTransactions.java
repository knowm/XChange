package org.knowm.xchange.cexio.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class CexIOOrderWithTransactions {
  private final String id;
  private final Long time;
  private final String type;
  private final BigDecimal price;
  private final BigDecimal amount;
  private final BigDecimal pending;
  private final String symbol1;
  private final String symbol2;
  private final String symbol1Amount;
  private final String symbol2Amount;
  private final String lastTxTime;
  private final String tradingFeeUserVolumeAmount;
  private final BigDecimal tradingFeeMaker;
  private final BigDecimal tradingFeeTaker;
  private final String tradingFeeStrategy;
  private final BigDecimal remains;
  private final String orderId;
  private final String pos;
  private final String status;
  private final Boolean next;
  private final Boolean prev;
  private final List<CexIOTransaction> vtx;

  public CexIOOrderWithTransactions(
      @JsonProperty("id") String id,
      @JsonProperty("time") Long time,
      @JsonProperty("type") String type,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("pending") BigDecimal pending,
      @JsonProperty("symbol1") String symbol1,
      @JsonProperty("symbol2") String symbol2,
      @JsonProperty("symbol1Amount") String symbol1Amount,
      @JsonProperty("symbol2Amount") String symbol2Amount,
      @JsonProperty("lastTxTime") String lastTxTime,
      @JsonProperty("tradingFeeUserVolumeAmount") String tradingFeeUserVolumeAmount,
      @JsonProperty("tradingFeeMaker") BigDecimal tradingFeeMaker,
      @JsonProperty("tradingFeeTaker") BigDecimal tradingFeeTaker,
      @JsonProperty("tradingFeeStrategy") String tradingFeeStrategy,
      @JsonProperty("remains") BigDecimal remains,
      @JsonProperty("orderId") String orderId,
      @JsonProperty("pos") String pos,
      @JsonProperty("status") String status,
      @JsonProperty("next") Boolean next,
      @JsonProperty("prev") Boolean prev,
      @JsonProperty("vtx") List<CexIOTransaction> vtx) {
    this.id = id;
    this.time = time;
    this.type = type;
    this.price = price;
    this.amount = amount;
    this.pending = pending;
    this.symbol1 = symbol1;
    this.symbol2 = symbol2;
    this.symbol1Amount = symbol1Amount;
    this.symbol2Amount = symbol2Amount;
    this.lastTxTime = lastTxTime;
    this.tradingFeeUserVolumeAmount = tradingFeeUserVolumeAmount;
    this.tradingFeeMaker = tradingFeeMaker;
    this.tradingFeeTaker = tradingFeeTaker;
    this.tradingFeeStrategy = tradingFeeStrategy;
    this.remains = remains;
    this.orderId = orderId;
    this.pos = pos;
    this.status = status;
    this.next = next;
    this.prev = prev;
    this.vtx = vtx;
  }

  public String getId() {
    return id;
  }

  public Long getTime() {
    return time;
  }

  public String getType() {
    return type;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public BigDecimal getPending() {
    return pending;
  }

  public String getSymbol1() {
    return symbol1;
  }

  public String getSymbol2() {
    return symbol2;
  }

  public String getSymbol1Amount() {
    return symbol1Amount;
  }

  public String getSymbol2Amount() {
    return symbol2Amount;
  }

  public String getLastTxTime() {
    return lastTxTime;
  }

  public String getTradingFeeUserVolumeAmount() {
    return tradingFeeUserVolumeAmount;
  }

  public BigDecimal getTradingFeeMaker() {
    return tradingFeeMaker;
  }

  public BigDecimal getTradingFeeTaker() {
    return tradingFeeTaker;
  }

  public String getTradingFeeStrategy() {
    return tradingFeeStrategy;
  }

  public BigDecimal getRemains() {
    return remains;
  }

  public String getOrderId() {
    return orderId;
  }

  public String getPos() {
    return pos;
  }

  public String getStatus() {
    return status;
  }

  public Boolean getNext() {
    return next;
  }

  public Boolean getPrev() {
    return prev;
  }

  public List<CexIOTransaction> getVtx() {
    return vtx;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CexIOOrderWithTransactions that = (CexIOOrderWithTransactions) o;
    return Objects.equals(id, that.id)
        && Objects.equals(time, that.time)
        && Objects.equals(type, that.type)
        && Objects.equals(price, that.price)
        && Objects.equals(amount, that.amount)
        && Objects.equals(pending, that.pending)
        && Objects.equals(symbol1, that.symbol1)
        && Objects.equals(symbol2, that.symbol2)
        && Objects.equals(symbol1Amount, that.symbol1Amount)
        && Objects.equals(symbol2Amount, that.symbol2Amount)
        && Objects.equals(lastTxTime, that.lastTxTime)
        && Objects.equals(tradingFeeUserVolumeAmount, that.tradingFeeUserVolumeAmount)
        && Objects.equals(tradingFeeMaker, that.tradingFeeMaker)
        && Objects.equals(tradingFeeTaker, that.tradingFeeTaker)
        && Objects.equals(tradingFeeStrategy, that.tradingFeeStrategy)
        && Objects.equals(remains, that.remains)
        && Objects.equals(orderId, that.orderId)
        && Objects.equals(pos, that.pos)
        && Objects.equals(status, that.status)
        && Objects.equals(next, that.next)
        && Objects.equals(prev, that.prev)
        && Objects.equals(vtx, that.vtx);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        id,
        time,
        type,
        price,
        amount,
        pending,
        symbol1,
        symbol2,
        symbol1Amount,
        symbol2Amount,
        lastTxTime,
        tradingFeeUserVolumeAmount,
        tradingFeeMaker,
        tradingFeeTaker,
        tradingFeeStrategy,
        remains,
        orderId,
        pos,
        status,
        next,
        prev,
        vtx);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("CexIOOrderWithTransactions{");
    sb.append("id='").append(id).append('\'');
    sb.append(", time=").append(time);
    sb.append(", type='").append(type).append('\'');
    sb.append(", price=").append(price);
    sb.append(", amount=").append(amount);
    sb.append(", pending=").append(pending);
    sb.append(", symbol1='").append(symbol1).append('\'');
    sb.append(", symbol2='").append(symbol2).append('\'');
    sb.append(", symbol1Amount='").append(symbol1Amount).append('\'');
    sb.append(", symbol2Amount='").append(symbol2Amount).append('\'');
    sb.append(", lastTxTime='").append(lastTxTime).append('\'');
    sb.append(", tradingFeeUserVolumeAmount='").append(tradingFeeUserVolumeAmount).append('\'');
    sb.append(", tradingFeeMaker=").append(tradingFeeMaker);
    sb.append(", tradingFeeTaker=").append(tradingFeeTaker);
    sb.append(", tradingFeeStrategy='").append(tradingFeeStrategy).append('\'');
    sb.append(", remains=").append(remains);
    sb.append(", orderId='").append(orderId).append('\'');
    sb.append(", pos='").append(pos).append('\'');
    sb.append(", status='").append(status).append('\'');
    sb.append(", next=").append(next);
    sb.append(", prev=").append(prev);
    sb.append(", vtx=").append(vtx);
    sb.append('}');
    return sb.toString();
  }
}
