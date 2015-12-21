package com.xeiam.xchange.bleutrade.service.polling;

import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bleutrade.BleutradeAuthenticated;
import com.xeiam.xchange.bleutrade.BleutradeException;
import com.xeiam.xchange.bleutrade.BleutradeExchange;
import com.xeiam.xchange.bleutrade.dto.trade.BleutradeCancelOrderReturn;
import com.xeiam.xchange.bleutrade.dto.trade.BleutradeOpenOrdersReturn;
import com.xeiam.xchange.bleutrade.dto.trade.BleutradePlaceOrderReturn;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.service.polling.trade.params.DefaultTradeHistoryParamCurrencyPair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.fail;
import static org.powermock.api.mockito.PowerMockito.mock;

@RunWith(PowerMockRunner.class)
public class BleutradeTradeServiceTest extends BleutradeServiceTestSupport {

  private BleutradeTradeService tradeService;

  @Before
  public void setUp() {
    BleutradeExchange exchange = (BleutradeExchange) ExchangeFactory.INSTANCE.createExchange(BleutradeExchange.class.getCanonicalName());
    ExchangeSpecification specification = exchange.getExchangeSpecification();
    specification.setUserName(SPECIFICATION_USERNAME);
    specification.setApiKey(SPECIFICATION_API_KEY);
    specification.setSecretKey(SPECIFICATION_SECRET_KEY);

    tradeService = new BleutradeTradeService(exchange);
  }

  @Test
  public void constructor() {
    assertThat(Whitebox.getInternalState(tradeService, "apiKey")).isEqualTo(SPECIFICATION_API_KEY);
  }

  @Test
  public void shouldGetOpenOrders() throws IOException {
    // given
    BleutradeOpenOrdersReturn openOrdersReturn = new BleutradeOpenOrdersReturn();
    openOrdersReturn.setSuccess(true);
    openOrdersReturn.setMessage("test message");
    openOrdersReturn.setResult(BLEUTRADE_OPEN_ORDERS_LIST);

    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.getOrders(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
        Mockito.any(SynchronizedValueFactory.class))).thenReturn(openOrdersReturn);
    Whitebox.setInternalState(tradeService, "bleutrade", bleutrade);

    // when
    OpenOrders openOrders = tradeService.getOpenOrders();
    List<LimitOrder> ordersList = openOrders.getOpenOrders();

    // then
    assertThat(ordersList).hasSize(BLEUTRADE_OPEN_ORDERS_LIST.size());
    assertThat(ordersList.get(0).toString()).isEqualTo(OPEN_ORDERS_STR[0]);
    assertThat(ordersList.get(1).toString()).isEqualTo(OPEN_ORDERS_STR[1]);
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnUnsuccessfulGetOpenOrders() throws IOException {
    // given
    BleutradeOpenOrdersReturn openOrdersReturn = new BleutradeOpenOrdersReturn();
    openOrdersReturn.setSuccess(false);
    openOrdersReturn.setMessage("test message");
    openOrdersReturn.setResult(BLEUTRADE_OPEN_ORDERS_LIST);

    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.getOrders(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
        Mockito.any(SynchronizedValueFactory.class))).thenReturn(openOrdersReturn);
    Whitebox.setInternalState(tradeService, "bleutrade", bleutrade);

    // when
    tradeService.getOpenOrders();

    // then
    fail("BleutradeAccountService should throw ExchangeException when open orders request was unsuccessful");
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnGetOpenOrdersError() throws IOException {
    // given
    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.getOrders(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
        Mockito.any(SynchronizedValueFactory.class))).thenThrow(BleutradeException.class);
    Whitebox.setInternalState(tradeService, "bleutrade", bleutrade);

    // when
    tradeService.getOpenOrders();

