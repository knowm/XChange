package org.knowm.xchange.huobi;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.huobi.dto.account.HuobiAccount;
import org.knowm.xchange.huobi.service.HuobiAccountService;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.TradeService;

class HuobiPrivateApiIntegration {

  private HuobiProperties properties;
  private Exchange exchange;

  @BeforeEach
  void setup() throws IOException {
    properties = new HuobiProperties();
    Assumptions.assumeTrue(properties.isValid(), "Ignore tests because credentials are missing");

    exchange =
        ExchangeFactory.INSTANCE.createExchange(
            HuobiExchange.class, properties.getApiKey(), properties.getSecretKey());
  }

  @AfterEach
  void teardown() throws IOException {
    if (exchange != null) {
      for (LimitOrder order : exchange.getTradeService().getOpenOrders().getOpenOrders()) {
        exchange.getTradeService().cancelOrder(order.getId());
      }
    }
  }

  @Test
  void getAccountTest() throws Exception {
    HuobiAccountService accountService = (HuobiAccountService) exchange.getAccountService();
    HuobiAccount[] accounts = accountService.getAccounts();
    System.out.println(Arrays.toString(accounts));
  }

  @Test
  void getBalanceTest() throws Exception {
    AccountService accountService = exchange.getAccountService();
    Balance balance = accountService.getAccountInfo().getWallet().getBalance(Currency.USDT);
    System.out.println(balance.toString());
    assertThat(balance).isNotNull();
  }

  @Test
  void getOpenOrdersTest() throws Exception {
    TradeService tradeService = exchange.getTradeService();
    OpenOrders openOrders = tradeService.getOpenOrders();
    System.out.println(openOrders.toString());
    assertThat(openOrders).isNotNull();
  }

  @Test
  void getOrderTest() throws Exception {
    TradeService tradeService = exchange.getTradeService();
    Collection<Order> orders = tradeService.getOrder("2132866355");
    System.out.println(orders.toString());
    assertThat(orders).isNotNull();
  }

  @Test
  void placeLimitOrderTest() throws Exception {
    String orderId = placePendingOrder();
    System.out.println(orderId);
  }

  private String placePendingOrder() throws IOException {
    TradeService tradeService = exchange.getTradeService();
    HuobiAccountService accountService = (HuobiAccountService) exchange.getAccountService();
    HuobiAccount[] accounts = accountService.getAccounts();
    LimitOrder limitOrder =
        new LimitOrder(
            OrderType.BID,
            new BigDecimal("0.001"),
            new CurrencyPair("BTC", "USDT"),
            String.valueOf(accounts[0].getId()),
            null,
            new BigDecimal("10000"));
    return tradeService.placeLimitOrder(limitOrder);
  }

  @Test
  void placeMarketOrderTest() throws Exception {
    TradeService tradeService = exchange.getTradeService();
    HuobiAccountService accountService = (HuobiAccountService) exchange.getAccountService();
    HuobiAccount[] accounts = accountService.getAccounts();
    MarketOrder marketOrder =
        new MarketOrder(
            OrderType.ASK,
            new BigDecimal("0.0002"),
            new CurrencyPair("BTC", "USDT"),
            String.valueOf(accounts[0].getId()),
            null);
    String orderId = tradeService.placeMarketOrder(marketOrder);
    System.out.println(orderId);
  }

  @Test
  @Disabled("Use it for manual")
  void cancelOrderTest() throws Exception {
    TradeService tradeService = exchange.getTradeService();
    boolean result = tradeService.cancelOrder("2134551697");
    System.out.println(result);
  }
}
