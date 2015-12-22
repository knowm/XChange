package com.xeiam.xchange.bitmarket.service.polling;

import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitmarket.BitMarketAuthenticated;
import com.xeiam.xchange.bitmarket.BitMarketExchange;
import com.xeiam.xchange.bitmarket.BitMarketTestSupport;
import com.xeiam.xchange.bitmarket.dto.BitMarketAPILimit;
import com.xeiam.xchange.bitmarket.dto.account.BitMarketBalance;
import com.xeiam.xchange.bitmarket.dto.trade.BitMarketCancelResponse;
import com.xeiam.xchange.bitmarket.dto.trade.BitMarketHistoryOperationsResponse;
import com.xeiam.xchange.bitmarket.dto.trade.BitMarketHistoryTradesResponse;
import com.xeiam.xchange.bitmarket.dto.trade.BitMarketOrder;
import com.xeiam.xchange.bitmarket.dto.trade.BitMarketOrdersResponse;
import com.xeiam.xchange.bitmarket.dto.trade.BitMarketTrade;
import com.xeiam.xchange.bitmarket.dto.trade.BitMarketTradeResponse;
import com.xeiam.xchange.bitmarket.service.polling.params.BitMarketHistoryParams;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.service.polling.trade.params.DefaultTradeHistoryParamCurrencyPair;
import com.xeiam.xchange.service.polling.trade.params.DefaultTradeHistoryParamPaging;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParams;
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
import java.util.Date;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.fail;
import static org.powermock.api.mockito.PowerMockito.mock;

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
    MarketOrder marketOrder = new MarketOrder(Order.OrderType.ASK, new BigDecimal("0.01"), CurrencyPair.BTC_USD, new Date());

    // when
    tradeService.placeMarketOrder(marketOrder);

    // then
    fail("BitMarketTradeService should throw NotAvailableFromExchangeException when call placeMarketOrder");
  }

  // should be changed after order type convertion fix
  @Test
  public void shouldPlaceLimitOrder() throws IOException {
    // given
    BitMarketTradeResponse responseBuy = new BitMarketTradeResponse(
        true,
        new BitMarketTrade(12345, new BitMarketOrder(12345, "BTCAUD", new BigDecimal("10.00000000"),
            new BigDecimal("1.1"), new BigDecimal("0"), "BUY", 1234567890L),
            new BitMarketBalance(createAvailable(), createBlocked())),
        new BitMarketAPILimit(3, 100, 12345000L),
        0,
        null
    );

    BitMarketTradeResponse responseSell = new BitMarketTradeResponse(
        true,
        new BitMarketTrade(11111, new BitMarketOrder(11111, "BTCAUD", new BigDecimal("20.00000000"),
            new BigDecimal("2.2"), new BigDecimal("30.00000000"), "SELL", 1234567999L),
            new BitMarketBalance(createAvailable(), createBlocked())),
        new BitMarketAPILimit(3, 100, 12345000L),
        0,
        null
    );

    BitMarketAuthenticated bitMarketAuthenticated = mock(BitMarketAuthenticated.class);
    PowerMockito.when(bitMarketAuthenticated.trade(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
        Mockito.any(SynchronizedValueFactory.class), Mockito.eq("BTCAUD"), Mockito.eq("buy"),
        Mockito.eq(new BigDecimal("10.00000000")), Mockito.eq(new BigDecimal("20.00000000")))).thenReturn(responseBuy);
    PowerMockito.when(bitMarketAuthenticated.trade(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
        Mockito.any(SynchronizedValueFactory.class), Mockito.eq("BTCAUD"), Mockito.eq("sell"),
        Mockito.eq(new BigDecimal("20.00000000")), Mockito.eq(new BigDecimal("30.00000000")))).thenReturn(responseSell);
    Whitebox.setInternalState(tradeService, "bitMarketAuthenticated", bitMarketAuthenticated);

    // when
    String placedBuy = tradeService.placeLimitOrder(
        new LimitOrder(Order.OrderType.ASK, new BigDecimal("10.00000000"), CurrencyPair.BTC_AUD, "12345", null, new BigDecimal("20.00000000")));
    String placedSell = tradeService.placeLimitOrder(
        new LimitOrder(Order.OrderType.BID, new BigDecimal("20.00000000"), CurrencyPair.BTC_AUD, "11111", null, new BigDecimal("30.00000000")));

    // then
    assertThat(placedBuy).isEqualTo("12345");
    assertThat(placedSell).isEqualTo("11111");
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnUnsuccessfulLimitOrder() throws IOException {
    // given
    BitMarketTradeResponse response = new BitMarketTradeResponse(
        false,
        null,
        new BitMarketAPILimit(3, 100, 12345000L),
        502,
        "Invalid message hash"
    );

    BitMarketAuthenticated bitMarketAuthenticated = mock(BitMarketAuthenticated.class);
    PowerMockito.when(bitMarketAuthenticated.trade(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
        Mockito.any(SynchronizedValueFactory.class), Mockito.eq("BTCAUD"), Mockito.eq("buy"),
        Mockito.eq(new BigDecimal("10.00000000")), Mockito.eq(new BigDecimal("20.00000000")))).thenReturn(response);
    Whitebox.setInternalState(tradeService, "bitMarketAuthenticated", bitMarketAuthenticated);

    // when
    tradeService.placeLimitOrder(
        new LimitOrder(Order.OrderType.ASK, new BigDecimal("10.00000000"), CurrencyPair.BTC_AUD, "12345", null, new BigDecimal("20.00000000")));

    // then
    fail("BitMarketTradeService should throw ExchangeException when place limit order request was unsuccessful");
  }


  @Test
  public void shouldCancelOrder() throws IOException {
    // given
    BitMarketCancelResponse response = new BitMarketCancelResponse(
        true,
        new BitMarketBalance(createAvailable(), createBlocked()),
        new BitMarketAPILimit(3, 100, 12345000L),
        0,
        null
    );

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
    BitMarketCancelResponse response = new BitMarketCancelResponse(
        false,
        null,
        new BitMarketAPILimit(3, 100, 12345000L),
        502,
        "Invalid message hash"
    );

    BitMarketAuthenticated bitMarketAuthenticated = mock(BitMarketAuthenticated.class);
    PowerMockito.when(
        bitMarketAuthenticated.cancel(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
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
    BitMarketOrdersResponse response = new BitMarketOrdersResponse(
        true,
        createOpenOrdersData(),
        new BitMarketAPILimit(3, 100, 12345000L),
        0,
        null
    );

    BitMarketAuthenticated bitMarketAuthenticated = mock(BitMarketAuthenticated.class);
    PowerMockito.when(
        bitMarketAuthenticated.orders(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
            Mockito.any(SynchronizedValueFactory.class))).thenReturn(response);
    Whitebox.setInternalState(tradeService, "bitMarketAuthenticated", bitMarketAuthenticated);

    // when
    OpenOrders orders = tradeService.getOpenOrders();
    List<LimitOrder> openOrders = orders.getOpenOrders();

    // then
    assertThat(openOrders).hasSize(2);
    assertThat(openOrders.get(0).getId()).isEqualTo("31393");
    assertThat(openOrders.get(0).getLimitPrice()).isEqualTo(new BigDecimal("3000.0000"));
    assertThat(openOrders.get(1).getCurrencyPair()).isEqualTo(CurrencyPair.BTC_PLN);
    assertThat(openOrders.get(1).getTradableAmount()).isEqualTo(new BigDecimal("0.08000000"));

    assertThat(orders.toString()).contains(
        String.format("[order=LimitOrder [limitPrice=3000.0000, Order [type=ASK, tradableAmount=0.20000000, currencyPair=BTC/PLN, id=31393, timestamp=%s]]]",
            new Date(1432661682000L)));
    assertThat(orders.toString()).contains(
        String.format("[order=LimitOrder [limitPrice=4140.0000, Order [type=BID, tradableAmount=0.08000000, currencyPair=BTC/PLN, id=31391, timestamp=%s]]]",
            new Date(1432551696000L)));
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnUnsuccessfulOpenOrders() throws IOException {
    // given
    BitMarketOrdersResponse response = new BitMarketOrdersResponse(
        false,
        null,
        new BitMarketAPILimit(3, 100, 12345000L),
        502,
        "Invalid message hash"
    );

    BitMarketAuthenticated bitMarketAuthenticated = mock(BitMarketAuthenticated.class);
    PowerMockito.when(
        bitMarketAuthenticated.orders(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
            Mockito.any(SynchronizedValueFactory.class))).thenReturn(response);
    Whitebox.setInternalState(tradeService, "bitMarketAuthenticated", bitMarketAuthenticated);

    // when
    tradeService.getOpenOrders();

    // then
    fail("BitMarketTradeService should throw ExchangeException when open orders request was unsuccessful");
  }

  @Test
  public void shouldGetTradeHistory() throws IOException {
    // given
    BitMarketHistoryTradesResponse historyTradesResponse =
        parse("trade/example-history-trades-data", BitMarketHistoryTradesResponse.class);
    BitMarketHistoryTradesResponse historyTradesCPResponse =
        parse("trade/example-history-trades-cp-data", BitMarketHistoryTradesResponse.class);
    BitMarketHistoryTradesResponse historyTradesBMResponse =
        parse("trade/example-history-trades-bm-data", BitMarketHistoryTradesResponse.class);
    BitMarketHistoryOperationsResponse marketHistoryOperationsPlnResponse =
        parse("trade/example-history-operations-data", BitMarketHistoryOperationsResponse.class);
    BitMarketHistoryOperationsResponse marketHistoryOperationsEurResponse =
        parse("trade/example-history-operations-eur-data", BitMarketHistoryOperationsResponse.class);
    BitMarketHistoryOperationsResponse marketHistoryOperationsBtcResponse =
        parse("trade/example-history-operations-btc-data", BitMarketHistoryOperationsResponse.class);

    BitMarketAuthenticated bitMarketAuthenticated = mock(BitMarketAuthenticated.class);
    PowerMockito.when(
        bitMarketAuthenticated.trades(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
            Mockito.any(SynchronizedValueFactory.class),
            Mockito.eq("BTCPLN"), Mockito.eq(1000), Mockito.eq(0L))).thenReturn(historyTradesResponse);
    PowerMockito.when(
        bitMarketAuthenticated.trades(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
            Mockito.any(SynchronizedValueFactory.class),
            Mockito.eq("BTCEUR"), Mockito.eq(1000), Mockito.eq(0L))).thenReturn(historyTradesCPResponse);
    PowerMockito.when(
        bitMarketAuthenticated.trades(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
            Mockito.any(SynchronizedValueFactory.class),
            Mockito.eq("BTCEUR"), Mockito.eq(3500), Mockito.eq(500L))).thenReturn(historyTradesBMResponse);

    PowerMockito.when(
        bitMarketAuthenticated.history(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
            Mockito.any(SynchronizedValueFactory.class), Mockito.eq("PLN"), Mockito.anyInt(), Mockito.anyLong())).thenReturn(
        marketHistoryOperationsPlnResponse);
    PowerMockito.when(
        bitMarketAuthenticated.history(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
            Mockito.any(SynchronizedValueFactory.class), Mockito.eq("EUR"), Mockito.anyInt(), Mockito.anyLong())).thenReturn(marketHistoryOperationsEurResponse);
    PowerMockito.when(
        bitMarketAuthenticated.history(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
            Mockito.any(SynchronizedValueFactory.class), Mockito.eq("BTC"), Mockito.anyInt(), Mockito.anyLong())).thenReturn(marketHistoryOperationsBtcResponse);

    Whitebox.setInternalState(tradeService, "bitMarketAuthenticated", bitMarketAuthenticated);

    // when
    UserTrades tradesPaging = tradeService.getTradeHistory(new DefaultTradeHistoryParamPaging(150));
    UserTrades tradesCP = tradeService.getTradeHistory(new DefaultTradeHistoryParamCurrencyPair(CurrencyPair.BTC_EUR));
    UserTrades tradesBM = tradeService.getTradeHistory(new BitMarketHistoryParams(CurrencyPair.BTC_EUR, 500L, 3500));

    // then
    assertThat(tradesPaging.toString()).isEqualTo(String.format("Trades\n" + "lastID= 0\n"
        + "[trade=UserTrade[type=ASK, tradableAmount=0.01000000, currencyPair=BTC/PLN, price=875.9898, timestamp=%s, id=386637, orderId='null', feeAmount=null, feeCurrency='BTC']]\n"
        + "[trade=UserTrade[type=ASK, tradableAmount=0.49000000, currencyPair=BTC/PLN, price=875.9898, timestamp=%s, id=386638, orderId='null', feeAmount=null, feeCurrency='BTC']]\n"
        + "[trade=UserTrade[type=ASK, tradableAmount=0.50000000, currencyPair=BTC/PLN, price=869.9900, timestamp=%s, id=386651, orderId='null', feeAmount=null, feeCurrency='BTC']]\n"
        + "[trade=UserTrade[type=ASK, tradableAmount=0.03120150, currencyPair=BTC/PLN, price=865.6667, timestamp=%s, id=386750, orderId='null', feeAmount=null, feeCurrency='BTC']]\n"
        + "[trade=UserTrade[type=BID, tradableAmount=1.08260046, currencyPair=BTC/PLN, price=877.0000, timestamp=%s, id=389406, orderId='11852566', feeAmount=0.30312011, feeCurrency='PLN']]\n",
        new Date(1429901376000L), new Date(1429901383000L), new Date(1429911236000L), new Date(1429965622000L), new Date(1430687948000L)));
    assertThat(tradesCP.toString()).isEqualTo(String.format("Trades\n" + "lastID= 0\n"
        + "[trade=UserTrade[type=BID, tradableAmount=2.140000000, currencyPair=BTC/EUR, price=110.0000, timestamp=%s, id=389406, orderId='null', feeAmount=null, feeCurrency='EUR']]\n"
        + "[trade=UserTrade[type=ASK, tradableAmount=0.05555555, currencyPair=BTC/EUR, price=115.5555, timestamp=%s, id=386750, orderId='null', feeAmount=null, feeCurrency='BTC']]\n",
        new Date(1234567890000L), new Date(1400000000000L)));
    assertThat(tradesBM.toString()).isEqualTo(String.format("Trades\n" + "lastID= 0\n"
        + "[trade=UserTrade[type=ASK, tradableAmount=0.08888888, currencyPair=BTC/EUR, price=210.3333, timestamp=%s, id=386775, orderId='null', feeAmount=null, feeCurrency='BTC']]\n",
        new Date(1444444444000L)));
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnUnsuccessfulTradeHistory() throws IOException {
    // given
    BitMarketHistoryTradesResponse response = new BitMarketHistoryTradesResponse(
        false,
        null,
        new BitMarketAPILimit(3, 100, 12345000L),
        502,
        "Invalid message hash"
    );

    BitMarketHistoryOperationsResponse marketHistoryOperationsPlnResponse =
        parse("trade/example-history-operations-data", BitMarketHistoryOperationsResponse.class);
    BitMarketHistoryOperationsResponse marketHistoryOperationsBtcResponse =
        parse("trade/example-history-operations-btc-data", BitMarketHistoryOperationsResponse.class);

    BitMarketAuthenticated bitMarketAuthenticated = mock(BitMarketAuthenticated.class);
    PowerMockito.when(
        bitMarketAuthenticated.trades(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
            Mockito.any(SynchronizedValueFactory.class), Mockito.eq("BTCPLN"), Mockito.eq(1000),
            Mockito.eq(0L))).thenReturn(response);
    PowerMockito.when(
        bitMarketAuthenticated.history(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
            Mockito.any(SynchronizedValueFactory.class), Mockito.eq("PLN"), Mockito.anyInt(), Mockito.anyLong())).thenReturn(
        marketHistoryOperationsPlnResponse);
    PowerMockito.when(
        bitMarketAuthenticated.history(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
            Mockito.any(SynchronizedValueFactory.class), Mockito.eq("BTC"), Mockito.anyInt(), Mockito.anyLong())).thenReturn(marketHistoryOperationsBtcResponse);

    Whitebox.setInternalState(tradeService, "bitMarketAuthenticated", bitMarketAuthenticated);

    // when
    tradeService.getTradeHistory(new DefaultTradeHistoryParamPaging(150));

    // then
    fail("BitMarketTradeService should throw ExchangeException when open orders request was unsuccessful");
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnUnsuccessfulOperationsHistory1() throws IOException {
    // given
    BitMarketHistoryOperationsResponse errorResponse = new BitMarketHistoryOperationsResponse(
        false,
        null,
        new BitMarketAPILimit(3, 100, 12345000L),
        502,
        "Invalid message hash"
    );

    BitMarketHistoryOperationsResponse marketHistoryOperationsBtcResponse =
        parse("trade/example-history-operations-btc-data", BitMarketHistoryOperationsResponse.class);

    BitMarketAuthenticated bitMarketAuthenticated = mock(BitMarketAuthenticated.class);
    PowerMockito.when(
        bitMarketAuthenticated.history(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
            Mockito.any(SynchronizedValueFactory.class), Mockito.eq("PLN"), Mockito.anyInt(), Mockito.anyLong()))
        .thenReturn(errorResponse);
    PowerMockito.when(
        bitMarketAuthenticated.history(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
            Mockito.any(SynchronizedValueFactory.class), Mockito.eq("BTC"), Mockito.anyInt(), Mockito.anyLong()))
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
    BitMarketHistoryOperationsResponse errorResponse = new BitMarketHistoryOperationsResponse(
        false,
        null,
        new BitMarketAPILimit(3, 100, 12345000L),
        502,
        "Invalid message hash"
    );

    BitMarketHistoryOperationsResponse marketHistoryOperationsPlnResponse =
        parse("trade/example-history-operations-data", BitMarketHistoryOperationsResponse.class);

    BitMarketAuthenticated bitMarketAuthenticated = mock(BitMarketAuthenticated.class);
    PowerMockito.when(
        bitMarketAuthenticated.history(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
            Mockito.any(SynchronizedValueFactory.class), Mockito.eq("PLN"), Mockito.anyInt(), Mockito.anyLong()))
        .thenReturn(marketHistoryOperationsPlnResponse);
    PowerMockito.when(
        bitMarketAuthenticated.history(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
            Mockito.any(SynchronizedValueFactory.class), Mockito.eq("BTC"), Mockito.anyInt(), Mockito.anyLong()))
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
