package org.knowm.xchange.simulated;

import static org.knowm.xchange.currency.Currency.BTC;
import static org.knowm.xchange.currency.Currency.USD;
import static org.knowm.xchange.currency.CurrencyPair.BTC_USD;
import static org.knowm.xchange.dto.Order.OrderType.BID;
import static org.knowm.xchange.simulated.SimulatedExchange.*;

import com.google.common.util.concurrent.RateLimiter;
import java.io.IOException;
import java.math.BigDecimal;
import org.junit.Test;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.exceptions.NonceException;
import org.knowm.xchange.exceptions.SystemOverloadException;
import org.knowm.xchange.service.trade.params.CancelOrderByCurrencyPair;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderByOrderTypeParams;
import org.knowm.xchange.service.trade.params.orders.DefaultQueryOrderParamCurrencyPair;

public class SimulatedExchangeExample {

  /** Demonstrates the simplest case. */
  @Test
  public void simple() throws IOException {

    // If you don't provide an API key you get read-only access. No secret is needed.
    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(SimulatedExchange.class);
    exchangeSpecification.setApiKey("Tester");
    SimulatedExchange exchange =
        (SimulatedExchange) ExchangeFactory.INSTANCE.createExchange(exchangeSpecification);

    // Provide an initial balance and fill the exchange with orders. By default
    // every order book is completely empty.
    exchange.getAccountService().deposit(USD, new BigDecimal(10000));
    exchange.getAccountService().deposit(BTC, new BigDecimal(10000));
    MockMarket.mockMarket(exchange);

    // Accounts
    System.out.println("Account: " + exchange.getAccountService().getAccountInfo());

    // Trades
    exchange
        .getTradeService()
        .placeMarketOrder(
            new MarketOrder.Builder(BID, BTC_USD).originalAmount(new BigDecimal("0.1")).build());

    // Market data
    System.out.println("Ticker: " + exchange.getMarketDataService().getTicker(BTC_USD));
    System.out.println("Order book: " + exchange.getMarketDataService().getOrderBook(BTC_USD));
    System.out.println("Trades: " + exchange.getMarketDataService().getTrades(BTC_USD));
  }

  /** Demonstrates cancelling an order. */
  @Test
  public void cancel() throws IOException {

    // If you don't provide an API key you get read-only access. No secret is needed.
    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(SimulatedExchange.class);
    exchangeSpecification.setApiKey("Tester");
    SimulatedExchange exchange =
        (SimulatedExchange) ExchangeFactory.INSTANCE.createExchange(exchangeSpecification);

    // Provide an initial balance and fill the exchange with orders. By default
    // every order book is completely empty.
    exchange.getAccountService().deposit(USD, new BigDecimal(10000));
    exchange.getAccountService().deposit(BTC, new BigDecimal(10000));
    MockMarket.mockMarket(exchange);

    // Accounts
    System.out.println("Account: " + exchange.getAccountService().getAccountInfo());

    // Trades
    String orderId =
        exchange
            .getTradeService()
            .placeLimitOrder(
                new LimitOrder.Builder(BID, BTC_USD)
                    .originalAmount(new BigDecimal("0.1"))
                    .limitPrice(new BigDecimal("90")) // this wont execute
                    .build());

    // Market data
    System.out.println("Order book: " + exchange.getMarketDataService().getOrderBook(BTC_USD));

    exchange
        .getTradeService() // this tests both getOrder and cancelOrder
        .getOrder(new DefaultQueryOrderParamCurrencyPair(BTC_USD, orderId))
        .stream()
        .forEach(
            order -> {
              try {
                exchange
                    .getTradeService()
                    .cancelOrder(
                        new CancelOrderAllParams(
                            order.getCurrencyPair(), orderId, order.getType()));
              } catch (IOException e) {
                e.printStackTrace();
              }
            });

    System.out.println("Order book: " + exchange.getMarketDataService().getOrderBook(BTC_USD));
  }

