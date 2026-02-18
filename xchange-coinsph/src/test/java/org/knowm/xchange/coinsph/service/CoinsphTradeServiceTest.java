package org.knowm.xchange.coinsph.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.coinsph.Coinsph;
import org.knowm.xchange.coinsph.CoinsphAuthenticated;
import org.knowm.xchange.coinsph.CoinsphExchange;
import org.knowm.xchange.coinsph.dto.trade.CoinsphOrder;
import org.knowm.xchange.coinsph.dto.trade.CoinsphTradeHistoryParams;
import org.knowm.xchange.coinsph.dto.trade.CoinsphUserTrade;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.params.CancelOrderByCurrencyPair;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

public class CoinsphTradeServiceTest {

  private CoinsphTradeService tradeService;
  private CoinsphAuthenticated coinsphAuthenticated;
  private CoinsphExchange exchange;
  private SynchronizedValueFactory<Long> timestampFactory;
  private ParamsDigest signatureCreator;

  @BeforeEach
  public void setUp() {
    exchange = mock(CoinsphExchange.class);
    Coinsph coinsph = mock(Coinsph.class);
    coinsphAuthenticated = mock(CoinsphAuthenticated.class);
    ResilienceRegistries resilienceRegistries = mock(ResilienceRegistries.class);
    signatureCreator = mock(ParamsDigest.class);
    timestampFactory = mock(SynchronizedValueFactory.class);
    when(timestampFactory.createValue()).thenReturn(1621234560000L);

    // Mock exchange specification
    org.knowm.xchange.ExchangeSpecification exchangeSpec =
        mock(org.knowm.xchange.ExchangeSpecification.class);
    when(exchange.getExchangeSpecification()).thenReturn(exchangeSpec);
    when(exchangeSpec.getApiKey()).thenReturn("dummyApiKey");
    when(exchangeSpec.getSecretKey()).thenReturn("dummySecretKey");
    when(exchange.getRecvWindow()).thenReturn(5000L);

    // Mock exchange methods
    when(exchange.getPublicApi()).thenReturn(coinsph);
    when(exchange.getAuthenticatedApi()).thenReturn(coinsphAuthenticated);
    when(exchange.getSignatureCreator()).thenReturn(signatureCreator);
    when(exchange.getNonceFactory()).thenReturn(timestampFactory);

    // Create the service
    tradeService = new CoinsphTradeService(exchange, resilienceRegistries);
  }

  @Test
  public void testGetOpenOrders() throws IOException {
    // given
    List<CoinsphOrder> mockOrders = new ArrayList<>();

    CoinsphOrder order1 =
        new CoinsphOrder(
            "BTCPHP", // symbol
            12345L, // orderId
            -1L, // orderListId
            "clientOrderId1", // clientOrderId
            new BigDecimal("5800000.0"), // price
            new BigDecimal("0.1"), // origQty
            new BigDecimal("0.0"), // executedQty
            new BigDecimal("0.0"), // cummulativeQuoteQty
            "NEW", // status
            "GTC", // timeInForce
            "LIMIT", // type
            "BUY", // side
            null, // stopPrice
            1621234560000L, // time
            1621234560000L, // updateTime
            true, // isWorking
            null // origQuoteOrderQty
            );
    mockOrders.add(order1);

    CoinsphOrder order2 =
        new CoinsphOrder(
            "ETHPHP", // symbol
            12346L, // orderId
            -1L, // orderListId
            "clientOrderId2", // clientOrderId
            new BigDecimal("350000.0"), // price
            new BigDecimal("0.5"), // origQty
            new BigDecimal("0.0"), // executedQty
            new BigDecimal("0.0"), // cummulativeQuoteQty
            "NEW", // status
            "GTC", // timeInForce
            "LIMIT", // type
            "SELL", // side
            null, // stopPrice
            1621234570000L, // time
            1621234570000L, // updateTime
            true, // isWorking
            null // origQuoteOrderQty
            );
    mockOrders.add(order2);

    // when
    when(coinsphAuthenticated.getOpenOrders(anyString(), any(), any(), any(), anyLong()))
        .thenReturn(mockOrders);

    // then
    OpenOrders openOrders = tradeService.getOpenOrders();

    assertThat(openOrders).isNotNull();
    assertThat(openOrders.getOpenOrders()).hasSize(2);

    LimitOrder btcOrder = openOrders.getOpenOrders().get(0);
    assertThat(btcOrder.getId()).isEqualTo("12345");
    assertThat(btcOrder.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_PHP);
    assertThat(btcOrder.getType()).isEqualTo(Order.OrderType.BID);
    assertThat(btcOrder.getLimitPrice()).isEqualByComparingTo(new BigDecimal("5800000.0"));
    assertThat(btcOrder.getOriginalAmount()).isEqualByComparingTo(new BigDecimal("0.1"));

    LimitOrder ethOrder = openOrders.getOpenOrders().get(1);
    assertThat(ethOrder.getId()).isEqualTo("12346");
    assertThat(ethOrder.getCurrencyPair()).isEqualTo(new CurrencyPair("ETH", "PHP"));
    assertThat(ethOrder.getType()).isEqualTo(Order.OrderType.ASK);
    assertThat(ethOrder.getLimitPrice()).isEqualByComparingTo(new BigDecimal("350000.0"));
    assertThat(ethOrder.getOriginalAmount()).isEqualByComparingTo(new BigDecimal("0.5"));
  }

