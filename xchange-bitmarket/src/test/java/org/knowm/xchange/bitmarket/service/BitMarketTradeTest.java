package org.knowm.xchange.bitmarket.service;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.fail;
import static org.powermock.api.mockito.PowerMockito.mock;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitmarket.BitMarketAssert;
import org.knowm.xchange.bitmarket.BitMarketAuthenticated;
import org.knowm.xchange.bitmarket.BitMarketExchange;
import org.knowm.xchange.bitmarket.BitMarketTestSupport;
import org.knowm.xchange.bitmarket.dto.BitMarketAPILimit;
import org.knowm.xchange.bitmarket.dto.account.BitMarketBalance;
import org.knowm.xchange.bitmarket.dto.trade.BitMarketCancelResponse;
import org.knowm.xchange.bitmarket.dto.trade.BitMarketHistoryOperationsResponse;
import org.knowm.xchange.bitmarket.dto.trade.BitMarketHistoryTradesResponse;
import org.knowm.xchange.bitmarket.dto.trade.BitMarketOrder;
import org.knowm.xchange.bitmarket.dto.trade.BitMarketOrdersResponse;
import org.knowm.xchange.bitmarket.dto.trade.BitMarketTrade;
import org.knowm.xchange.bitmarket.dto.trade.BitMarketTradeResponse;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

@RunWith(PowerMockRunner.class)
public class BitMarketTradeTest extends BitMarketTestSupport {

  private BitMarketTradeService tradeService;

  @Before
  public void setUp() {
    BitMarketExchange exchange = (BitMarketExchange) ExchangeFactory.INSTANCE.createExchange(BitMarketExchange.class.getCanonicalName());
    ExchangeSpecification specification = exchange.getExchangeSpecification();
    specification.setUserName(SPECIFICATION_USERNAME);
    specification.setApiKey(SPECIFICATION_API_KEY);
    specification.setSecretKey(SPECIFICATION_SECRET_KEY);

    tradeService = new BitMarketTradeService(exchange);
  }

  @Test
  public void constructor() {
    assertThat(Whitebox.getInternalState(tradeService, "apiKey")).isEqualTo(SPECIFICATION_API_KEY);
  }

  @Test(expected = NotAvailableFromExchangeException.class)
  public void shouldFailOnPlaceMarketOrder() throws IOException {
    // given
    MarketOrder marketOrder = new MarketOrder(Order.OrderType.ASK, BigDecimal.TEN, CurrencyPair.BTC_USD, new Date());

    // when
    tradeService.placeMarketOrder(marketOrder);

    // then
    fail("BitMarketTradeService should throw NotAvailableFromExchangeException when call placeMarketOrder");
  }

  // should be changed after order type convertion fix
  @Test
  public void shouldPlaceBuyLimitOrder() throws IOException {
    // given
    BitMarketTradeResponse responseBuy = new BitMarketTradeResponse(true,
        new BitMarketTrade(12345, new BitMarketOrder(12345, "BTCAUD", BigDecimal.ONE, new BigDecimal("1.1"), BigDecimal.ZERO, "BUY", 1234567890L),
            new BitMarketBalance(createAvailable(), createBlocked())),
        new BitMarketAPILimit(3, 100, 12345000L), 0, null);

    BitMarketAuthenticated bitMarketAuthenticated = mock(BitMarketAuthenticated.class);
    PowerMockito.when(bitMarketAuthenticated.trade(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
        Mockito.any(SynchronizedValueFactory.class), Mockito.eq("BTCAUD"), Mockito.eq("buy"), Mockito.eq(BigDecimal.ONE), Mockito.eq(BigDecimal.TEN)))
        .thenReturn(responseBuy);
    Whitebox.setInternalState(tradeService, "bitMarketAuthenticated", bitMarketAuthenticated);

    // when
    String placedBuy = tradeService
        .placeLimitOrder(new LimitOrder(Order.OrderType.BID, BigDecimal.ONE, CurrencyPair.BTC_AUD, "12345", null, BigDecimal.TEN));

    // then
    assertThat(placedBuy).isEqualTo("12345");
  }

