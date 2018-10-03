package org.knowm.xchange.huobi;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Ignore;
import org.junit.Test;
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

@Ignore("Use it for manual launch only")
public class HuobiPrivateApiIntegration {

  @Test
  public void getAccountTest() throws IOException {
    HuobiProperties properties = new HuobiProperties();
    Exchange exchange =
        ExchangeFactory.INSTANCE.createExchange(
            HuobiExchange.class.getName(), properties.getApiKey(), properties.getSecretKey());
    HuobiAccountService accountService = (HuobiAccountService) exchange.getAccountService();
    HuobiAccount[] accounts = accountService.getAccounts();
    System.out.println(Arrays.toString(accounts));
  }

  @Test
  public void getBalanceTest() throws IOException {
    HuobiProperties properties = new HuobiProperties();
    Exchange exchange =
        ExchangeFactory.INSTANCE.createExchange(
            HuobiExchange.class.getName(), properties.getApiKey(), properties.getSecretKey());
    AccountService accountService = exchange.getAccountService();
    Balance balance = accountService.getAccountInfo().getWallet().getBalance(Currency.USDT);
    System.out.println(balance.toString());
    assertThat(balance).isNotNull();
  }

  @Test
  public void getOpenOrdersTest() throws IOException {
    HuobiProperties properties = new HuobiProperties();
    Exchange exchange =
        ExchangeFactory.INSTANCE.createExchange(
            HuobiExchange.class.getName(), properties.getApiKey(), properties.getSecretKey());
    TradeService tradeService = exchange.getTradeService();
    OpenOrders openOrders = tradeService.getOpenOrders();
    System.out.println(openOrders.toString());
    assertThat(openOrders).isNotNull();
  }

  @Test
  public void getOrderTest() throws IOException {
    HuobiProperties properties = new HuobiProperties();
    Exchange exchange =
        ExchangeFactory.INSTANCE.createExchange(
            HuobiExchange.class.getName(), properties.getApiKey(), properties.getSecretKey());
    TradeService tradeService = exchange.getTradeService();
    Collection<Order> orders = tradeService.getOrder("2132866355");
    System.out.println(orders.toString());
    assertThat(orders).isNotNull();
  }

  @Test
  public void placeLimitOrderTest() throws IOException {
    String orderId = placePendingOrder();
    System.out.println(orderId);
  }

  private String placePendingOrder() throws IOException {
    HuobiProperties properties = new HuobiProperties();
    Exchange exchange =
        ExchangeFactory.INSTANCE.createExchange(
            HuobiExchange.class.getName(), properties.getApiKey(), properties.getSecretKey());
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
  public void placeMarketOrderTest() throws IOException {
    HuobiProperties properties = new HuobiProperties();
    Exchange exchange =
        ExchangeFactory.INSTANCE.createExchange(
            HuobiExchange.class.getName(), properties.getApiKey(), properties.getSecretKey());
    TradeService tradeService = exchange.getTradeService();
    HuobiAccountService accountService = (HuobiAccountService) exchange.getAccountService();
    HuobiAccount[] accounts = accountService.getAccounts();
    MarketOrder marketOrder =
        new MarketOrder(
            OrderType.BID,
            new BigDecimal("0.001"),
            new CurrencyPair("BTC", "USDT"),
            String.valueOf(accounts[0].getId()),
            null);
    String orderId = tradeService.placeMarketOrder(marketOrder);
    System.out.println(orderId);
  }

  @Test
  @Ignore("Use it for manual")
  public void cancelOrderTest() throws IOException {
    HuobiProperties properties = new HuobiProperties();
    Exchange exchange =
        ExchangeFactory.INSTANCE.createExchange(
            HuobiExchange.class.getName(), properties.getApiKey(), properties.getSecretKey());
    TradeService tradeService = exchange.getTradeService();
    boolean result = tradeService.cancelOrder("2134551697");
    System.out.println(result);
  }
}