  @Test
  public void testPlaceLimitOrder() throws IOException {
    // given
    LimitOrder limitOrder =
        new LimitOrder.Builder(Order.OrderType.BID, CurrencyPair.BTC_PHP)
            .limitPrice(new BigDecimal("5800000.0"))
            .originalAmount(new BigDecimal("0.1"))
            .build();

    CoinsphOrder mockResponse =
        new CoinsphOrder(
            "BTCPHP", // symbol
            12345L, // orderId
            -1L, // orderListId
            "clientOrderId1", // clientOrderId
            new BigDecimal("5800000.0"), // price
            new BigDecimal("0.1"), // origQty
            new BigDecimal("0.0"), // executedQty
            new BigDecimal("0.0"), // cummulativeQuoteQty
            "NEW", // status
            "GTC", // timeInForce
            "LIMIT", // type
            "BUY", // side
            null, // stopPrice
            1621234560000L, // time
            1621234560000L, // updateTime
            true, // isWorking
            null // origQuoteOrderQty
            );

    // when
    when(coinsphAuthenticated.newOrder(
            anyString(),
            anyString(),
            any(),
            any(),
            any(),
            any(),
            any(),
            any(),
            any(),
            any(),
            anyLong(),
            any(),
            any()))
        .thenReturn(mockResponse);

    // then
    String orderId = tradeService.placeLimitOrder(limitOrder);

    assertThat(orderId).isEqualTo("12345");
  }

  @Test
  public void testPlaceMarketOrder() throws IOException {
    // given
    MarketOrder marketOrder =
        new MarketOrder.Builder(Order.OrderType.BID, CurrencyPair.BTC_PHP)
            .originalAmount(new BigDecimal("0.1"))
            .build();

    CoinsphOrder mockResponse =
        new CoinsphOrder(
            "BTCPHP", // symbol
            12345L, // orderId
            -1L, // orderListId
            "clientOrderId1", // clientOrderId
            null, // price (market order)
            new BigDecimal("0.1"), // origQty
            new BigDecimal("0.1"), // executedQty
            new BigDecimal("580000.0"), // cummulativeQuoteQty
            "FILLED", // status
            "IOC", // timeInForce
            "MARKET", // type
            "BUY", // side
            null, // stopPrice
            1621234560000L, // time
            1621234560000L, // updateTime
            false, // isWorking
            null // origQuoteOrderQty
            );

    // when
    when(coinsphAuthenticated.newOrder(
            anyString(),
            anyString(),
            any(),
            any(),
            any(),
            any(),
            any(),
            any(),
            any(),
            any(),
            anyLong(),
            any(),
            any()))
        .thenReturn(mockResponse);

    // then
    String orderId = tradeService.placeMarketOrder(marketOrder);

    assertThat(orderId).isEqualTo("12345");
  }

  @Test
  public void testCancelOrder() throws IOException {
    // given
    String orderId = "12345";
    CurrencyPair currencyPair = CurrencyPair.BTC_PHP;

    CoinsphOrder mockResponse =
        new CoinsphOrder(
            "BTCPHP", // symbol
            12345L, // orderId
            -1L, // orderListId
            "clientOrderId1", // clientOrderId
            new BigDecimal("5800000.0"), // price
            new BigDecimal("0.1"), // origQty
            new BigDecimal("0.0"), // executedQty
            new BigDecimal("0.0"), // cummulativeQuoteQty
            "CANCELED", // status
            "GTC", // timeInForce
            "LIMIT", // type
            "BUY", // side
            null, // stopPrice
            1621234560000L, // time
            1621234570000L, // updateTime
            false, // isWorking
            null // origQuoteOrderQty
            );

    // Create a cancel order params that implements both required interfaces
    CancelOrderByIdParams cancelOrderByIdParams =
        new CancelOrderByIdParams() {
          @Override
          public String getOrderId() {
            return orderId;
          }
        };

    CancelOrderByCurrencyPair cancelOrderByCurrencyPair =
        new CancelOrderByCurrencyPair() {
          @Override
          public CurrencyPair getCurrencyPair() {
            return currencyPair;
          }
        };

    // Combined params
    class CombinedCancelParams implements CancelOrderByIdParams, CancelOrderByCurrencyPair {
      @Override
      public String getOrderId() {
        return orderId;
      }

      @Override
      public CurrencyPair getCurrencyPair() {
        return currencyPair;
      }
    }

    CombinedCancelParams cancelParams = new CombinedCancelParams();

    // when
    when(coinsphAuthenticated.cancelOrder(
            anyString(), any(), any(), eq("BTCPHP"), eq(12345L), any(), anyLong()))
        .thenReturn(mockResponse);

    // then
    boolean result = tradeService.cancelOrder(cancelParams);

    assertThat(result).isTrue();
  }

