package info.bitrich.xchangestream.gateio.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Data;

import java.time.Clock;
import java.util.Arrays;
import java.util.List;

@Data
public final class Config {

  public static final String V4_URL = "wss://api.gateio.ws/ws/v4/";
  public static final String V4_FUTURES_URL = "wss://fx-ws.gateio.ws/v4/ws/usdt";
  public static final String SPOT_ORDERBOOK_CHANNEL = "spot.order_book";
  public static final String SPOT_ORDERBOOKV2_CHANNEL = "spot.obu";
  public static final String SPOT_ORDERBOOK_TICKER_CHANNEL = "spot.book_ticker";
  public static final String FUTURES_ORDERBOOK_TICKER_CHANNEL = "futures.book_ticker";
  public static final String FUTURES_ORDERBOOKV2_CHANNEL = "futures.obu";
  public static final String SPOT_TRADES_CHANNEL = "spot.trades";
  public static final String FUTURES_TRADES_CHANNEL = "futures.trades";
  public static final String SPOT_TICKERS_CHANNEL = "spot.tickers";
  public static final String FUTURES_TICKERS_CHANNEL = "futures.tickers";
  public static final String SPOT_BALANCES_CHANNEL = "spot.balances";
  public static final String SPOT_USER_TRADES_CHANNEL = "spot.usertrades";
  public static final String SPOT_USER_ORDERS_CHANNEL = "spot.orders";
  public static final String SPOT_ORDER_PLACE_CHANNEL = "spot.order_place";
  public static final String FUTURES_ORDER_PLACE_CHANNEL = "futures.order_place";
  public static final String FUTURES_USER_ORDERS_CHANNEL = "futures.orders";
  public static final String FUTURES_TICKET_AND_FUNDING_CHANNEL = "futures.tickers";
  public static final List<String> PRIVATE_CHANNELS =
      Arrays.asList(SPOT_BALANCES_CHANNEL, SPOT_USER_TRADES_CHANNEL, SPOT_USER_ORDERS_CHANNEL, FUTURES_USER_ORDERS_CHANNEL);

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
