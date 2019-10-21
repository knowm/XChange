package org.knowm.xchange.dto.trade;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import org.junit.Test;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.utils.ObjectMapperHelper;

public class UserTradeTest {

  @Test
  public void testBuilder() {
    final OrderType type = OrderType.BID;
    final BigDecimal originalAmount = new BigDecimal("99.401");
    final CurrencyPair currencyPair = CurrencyPair.LTC_BTC;
    final BigDecimal price = new BigDecimal("251.64");
    final Date timestamp = new Date();
    final String id = "id";
    final String orderId = "OrderId";
    final BigDecimal feeAmount = new BigDecimal("0.0006");
    final Currency feeCurrency = Currency.BTC;

    final UserTrade copy =
        new UserTrade.Builder()
            .type(type)
            .originalAmount(originalAmount)
            .currencyPair(currencyPair)
            .price(price)
            .timestamp(timestamp)
            .id(id)
            .orderId(orderId)
            .feeAmount(feeAmount)
            .feeCurrency(feeCurrency)
            .build();

    assertThat(copy.getType()).isEqualTo(type);
    assertThat(copy.getOriginalAmount()).isEqualTo(originalAmount);
    assertThat(copy.getCurrencyPair()).isEqualTo(currencyPair);
    assertThat(copy.getPrice()).isEqualTo(price);
    assertThat(copy.getTimestamp()).isEqualTo(timestamp);
    assertThat(copy.getId()).isEqualTo(id);
    assertThat(copy.getOrderId()).isEqualTo(orderId);
    assertThat(copy.getFeeAmount()).isEqualTo(feeAmount);
    assertThat(copy.getFeeCurrency()).isEqualTo(feeCurrency);
  }

  @Test
  public void testBuilderFrom() {
    final OrderType type = OrderType.ASK;
    final BigDecimal originalAmount = new BigDecimal("100.501");
    final CurrencyPair currencyPair = CurrencyPair.BTC_USD;
    final BigDecimal price = new BigDecimal("250.34");
    final Date timestamp = new Date();
    final String id = "id";
    final String orderId = "OrderId";
    final BigDecimal feeAmount = new BigDecimal("0");
    final Currency feeCurrency = Currency.BTC;

    final UserTrade original =
        new UserTrade(
            type,
            originalAmount,
            currencyPair,
            price,
            timestamp,
            id,
            orderId,
            feeAmount,
            feeCurrency);
    final UserTrade copy = UserTrade.Builder.from(original).build();

    assertThat(copy.getType()).isEqualTo(original.getType());
    assertThat(copy.getOriginalAmount()).isEqualTo(original.getOriginalAmount());
    assertThat(copy.getCurrencyPair()).isEqualTo(original.getCurrencyPair());
    assertThat(copy.getPrice()).isEqualTo(original.getPrice());
    assertThat(copy.getTimestamp()).isEqualTo(original.getTimestamp());
    assertThat(copy.getId()).isEqualTo(original.getId());
    assertThat(copy.getOrderId()).isEqualTo(original.getOrderId());
    assertThat(copy.getFeeAmount()).isEqualTo(original.getFeeAmount());
    assertThat(copy.getFeeCurrency()).isEqualTo(original.getFeeCurrency());
  }

  @Test
  public void testSerializeDeserialize() throws IOException {
    final OrderType type = OrderType.ASK;
    final BigDecimal originalAmount = new BigDecimal("100.501");
    final CurrencyPair currencyPair = CurrencyPair.BTC_USD;
    final BigDecimal price = new BigDecimal("250.34");
    final Date timestamp = new Date();
    final String id = "id";
    final String orderId = "OrderId";
    final BigDecimal feeAmount = new BigDecimal("0");
    final Currency feeCurrency = Currency.BTC;
    final UserTrade original =
        new UserTrade(
            type,
            originalAmount,
            currencyPair,
            price,
            timestamp,
            id,
            orderId,
            feeAmount,
            feeCurrency);

    String json = ObjectMapperHelper.toCompactJSON(original);
    assertThat(json).contains("\"currencyPair\":\"BTC/USD\"");

    UserTrade jsonCopy = ObjectMapperHelper.readValue(json, UserTrade.class);
    assertThat(jsonCopy).isEqualToComparingFieldByField(original);
  }

  @Test
  public void returnsEqualsCorrectlyWithEqualUserTrades() {
    final OrderType type = OrderType.ASK;
    final BigDecimal originalAmount = new BigDecimal("100.501");
    final CurrencyPair currencyPair = CurrencyPair.BTC_USD;
    final BigDecimal price = new BigDecimal("250.34");
    final Date timestamp = new Date();
    final String id = "id";
    final String orderId = "OrderId";
    final BigDecimal feeAmount = new BigDecimal("0");
    final Currency feeCurrency = Currency.BTC;

    final UserTrade original =
        new UserTrade(
            type,
            originalAmount,
            currencyPair,
            price,
            timestamp,
            id,
            orderId,
            feeAmount,
            feeCurrency);
    final UserTrade copy =
        new UserTrade(
            type,
            originalAmount,
            currencyPair,
            price,
            timestamp,
            id,
            orderId,
            feeAmount,
            feeCurrency);

    assertEquals(original, copy);
  }

  @Test
  public void returnsEqualsCorrectlyWithUnequalUserTradesOfUserTradeAttributes() {
    final UserTrade original =
        new UserTrade(
            OrderType.ASK,
            new BigDecimal("100.501"),
            CurrencyPair.BTC_USD,
            new BigDecimal("250.34"),
            new Date(),
            "id",
            "FooOrderId",
            new BigDecimal("0"),
            Currency.BTC);

    final UserTrade copy =
        new UserTrade(
            OrderType.ASK,
            new BigDecimal("100.501"),
            CurrencyPair.BTC_USD,
            new BigDecimal("250.34"),
            new Date(),
            "id",
            "BarOrderId",
            new BigDecimal("0.15"),
            Currency.USD);

    assertFalse(original.equals(copy));
  }

  @Test
  public void returnsEqualsCorrectlyWithUnequalUserTradesOfTradeAttributes() {
    final UserTrade original =
        new UserTrade(
            OrderType.ASK,
            new BigDecimal("100.501"),
            CurrencyPair.BTC_USD,
            new BigDecimal("250.34"),
            new Date(),
            "FooTradeId",
            "OrderId",
            new BigDecimal("0"),
            Currency.BTC);

    final UserTrade copy =
        new UserTrade(
            OrderType.ASK,
            new BigDecimal("100.501"),
            CurrencyPair.BTC_USD,
            new BigDecimal("250.34"),
            new Date(),
            "BarTradeId",
            "OrderId",
            new BigDecimal("0"),
            Currency.BTC);

    assertFalse(original.equals(copy));
  }
}
