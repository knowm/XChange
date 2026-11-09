package org.knowm.xchange.dto.trade;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Date;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.utils.ObjectMapperHelper;

class UserTradeTest {

  @Test
  void builder() {
    final OrderType type = OrderType.BID;
    final BigDecimal originalAmount = new BigDecimal("99.401");
    final CurrencyPair currencyPair = CurrencyPair.LTC_BTC;
    final BigDecimal price = new BigDecimal("251.64");
    final Date timestamp = new Date();
    final String id = "id";
    final String orderId = "OrderId";
    final BigDecimal feeAmount = new BigDecimal("0.0006");
    final Currency feeCurrency = Currency.BTC;
    final String orderUserReference = "orderUserReference";

    final UserTrade copy =
        UserTrade.builder()
            .type(type)
            .originalAmount(originalAmount)
            .instrument(currencyPair)
            .price(price)
            .timestamp(timestamp)
            .id(id)
            .orderId(orderId)
            .feeAmount(feeAmount)
            .feeCurrency(feeCurrency)
            .orderUserReference(orderUserReference)
            .build();

    assertThat(copy.getType()).isEqualTo(type);
    assertThat(copy.getOriginalAmount()).isEqualTo(originalAmount);
    assertThat(copy.getInstrument()).isEqualTo(currencyPair);
    assertThat(copy.getPrice()).isEqualTo(price);
    assertThat(copy.getTimestamp()).isEqualTo(timestamp);
    assertThat(copy.getId()).isEqualTo(id);
    assertThat(copy.getOrderId()).isEqualTo(orderId);
    assertThat(copy.getFeeAmount()).isEqualTo(feeAmount);
    assertThat(copy.getFeeCurrency()).isEqualTo(feeCurrency);
    assertThat(copy.getOrderUserReference()).isEqualTo(orderUserReference);
  }

  @Test
  void serializeDeserialize() throws Exception {
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
        UserTrade.builder()
            .type(type)
            .originalAmount(originalAmount)
            .instrument(currencyPair)
            .price(price)
            .timestamp(timestamp)
            .id(id)
            .orderId(orderId)
            .feeAmount(feeAmount)
            .feeCurrency(feeCurrency)
            .build();

    String json = ObjectMapperHelper.toCompactJSON(original);
    assertThat(json).contains("\"instrument\":\"BTC/USD\"");

    UserTrade jsonCopy = ObjectMapperHelper.readValueStrict(json, UserTrade.class);
    assertThat(jsonCopy).isEqualToComparingFieldByField(original);
  }

  @Test
  void returnsEqualsCorrectlyWithEqualUserTrades() {
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
        UserTrade.builder()
            .type(type)
            .originalAmount(originalAmount)
            .instrument(currencyPair)
            .price(price)
            .timestamp(timestamp)
            .id(id)
            .orderId(orderId)
            .feeAmount(feeAmount)
            .feeCurrency(feeCurrency)
            .build();
    final UserTrade copy =
        UserTrade.builder()
            .type(type)
            .originalAmount(originalAmount)
            .instrument(currencyPair)
            .price(price)
            .timestamp(timestamp)
            .id(id)
            .orderId(orderId)
            .feeAmount(feeAmount)
            .feeCurrency(feeCurrency)
            .build();

    assertThat(copy).isEqualTo(original);
  }

  @Test
  void returnsEqualsCorrectlyWithUnequalUserTradesOfUserTradeAttributes() {
    final OrderType type = OrderType.ASK;
    final BigDecimal originalAmount = new BigDecimal("100.501");
    final CurrencyPair currencyPair = CurrencyPair.BTC_USD;
    final BigDecimal price = new BigDecimal("250.34");
    final Date timestamp = new Date();
    final String id = "id";

    final UserTrade original =
        UserTrade.builder()
            .type(type)
            .originalAmount(originalAmount)
            .instrument(currencyPair)
            .price(price)
            .timestamp(timestamp)
            .id(id)
            .orderId("FooOrderId")
            .feeAmount(new BigDecimal("0"))
            .feeCurrency(Currency.BTC)
            .build();

    final UserTrade copy =
        UserTrade.builder()
            .type(type)
            .originalAmount(originalAmount)
            .instrument(currencyPair)
            .price(price)
            .timestamp(timestamp)
            .id(id)
            .orderId("BarOrderId")
            .feeAmount(new BigDecimal("0.15"))
            .feeCurrency(Currency.USD)
            .build();

    assertThat(copy).isNotEqualTo(original);
  }

  @Test
  void returnsEqualsCorrectlyWithUnequalUserTradesOfTradeAttributes() {
    final OrderType type = OrderType.ASK;
    final BigDecimal originalAmount = new BigDecimal("100.501");
    final CurrencyPair currencyPair = CurrencyPair.BTC_USD;
    final BigDecimal price = new BigDecimal("250.34");
    final Date timestamp = new Date();

    final UserTrade original =
        UserTrade.builder()
            .type(type)
            .originalAmount(originalAmount)
            .instrument(currencyPair)
            .price(price)
            .timestamp(timestamp)
            .id("FooTradeId")
            .orderId("OrderId")
            .feeAmount(new BigDecimal("0"))
            .feeCurrency(Currency.BTC)
            .build();

    final UserTrade copy =
        UserTrade.builder()
            .type(type)
            .originalAmount(originalAmount)
            .instrument(currencyPair)
            .price(price)
            .timestamp(timestamp)
            .id("BarTradeId")
            .orderId("OrderId")
            .feeAmount(new BigDecimal("0"))
            .feeCurrency(Currency.BTC)
            .build();

    assertThat(copy).isNotEqualTo(original);
  }
}
