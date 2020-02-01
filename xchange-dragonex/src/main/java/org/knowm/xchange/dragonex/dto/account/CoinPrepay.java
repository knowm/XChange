package org.knowm.xchange.dragonex.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;

public class CoinPrepay {

  public final long prepayId;
  public final long uid;
  public final long coinId;
  public final String addr;
  public final String txId;
  public final BigDecimal volume;
  public final long arriveTime;
  public final int status;
  public final String tag;

  public CoinPrepay(
      @JsonProperty("prepay_id") long prepayId,
      @JsonProperty("uid") long uid,
      @JsonProperty("coin_id") long coinId,
      @JsonProperty("addr") String addr,
      @JsonProperty("tx_id") String txId,
      @JsonProperty("volume") BigDecimal volume,
      @JsonProperty("arrive_time") long arriveTime,
      @JsonProperty("status") int status,
      @JsonProperty("tag") String tag) {
    super();
    this.prepayId = prepayId;
    this.uid = uid;
    this.coinId = coinId;
    this.addr = addr;
    this.txId = txId;
    this.volume = volume;
    this.arriveTime = arriveTime;
    this.status = status;
    this.tag = tag;
  }

  public Date getArriveTime() {
    return new Date(arriveTime * 1000);
  }

  @Override
  public String toString() {
    return "CoinPrepay [prepayId="
        + prepayId
        + ", uid="
        + uid
        + ", coinId="
        + coinId
        + ", "
        + (addr != null ? "addr=" + addr + ", " : "")
        + (txId != null ? "txId=" + txId + ", " : "")
        + (volume != null ? "volume=" + volume + ", " : "")
        + "arriveTime="
        + arriveTime
        + ", status="
        + status
        + ", "
        + (tag != null ? "tag=" + tag + ", " : "")
        + (getArriveTime() != null ? "getArriveTime()=" + getArriveTime() : "")
        + "]";
  }
}
