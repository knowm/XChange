package com.xeiam.xchange.bleutrade.service.polling;

import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.bleutrade.BleutradeAuthenticated;
import com.xeiam.xchange.bleutrade.BleutradeException;
import com.xeiam.xchange.bleutrade.BleutradeExchange;
import com.xeiam.xchange.bleutrade.dto.trade.BleutradeCancelOrderReturn;
import com.xeiam.xchange.bleutrade.dto.trade.BleutradeOpenOrdersReturn;
import com.xeiam.xchange.bleutrade.dto.trade.BleutradeOrderId;
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

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.fail;
import static org.powermock.api.mockito.PowerMockito.mock;

@RunWith(PowerMockRunner.class)
public class BleutradeTradeServiceTest extends BleutradeServiceTestSupport {

  private BleutradeTradeService tradeService;

  @Before
  public void setUp() throws Exception {
    BleutradeExchange exchange = (BleutradeExchange) ExchangeFactory.INSTANCE.createExchange(BleutradeExchange.class.getCanonicalName());
    exchange.getExchangeSpecification().setUserName("admin");
    exchange.getExchangeSpecification().setApiKey("publicKey");
    exchange.getExchangeSpecification().setSecretKey("secretKey");

    tradeService = new BleutradeTradeService(exchange);
  }

  @Test
  public void constructor() throws Exception {
    assertThat(Whitebox.getInternalState(tradeService, "apiKey")).isEqualTo("publicKey");
  }

  @Test
  public void shouldGetOpenOrders() throws Exception {
    // given
    BleutradeOpenOrdersReturn openOrdersReturn = new BleutradeOpenOrdersReturn();
    openOrdersReturn.setSuccess(true);
    openOrdersReturn.setMessage("test message");
    openOrdersReturn.setResult(Arrays.asList(
        createBleutradeOpenOrder("65489", "LTC_BTC", "BUY", new BigDecimal("20.00000000"), new BigDecimal("5.00000000"), "0.16549400", new BigDecimal("0.01268311"), "OPEN", "2014-08-03 13:55:20", "My optional comment, eg function id #123"),
        createBleutradeOpenOrder("65724", "DOGE_BTC", "SELL", new BigDecimal("150491.98700000"), new BigDecimal("795.00000000"), "0.04349400", new BigDecimal("0.00000055"), "OPEN", "2014-07-29 18:45:17", "Function #123 Connect #456")
    ));

    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.getOrders(Mockito.eq("publicKey"), Mockito.any(ParamsDigest.class), Mockito.any(SynchronizedValueFactory.class))).thenReturn(openOrdersReturn);
    Whitebox.setInternalState(tradeService, "bleutrade", bleutrade);

    // when
    OpenOrders openOrders = tradeService.getOpenOrders();

    // then
    assertThat(openOrders.getOpenOrders()).hasSize(2);
    assertThat(openOrders.getOpenOrders().get(0).toString()).isEqualTo(
        "LimitOrder [limitPrice=0.01268311, Order [type=BID, tradableAmount=5.00000000, currencyPair=LTC/BTC, id=65489, timestamp=null]]");
    assertThat(openOrders.getOpenOrders().get(1).toString()).isEqualTo(
        "LimitOrder [limitPrice=5.5E-7, Order [type=ASK, tradableAmount=795.00000000, currencyPair=DOGE/BTC, id=65724, timestamp=null]]");
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnUnsuccessfulGetOpenOrders() throws Exception {
    // given
    BleutradeOpenOrdersReturn openOrdersReturn = new BleutradeOpenOrdersReturn();
    openOrdersReturn.setSuccess(false);
    openOrdersReturn.setMessage("test message");
    openOrdersReturn.setResult(Arrays.asList(
        createBleutradeOpenOrder("65489", "LTC_BTC", "BUY", new BigDecimal("20.00000000"), new BigDecimal("5.00000000"), "0.16549400", new BigDecimal("0.01268311"), "OPEN", "2014-08-03 13:55:20",
            "My optional comment, eg function id #123"),
        createBleutradeOpenOrder("65724", "DOGE_BTC", "SELL", new BigDecimal("150491.98700000"), new BigDecimal("795.00000000"), "0.04349400", new BigDecimal("0.00000055"), "OPEN",
            "2014-07-29 18:45:17", "Function #123 Connect #456")));

    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.getOrders(Mockito.eq("publicKey"), Mockito.any(ParamsDigest.class), Mockito.any(SynchronizedValueFactory.class))).thenReturn(openOrdersReturn);
    Whitebox.setInternalState(tradeService, "bleutrade", bleutrade);

