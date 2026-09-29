package info.bitrich.xchangestream.hitbtc.dto;

import static java.util.Map.entry;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Created by Pavel Chertalev on 19.03.2018. */
public class HitbtcMessageTest {
  private static final Logger LOG = LoggerFactory.getLogger(HitbtcMessageTest.class);

  private ObjectMapper objectMapper;

  @BeforeEach
  public void setUp() {
    objectMapper = new ObjectMapper();

    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    SimpleModule module = new SimpleModule();
    module.addSerializer(BigDecimal.class, new ToStringSerializer());
    SimpleDateFormat customFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    customFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    module.addSerializer(Date.class, new DateSerializer(false, customFormat));
    objectMapper.registerModule(module);
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
  }

  @MethodSource("data")
  @ParameterizedTest
  public void test(Class<?> clazz, Map<String, Object> expectedJsonPaths, String testResource)
      throws Exception {
    LOG.info("Testing {} message...", testResource);

    String message =
        new String(
            Files.readAllBytes(Paths.get(getClass().getResource(testResource).toURI())),
            StandardCharsets.UTF_8);

    Object object = objectMapper.readValue(message, clazz);

    assertThat(object).isNotNull();

    message = objectMapper.writeValueAsString(object);
    LOG.info(message);

    DocumentContext json = JsonPath.parse(message);
    expectedJsonPaths.forEach(
        (path, expected) -> assertThat((Object) json.read(path)).as(path).isEqualTo(expected));
  }

  public static Collection<Object[]> data() {
    return Arrays.asList(
        new Object[][] {
          {
            HitbtcWebSocketSubscriptionMessage.class,
            Map.ofEntries(
                entry("$.method", "subscribeTicker"),
                entry("$.id", 123),
                entry("$.params.symbol", "ETHBTC")),
            "/example/subscriptionMessage.json"
          },
          {
            HitbtcWebSocketTickerTransaction.class,
            Map.ofEntries(
                entry("$.method", "ticker"),
                entry("$.params.ask", "0.054464"),
                entry("$.params.bid", "0.054463"),
                entry("$.params.last", "0.054463"),
                entry("$.params.open", "0.057133"),
                entry("$.params.low", "0.053615"),
                entry("$.params.high", "0.057559"),
                entry("$.params.volume", "33068.346"),
                entry("$.params.volumeQuote", "1832.687530809"),
                entry("$.params.timestamp", "2017-10-19T15:45:44.941Z"),
                entry("$.params.symbol", "ETHBTC")),
            "/example/notificationTicker.json"
          },
          {
            HitbtcWebSocketTradesTransaction.class,
            Map.ofEntries(
                entry("$.method", "snapshotTrades"),
                entry("$.params.data[0].id", "54469456"),
                entry("$.params.data[0].price", "0.054656"),
                entry("$.params.data[0].quantity", "0.057"),
                entry("$.params.data[0].side", "buy"),
                entry("$.params.data[0].timestamp", "2017-10-19T16:33:42.821Z"),
                entry("$.params.data[2].id", "54469697"),
                entry("$.params.data[2].price", "0.054669"),
                entry("$.params.data[2].quantity", "0.002"),
                entry("$.params.data[2].side", "buy"),
                entry("$.params.data[2].timestamp", "2017-10-19T16:34:13.288Z"),
                entry("$.params.symbol", "ETHBTC")),
            "/example/notificationSnapshotTrades.json"
          },
          {
            HitbtcWebSocketOrderBookTransaction.class,
            Map.ofEntries(
                entry("$.method", "snapshotOrderbook"),
                entry("$.params.ask[0].price", "0.054588"),
                entry("$.params.ask[0].size", "0.245"),
                entry("$.params.ask[1].price", "0.054590"),
                entry("$.params.ask[1].size", "0.000"),
                entry("$.params.bid[0].price", "0.054558"),
                entry("$.params.bid[0].size", "0.500"),
                entry("$.params.bid[1].price", "0.054557"),
                entry("$.params.bid[1].size", "0.076"),
                entry("$.params.sequence", 8073827),
                entry("$.params.symbol", "ETHBTC")),
            "/example/notificationSnapshotOrderBook.json"
          }
        });
  }
}