  @Test
  public void testGetTradeHistoryCurrencyPairParamsScenario() throws IOException {
    // given
    List<CoinsphUserTrade> mockTrades = getCoinsphUserTrades();

    when(coinsphAuthenticated.getMyTrades(
            anyString(), any(), any(), eq("BTCPHP"), any(), any(), any(), any(), any(), anyLong()))
        .thenReturn(mockTrades);

    // Create trade history params
    TradeHistoryParams params = new CoinsphTradeHistoryParams(CurrencyPair.BTC_PHP);

    // when
    UserTrades userTrades = tradeService.getTradeHistory(params);

    // then
    assertCoinsphUserTrades(userTrades);
  }

  @Test
  public void testGetTradeHistoryInstrumentParamsScenario() throws IOException {
    // given
    List<CoinsphUserTrade> mockTrades = getCoinsphUserTrades();

    when(coinsphAuthenticated.getMyTrades(
            anyString(), any(), any(), eq("BTCPHP"), any(), any(), any(), any(), any(), anyLong()))
        .thenReturn(mockTrades);

    // Create trade history params
    TradeHistoryParams params = new CoinsphTradeHistoryParams((Instrument) CurrencyPair.BTC_PHP);

    // when
    UserTrades userTrades = tradeService.getTradeHistory(params);

    // then
    assertCoinsphUserTrades(userTrades);
  }

  @Test
  public void testGetTradeHistoryInvalidParamsScenario() {
    // given
    TradeHistoryParams params = new CoinsphTradeHistoryParams();

    // when
    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> tradeService.getTradeHistory(params));

    // then
    assertThat(exception.getMessage())
        .contains("TradeHistoryParams must include either CurrencyPair or Instrument for Coins.ph");
  }

  private static List<CoinsphUserTrade> getCoinsphUserTrades() {
    List<CoinsphUserTrade> mockTrades = new ArrayList<>();

    CoinsphUserTrade trade1 =
        new CoinsphUserTrade(
            "BTCPHP", // symbol
            12345L, // id
            11111L, // orderId
            new BigDecimal("5800000.0"), // price
            new BigDecimal("0.1"), // qty
            new BigDecimal("580000.0"), // quoteQty
            new BigDecimal("5.8"), // commission
            "PHP", // commissionAsset
            1621234560000L, // time
            true, // isBuyer
            false, // isMaker
            true // isBestMatch
            );
    mockTrades.add(trade1);

    CoinsphUserTrade trade2 =
        new CoinsphUserTrade(
            "BTCPHP", // symbol
            12346L, // id
            22222L, // orderId
            new BigDecimal("5801000.0"), // price
            new BigDecimal("0.2"), // qty
            new BigDecimal("1160200.0"), // quoteQty
            new BigDecimal("11.6"), // commission
            "PHP", // commissionAsset
            1621234570000L, // time
            false, // isBuyer
            true, // isMaker
            true // isBestMatch
            );
    mockTrades.add(trade2);
    return mockTrades;
  }

  private static void assertCoinsphUserTrades(UserTrades userTrades) {
    assertThat(userTrades).isNotNull();
    assertThat(userTrades.getUserTrades()).hasSize(2);

    assertThat(userTrades.getUserTrades().get(0).getId()).isEqualTo("12345");
    assertThat(userTrades.getUserTrades().get(0).getOrderId()).isEqualTo("11111");
    assertThat(userTrades.getUserTrades().get(0).getType()).isEqualTo(Order.OrderType.BID);
    assertThat(userTrades.getUserTrades().get(0).getPrice())
        .isEqualByComparingTo(new BigDecimal("5800000.0"));
    assertThat(userTrades.getUserTrades().get(0).getOriginalAmount())
        .isEqualByComparingTo(new BigDecimal("0.1"));

    assertThat(userTrades.getUserTrades().get(1).getId()).isEqualTo("12346");
    assertThat(userTrades.getUserTrades().get(1).getOrderId()).isEqualTo("22222");
    assertThat(userTrades.getUserTrades().get(1).getType()).isEqualTo(Order.OrderType.ASK);
    assertThat(userTrades.getUserTrades().get(1).getPrice())
        .isEqualByComparingTo(new BigDecimal("5801000.0"));
    assertThat(userTrades.getUserTrades().get(1).getOriginalAmount())
        .isEqualByComparingTo(new BigDecimal("0.2"));
  }
}