  // should be changed after order type convertion fix
  @Test
  public void shouldPlaceSellLimitOrder() throws IOException {
    // given
    BitMarketTradeResponse responseSell = new BitMarketTradeResponse(true,
        new BitMarketTrade(11111, new BitMarketOrder(11111, "BTCAUD", BigDecimal.ONE, new BigDecimal("2.2"), BigDecimal.TEN, "SELL", 1234567999L),
            new BitMarketBalance(createAvailable(), createBlocked())),
        new BitMarketAPILimit(3, 100, 12345000L), 0, null);

    BitMarketAuthenticated bitMarketAuthenticated = mock(BitMarketAuthenticated.class);
    PowerMockito.when(
        bitMarketAuthenticated.trade(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class), Mockito.any(SynchronizedValueFactory.class),
            Mockito.eq("BTCAUD"), Mockito.eq("sell"), Mockito.eq(BigDecimal.ONE), Mockito.eq(BigDecimal.TEN)))
        .thenReturn(responseSell);
    Whitebox.setInternalState(tradeService, "bitMarketAuthenticated", bitMarketAuthenticated);

    // when
    String placedSell = tradeService
        .placeLimitOrder(new LimitOrder(Order.OrderType.ASK, BigDecimal.ONE, CurrencyPair.BTC_AUD, "11111", null, BigDecimal.TEN));

    // then
    assertThat(placedSell).isEqualTo("11111");
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnUnsuccessfulLimitOrder() throws IOException {
    // given
    BitMarketTradeResponse response = new BitMarketTradeResponse(false, null, new BitMarketAPILimit(3, 100, 12345000L), 502, "Invalid message hash");

    BitMarketAuthenticated bitMarketAuthenticated = mock(BitMarketAuthenticated.class);
    PowerMockito.when(
        bitMarketAuthenticated.trade(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class), Mockito.any(SynchronizedValueFactory.class),
            Mockito.eq("BTCAUD"), Mockito.eq("sell"), Mockito.eq(BigDecimal.ONE), Mockito.eq(BigDecimal.TEN)))
        .thenReturn(response);
    Whitebox.setInternalState(tradeService, "bitMarketAuthenticated", bitMarketAuthenticated);

    // when
    tradeService.placeLimitOrder(new LimitOrder(Order.OrderType.ASK, BigDecimal.ONE, CurrencyPair.BTC_AUD, "12345", null, BigDecimal.TEN));

