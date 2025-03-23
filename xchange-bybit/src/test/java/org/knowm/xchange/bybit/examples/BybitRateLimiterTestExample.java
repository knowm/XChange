package org.knowm.xchange.bybit.examples;

import static org.knowm.xchange.Exchange.USE_SANDBOX;
import static org.knowm.xchange.bybit.BybitExchange.SPECIFIC_PARAM_ACCOUNT_TYPE;
import static org.knowm.xchange.bybit.BybitResilience.GLOBAL_RATE_LIMITER;
import static org.knowm.xchange.bybit.BybitResilience.ORDER_AMEND_LINEAR_AND_INVERSE_RATE_LIMITER;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bybit.BybitExchange;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.bybit.dto.account.walletbalance.BybitAccountType;
import org.knowm.xchange.bybit.service.BybitAccountService;
import org.knowm.xchange.bybit.service.BybitTradeService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.instrument.Instrument;

public class BybitRateLimiterTestExample {

  public static void main(String[] args) {
    try {
      testRateLimits();
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  static Instrument ETH_USDT_PERP = new FuturesContract(new CurrencyPair("ETH/USDT"), "PERP");

  private static void testRateLimits() throws IOException, InterruptedException {
    SimpleDateFormat sdf = new SimpleDateFormat("mm-ss-SSS");
    ExchangeSpecification exchangeSpecification =
        new BybitExchange().getDefaultExchangeSpecification();
    exchangeSpecification.setApiKey(System.getProperty("test_api_key"));
    exchangeSpecification.setSecretKey(System.getProperty("test_secret_key"));
    exchangeSpecification.setExchangeSpecificParametersItem(
        SPECIFIC_PARAM_ACCOUNT_TYPE, BybitAccountType.UNIFIED);
    exchangeSpecification.setExchangeSpecificParametersItem(USE_SANDBOX, true);
    exchangeSpecification.getResilience().setRetryEnabled(true);
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(exchangeSpecification);

    Ticker tickerETH_USDT_PERP = exchange.getMarketDataService().getTicker(ETH_USDT_PERP);
    LimitOrder limitOrderFuture = createOrder("", tickerETH_USDT_PERP.getHigh());
    BybitAccountService bybitAccountService = (BybitAccountService) exchange.getAccountService();
    bybitAccountService.switchPositionMode(BybitCategory.LINEAR, ETH_USDT_PERP, "USDT", 0);
    String limitFutureOrderId = exchange.getTradeService().placeLimitOrder(limitOrderFuture);
    BybitTradeService bybitTradeService = (BybitTradeService) exchange.getTradeService();
    exchange
        .getResilienceRegistries()
        .rateLimiters()
        .rateLimiter(GLOBAL_RATE_LIMITER)
        .getEventPublisher()
        .onSuccess(
            event ->
                System.out.println(
                    sdf.format(new Date())
                        + " event type "
                        + event.getEventType()
                        + " availablePermissions: "
                        + exchange
                            .getResilienceRegistries()
                            .rateLimiters()
                            .rateLimiter(ORDER_AMEND_LINEAR_AND_INVERSE_RATE_LIMITER)
                            .getMetrics()
                            .getAvailablePermissions()))
        .onFailure(
            event ->
                System.out.println(
                    sdf.format(new Date())
                        + " event type "
                        + event.getEventType()
                        + " availablePermissions: "
                        + exchange
                            .getResilienceRegistries()
                            .rateLimiters()
                            .rateLimiter(ORDER_AMEND_LINEAR_AND_INVERSE_RATE_LIMITER)
                            .getMetrics()
                            .getAvailablePermissions()));
    System.out.println(
        sdf.format(new Date())
            + " RateLimiterEnabled: "
            + exchange.getExchangeSpecification().getResilience().isRateLimiterEnabled());
    ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);
    for (int i = 0; i < 8; i++) {
      int finalI = i;
      System.out.println(
          sdf.format(new Date())
              + " Amend order, availablePermissions: "
              + exchange
                  .getResilienceRegistries()
                  .rateLimiters()
                  .rateLimiter(ORDER_AMEND_LINEAR_AND_INVERSE_RATE_LIMITER)
                  .getMetrics()
                  .getAvailablePermissions());
      Thread.sleep(1);
      executor.execute(
          () -> {
            try {
              String result =
                  bybitTradeService.changeOrder(
                      createOrder(
                          limitFutureOrderId,
                          tickerETH_USDT_PERP.getHigh().add(new BigDecimal("0.1" + finalI))));
              System.out.println(sdf.format(new Date()) + " amend order # " + result);
            } catch (IOException e) {
              System.out.println(sdf.format(new Date()) + " IOException " + e.getMessage());
            }
          });
    }
    System.out.println(sdf.format(new Date()) + " end");
  }

  private static LimitOrder createOrder(String orderId, BigDecimal price) throws IOException {
    return new LimitOrder(
        OrderType.ASK, new BigDecimal("0.05"), ETH_USDT_PERP, orderId, null, price);
  }
}
