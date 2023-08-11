package info.bitrich.xchangestream.gateio.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.List;

public final class Config {

  public static final String V4_URL = "wss://api.gateio.ws/ws/v4/";

  public static final String SPOT_ORDERBOOK_CHANNEL = "spot.order_book";
  public static final String SPOT_TRADES_CHANNEL = "spot.trades";
  public static final String SPOT_TICKERS_CHANNEL = "spot.tickers";
  public static final String SPOT_BALANCES_CHANNEL = "spot.balances";
  public static final String SPOT_USER_TRADES_CHANNEL = "spot.usertrades";
  public static final List<String> PRIVATE_CHANNELS = List.of(SPOT_BALANCES_CHANNEL, SPOT_USER_TRADES_CHANNEL);

  private static final ObjectMapper MAPPER = new ObjectMapper();
  public static final String CHANNEL_NAME_DELIMITER = "-";

  static {
    // by default read and write timetamps as milliseconds
    MAPPER.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
    MAPPER.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);

    // don't fail un unknown properties
    MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    // don't write nulls
    MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);

    // enable parsing to Instant
    MAPPER.registerModule(new JavaTimeModule());
  }

  private Config() {
  }


  public static ObjectMapper getObjectMapper() {
    return MAPPER;
  }

}
