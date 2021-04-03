package info.bitrich.xchangestream.ftx.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

public class FtxStreamResponse implements Serializable {

  @JsonProperty("channel")
  private final String channel;

  @JsonProperty("market")
  private final String market;

  @JsonProperty("type")
  private final String type;

  @JsonProperty("code")
  @JsonIgnore
  private final String code;

  @JsonProperty("msg")
  @JsonIgnore
  private final String msg;

  @JsonProperty("data")
  @JsonIgnore
  private final String data;

  @JsonCreator
  public FtxStreamResponse(
      @JsonProperty("channel") String channel,
      @JsonProperty("market") String market,
      @JsonProperty("type") String type,
      @JsonProperty("code") String code,
      @JsonProperty("msg") String msg,
      @JsonProperty("data") String data) {
    this.channel = channel;
    this.market = market;
    this.type = type;
    this.code = code;
    this.msg = msg;
    this.data = data;
  }

  public String getChannel() {
    return channel;
  }

  public String getMarket() {
    return market;
  }

  public String getType() {
    return type;
  }

  public String getCode() {
    return code;
  }

  public String getMsg() {
    return msg;
  }

  public String getData() {
    return data;
  }

  @Override
  public String toString() {
    return "FtxRequest{"
        + "channel='"
        + channel
        + '\''
        + ", market='"
        + market
        + '\''
        + ", type='"
        + type
        + '\''
        + ", code='"
        + code
        + '\''
        + ", msg='"
        + msg
        + '\''
        + ", data='"
        + data
        + '\''
        + '}';
  }
}
