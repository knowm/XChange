package info.bitrich.xchangestream.lgo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import info.bitrich.xchangestream.lgo.domain.LgoAckOrderEvent;
import java.util.List;

public class LgoAckUpdate {

  private final String type;
  private final String channel;
  private final List<LgoAckOrderEvent> data;

  public LgoAckUpdate(
      @JsonProperty("type") String type,
      @JsonProperty("channel") String channel,
      @JsonProperty("payload") List<LgoAckOrderEvent> data) {
    this.type = type;
    this.channel = channel;
    this.data = data;
  }

  public String getType() {
    return type;
  }

  public String getChannel() {
    return channel;
  }

  public List<LgoAckOrderEvent> getData() {
    return data;
  }
}
