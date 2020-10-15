package org.knowm.xchange.bitbns.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;

public class Data {

  private long entry_id;
  private BigDecimal btc;
  private BigDecimal rate;
  private Date time;
  private int type;
  private int status;
  private BigDecimal total;
  private BigDecimal avg_cost;
  private String side;
  private BigDecimal amount;
  private BigDecimal remaining;
  private BigDecimal filled;
  private BigDecimal cost;
  private BigDecimal fee;

  public Data(
      @JsonProperty("entry_id") long entry_id,
      @JsonProperty("btc") BigDecimal btc,
      @JsonProperty("rate") BigDecimal rate,
      @JsonProperty("time") Date time,
      @JsonProperty("type") int type,
      @JsonProperty("status") int status,
      @JsonProperty("total") BigDecimal total,
      @JsonProperty("avg_cost") BigDecimal avg_cost,
      @JsonProperty("side") String side,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("remaining") BigDecimal remaining,
      @JsonProperty("filled") BigDecimal filled,
      @JsonProperty("cost") BigDecimal cost,
      @JsonProperty("fee") BigDecimal fee) {

    this.entry_id = entry_id;
    this.btc = btc;
    this.rate = rate;
    this.time = time;
    this.type = type;
    this.status = status;
    this.total = total;
    this.avg_cost = avg_cost;
    this.side = side;
    this.amount = amount;
    this.remaining = remaining;
    this.filled = filled;
    this.cost = cost;
    this.fee = fee;
  }

  public long getEntry_id() {
    return entry_id;
  }

  public void setEntry_id(long entry_id) {
    this.entry_id = entry_id;
  }

  public BigDecimal getBtc() {
    return btc;
  }

  public void setBtc(BigDecimal btc) {
    this.btc = btc;
  }

  public BigDecimal getRate() {
    return rate;
  }

  public void setRate(BigDecimal rate) {
    this.rate = rate;
  }

  public Date getTime() {
    return time;
  }

  public void setTime(Date time) {
    this.time = time;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public BigDecimal getTotal() {
    return total;
  }

  public void setTotal(BigDecimal total) {
    this.total = total;
  }

  public BigDecimal getAvg_cost() {
    return avg_cost;
  }

  public void setAvg_cost(BigDecimal avg_cost) {
    this.avg_cost = avg_cost;
  }

  public String getSide() {
    return side;
  }

  public void setSide(String side) {
    this.side = side;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public BigDecimal getRemaining() {
    return remaining;
  }

  public void setRemaining(BigDecimal remaining) {
    this.remaining = remaining;
  }

  public BigDecimal getFilled() {
    return filled;
  }

  public void setFilled(BigDecimal filled) {
    this.filled = filled;
  }

  public BigDecimal getCost() {
    return cost;
  }

  public void setCost(BigDecimal cost) {
    this.cost = cost;
  }

  public BigDecimal getFee() {
    return fee;
  }

  public void setFee(BigDecimal fee) {
    this.fee = fee;
  }

  @Override
  public String toString() {
    return "Data [entry_id="
        + entry_id
        + ", btc="
        + btc
        + ", rate="
        + rate
        + ", time="
        + time
        + ", type="
        + type
        + ", status="
        + status
        + ", total="
        + total
        + ", avg_cost="
        + avg_cost
        + ", side="
        + side
        + ", amount="
        + amount
        + ", remaining="
        + remaining
        + ", filled="
        + filled
        + ", cost="
        + cost
        + ", fee="
        + fee
        + "]";
  }
}
