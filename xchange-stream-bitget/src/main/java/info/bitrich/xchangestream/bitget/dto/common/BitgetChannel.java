package info.bitrich.xchangestream.bitget.dto.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class BitgetChannel {

  @JsonProperty("instType")
  private MarketType marketType;

  @JsonProperty("channel")
  private ChannelType channelType;

  @JsonProperty("instId")
  private String instrumentId;


  public static enum MarketType {
    @JsonProperty("SPOT")
    SPOT
  }


  @Getter
  @AllArgsConstructor
  public static enum ChannelType {
    TICKER("ticker");

    @JsonValue
    private final String value;

  }

}
