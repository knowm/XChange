package info.bitrich.xchangestream.bitfinex;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.bitfinex.dto.BitfinexWebSocketAuthBalance;
import info.bitrich.xchangestream.bitfinex.dto.BitfinexWebSocketAuthOrder;
import info.bitrich.xchangestream.bitfinex.dto.BitfinexWebSocketAuthPreTrade;
import info.bitrich.xchangestream.bitfinex.dto.BitfinexWebSocketAuthTrade;
import io.reactivex.rxjava3.subscribers.TestSubscriber;
import java.io.IOException;
import java.math.BigDecimal;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import si.mazi.rescu.SynchronizedValueFactory;

public class BitfinexStreamingServiceTest {

  private BitfinexStreamingService service;
  private final ObjectMapper objectMapper = new ObjectMapper();

  @Mock SynchronizedValueFactory<Long> nonceFactory;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    service = new BitfinexStreamingService(BitfinexStreamingExchange.API_URI, nonceFactory);
  }

  @Test
  public void testGetOrders() throws Exception {

    JsonNode jsonNode =
        objectMapper.readTree(
            ClassLoader.getSystemClassLoader().getResourceAsStream("orders.json"));

    TestSubscriber<BitfinexWebSocketAuthOrder> test = service.getAuthenticatedOrders().test();

    service.handleMessage(jsonNode);
    BitfinexWebSocketAuthOrder expected =
        new BitfinexWebSocketAuthOrder(
            13759731408L,
            0,
            50999677532L,
            "tETHUSD",
            1530108599707L,
            1530108599726L,
            new BigDecimal("-0.02"),
            new BigDecimal("-0.02"),
            "EXCHANGE LIMIT",
            null,
            "ACTIVE",
            new BigDecimal("431.19"),
            BigDecimal.ZERO,
            BigDecimal.ZERO,
            BigDecimal.ZERO,
            0L,
            0);
    test.assertValue(expected);
  }

  @Test
  public void testGetPreTrades() throws Exception {

    JsonNode jsonNode =
        objectMapper.readTree(
            ClassLoader.getSystemClassLoader().getResourceAsStream("preTrade.json"));

    TestSubscriber<BitfinexWebSocketAuthPreTrade> test = service.getAuthenticatedPreTrades().test();

    service.handleMessage(jsonNode);

    BitfinexWebSocketAuthPreTrade expected =
        new BitfinexWebSocketAuthPreTrade(
            262861164L,
            "tETHUSD",
            1530187145559L,
            13787457748L,
            new BigDecimal("-0.04"),
            new BigDecimal("435.8"),
            "EXCHANGE LIMIT",
            new BigDecimal("435.8"),
            1);
    test.assertValue(expected);
  }

  @Test
  public void testGetTrades() throws Exception {

    JsonNode jsonNode =
        objectMapper.readTree(ClassLoader.getSystemClassLoader().getResourceAsStream("trade.json"));

    TestSubscriber<BitfinexWebSocketAuthTrade> test = service.getAuthenticatedTrades().test();

    service.handleMessage(jsonNode);

    BitfinexWebSocketAuthTrade expected =
        new BitfinexWebSocketAuthTrade(
            262861164L,
            "tETHUSD",
            1530187145559L,
            13787457748L,
            new BigDecimal("-0.04"),
            new BigDecimal("435.8"),
            "EXCHANGE LIMIT",
            new BigDecimal("435.8"),
            1,
            new BigDecimal("-0.0104592"),
            "USD");
    test.assertValue(expected);
  }

  @Test
  public void testGetBalances() throws Exception {
    JsonNode jsonNode =
        objectMapper.readTree(
            ClassLoader.getSystemClassLoader().getResourceAsStream("balances.json"));

    TestSubscriber<BitfinexWebSocketAuthBalance> test = service.getAuthenticatedBalances().test();

    service.handleMessage(jsonNode);

    BitfinexWebSocketAuthBalance expected =
        new BitfinexWebSocketAuthBalance(
            "exchange", "ETH", new BigDecimal("0.38772"), BigDecimal.ZERO, null);

    BitfinexWebSocketAuthBalance expected1 =
        new BitfinexWebSocketAuthBalance(
            "exchange", "USD", new BigDecimal("69.4747619"), BigDecimal.ZERO, null);
    test.assertNoErrors();
    test.assertValueCount(2);
    assertThat(test.values().contains(expected));
    assertThat(test.values().contains(expected1));
  }

  @Test
  public void testGetBalance() throws IOException {
    JsonNode jsonNode =
        objectMapper.readTree(
            ClassLoader.getSystemClassLoader().getResourceAsStream("balance.json"));
    TestSubscriber<BitfinexWebSocketAuthBalance> test = service.getAuthenticatedBalances().test();
    service.handleMessage(jsonNode);

    BitfinexWebSocketAuthBalance balance =
        new BitfinexWebSocketAuthBalance(
            "exchange", "USD", new BigDecimal("78.5441867"), BigDecimal.ZERO, null);

    test.assertValue(balance);
  }
}
