package org.knowm.xchange.dto.trade;

import static org.fest.assertions.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;

public class UserTradeTest {

  @Test
  public void testBuilder() {
    final OrderType type = OrderType.BID;
    final BigDecimal tradableAmount = new BigDecimal("99.401");
    final CurrencyPair currencyPair = CurrencyPair.LTC_BTC;
    final BigDecimal price = new BigDecimal("251.64");
    final Date timestamp = new Date();
    final String id = "id";
    final String orderId = "OrderId";
    final BigDecimal feeAmount = new BigDecimal("0.0006");
    final Currency feeCurrency = Currency.BTC;

    final UserTrade copy = new UserTrade.Builder().type(type).tradableAmount(tradableAmount).currencyPair(currencyPair).price(price)
        .timestamp(timestamp).id(id).orderId(orderId).feeAmount(feeAmount).feeCurrency(feeCurrency).build();

    assertThat(copy.getType()).isEqualTo(type);
    assertThat(copy.getTradableAmount()).isEqualTo(tradableAmount);
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
    final BigDecimal tradableAmount = new BigDecimal("100.501");
    final CurrencyPair currencyPair = CurrencyPair.BTC_USD;
    final BigDecimal price = new BigDecimal("250.34");
    final Date timestamp = new Date();
    final String id = "id";
    final String orderId = "OrderId";
    final BigDecimal feeAmount = new BigDecimal("0");
    final Currency feeCurrency = Currency.BTC;

    final UserTrade original = new UserTrade(type, tradableAmount, currencyPair, price, timestamp, id, orderId, feeAmount, feeCurrency);
    final UserTrade copy = UserTrade.Builder.from(original).build();

    assertThat(copy.getType()).isEqualTo(original.getType());
    assertThat(copy.getTradableAmount()).isEqualTo(original.getTradableAmount());
    assertThat(copy.getCurrencyPair()).isEqualTo(original.getCurrencyPair());
    assertThat(copy.getPrice()).isEqualTo(original.getPrice());
    assertThat(copy.getTimestamp()).isEqualTo(original.getTimestamp());
    assertThat(copy.getId()).isEqualTo(original.getId());
    assertThat(copy.getOrderId()).isEqualTo(original.getOrderId());
    assertThat(copy.getFeeAmount()).isEqualTo(original.getFeeAmount());
    assertThat(copy.getFeeCurrency()).isEqualTo(original.getFeeCurrency());
  }
}