  static class CancelOrderAllParams
      implements CancelOrderByCurrencyPair, CancelOrderByIdParams, CancelOrderByOrderTypeParams {
    private CurrencyPair currencyPair;
    private String orderId;
    private Order.OrderType orderType;

    public CancelOrderAllParams(
        CurrencyPair currencyPair, String orderId, Order.OrderType orderType) {
      this.currencyPair = currencyPair;
      this.orderId = orderId;
      this.orderType = orderType;
    }

    @Override
    public String getOrderId() {
      return orderId;
    }

    @Override
    public Order.OrderType getOrderType() {
      return orderType;
    }

    @Override
    public CurrencyPair getCurrencyPair() {
      return currencyPair;
    }
  }

  /** Demonstrates advanced features. */
  @Test
  public void complex() throws IOException {

    // By default, the matching engines are scoped to each instance of the Exchange. This ensures
    // that all instances share the same engine within the scope of each test.
    AccountFactory accountFactory = new AccountFactory();
    MatchingEngineFactory matchingEngineFactory = new MatchingEngineFactory(accountFactory);

    // Access as a market maker user and use *that* to fill the order books
    ExchangeSpecification makerSpec = new ExchangeSpecification(SimulatedExchange.class);
    makerSpec.setApiKey("MarketMaker");
    makerSpec.setExchangeSpecificParametersItem(ENGINE_FACTORY_PARAM, matchingEngineFactory);
    makerSpec.setExchangeSpecificParametersItem(ACCOUNT_FACTORY_PARAM, accountFactory);
    SimulatedExchange makerEx =
        (SimulatedExchange) ExchangeFactory.INSTANCE.createExchange(makerSpec);
    makerEx.getAccountService().deposit(USD, new BigDecimal(10000));
    makerEx.getAccountService().deposit(BTC, new BigDecimal(10000));
    MockMarket.mockMarket(makerEx);

    // Access as a test user. Add realistic transient errors and rate limitation, which
    // we have to handle.
    ExchangeSpecification takerSpec = new ExchangeSpecification(SimulatedExchange.class);
    takerSpec.setApiKey("Tester");
    takerSpec.setExchangeSpecificParametersItem(ENGINE_FACTORY_PARAM, matchingEngineFactory);
    takerSpec.setExchangeSpecificParametersItem(ACCOUNT_FACTORY_PARAM, accountFactory);
    takerSpec.setExchangeSpecificParametersItem(ON_OPERATION_PARAM, new RandomExceptionThrower());
    SimulatedExchange takerEx =
        (SimulatedExchange) ExchangeFactory.INSTANCE.createExchange(takerSpec);
    takerEx.getAccountService().deposit(USD, new BigDecimal(1000));

    // We can now go ahead and interact with the exchange, but now we are forced to obey best
    // practice; we need to obey the rate limit and if we encounter transient exceptions, we
    // need to keep trying.
    RateLimiter rateLimiter = RateLimiter.create(5);

    // Accounts
    retryTransientErrors(
        rateLimiter,
        () -> System.out.println("Account: " + takerEx.getAccountService().getAccountInfo()));

    // Trades
    retryTransientErrors(
        rateLimiter,
        () ->
            takerEx
                .getTradeService()
                .placeMarketOrder(
                    new MarketOrder.Builder(BID, BTC_USD)
                        .originalAmount(new BigDecimal("0.1"))
                        .build()));

    // Market data
    retryTransientErrors(
        rateLimiter,
        () -> System.out.println("Ticker: " + takerEx.getMarketDataService().getTicker(BTC_USD)));
    retryTransientErrors(
        rateLimiter,
        () ->
            System.out.println(
                "Order book: " + takerEx.getMarketDataService().getOrderBook(BTC_USD)));
    retryTransientErrors(
        rateLimiter,
        () -> System.out.println("Trades: " + takerEx.getMarketDataService().getTrades(BTC_USD)));
  }

  private void retryTransientErrors(RateLimiter rateLimiter, IOExceptionThrowingRunnable runnable) {
    while (true) {
      try {
        rateLimiter.acquire();
        runnable.run();
        break;
      } catch (NonceException | SystemOverloadException e) {
        System.out.println("Got a transient error (" + e.getMessage() + ")");
      } catch (IOException e) {
        System.out.println("Got a socket or I/O error (" + e.getMessage() + ")");
      }
    }
  }

  private interface IOExceptionThrowingRunnable {
    void run() throws IOException;
  }
}
