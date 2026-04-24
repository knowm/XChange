package info.bitrich.xchangestream.gateio.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Data;

import java.time.Clock;
import java.util.List;

@Data
public class ConfigFutures {
  public static final String V4_URL = "wss://api.gateio.ws/ws/v4/";

  public static final String FUTURES_TRADES_CHANNEL = "futures.trades";
  public static final List<String> PRIVATE_CHANNELS =
      List.of();

  public static final String CHANNEL_NAME_DELIMITER = "-";

  private ObjectMapper objectMapper;
  private Clock clock;

  private static ConfigFutures instance = new ConfigFutures();

  private ConfigFutures() {
    clock = Clock.systemDefaultZone();

    objectMapper = new ObjectMapper();

    // by default read and write timetamps as milliseconds
    objectMapper.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
    objectMapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);

    // don't fail un unknown properties
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    // don't write nulls
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

    // enable parsing to Instant
    objectMapper.registerModule(new JavaTimeModule());
  }

  public static ConfigFutures getInstance() {
    return instance;
  }
}
