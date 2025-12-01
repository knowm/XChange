package info.bitrich.xchangestream.kraken.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import info.bitrich.xchangestream.kraken.dto.common.ChannelType;
import lombok.Data;

import java.time.Clock;
import java.util.Set;

@Data
public final class Config {

  public static final Set<String> PRIVATE_CHANNELS = Set.of(ChannelType.BALANCES.getValue(), ChannelType.USER_TRADES.getValue());
  private ObjectMapper objectMapper;
  private Clock clock;

  private static Config instance = new Config();

  private Config() {
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

  public static Config getInstance() {
    return instance;
  }
}
