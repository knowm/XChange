package org.knowm.xchange.dragonex.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;

public class CoinWithdraw {

  public final long withdrawId;
  public final long uid;
  public final long coinId;
  public final String addr;
  public final String txId;
  public final BigDecimal volume;
  public final int status;
  public final long arriveTime;
  public final String rejectReason;
  public final String tag;

  public CoinWithdraw(
      @JsonProperty("withdraw_id") long withdrawId,
      @JsonProperty("uid") long uid,
      @JsonProperty("coin_id") long coinId,
      @JsonProperty("addr") String addr,
      @JsonProperty("tx_id") String txId,
      @JsonProperty("volume") BigDecimal volume,
      @JsonProperty("status") int status,
      @JsonProperty("arrive_time") long arriveTime,
      @JsonProperty("reject_reason") String rejectReason,
      @JsonProperty("tag") String tag) {
    super();
    this.withdrawId = withdrawId;
    this.uid = uid;
    this.coinId = coinId;
    this.addr = addr;
    this.txId = txId;
    this.volume = volume;
    this.status = status;
    this.arriveTime = arriveTime;
    this.rejectReason = rejectReason;
    this.tag = tag;
  }

  public Date getArriveTime() {
    return new Date(arriveTime * 1000);
  }

  @Override
  public String toString() {
    return "CoinWithdraw [withdrawId="
        + withdrawId
        + ", uid="
        + uid
        + ", coinId="
        + coinId
        + ", "
        + (addr != null ? "addr=" + addr + ", " : "")
        + (txId != null ? "txId=" + txId + ", " : "")
        + (volume != null ? "volume=" + volume + ", " : "")
        + "status="
        + status
        + ", arriveTime="
        + arriveTime
        + ", "
        + (rejectReason != null ? "rejectReason=" + rejectReason + ", " : "")
        + (tag != null ? "tag=" + tag + ", " : "")
        + (getArriveTime() != null ? "getArriveTime()=" + getArriveTime() : "")
        + "]";
  }
}
