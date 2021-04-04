package org.knowm.xchange.ftx.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

public class FtxLendingHistoryDto {

  @JsonProperty("coin")
  private final String coin;

  @JsonProperty("cost")
  private final double cost;

  @JsonProperty("rate")
  private final double rate;

  @JsonProperty("size")
  private final double size;

  @JsonProperty("time")
  private final Date time;

  public FtxLendingHistoryDto(
      @JsonProperty("coin") String coin,
      @JsonProperty("cost") double cost,
      @JsonProperty("rate") double rate,
      @JsonProperty("size") double size,
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

  public double getCost() {
    return cost;
  }

  public double getRate() {
    return rate;
  }

  public double getSize() {
    return size;
  }

  public Date getTime() {
    return time;
  }

  @Override
  public String toString() {
    return "FtxLendingHistoriesDto{"
        + "coin='"
        + coin
        + '\''
        + ", cost="
        + cost
        + ", rate="
        + rate
        + ", size="
        + size
        + ", time='"
        + time
        + '\''
        + '}';
  }
}
