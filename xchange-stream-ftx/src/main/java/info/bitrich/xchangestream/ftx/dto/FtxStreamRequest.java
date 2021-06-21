package info.bitrich.xchangestream.ftx.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

public class FtxStreamRequest implements Serializable {

  @JsonProperty("channel")
  private final String channel;

  @JsonProperty("market")
  private final String market;

  @JsonProperty("op")
  private final String op;

  @JsonCreator
  public FtxStreamRequest(
      @JsonProperty("channel") String channel,
      @JsonProperty("market") String market,
      @JsonProperty("op") String op) {
    this.channel = channel;
    this.market = market;
    this.op = op;
  }

  public String getChannel() {
    return channel;
  }

  public String getMarket() {
    return market;
  }

  public String getOp() {
    return op;
  }

  @Override
  public String toString() {
    return "FtxResponse{"
        + "channel='"
        + channel
        + '\''
        + ", market='"
        + market
        + '\''
        + ", op='"
        + op
        + '\''
        + '}';
  }
}