    // then
    fail("BitMarketTradeService should throw ExchangeException when place limit order request was unsuccessful");
  }

  @Test
  public void shouldCancelOrder() throws IOException {
    // given
    BitMarketCancelResponse response = new BitMarketCancelResponse(true, new BitMarketBalance(createAvailable(), createBlocked()),
        new BitMarketAPILimit(3, 100, 12345000L), 0, null);

    BitMarketAuthenticated bitMarketAuthenticated = mock(BitMarketAuthenticated.class);
    PowerMockito.when(bitMarketAuthenticated.cancel(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
        Mockito.any(SynchronizedValueFactory.class), Mockito.eq(12345L))).thenReturn(response);
    Whitebox.setInternalState(tradeService, "bitMarketAuthenticated", bitMarketAuthenticated);

    // when
    boolean cancelled = tradeService.cancelOrder("12345");

    // then
    assertThat(cancelled).isTrue();
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnUnsuccessfulCancelOrder() throws IOException {
    // given
    BitMarketCancelResponse response = new BitMarketCancelResponse(false, null, new BitMarketAPILimit(3, 100, 12345000L), 502,
        "Invalid message hash");

    BitMarketAuthenticated bitMarketAuthenticated = mock(BitMarketAuthenticated.class);
    PowerMockito.when(bitMarketAuthenticated.cancel(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
        Mockito.any(SynchronizedValueFactory.class), Mockito.eq(11111L))).thenReturn(response);
    Whitebox.setInternalState(tradeService, "bitMarketAuthenticated", bitMarketAuthenticated);

    // when
    tradeService.cancelOrder("11111");

    // then
    fail("BitMarketTradeService should throw ExchangeException when cancel order request was unsuccessful");
  }

  @Test
  public void shouldGetOpenOrders() throws IOException {
    // given
    final LimitOrder[] expectedOrders = expectedOrders();

    BitMarketOrdersResponse response = new BitMarketOrdersResponse(true, createOpenOrdersData(), new BitMarketAPILimit(3, 100, 12345000L), 0, null);

    BitMarketAuthenticated bitMarketAuthenticated = mock(BitMarketAuthenticated.class);
    PowerMockito.when(bitMarketAuthenticated.orders(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
        Mockito.any(SynchronizedValueFactory.class))).thenReturn(response);
    Whitebox.setInternalState(tradeService, "bitMarketAuthenticated", bitMarketAuthenticated);

    // when
    OpenOrders orders = tradeService.getOpenOrders();
    List<LimitOrder> openOrders = orders.getOpenOrders();

    // then
    assertThat(openOrders).hasSize(2);
    for (int i = 0; i < openOrders.size(); i++) {
      BitMarketAssert.assertEquals(openOrders.get(i), expectedOrders[i]);
      assertThat(orders.toString()).contains(expectedOrders[i].toString());
    }
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnUnsuccessfulOpenOrders() throws IOException {
    // given
    BitMarketOrdersResponse response = new BitMarketOrdersResponse(false, null, new BitMarketAPILimit(3, 100, 12345000L), 502,
        "Invalid message hash");

    BitMarketAuthenticated bitMarketAuthenticated = mock(BitMarketAuthenticated.class);
    PowerMockito.when(bitMarketAuthenticated.orders(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
        Mockito.any(SynchronizedValueFactory.class))).thenReturn(response);
    Whitebox.setInternalState(tradeService, "bitMarketAuthenticated", bitMarketAuthenticated);

    // when
    tradeService.getOpenOrders();

    // then
    fail("BitMarketTradeService should throw ExchangeException when open orders request was unsuccessful");
  }

  @Test
  public void shouldGetPagingTradeHistory() throws IOException {
    // given
    final UserTrade[] expectedUserTrades = expectedUserTrades();

    BitMarketHistoryTradesResponse historyTradesResponse = parse("trade/example-history-trades-data", BitMarketHistoryTradesResponse.class);
    BitMarketHistoryOperationsResponse marketHistoryOperationsPlnResponse = parse("trade/example-history-operations-data",
        BitMarketHistoryOperationsResponse.class);
    BitMarketHistoryOperationsResponse marketHistoryOperationsBtcResponse = parse("trade/example-history-operations-btc-data",
        BitMarketHistoryOperationsResponse.class);

    BitMarketAuthenticated bitMarketAuthenticated = mock(BitMarketAuthenticated.class);
    PowerMockito.when(bitMarketAuthenticated.trades(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
        Mockito.any(SynchronizedValueFactory.class), Mockito.eq("BTCPLN"), Mockito.eq(1000), Mockito.eq(0L))).thenReturn(historyTradesResponse);

    PowerMockito
        .when(bitMarketAuthenticated.history(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
            Mockito.any(SynchronizedValueFactory.class), Mockito.eq(Currency.PLN.toString()), Mockito.anyInt(), Mockito.anyLong()))
        .thenReturn(marketHistoryOperationsPlnResponse);
    PowerMockito
        .when(bitMarketAuthenticated.history(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
            Mockito.any(SynchronizedValueFactory.class), Mockito.eq(Currency.BTC.toString()), Mockito.anyInt(), Mockito.anyLong()))
        .thenReturn(marketHistoryOperationsBtcResponse);

    Whitebox.setInternalState(tradeService, "bitMarketAuthenticated", bitMarketAuthenticated);

    // when
    UserTrades tradesPaging = tradeService.getTradeHistory(new DefaultTradeHistoryParamPaging(150));
    List<UserTrade> userTrades = tradesPaging.getUserTrades();

    // then
    assertThat(userTrades).hasSize(5);
    for (int i = 0; i < userTrades.size(); i++) {
      BitMarketAssert.assertEquals(userTrades.get(i), expectedUserTrades[i]);
    }
  }

  @Test
  public void shouldGetCurrencyPairTradeHistory() throws IOException {
    // given
    final UserTrade[] expectedCpUserTrades = expectedCpUserTrades();

    BitMarketHistoryTradesResponse historyTradesCPResponse = parse("trade/example-history-trades-cp-data", BitMarketHistoryTradesResponse.class);
    BitMarketHistoryOperationsResponse marketHistoryOperationsEurResponse = parse("trade/example-history-operations-eur-data",
        BitMarketHistoryOperationsResponse.class);
    BitMarketHistoryOperationsResponse marketHistoryOperationsBtcResponse = parse("trade/example-history-operations-btc-data",
        BitMarketHistoryOperationsResponse.class);

    BitMarketAuthenticated bitMarketAuthenticated = mock(BitMarketAuthenticated.class);
    PowerMockito.when(bitMarketAuthenticated.trades(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
        Mockito.any(SynchronizedValueFactory.class), Mockito.eq("BTCEUR"), Mockito.eq(1000), Mockito.eq(0L))).thenReturn(historyTradesCPResponse);

    PowerMockito
        .when(bitMarketAuthenticated.history(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
            Mockito.any(SynchronizedValueFactory.class), Mockito.eq(Currency.EUR.toString()), Mockito.anyInt(), Mockito.anyLong()))
        .thenReturn(marketHistoryOperationsEurResponse);
    PowerMockito
        .when(bitMarketAuthenticated.history(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
            Mockito.any(SynchronizedValueFactory.class), Mockito.eq(Currency.BTC.toString()), Mockito.anyInt(), Mockito.anyLong()))
        .thenReturn(marketHistoryOperationsBtcResponse);

    Whitebox.setInternalState(tradeService, "bitMarketAuthenticated", bitMarketAuthenticated);

    // when
    UserTrades tradesCP = tradeService.getTradeHistory(new DefaultTradeHistoryParamCurrencyPair(CurrencyPair.BTC_EUR));
    List<UserTrade> userTrades = tradesCP.getUserTrades();

    // then
    assertThat(userTrades).hasSize(2);
    for (int i = 0; i < userTrades.size(); i++) {
      BitMarketAssert.assertEquals(userTrades.get(i), expectedCpUserTrades[i]);
    }
  }

  @Test
  public void shouldGetTradeHistory() throws IOException {
    // given
    BitMarketHistoryTradesResponse historyTradesBMResponse = parse("trade/example-history-trades-bm-data", BitMarketHistoryTradesResponse.class);
    BitMarketHistoryOperationsResponse marketHistoryOperationsEurResponse = parse("trade/example-history-operations-eur-data",
        BitMarketHistoryOperationsResponse.class);
    BitMarketHistoryOperationsResponse marketHistoryOperationsBtcResponse = parse("trade/example-history-operations-btc-data",
        BitMarketHistoryOperationsResponse.class);

    BitMarketAuthenticated bitMarketAuthenticated = mock(BitMarketAuthenticated.class);
    PowerMockito
        .when(bitMarketAuthenticated.trades(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
            Mockito.any(SynchronizedValueFactory.class), Mockito.eq("BTCEUR"), Mockito.eq(3500), Mockito.eq(500L)))
        .thenReturn(historyTradesBMResponse);

    PowerMockito
        .when(bitMarketAuthenticated.history(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
            Mockito.any(SynchronizedValueFactory.class), Mockito.eq(Currency.EUR.toString()), Mockito.anyInt(), Mockito.anyLong()))
        .thenReturn(marketHistoryOperationsEurResponse);
    PowerMockito
        .when(bitMarketAuthenticated.history(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
            Mockito.any(SynchronizedValueFactory.class), Mockito.eq(Currency.BTC.toString()), Mockito.anyInt(), Mockito.anyLong()))
        .thenReturn(marketHistoryOperationsBtcResponse);

    Whitebox.setInternalState(tradeService, "bitMarketAuthenticated", bitMarketAuthenticated);

    // when
    UserTrades tradesBM = tradeService.getTradeHistory(new BitMarketHistoryParams(CurrencyPair.BTC_EUR, 500L, 3500));
    List<UserTrade> userTrades = tradesBM.getUserTrades();

    // then
    assertThat(userTrades).hasSize(1);
    BitMarketAssert.assertEquals(userTrades.get(0), EXPECTED_BM_USER_TRADES);
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnUnsuccessfulTradeHistory() throws IOException {
    // given
    BitMarketHistoryTradesResponse response = new BitMarketHistoryTradesResponse(false, null, new BitMarketAPILimit(3, 100, 12345000L), 502,
        "Invalid message hash");

    BitMarketHistoryOperationsResponse marketHistoryOperationsPlnResponse = parse("trade/example-history-operations-data",
        BitMarketHistoryOperationsResponse.class);
    BitMarketHistoryOperationsResponse marketHistoryOperationsBtcResponse = parse("trade/example-history-operations-btc-data",
        BitMarketHistoryOperationsResponse.class);

    BitMarketAuthenticated bitMarketAuthenticated = mock(BitMarketAuthenticated.class);
    PowerMockito.when(bitMarketAuthenticated.trades(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
        Mockito.any(SynchronizedValueFactory.class), Mockito.eq("BTCPLN"), Mockito.eq(1000), Mockito.eq(0L))).thenReturn(response);
    PowerMockito
        .when(bitMarketAuthenticated.history(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
            Mockito.any(SynchronizedValueFactory.class), Mockito.eq(Currency.PLN.toString()), Mockito.anyInt(), Mockito.anyLong()))
        .thenReturn(marketHistoryOperationsPlnResponse);
    PowerMockito
        .when(bitMarketAuthenticated.history(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
            Mockito.any(SynchronizedValueFactory.class), Mockito.eq(Currency.BTC.toString()), Mockito.anyInt(), Mockito.anyLong()))
        .thenReturn(marketHistoryOperationsBtcResponse);

    Whitebox.setInternalState(tradeService, "bitMarketAuthenticated", bitMarketAuthenticated);

    // when
    tradeService.getTradeHistory(new DefaultTradeHistoryParamPaging(150));

    // then
    fail("BitMarketTradeService should throw ExchangeException when open orders request was unsuccessful");
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnUnsuccessfulOperationsHistory1() throws IOException {
    // given
    BitMarketHistoryOperationsResponse errorResponse = new BitMarketHistoryOperationsResponse(false, null, new BitMarketAPILimit(3, 100, 12345000L),
        502, "Invalid message hash");

    BitMarketHistoryOperationsResponse marketHistoryOperationsBtcResponse = parse("trade/example-history-operations-btc-data",
        BitMarketHistoryOperationsResponse.class);

    BitMarketAuthenticated bitMarketAuthenticated = mock(BitMarketAuthenticated.class);
    PowerMockito
        .when(bitMarketAuthenticated.history(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
            Mockito.any(SynchronizedValueFactory.class), Mockito.eq(Currency.PLN.toString()), Mockito.anyInt(), Mockito.anyLong()))
        .thenReturn(errorResponse);
    PowerMockito
        .when(bitMarketAuthenticated.history(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
            Mockito.any(SynchronizedValueFactory.class), Mockito.eq(Currency.BTC.toString()), Mockito.anyInt(), Mockito.anyLong()))
        .thenReturn(marketHistoryOperationsBtcResponse);

    Whitebox.setInternalState(tradeService, "bitMarketAuthenticated", bitMarketAuthenticated);

    // when
    tradeService.getBitMarketOperationHistory(new DefaultTradeHistoryParamPaging(150));

    // then
    fail("BitMarketTradeService should throw ExchangeException when operations history request was unsuccessful");
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnUnsuccessfulOperationsHistory2() throws IOException {
    // given
    BitMarketHistoryOperationsResponse errorResponse = new BitMarketHistoryOperationsResponse(false, null, new BitMarketAPILimit(3, 100, 12345000L),
        502, "Invalid message hash");

    BitMarketHistoryOperationsResponse marketHistoryOperationsPlnResponse = parse("trade/example-history-operations-data",
        BitMarketHistoryOperationsResponse.class);

    BitMarketAuthenticated bitMarketAuthenticated = mock(BitMarketAuthenticated.class);
    PowerMockito
        .when(bitMarketAuthenticated.history(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
            Mockito.any(SynchronizedValueFactory.class), Mockito.eq(Currency.PLN.toString()), Mockito.anyInt(), Mockito.anyLong()))
        .thenReturn(marketHistoryOperationsPlnResponse);
    PowerMockito
        .when(bitMarketAuthenticated.history(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
            Mockito.any(SynchronizedValueFactory.class), Mockito.eq(Currency.BTC.toString()), Mockito.anyInt(), Mockito.anyLong()))
        .thenReturn(errorResponse);

    Whitebox.setInternalState(tradeService, "bitMarketAuthenticated", bitMarketAuthenticated);

    // when
    tradeService.getBitMarketOperationHistory(new DefaultTradeHistoryParamPaging(150));

    // then
    fail("BitMarketTradeService should throw ExchangeException when operations history request was unsuccessful");
  }

  @Test
  public void shouldCreateParams() {
    // when
    TradeHistoryParams params = tradeService.createTradeHistoryParams();

    // then
    assertThat(params instanceof BitMarketHistoryParams).isTrue();
    assertThat(((BitMarketHistoryParams) params).getCount()).isEqualTo(1000);
    assertThat(((BitMarketHistoryParams) params).getCurrencyPair()).isEqualTo(CurrencyPair.BTC_PLN);
    assertThat(((BitMarketHistoryParams) params).getOffset()).isEqualTo(0L);
  }
}
