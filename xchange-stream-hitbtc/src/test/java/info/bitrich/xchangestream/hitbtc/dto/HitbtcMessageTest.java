package info.bitrich.xchangestream.hitbtc.dto;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.AllOf.allOf;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.TimeZone;
import org.apache.commons.io.IOUtils;
import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Created by Pavel Chertalev on 19.03.2018. */
@RunWith(Parameterized.class)
public class HitbtcMessageTest {
  private static final Logger LOG = LoggerFactory.getLogger(HitbtcMessageTest.class);

  private final Class<?> clazz;
  private final Matcher<String> matcher;
  private final String testResource;
  private final ObjectMapper objectMapper;

  public HitbtcMessageTest(Class<?> clazz, Matcher<String> matcher, String testResource) {
    this.clazz = clazz;
    this.matcher = matcher;
    this.testResource = testResource;
    this.objectMapper = new ObjectMapper();

    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    SimpleModule module = new SimpleModule();
    module.addSerializer(BigDecimal.class, new ToStringSerializer());
    SimpleDateFormat customFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    customFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    module.addSerializer(Date.class, new DateSerializer(false, customFormat));
    objectMapper.registerModule(module);
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
  }

  @Test
  public void test() throws IOException {
    LOG.info("Testing {} message...", testResource);

    String message =
        IOUtils.toString(getClass().getResource(testResource).openStream(), StandardCharsets.UTF_8);
    Object object = objectMapper.readValue(message, clazz);

    Assert.assertNotNull(object);

    message = objectMapper.writeValueAsString(object);
    LOG.info(message);

    assertThat(message, matcher);
  }

  @Parameterized.Parameters
  @SuppressWarnings("unchecked")
  public static Collection<Object[]> data() {
    return Arrays.asList(
        new Object[][] {
          {
            HitbtcWebSocketSubscriptionMessage.class,
            allOf(
                hasJsonPath("$.method", equalTo("subscribeTicker")),
                hasJsonPath("$.id", equalTo(123)),
                hasJsonPath("$.params.symbol", equalTo("ETHBTC"))),
            "/example/subscriptionMessage.json"
          },
          {
            HitbtcWebSocketTickerTransaction.class,
            allOf(
                hasJsonPath("$.method", equalTo("ticker")),
                hasJsonPath("$.params.ask", equalTo("0.054464")),
                hasJsonPath("$.params.bid", equalTo("0.054463")),
                hasJsonPath("$.params.last", equalTo("0.054463")),
                hasJsonPath("$.params.open", equalTo("0.057133")),
                hasJsonPath("$.params.low", equalTo("0.053615")),
                hasJsonPath("$.params.high", equalTo("0.057559")),
                hasJsonPath("$.params.volume", equalTo("33068.346")),
                hasJsonPath("$.params.volumeQuote", equalTo("1832.687530809")),
                hasJsonPath("$.params.timestamp", equalTo("2017-10-19T15:45:44.941Z")),
                hasJsonPath("$.params.symbol", equalTo("ETHBTC"))),
            "/example/notificationTicker.json"
          },
          {
            HitbtcWebSocketTradesTransaction.class,
            allOf(
                hasJsonPath("$.method", equalTo("snapshotTrades")),
                hasJsonPath("$.params.data[0].id", equalTo("54469456")),
                hasJsonPath("$.params.data[0].price", equalTo("0.054656")),
                hasJsonPath("$.params.data[0].quantity", equalTo("0.057")),
                hasJsonPath("$.params.data[0].side", equalTo("buy")),
                hasJsonPath("$.params.data[0].timestamp", equalTo("2017-10-19T16:33:42.821Z")),
                hasJsonPath("$.params.data[2].id", equalTo("54469697")),
                hasJsonPath("$.params.data[2].price", equalTo("0.054669")),
                hasJsonPath("$.params.data[2].quantity", equalTo("0.002")),
                hasJsonPath("$.params.data[2].side", equalTo("buy")),
                hasJsonPath("$.params.data[2].timestamp", equalTo("2017-10-19T16:34:13.288Z")),
                hasJsonPath("$.params.symbol", equalTo("ETHBTC"))),
            "/example/notificationSnapshotTrades.json"
          },
          {
            HitbtcWebSocketOrderBookTransaction.class,
            allOf(
                hasJsonPath("$.method", equalTo("snapshotOrderbook")),
                hasJsonPath("$.params.ask[0].price", equalTo("0.054588")),
                hasJsonPath("$.params.ask[0].size", equalTo("0.245")),
                hasJsonPath("$.params.ask[1].price", equalTo("0.054590")),
                hasJsonPath("$.params.ask[1].size", equalTo("0.000")),
                hasJsonPath("$.params.bid[0].price", equalTo("0.054558")),
                hasJsonPath("$.params.bid[0].size", equalTo("0.500")),
                hasJsonPath("$.params.bid[1].price", equalTo("0.054557")),
                hasJsonPath("$.params.bid[1].size", equalTo("0.076")),
                hasJsonPath("$.params.sequence", equalTo(8073827)),
                hasJsonPath("$.params.symbol", equalTo("ETHBTC"))),
            "/example/notificationSnapshotOrderBook.json"
          }
        });
  }
}
