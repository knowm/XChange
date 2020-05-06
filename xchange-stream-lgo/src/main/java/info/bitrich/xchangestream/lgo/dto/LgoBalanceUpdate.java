package info.bitrich.xchangestream.lgo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class LgoBalanceUpdate {
  private final long seq;
  private final String type;
  private final String channel;
  private final List<List<String>> data;

  public LgoBalanceUpdate(
      @JsonProperty("seq") long seq,
      @JsonProperty("type") String type,
      @JsonProperty("channel") String channel,
      @JsonProperty("payload") List<List<String>> data) {
    this.seq = seq;
    this.type = type;
    this.channel = channel;
    this.data = data;
  }

  public long getSeq() {
    return seq;
  }

  public String getType() {
    return type;
  }

  public String getChannel() {
    return channel;
  }

  public List<List<String>> getData() {
    return data;
  }
}
