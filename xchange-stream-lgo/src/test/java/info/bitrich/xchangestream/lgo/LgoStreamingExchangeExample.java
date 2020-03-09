package info.bitrich.xchangestream.lgo;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;
import org.apache.commons.io.IOUtils;
import org.junit.*;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.*;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.*;
import org.knowm.xchange.lgo.LgoEnv;

@Ignore
public class LgoStreamingExchangeExample {

  private LgoStreamingExchange exchange;

  // api key and secret key are expected to be in test resources under
  // example directory
  // this directory is added to .gitignore to avoid committing a real usable key
  @Before
  public void setUp() throws Exception {
    exchange = new LgoStreamingExchange();
    ExchangeSpecification spec = LgoEnv.sandbox();
    spec.setSecretKey(readResource("/example/private_key.pem"));
    spec.setApiKey(readResource("/example/api_key.txt"));
    spec.setShouldLoadRemoteMetaData(false);
    exchange.applySpecification(spec);
    exchange.connect().blockingAwait();
  }

  private String readResource(String path) throws IOException {
    InputStream stream = LgoStreamingExchangeExample.class.getResourceAsStream(path);
    return IOUtils.toString(stream, StandardCharsets.UTF_8);
  }

  @Test
  public void connectToMarketData() {
    exchange
        .getStreamingMarketDataService()
        .getOrderBook(CurrencyPair.BTC_USD)
        .blockingForEach(System.out::println);
  }

  @Test
  public void connectToTrades() {
    exchange
        .getStreamingMarketDataService()
        .getTrades(CurrencyPair.BTC_USD)
        .blockingForEach(System.out::println);
  }

  @Test
  public void connectToBalances() {
    exchange.getStreamingAccountService().getWallet().blockingForEach(System.out::println);
  }

  @Test
  public void connectToBalance() {
    exchange
        .getStreamingAccountService()
        .getBalanceChanges(Currency.BTC)
        .blockingForEach(System.out::println);
  }

  @Test
  public void connectToUserTrades() {
    exchange
        .getStreamingTradeService()
        .getUserTrades(CurrencyPair.BTC_USD)
        .blockingSubscribe(System.out::println);
  }

  @Test
  public void connectToOpenOrders() {
    exchange
        .getStreamingTradeService()
        .getOpenOrders(CurrencyPair.BTC_USD)
        .blockingForEach(System.out::println);
  }

  @Test
  public void connectToOrderChanges() {
    exchange
        .getStreamingTradeService()
        .getOrderChanges(CurrencyPair.BTC_USD)
        .blockingForEach(System.out::println);
  }

  @Test
  public void connectToOrderBatchChanges() {
    exchange
        .getStreamingTradeService()
        .getOrderBatchChanges(CurrencyPair.BTC_USD)
        .blockingForEach(System.out::println);
  }

  @Test
  public void connectToOrderEvents() {
    exchange
        .getStreamingTradeService()
        .getRawAllOrderEvents(Collections.singletonList(CurrencyPair.BTC_USD))
        .blockingForEach(System.out::println);
  }

  @Test
  public void placeLimitOrder() throws IOException {
    String ref =
        exchange
            .getStreamingTradeService()
            .placeLimitOrder(
                new LimitOrder(
                    Order.OrderType.ASK,
                    new BigDecimal("0.5"),
                    CurrencyPair.BTC_USD,
                    null,
                    new Date(),
                    new BigDecimal("12000")));
    System.out.println("Order was placed with reference: " + ref);
  }

  @Test
  public void placeMarketOrder() throws IOException {
    String ref =
        exchange
            .getStreamingTradeService()
            .placeMarketOrder(
                new MarketOrder(
                    Order.OrderType.ASK,
                    new BigDecimal("0.5"),
                    CurrencyPair.BTC_USD,
                    null,
                    new Date()));
    System.out.println("Order was placed with reference: " + ref);
  }

  @Test
  public void cancelOrder() throws IOException {
    exchange.getStreamingTradeService().cancelOrder("156406068135700001");
  }
}