    // then
    fail("BleutradeAccountService should throw ExchangeException when open orders request throw error");
  }

  @Test(expected = NotAvailableFromExchangeException.class)
  public void shouldFailOnPlaceMarketOrder() throws IOException {
    // when
    tradeService.placeMarketOrder(new MarketOrder(Order.OrderType.ASK, BigDecimal.TEN, CurrencyPair.BTC_AUD));

    // then
    fail("BleutradeAccountService should throw NotAvailableFromExchangeException when placeMarketOrder is called");
  }


  @Test
  public void shouldProcessPlaceLimitOrder() throws IOException {
    // given
    BleutradePlaceOrderReturn placeBuyOrderReturn = new BleutradePlaceOrderReturn();
    placeBuyOrderReturn.setSuccess(true);
    placeBuyOrderReturn.setMessage("test message");
    placeBuyOrderReturn.setResult(createBleutradeOrderId("11111"));

    BleutradePlaceOrderReturn placeSellOrderReturn = new BleutradePlaceOrderReturn();
    placeSellOrderReturn.setSuccess(true);
    placeSellOrderReturn.setMessage("test message");
    placeSellOrderReturn.setResult(createBleutradeOrderId("22222"));

    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.buyLimit(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
        Mockito.any(SynchronizedValueFactory.class), Mockito.eq("BTC_AUD"), Mockito.eq("10.00000000"),
        Mockito.eq("1.1"))).thenReturn(placeBuyOrderReturn);
    PowerMockito.when(bleutrade.sellLimit(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
        Mockito.any(SynchronizedValueFactory.class), Mockito.eq("BTC_AUD"), Mockito.eq("20.00000000"),
        Mockito.eq("2.2"))).thenReturn(placeSellOrderReturn);
    Whitebox.setInternalState(tradeService, "bleutrade", bleutrade);

    // when
    String placeBuyLimitOrder = tradeService.placeLimitOrder(PLACED_ORDERS[0]);
    String placeSellLimitOrder = tradeService.placeLimitOrder(PLACED_ORDERS[1]);

    // then
    assertThat(placeBuyLimitOrder).isEqualTo("11111");
    assertThat(placeSellLimitOrder).isEqualTo("22222");
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnUnsuccessfulPlaceBuyLimitOrder() throws IOException {
    // given
    BleutradePlaceOrderReturn placeBuyOrderReturn = new BleutradePlaceOrderReturn();
    placeBuyOrderReturn.setSuccess(false);
    placeBuyOrderReturn.setMessage("test message");
    placeBuyOrderReturn.setResult(createBleutradeOrderId("11111"));

    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.buyLimit(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
        Mockito.any(SynchronizedValueFactory.class), Mockito.eq("BTC_AUD"), Mockito.eq("10.00000000"),
        Mockito.eq("1.1"))).thenReturn(placeBuyOrderReturn);
    Whitebox.setInternalState(tradeService, "bleutrade", bleutrade);

    // when
    tradeService.placeLimitOrder(PLACED_ORDERS[0]);

    // then
    fail("BleutradeAccountService should throw ExchangeException on unsuccessful place buy limit order request");
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnUnsuccessfulPlaceSellLimitOrder() throws IOException {
    // given
    BleutradePlaceOrderReturn placeSellOrderReturn = new BleutradePlaceOrderReturn();
    placeSellOrderReturn.setSuccess(false);
    placeSellOrderReturn.setMessage("test message");
    placeSellOrderReturn.setResult(createBleutradeOrderId("22222"));

    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.sellLimit(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
        Mockito.any(SynchronizedValueFactory.class), Mockito.eq("BTC_AUD"), Mockito.eq("20.00000000"),
        Mockito.eq("2.2"))).thenReturn(placeSellOrderReturn);
    Whitebox.setInternalState(tradeService, "bleutrade", bleutrade);

    // when
    tradeService.placeLimitOrder(PLACED_ORDERS[1]);

    // then
    fail("BleutradeAccountService should throw ExchangeException on unsuccessful place sell limit order request");
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnPlaceBuyLimitOrderError() throws IOException {
    // given
    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.buyLimit(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
        Mockito.any(SynchronizedValueFactory.class), Mockito.eq("BTC_AUD"), Mockito.eq("10.00000000"),
        Mockito.eq("1.1"))).thenThrow(BleutradeException.class);
    Whitebox.setInternalState(tradeService, "bleutrade", bleutrade);

    // when
    tradeService.placeLimitOrder(PLACED_ORDERS[0]);

    // then
    fail("BleutradeAccountService should throw ExchangeException when place buy limit order request throw error");
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnPlaceSellLimitOrderError() throws IOException {
    // given
    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.sellLimit(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
        Mockito.any(SynchronizedValueFactory.class), Mockito.eq("BTC_AUD"), Mockito.eq("20.00000000"),
        Mockito.eq("2.2"))).thenThrow(BleutradeException.class);
    Whitebox.setInternalState(tradeService, "bleutrade", bleutrade);

    // when
    tradeService.placeLimitOrder(PLACED_ORDERS[1]);

    // then
    fail("BleutradeAccountService should throw ExchangeException when place sell limit order request throw error");
  }

  @Test
  public void shouldProcessCancelOrder() throws IOException {
    // given
    BleutradeCancelOrderReturn cancelOrderReturnPassed = new BleutradeCancelOrderReturn();
    cancelOrderReturnPassed.setSuccess(true);
    cancelOrderReturnPassed.setMessage("test message");
    cancelOrderReturnPassed.setResult(Collections.singletonList("12345"));

    BleutradeCancelOrderReturn cancelOrderReturnFailed = new BleutradeCancelOrderReturn();
    cancelOrderReturnFailed.setSuccess(false);
    cancelOrderReturnFailed.setMessage("test message");
    cancelOrderReturnFailed.setResult(Collections.singletonList("11111"));


    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.cancel(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
        Mockito.any(SynchronizedValueFactory.class), Mockito.eq("12345"))).thenReturn(cancelOrderReturnPassed);
    PowerMockito.when(bleutrade.cancel(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
        Mockito.any(SynchronizedValueFactory.class), Mockito.eq("11111"))).thenReturn(cancelOrderReturnFailed);
    Whitebox.setInternalState(tradeService, "bleutrade", bleutrade);

    // when
    boolean passed1 = tradeService.cancelOrder("12345");
    boolean passed2 = tradeService.cancelOrder("11111");

    // then
    assertThat(passed1).isTrue();
    assertThat(passed2).isFalse();
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailCancelOrderError() throws IOException {
    // given
    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.cancel(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
        Mockito.any(SynchronizedValueFactory.class), Mockito.eq("12345"))).thenThrow(BleutradeException.class);
    Whitebox.setInternalState(tradeService, "bleutrade", bleutrade);

    // when
    tradeService.cancelOrder("12345");

    // then
    fail("BleutradeAccountService should throw ExchangeException on cancel order request error");
  }

  @Test(expected = NotAvailableFromExchangeException.class)
  public void shouldFailOnGetTradeHistory() throws IOException {
    // when
    tradeService.getTradeHistory(new DefaultTradeHistoryParamCurrencyPair(CurrencyPair.BTC_AUD));

    // then
    fail("BleutradeAccountService should throw NotAvailableFromExchangeException when getTradeHistory is called");
  }

  @Test(expected = NotAvailableFromExchangeException.class)
  public void shouldFailOnCreateTradeHistoryParams() {
    // when
    tradeService.createTradeHistoryParams();

    // then
    fail("BleutradeAccountService should throw NotAvailableFromExchangeException when createTradeHistoryParams is called");
  }
}
