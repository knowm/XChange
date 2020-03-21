package org.knowm.xchange.lgo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.lgo.LgoAdapters;
import org.knowm.xchange.lgo.LgoEnv;
import org.knowm.xchange.lgo.LgoExchange;
import org.knowm.xchange.lgo.dto.currency.LgoCurrencies;
import org.knowm.xchange.lgo.dto.product.LgoProducts;
import org.knowm.xchange.lgo.dto.product.LgoProductsTest;

public class LgoTradeServiceTest {

  private LgoTradeService tradeService;
  private LgoExchange exchange;

  @Before
  public void setUp() throws Exception {
    exchange = mock(LgoExchange.class);
    LgoProducts products =
        load("/org/knowm/xchange/lgo/product/example-products-data.json", LgoProducts.class);
    LgoCurrencies currencies =
        load("/org/knowm/xchange/lgo/currency/example-currencies-data.json", LgoCurrencies.class);
    ExchangeMetaData metaData = load("/lgo.json", ExchangeMetaData.class);
    when(exchange.getProducts()).thenReturn(products);
    when(exchange.getCurrencies()).thenReturn(currencies);
    when(exchange.getExchangeSpecification()).thenReturn(LgoEnv.sandbox());
    when(exchange.getExchangeMetaData())
        .thenReturn(LgoAdapters.adaptMetadata(metaData, products, currencies));
    tradeService = new LgoTradeService(exchange, mock(LgoKeyService.class));
  }

  private <T> T load(String resource, Class<T> clazz) throws java.io.IOException {
    InputStream is = LgoProductsTest.class.getResourceAsStream(resource);
    ObjectMapper mapper = new ObjectMapper();

    return mapper.readValue(is, clazz);
  }

  @Test
  public void acceptsCorrectLimitOrder() {
    tradeService.verifyOrder(
        new LimitOrder(
            OrderType.ASK,
            new BigDecimal("3"),
            CurrencyPair.BTC_USD,
            "",
            new Date(),
            new BigDecimal(7000)));
  }

  @Test
  public void acceptsCorrectMarketOrder() {
    tradeService.verifyOrder(
        new MarketOrder(OrderType.BID, new BigDecimal("1000"), CurrencyPair.BTC_USD, new Date()));
  }

  @Test
  public void cannotPlaceLimitOrderWithToLowBaseAmount() {
    ThrowingCallable check =
        () ->
            tradeService.verifyOrder(
                new LimitOrder(
                    OrderType.ASK,
                    new BigDecimal("0.00000001"),
                    CurrencyPair.BTC_USD,
                    "",
                    new Date(),
                    new BigDecimal(7000)));

    assertThatThrownBy(check)
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Unsupported amount scale 8");
  }

  @Test
  public void cannotPlaceLimitOrderWithToHighBaseAmount() {
    ThrowingCallable check =
        () ->
            tradeService.verifyOrder(
                new LimitOrder(
                    OrderType.ASK,
                    new BigDecimal("1001"),
                    CurrencyPair.BTC_USD,
                    "",
                    new Date(),
                    new BigDecimal(7000)));

    assertThatThrownBy(check)
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Order amount more than maximum");
  }

  @Test
  public void cannotPlaceLimitOrderWithToLowPrice() {
    ThrowingCallable check =
        () ->
            tradeService.verifyOrder(
                new LimitOrder(
                    OrderType.ASK,
                    new BigDecimal("10"),
                    CurrencyPair.BTC_USD,
                    "",
                    new Date(),
                    new BigDecimal(9)));

    assertThatThrownBy(check)
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Order price to low");
  }

  @Test
  public void cannotPlaceLimitOrderWithToHighPrice() {
    ThrowingCallable check =
        () ->
            tradeService.verifyOrder(
                new LimitOrder(
                    OrderType.ASK,
                    new BigDecimal("10"),
                    CurrencyPair.BTC_USD,
                    "",
                    new Date(),
                    new BigDecimal(1000001)));

    assertThatThrownBy(check)
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Order price to high");
  }

  @Test
  public void cannotPlaceLimitOrderWithInvalidPriceIncrement() {
    ThrowingCallable check =
        () ->
            tradeService.verifyOrder(
                new LimitOrder(
                    OrderType.ASK,
                    new BigDecimal("10"),
                    CurrencyPair.BTC_USD,
                    "",
                    new Date(),
                    new BigDecimal("100.05")));

    assertThatThrownBy(check)
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Unsupported price scale 2");
  }

  @Test
  public void canPlaceLimitOrderWithValidPriceIncrementAndScaleGreaterThan0() {
    tradeService.verifyOrder(
        new LimitOrder(
            OrderType.ASK,
            new BigDecimal("10"),
            CurrencyPair.BTC_USD,
            "",
            new Date(),
            new BigDecimal("100.1")));

    assertThat(true).describedAs("No exception thrown").isTrue();
  }

  @Test
  public void cannotPlaceBidMarketOrderWithAmountToLow() {
    ThrowingCallable check =
        () ->
            tradeService.verifyOrder(
                new MarketOrder(
                    OrderType.BID, new BigDecimal("0.00001"), CurrencyPair.BTC_USD, new Date()));

    assertThatThrownBy(check)
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Quantity to low");
  }

  @Test
  public void cannotPlaceBidMarketOrderWithAmountToHigh() {
    ThrowingCallable check =
        () ->
            tradeService.verifyOrder(
                new MarketOrder(
                    OrderType.BID, new BigDecimal("1000001"), CurrencyPair.BTC_USD, new Date()));

    assertThatThrownBy(check)
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Quantity to high");
  }

  @Test
  public void cannotPlaceAskMarketOrderWithAmountToLow() {
    ThrowingCallable check =
        () ->
            tradeService.verifyOrder(
                new MarketOrder(
                    OrderType.ASK, new BigDecimal("0.00001"), CurrencyPair.BTC_USD, new Date()));

    assertThatThrownBy(check)
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Quantity to low");
  }

  @Test
  public void cannotPlaceAskMarketOrderWithAmountToHigh() {
    ThrowingCallable check =
        () ->
            tradeService.verifyOrder(
                new MarketOrder(
                    OrderType.ASK, new BigDecimal("1001"), CurrencyPair.BTC_USD, new Date()));

    assertThatThrownBy(check)
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Quantity to high");
  }
}
