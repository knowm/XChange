package org.knowm.xchange.lgo;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import org.apache.commons.io.IOUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.lgo.service.LgoTradeService;

@Ignore
public class LgoExchangeTradeIntegration {

  @Test
  public void fetchLastTrades() throws IOException {
    LgoExchange lgoExchange = exchangeWithCredentials(false);
    LgoTradeService tradeService = lgoExchange.getTradeService();
    UserTrades tradeHistory = tradeService.getTradeHistory(tradeService.createTradeHistoryParams());
    assertThat(tradeHistory.getUserTrades()).isNotEmpty();
    System.out.println(tradeHistory.getUserTrades().size());
  }

  @Test
  public void placeLimitOrder() throws IOException {
    LgoExchange lgoExchange = exchangeWithCredentials(false);
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
  public void placeMarketOrder() throws IOException {
    LgoExchange lgoExchange = exchangeWithCredentials(false);
    LgoTradeService tradeService = lgoExchange.getTradeService();

    String orderId =
        tradeService.placeMarketOrder(
            new MarketOrder(
                OrderType.ASK, new BigDecimal("2"), CurrencyPair.BTC_USD, null, new Date()));

    System.out.println(orderId);
  }

  @Test
  public void cancelOrder() throws IOException {
    LgoExchange lgoExchange = exchangeWithCredentials(false);
    LgoTradeService tradeService = lgoExchange.getTradeService();

    tradeService.cancelOrder("156941460160700001");
  }

  @Test
  public void placeEncryptedLimitOrder() throws IOException {
    LgoExchange lgoExchange = exchangeWithCredentials(true);
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
  public void placeEncryptedMarketOrder() throws IOException {
    LgoExchange lgoExchange = exchangeWithCredentials(true);
    LgoTradeService tradeService = lgoExchange.getTradeService();

    String orderId =
        tradeService.placeMarketOrder(
            new MarketOrder(
                OrderType.ASK, new BigDecimal("2"), CurrencyPair.BTC_USD, null, new Date()));

    System.out.println(orderId);
  }

  @Test
  public void placeEncryptedCancelOrder() throws IOException {
    LgoExchange lgoExchange = exchangeWithCredentials(true);
    LgoTradeService tradeService = lgoExchange.getTradeService();

    tradeService.cancelOrder("157771427343700001");
  }

  // api key and secret key are expected to be in test resources under
  // integration directory
  // this directory is added to .gitignore to avoid committing a real usable key
  protected LgoExchange exchangeWithCredentials(boolean shouldEncryptOrders) throws IOException {
    ExchangeSpecification spec = LgoEnv.sandbox();
    spec.setSecretKey(readResource("/integration/private_key.pem"));
    spec.setApiKey(readResource("/integration/api_key.txt"));
    spec.setExchangeSpecificParametersItem(LgoEnv.SHOULD_ENCRYPT_ORDERS, shouldEncryptOrders);

    return (LgoExchange) ExchangeFactory.INSTANCE.createExchange(spec);
  }

  private String readResource(String path) throws IOException {
    InputStream stream = LgoExchange.class.getResourceAsStream(path);
    return IOUtils.toString(stream, StandardCharsets.UTF_8);
  }
}
