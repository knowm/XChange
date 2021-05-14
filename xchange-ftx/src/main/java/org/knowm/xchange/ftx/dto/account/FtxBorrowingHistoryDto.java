package org.knowm.xchange.ftx.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;

public class FtxBorrowingHistoryDto {

  @JsonProperty("coin")
  private final String coin;

  @JsonProperty("cost")
  private final BigDecimal cost;

  @JsonProperty("rate")
  private final BigDecimal rate;

  @JsonProperty("size")
  private final BigDecimal size;

  @JsonProperty("time")
  private final Date time;

  public FtxBorrowingHistoryDto(
      @JsonProperty("coin") String coin,
      @JsonProperty("cost") BigDecimal cost,
      @JsonProperty("rate") BigDecimal rate,
      @JsonProperty("size") BigDecimal size,
      @JsonProperty("time") Date time) {
    this.coin = coin;
    this.cost = cost;
    this.rate = rate;
    this.size = size;
    this.time = time;
  }

  public String getCoin() {
    return coin;
  }

  public BigDecimal getCost() {
    return cost;
  }

  public BigDecimal getRate() {
    return rate;
  }

  public BigDecimal getSize() {
    return size;
  }

  public Date getTime() {
    return time;
  }

  @Override
  public String toString() {
    return "FtxBorrowingHistoryDto{"
        + "coin='"
        + coin
        + '\''
        + ", cost="
        + cost
        + ", rate="
        + rate
        + ", size="
        + size
        + ", time="
        + time
        + '}';
  }
}
