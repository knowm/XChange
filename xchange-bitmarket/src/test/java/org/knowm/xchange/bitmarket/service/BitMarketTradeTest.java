package org.knowm.xchange.bitmarket.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assume.assumeThat;
import static org.knowm.xchange.utils.nonce.LongConstNonceFactory.NONCE_FACTORY;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitmarket.BitMarketAssert;
import org.knowm.xchange.bitmarket.BitMarketAuthenticated;
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
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import si.mazi.rescu.ClientConfig;
import si.mazi.rescu.IRestProxyFactory;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

@RunWith(MockitoJUnitRunner.class)
public class BitMarketTradeTest extends BitMarketTestSupport {

  @Mock private Exchange exchange;

  @Mock private IRestProxyFactory restProxyFactory;

  @Mock private BitMarketAuthenticated bitMarketAuthenticated;

  private BitMarketTradeService tradeService;

  @Before
  public void setUp() {
    ExchangeSpecification specification = createExchangeSpecification();

    when(restProxyFactory.createProxy(
            eq(BitMarketAuthenticated.class), any(String.class), any(ClientConfig.class)))
        .thenReturn(bitMarketAuthenticated);

    when(exchange.getExchangeSpecification()).thenReturn(specification);
    when(exchange.getNonceFactory()).thenReturn(NONCE_FACTORY);

    tradeService = new BitMarketTradeService(exchange, restProxyFactory);
  }

  @Test
  public void constructor() {
    assumeThat(SPECIFICATION_API_KEY, is(tradeService.apiKey));
  }

  @Test(expected = NotAvailableFromExchangeException.class)
  public void shouldFailOnPlaceMarketOrder() throws IOException {
    // given
    MarketOrder marketOrder =
        new MarketOrder(Order.OrderType.ASK, BigDecimal.TEN, CurrencyPair.BTC_USD, new Date());

    // when
    tradeService.placeMarketOrder(marketOrder);

    // then
    fail(
        "BitMarketTradeService should throw NotAvailableFromExchangeException when call placeMarketOrder");
  }

  // should be changed after order type convertion fix
  @Test
  public void shouldPlaceBuyLimitOrder() throws IOException {
    // given
    BitMarketTradeResponse responseBuy =
        new BitMarketTradeResponse(
            true,
            new BitMarketTrade(
                12345,
                new BitMarketOrder(
                    12345,
                    "BTCAUD",
                    BigDecimal.ONE,
                    new BigDecimal("1.1"),
                    BigDecimal.ZERO,
                    "BUY",
                    1234567890L),
                new BitMarketBalance(createAvailable(), createBlocked())),
            new BitMarketAPILimit(3, 100, 12345000L),
            0,
            null);

    when(bitMarketAuthenticated.trade(
            eq(SPECIFICATION_API_KEY),
            any(ParamsDigest.class),
            any(SynchronizedValueFactory.class),
            eq("BTCAUD"),
            eq("buy"),
            eq(BigDecimal.ONE),
            eq(BigDecimal.TEN)))
        .thenReturn(responseBuy);

    // when
    String placedBuy =
        tradeService.placeLimitOrder(
            new LimitOrder(
                Order.OrderType.BID,
                BigDecimal.ONE,
                CurrencyPair.BTC_AUD,
                "12345",
                null,
                BigDecimal.TEN));

    // then
    assertThat(placedBuy).isEqualTo("12345");
  }

