package info.bitrich.xchangestream.gateio.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import info.bitrich.xchangestream.gateio.config.Config;
import info.bitrich.xchangestream.gateio.config.TimestampSecondsToInstantConverter;
import info.bitrich.xchangestream.gateio.dto.response.orderbook.GateioOrderBookNotification;
import info.bitrich.xchangestream.gateio.dto.response.ticker.GateioTickerNotification;
import info.bitrich.xchangestream.gateio.dto.response.trade.GateioTradeNotification;
import java.time.Instant;
import lombok.Data;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    property = "channel")
@JsonSubTypes({
    @Type(value = GateioTradeNotification.class, name = Config.SPOT_TRADES_CHANNEL),
    @Type(value = GateioTickerNotification.class, name = Config.SPOT_TICKERS_CHANNEL),
    @Type(value = GateioOrderBookNotification.class, name = Config.SPOT_ORDERBOOK_CHANNEL)
})
@Data
public class GateioWebSocketNotification<T> {

  @JsonProperty("time")
  @JsonDeserialize(converter = TimestampSecondsToInstantConverter.class)
  private Instant time;

  @JsonProperty("time_ms")
  private Instant timeMs;

  @JsonProperty("channel")
  private String channel;

  @JsonProperty("event")
  private String event;

  @JsonProperty("error")
  private String error;

  @JsonProperty("result")
  private T result;
}
