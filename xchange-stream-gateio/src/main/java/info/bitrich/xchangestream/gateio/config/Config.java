package info.bitrich.xchangestream.gateio.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.Clock;
import java.util.Arrays;
import java.util.List;
import lombok.Data;

@Data
public final class Config {

  public static final String V4_URL = "wss://api.gateio.ws/ws/v4/";

  public static final String SPOT_ORDERBOOK_CHANNEL = "spot.order_book";
  public static final String SPOT_TRADES_CHANNEL = "spot.trades";
  public static final String SPOT_TICKERS_CHANNEL = "spot.tickers";
  public static final String SPOT_BALANCES_CHANNEL = "spot.balances";
  public static final String SPOT_USER_TRADES_CHANNEL = "spot.usertrades";
  public static final List<String> PRIVATE_CHANNELS = Arrays.asList(SPOT_BALANCES_CHANNEL, SPOT_USER_TRADES_CHANNEL);

  public static final String CHANNEL_NAME_DELIMITER = "-";

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