  // should be changed after order type convertion fix
  @Test
  public void shouldPlaceSellLimitOrder() throws IOException {
    // given
    BitMarketTradeResponse responseSell =
        new BitMarketTradeResponse(
            true,
            new BitMarketTrade(
                11111,
                new BitMarketOrder(
                    11111,
                    "BTCAUD",
                    BigDecimal.ONE,
                    new BigDecimal("2.2"),
                    BigDecimal.TEN,
                    "SELL",
                    1234567999L),
                new BitMarketBalance(createAvailable(), createBlocked())),
            new BitMarketAPILimit(3, 100, 12345000L),
            0,
            null);

    when(bitMarketAuthenticated.trade(
            eq(SPECIFICATION_API_KEY),
            any(ParamsDigest.class),
            any(SynchronizedValueFactory.class),
            eq("BTCAUD"),
            eq("sell"),
            eq(BigDecimal.ONE),
            eq(BigDecimal.TEN)))
        .thenReturn(responseSell);

    // when
    String placedSell =
        tradeService.placeLimitOrder(
            new LimitOrder(
                Order.OrderType.ASK,
                BigDecimal.ONE,
                CurrencyPair.BTC_AUD,
                "11111",
                null,
                BigDecimal.TEN));

    // then
    assertThat(placedSell).isEqualTo("11111");
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnUnsuccessfulLimitOrder() throws IOException {
    // given
    BitMarketTradeResponse response =
        new BitMarketTradeResponse(
            false, null, new BitMarketAPILimit(3, 100, 12345000L), 502, "Invalid message hash");

    when(bitMarketAuthenticated.trade(
            eq(SPECIFICATION_API_KEY),
            any(ParamsDigest.class),
            any(SynchronizedValueFactory.class),
            eq("BTCAUD"),
            eq("sell"),
            eq(BigDecimal.ONE),
            eq(BigDecimal.TEN)))
        .thenReturn(response);

    // when
    tradeService.placeLimitOrder(
        new LimitOrder(
            Order.OrderType.ASK,
            BigDecimal.ONE,
            CurrencyPair.BTC_AUD,
            "12345",
            null,
            BigDecimal.TEN));

    // then
    fail(
        "BitMarketTradeService should throw ExchangeException when place limit order request was unsuccessful");
  }

  @Test
  public void shouldCancelOrder() throws IOException {
    // given
    BitMarketCancelResponse response =
        new BitMarketCancelResponse(
            true,
            new BitMarketBalance(createAvailable(), createBlocked()),
            new BitMarketAPILimit(3, 100, 12345000L),
            0,
            null);

    when(bitMarketAuthenticated.cancel(
            eq(SPECIFICATION_API_KEY),
            any(ParamsDigest.class),
            any(SynchronizedValueFactory.class),
            eq(12345L)))
        .thenReturn(response);

    // when
    boolean cancelled = tradeService.cancelOrder("12345");

    // then
    assertThat(cancelled).isTrue();
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnUnsuccessfulCancelOrder() throws IOException {
    // given
    BitMarketCancelResponse response =
        new BitMarketCancelResponse(
            false, null, new BitMarketAPILimit(3, 100, 12345000L), 502, "Invalid message hash");

    when(bitMarketAuthenticated.cancel(
            eq(SPECIFICATION_API_KEY),
            any(ParamsDigest.class),
            any(SynchronizedValueFactory.class),
            eq(11111L)))
        .thenReturn(response);

    // when
    tradeService.cancelOrder("11111");

    // then
    fail(
        "BitMarketTradeService should throw ExchangeException when cancel order request was unsuccessful");
  }

  @Test
  public void shouldGetOpenOrders() throws IOException {
    // given
    final LimitOrder[] expectedOrders = expectedOrders();

    BitMarketOrdersResponse response =
        new BitMarketOrdersResponse(
            true, createOpenOrdersData(), new BitMarketAPILimit(3, 100, 12345000L), 0, null);

    when(bitMarketAuthenticated.orders(
            eq(SPECIFICATION_API_KEY),
            any(ParamsDigest.class),
            any(SynchronizedValueFactory.class)))
        .thenReturn(response);

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
    BitMarketOrdersResponse response =
        new BitMarketOrdersResponse(
            false, null, new BitMarketAPILimit(3, 100, 12345000L), 502, "Invalid message hash");

    when(bitMarketAuthenticated.orders(
            eq(SPECIFICATION_API_KEY),
            any(ParamsDigest.class),
            any(SynchronizedValueFactory.class)))
        .thenReturn(response);

    // when
    tradeService.getOpenOrders();

    // then
    fail(
        "BitMarketTradeService should throw ExchangeException when open orders request was unsuccessful");
  }

  @Test
  public void shouldGetPagingTradeHistory() throws IOException {
    // given
    final UserTrade[] expectedUserTrades = expectedUserTrades();

    BitMarketHistoryTradesResponse historyTradesResponse =
        parse(
            "org/knowm/xchange/bitmarket/dto/trade/example-history-trades-data",
            BitMarketHistoryTradesResponse.class);
    BitMarketHistoryOperationsResponse marketHistoryOperationsPlnResponse =
        parse(
            "org/knowm/xchange/bitmarket/dto/trade/example-history-operations-data",
            BitMarketHistoryOperationsResponse.class);
    BitMarketHistoryOperationsResponse marketHistoryOperationsBtcResponse =
        parse(
            "org/knowm/xchange/bitmarket/dto/trade/example-history-operations-btc-data",
            BitMarketHistoryOperationsResponse.class);

    when(bitMarketAuthenticated.trades(
            eq(SPECIFICATION_API_KEY),
            any(ParamsDigest.class),
            any(SynchronizedValueFactory.class),
            eq("BTCPLN"),
            eq(1000),
            eq(0L)))
        .thenReturn(historyTradesResponse);

    when(bitMarketAuthenticated.history(
            eq(SPECIFICATION_API_KEY),
            any(ParamsDigest.class),
            any(SynchronizedValueFactory.class),
            eq(Currency.PLN.toString()),
            anyInt(),
            anyLong()))
        .thenReturn(marketHistoryOperationsPlnResponse);
    when(bitMarketAuthenticated.history(
            eq(SPECIFICATION_API_KEY),
            any(ParamsDigest.class),
            any(SynchronizedValueFactory.class),
            eq(Currency.BTC.toString()),
            anyInt(),
            anyLong()))
        .thenReturn(marketHistoryOperationsBtcResponse);

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

    BitMarketHistoryTradesResponse historyTradesCPResponse =
        parse(
            "org/knowm/xchange/bitmarket/dto/trade/example-history-trades-cp-data",
            BitMarketHistoryTradesResponse.class);
    BitMarketHistoryOperationsResponse marketHistoryOperationsEurResponse =
        parse(
            "org/knowm/xchange/bitmarket/dto/trade/example-history-operations-eur-data",
            BitMarketHistoryOperationsResponse.class);
    BitMarketHistoryOperationsResponse marketHistoryOperationsBtcResponse =
        parse(
            "org/knowm/xchange/bitmarket/dto/trade/example-history-operations-btc-data",
            BitMarketHistoryOperationsResponse.class);

    when(bitMarketAuthenticated.trades(
            eq(SPECIFICATION_API_KEY),
            any(ParamsDigest.class),
            any(SynchronizedValueFactory.class),
            eq("BTCEUR"),
            eq(1000),
            eq(0L)))
        .thenReturn(historyTradesCPResponse);

    when(bitMarketAuthenticated.history(
            eq(SPECIFICATION_API_KEY),
            any(ParamsDigest.class),
            any(SynchronizedValueFactory.class),
            eq(Currency.EUR.toString()),
            anyInt(),
            anyLong()))
        .thenReturn(marketHistoryOperationsEurResponse);
    when(bitMarketAuthenticated.history(
            eq(SPECIFICATION_API_KEY),
            any(ParamsDigest.class),
            any(SynchronizedValueFactory.class),
            eq(Currency.BTC.toString()),
            anyInt(),
            anyLong()))
        .thenReturn(marketHistoryOperationsBtcResponse);

    // when
    UserTrades tradesCP =
        tradeService.getTradeHistory(
            new DefaultTradeHistoryParamCurrencyPair(CurrencyPair.BTC_EUR));
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
    BitMarketHistoryTradesResponse historyTradesBMResponse =
        parse(
            "org/knowm/xchange/bitmarket/dto/trade/example-history-trades-bm-data",
            BitMarketHistoryTradesResponse.class);
    BitMarketHistoryOperationsResponse marketHistoryOperationsEurResponse =
        parse(
            "org/knowm/xchange/bitmarket/dto/trade/example-history-operations-eur-data",
            BitMarketHistoryOperationsResponse.class);
    BitMarketHistoryOperationsResponse marketHistoryOperationsBtcResponse =
        parse(
            "org/knowm/xchange/bitmarket/dto/trade/example-history-operations-btc-data",
            BitMarketHistoryOperationsResponse.class);

    when(bitMarketAuthenticated.trades(
            eq(SPECIFICATION_API_KEY),
            any(ParamsDigest.class),
            any(SynchronizedValueFactory.class),
            eq("BTCEUR"),
            eq(3500),
            eq(500L)))
        .thenReturn(historyTradesBMResponse);

    when(bitMarketAuthenticated.history(
            eq(SPECIFICATION_API_KEY),
            any(ParamsDigest.class),
            any(SynchronizedValueFactory.class),
            eq(Currency.EUR.toString()),
            anyInt(),
            anyLong()))
        .thenReturn(marketHistoryOperationsEurResponse);
    when(bitMarketAuthenticated.history(
            eq(SPECIFICATION_API_KEY),
            any(ParamsDigest.class),
            any(SynchronizedValueFactory.class),
            eq(Currency.BTC.toString()),
            anyInt(),
            anyLong()))
        .thenReturn(marketHistoryOperationsBtcResponse);

    // when
    UserTrades tradesBM =
        tradeService.getTradeHistory(new BitMarketHistoryParams(CurrencyPair.BTC_EUR, 500L, 3500));
    List<UserTrade> userTrades = tradesBM.getUserTrades();

    // then
    assertThat(userTrades).hasSize(1);
    BitMarketAssert.assertEquals(userTrades.get(0), EXPECTED_BM_USER_TRADES);
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnUnsuccessfulTradeHistory() throws IOException {
    // given
    BitMarketHistoryTradesResponse response =
        new BitMarketHistoryTradesResponse(
            false, null, new BitMarketAPILimit(3, 100, 12345000L), 502, "Invalid message hash");

    when(bitMarketAuthenticated.trades(
            eq(SPECIFICATION_API_KEY),
            any(ParamsDigest.class),
            any(SynchronizedValueFactory.class),
            eq("BTCPLN"),
            eq(1000),
            eq(0L)))
        .thenReturn(response);

    // when
    tradeService.getTradeHistory(new DefaultTradeHistoryParamPaging(150));

    // then
    fail(
        "BitMarketTradeService should throw ExchangeException when open orders request was unsuccessful");
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnUnsuccessfulOperationsHistory1() throws IOException {
    // given
    BitMarketHistoryOperationsResponse errorResponse =
        new BitMarketHistoryOperationsResponse(
            false, null, new BitMarketAPILimit(3, 100, 12345000L), 502, "Invalid message hash");

    BitMarketHistoryOperationsResponse marketHistoryOperationsBtcResponse =
        parse(
            "org/knowm/xchange/bitmarket/dto/trade/example-history-operations-btc-data",
            BitMarketHistoryOperationsResponse.class);

    when(bitMarketAuthenticated.history(
            eq(SPECIFICATION_API_KEY),
            any(ParamsDigest.class),
            any(SynchronizedValueFactory.class),
            eq(Currency.PLN.toString()),
            anyInt(),
            anyLong()))
        .thenReturn(errorResponse);
    when(bitMarketAuthenticated.history(
            eq(SPECIFICATION_API_KEY),
            any(ParamsDigest.class),
            any(SynchronizedValueFactory.class),
            eq(Currency.BTC.toString()),
            anyInt(),
            anyLong()))
        .thenReturn(marketHistoryOperationsBtcResponse);

    // when
    tradeService.getBitMarketOperationHistory(new DefaultTradeHistoryParamPaging(150));

    // then
    fail(
        "BitMarketTradeService should throw ExchangeException when operations history request was unsuccessful");
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnUnsuccessfulOperationsHistory2() throws IOException {
    // given
    BitMarketHistoryOperationsResponse errorResponse =
        new BitMarketHistoryOperationsResponse(
            false, null, new BitMarketAPILimit(3, 100, 12345000L), 502, "Invalid message hash");

    BitMarketHistoryOperationsResponse marketHistoryOperationsPlnResponse =
        parse(
            "org/knowm/xchange/bitmarket/dto/trade/example-history-operations-data",
            BitMarketHistoryOperationsResponse.class);

    when(bitMarketAuthenticated.history(
            eq(SPECIFICATION_API_KEY),
            any(ParamsDigest.class),
            any(SynchronizedValueFactory.class),
            eq(Currency.PLN.toString()),
            anyInt(),
            anyLong()))
        .thenReturn(marketHistoryOperationsPlnResponse);
    when(bitMarketAuthenticated.history(
            eq(SPECIFICATION_API_KEY),
            any(ParamsDigest.class),
            any(SynchronizedValueFactory.class),
            eq(Currency.BTC.toString()),
            anyInt(),
            anyLong()))
        .thenReturn(errorResponse);

    // when
    tradeService.getBitMarketOperationHistory(new DefaultTradeHistoryParamPaging(150));

    // then
    fail(
        "BitMarketTradeService should throw ExchangeException when operations history request was unsuccessful");
  }

  @Test
  public void shouldCreateParams() {
    // when
    TradeHistoryParams params = tradeService.createTradeHistoryParams();

    // then
    assertThat(params instanceof BitMarketHistoryParams).isTrue();
    BitMarketHistoryParams bitMarketHistoryParams = (BitMarketHistoryParams) params;
    assertThat(bitMarketHistoryParams.getCount()).isEqualTo(1000);
    assertThat(bitMarketHistoryParams.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_PLN);
    assertThat(bitMarketHistoryParams.getOffset()).isEqualTo(0L);
  }
}