    // when
    tradeService.getOpenOrders();

    // then
    fail("BleutradeAccountService should throw ExchangeException when open orders request was unsuccessful");
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnGetOpenOrdersError() throws Exception {
    // given
    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.getOrders(Mockito.eq("publicKey"), Mockito.any(ParamsDigest.class), Mockito.any(SynchronizedValueFactory.class))).thenThrow(BleutradeException.class);
    Whitebox.setInternalState(tradeService, "bleutrade", bleutrade);

    // when
    tradeService.getOpenOrders();

    // then
    fail("BleutradeAccountService should throw ExchangeException when open orders request throw error");
  }

  @Test(expected = NotAvailableFromExchangeException.class)
  public void shouldFailOnPlaceMarketOrder() throws Exception {
    // when
    tradeService.placeMarketOrder(new MarketOrder(Order.OrderType.ASK, new BigDecimal("10.00000000"), CurrencyPair.BTC_AUD));

    // then
    fail("BleutradeAccountService should throw NotAvailableFromExchangeException when placeMarketOrder is called");
  }


  @Test
  public void shouldProcessPlaceLimitOrder() throws Exception {
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
    PowerMockito.when(bleutrade.buyLimit(Mockito.eq("publicKey"), Mockito.any(ParamsDigest.class), Mockito.any(SynchronizedValueFactory.class), Mockito.eq("BTC_AUD"), Mockito.eq("10.00000000"),
        Mockito.eq("1.1"))).thenReturn(placeBuyOrderReturn);
    PowerMockito.when(bleutrade.sellLimit(Mockito.eq("publicKey"), Mockito.any(ParamsDigest.class), Mockito.any(SynchronizedValueFactory.class), Mockito.eq("BTC_AUD"), Mockito.eq("20.00000000"),
        Mockito.eq("2.2"))).thenReturn(placeSellOrderReturn);
    Whitebox.setInternalState(tradeService, "bleutrade", bleutrade);

    // when
    String placeBuyLimitOrder = tradeService.placeLimitOrder(new LimitOrder(Order.OrderType.BID, new BigDecimal("10.00000000"), CurrencyPair.BTC_AUD, "", null, new BigDecimal("1.1")));
    String placeSellLimitOrder = tradeService.placeLimitOrder(new LimitOrder(Order.OrderType.ASK, new BigDecimal("20.00000000"), CurrencyPair.BTC_AUD, "", null, new BigDecimal("2.2")));

    // then
    assertThat(placeBuyLimitOrder).isEqualTo("11111");
    assertThat(placeSellLimitOrder).isEqualTo("22222");
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnUnsuccessfulPlaceBuyLimitOrder() throws Exception {
    // given
    BleutradePlaceOrderReturn placeBuyOrderReturn = new BleutradePlaceOrderReturn();
    placeBuyOrderReturn.setSuccess(false);
    placeBuyOrderReturn.setMessage("test message");
    placeBuyOrderReturn.setResult(createBleutradeOrderId("11111"));

    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.buyLimit(Mockito.eq("publicKey"), Mockito.any(ParamsDigest.class), Mockito.any(SynchronizedValueFactory.class), Mockito.eq("BTC_AUD"), Mockito.eq("10.00000000"),
        Mockito.eq("1.1"))).thenReturn(placeBuyOrderReturn);
    Whitebox.setInternalState(tradeService, "bleutrade", bleutrade);

    // when
    tradeService.placeLimitOrder(new LimitOrder(Order.OrderType.BID, new BigDecimal("10.00000000"), CurrencyPair.BTC_AUD, "", null, new BigDecimal("1.1")));

    // then
    fail("BleutradeAccountService should throw ExchangeException on unsuccessful place buy limit order request");
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnUnsuccessfulPlaceSellLimitOrder() throws Exception {
    // given
    BleutradePlaceOrderReturn placeSellOrderReturn = new BleutradePlaceOrderReturn();
    placeSellOrderReturn.setSuccess(false);
    placeSellOrderReturn.setMessage("test message");
    placeSellOrderReturn.setResult(createBleutradeOrderId("22222"));

    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.sellLimit(Mockito.eq("publicKey"), Mockito.any(ParamsDigest.class), Mockito.any(SynchronizedValueFactory.class), Mockito.eq("BTC_AUD"), Mockito.eq("20.00000000"),
        Mockito.eq("2.2"))).thenReturn(placeSellOrderReturn);
    Whitebox.setInternalState(tradeService, "bleutrade", bleutrade);

    // when
    tradeService.placeLimitOrder(new LimitOrder(Order.OrderType.ASK, new BigDecimal("20.00000000"), CurrencyPair.BTC_AUD, "", null, new BigDecimal("2.2")));

    // then
    fail("BleutradeAccountService should throw ExchangeException on unsuccessful place sell limit order request");
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnPlaceBuyLimitOrderError() throws Exception {
    // given
    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.buyLimit(Mockito.eq("publicKey"), Mockito.any(ParamsDigest.class), Mockito.any(SynchronizedValueFactory.class), Mockito.eq("BTC_AUD"), Mockito.eq("10.00000000"),
        Mockito.eq("1.1"))).thenThrow(BleutradeException.class);
    Whitebox.setInternalState(tradeService, "bleutrade", bleutrade);

    // when
    tradeService.placeLimitOrder(new LimitOrder(Order.OrderType.BID, new BigDecimal("10.00000000"), CurrencyPair.BTC_AUD, "", null, new BigDecimal("1.1")));

    // then
    fail("BleutradeAccountService should throw ExchangeException when place buy limit order request throw error");
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnPlaceSellLimitOrderError() throws Exception {
    // given
    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.sellLimit(Mockito.eq("publicKey"), Mockito.any(ParamsDigest.class), Mockito.any(SynchronizedValueFactory.class), Mockito.eq("BTC_AUD"), Mockito.eq("20.00000000"),
        Mockito.eq("2.2"))).thenThrow(BleutradeException.class);
    Whitebox.setInternalState(tradeService, "bleutrade", bleutrade);

    // when
    tradeService.placeLimitOrder(new LimitOrder(Order.OrderType.ASK, new BigDecimal("20.00000000"), CurrencyPair.BTC_AUD, "", null, new BigDecimal("2.2")));

    // then
    fail("BleutradeAccountService should throw ExchangeException when place sell limit order request throw error");
  }

  @Test
  public void shouldProcessCancelOrder() throws Exception {
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
    PowerMockito.when(bleutrade.cancel(Mockito.eq("publicKey"), Mockito.any(ParamsDigest.class), Mockito.any(SynchronizedValueFactory.class), Mockito.eq("12345"))).thenReturn(cancelOrderReturnPassed);
    PowerMockito.when(bleutrade.cancel(Mockito.eq("publicKey"), Mockito.any(ParamsDigest.class), Mockito.any(SynchronizedValueFactory.class), Mockito.eq("11111"))).thenReturn(cancelOrderReturnFailed);
    Whitebox.setInternalState(tradeService, "bleutrade", bleutrade);

    // when
    boolean passed1 = tradeService.cancelOrder("12345");
    boolean passed2 = tradeService.cancelOrder("11111");

    // then
    assertThat(passed1).isTrue();
    assertThat(passed2).isFalse();
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailCancelOrderError() throws Exception {
    // given
    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.cancel(Mockito.eq("publicKey"), Mockito.any(ParamsDigest.class), Mockito.any(SynchronizedValueFactory.class), Mockito.eq("12345"))).thenThrow(BleutradeException.class);
    Whitebox.setInternalState(tradeService, "bleutrade", bleutrade);

    // when
    tradeService.cancelOrder("12345");

    // then
    fail("BleutradeAccountService should throw ExchangeException on cancel order request error");
  }

  @Test(expected = NotAvailableFromExchangeException.class)
  public void shouldFailOnGetTradeHistory() throws Exception {
    // when
    tradeService.getTradeHistory(new DefaultTradeHistoryParamCurrencyPair(CurrencyPair.BTC_AUD));

    // then
    fail("BleutradeAccountService should throw NotAvailableFromExchangeException when getTradeHistory is called");
  }

  @Test(expected = NotAvailableFromExchangeException.class)
  public void shouldFailOnCreateTradeHistoryParams() throws Exception {
    // when
    tradeService.createTradeHistoryParams();

    // then
    fail("BleutradeAccountService should throw NotAvailableFromExchangeException when createTradeHistoryParams is called");
  }
}
