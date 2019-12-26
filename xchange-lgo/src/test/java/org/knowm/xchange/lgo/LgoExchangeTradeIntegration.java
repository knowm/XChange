package org.knowm.xchange.lgo;

import org.apache.commons.io.IOUtils;
import org.junit.*;
import org.knowm.xchange.*;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.*;
import org.knowm.xchange.lgo.service.LgoTradeService;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static org.assertj.core.api.Assertions.*;

@Ignore
public class LgoExchangeTradeIntegration {

  @Test
  public void fetchLastTrades() throws IOException {
    LgoExchange lgoExchange = exchangeWithCredentials();
    LgoTradeService tradeService = lgoExchange.getTradeService();
    UserTrades tradeHistory = tradeService.getTradeHistory(tradeService.createTradeHistoryParams());
    assertThat(tradeHistory.getUserTrades()).isNotEmpty();
    System.out.println(tradeHistory.getUserTrades().size());
  }

  @Test
  public void placeLimitOrder() throws IOException {
    LgoExchange lgoExchange = exchangeWithCredentials();
    LgoTradeService tradeService = lgoExchange.getTradeService();

    String orderId =
        tradeService.placeEncryptedLimitOrder(
            new LimitOrder(
                OrderType.ASK,
                new BigDecimal("2"),
                CurrencyPair.BTC_USD,
                null,
                new Date(),
                new BigDecimal("6000")));

    System.out.println(orderId);
  }

  @Test
  public void placeMarketOrder() throws IOException {
    LgoExchange lgoExchange = exchangeWithCredentials();
    LgoTradeService tradeService = lgoExchange.getTradeService();

    String orderId =
        tradeService.placeEncryptedMarketOrder(
            new MarketOrder(
                OrderType.ASK, new BigDecimal("2"), CurrencyPair.BTC_USD, null, new Date()));

    System.out.println(orderId);
  }

  @Test
  public void cancelOrder() throws IOException {
    LgoExchange lgoExchange = exchangeWithCredentials();
    LgoTradeService tradeService = lgoExchange.getTradeService();

    tradeService.placeEncryptedCancelOrder("156941460160700001");
  }

  @Test
  public void placeUnencryptedLimitOrder() throws IOException {
    LgoExchange lgoExchange = exchangeWithCredentials();
    LgoTradeService tradeService = lgoExchange.getTradeService();

    String orderId =
        tradeService.placeLimitOrder(
            new LimitOrder(
                OrderType.ASK,
                new BigDecimal("2"),
                CurrencyPair.BTC_USD,
                null,
                new Date(),
                new BigDecimal("6000")));

    System.out.println(orderId);
  }

  @Test
  public void placeUnencryptedMarketOrder() throws IOException {
    LgoExchange lgoExchange = exchangeWithCredentials();
    LgoTradeService tradeService = lgoExchange.getTradeService();

    String orderId =
        tradeService.placeMarketOrder(
            new MarketOrder(
                OrderType.ASK, new BigDecimal("200"), CurrencyPair.BTC_USD, null, new Date()));

    System.out.println(orderId);
  }

  @Test
  public void placeUnencryptedCancelOrder() throws IOException {
    LgoExchange lgoExchange = exchangeWithCredentials();
    LgoTradeService tradeService = lgoExchange.getTradeService();

    tradeService.cancelOrder("156940341166500001");
  }

  // api key and secret key are expected to be in test resources under
  // integration directory
  // this directory is added to .gitignore to avoid committing a real usable key
  protected LgoExchange exchangeWithCredentials() throws IOException {
    ExchangeSpecification spec = LgoEnv.sandbox();
    spec.setSecretKey(readResource("/integration/private_key.pem"));
    spec.setApiKey(readResource("/integration/api_key.txt"));

    return (LgoExchange) ExchangeFactory.INSTANCE.createExchange(spec);
  }

  private String readResource(String path) throws IOException {
    InputStream stream = LgoExchange.class.getResourceAsStream(path);
    return IOUtils.toString(stream, StandardCharsets.UTF_8);
  }
}
