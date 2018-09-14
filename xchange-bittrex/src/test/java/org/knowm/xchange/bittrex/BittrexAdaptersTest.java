package org.knowm.xchange.bittrex;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;
import org.knowm.xchange.bittrex.dto.BittrexBaseResponse;
import org.knowm.xchange.bittrex.dto.trade.BittrexOrder;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;

public class BittrexAdaptersTest {

  @Test
  public void testAdaptBittrexOrderIsNew() {

    BittrexOrder order =
        new BittrexOrder(
            null,
            null,
            "USDT-ETH",
            "LIMIT_SELL",
            new BigDecimal("100.32"),
            new BigDecimal("100.32"),
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            true,
            null,
            false,
            null,
            null,
            null,
            null);

    assertEquals(Order.OrderStatus.NEW, BittrexAdapters.adaptOrder(order).getStatus());
  }

  @Test
  public void testAdaptBittrexOrderIsPartiallyFilled() {

    BittrexOrder order =
        new BittrexOrder(
            null,
            null,
            "USDT-ETH",
            "LIMIT_SELL",
            new BigDecimal("100.32"),
            new BigDecimal("100.00"),
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            true,
            null,
            false,
            null,
            null,
            null,
            null);

    assertEquals(Order.OrderStatus.PARTIALLY_FILLED, BittrexAdapters.adaptOrder(order).getStatus());
  }

  @Test
  public void testAdaptBittrexOrderIsCancelPending() {

    BittrexOrder order =
        new BittrexOrder(
            null,
            null,
            "USDT-ETH",
            "LIMIT_SELL",
            new BigDecimal("100.32"),
            new BigDecimal("100.00"),
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            true,
            null,
            true,
            null,
            null,
            null,
            null);

    assertEquals(Order.OrderStatus.PENDING_CANCEL, BittrexAdapters.adaptOrder(order).getStatus());
  }

  @Test
  public void testAdaptBittrexOrderIsCanceled() {

    BittrexOrder order =
        new BittrexOrder(
            null,
            null,
            "USDT-ETH",
            "LIMIT_SELL",
            new BigDecimal("100.32"),
            new BigDecimal("100.00"),
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            false,
            null,
            true,
            null,
            null,
            null,
            null);

    assertEquals(Order.OrderStatus.CANCELED, BittrexAdapters.adaptOrder(order).getStatus());
  }

  @Test
  public void testAdaptBittrexOrderIsFilled() {

    BittrexOrder order =
        new BittrexOrder(
            null,
            null,
            "USDT-ETH",
            "LIMIT_SELL",
            new BigDecimal("100.32"),
            new BigDecimal("0E-8"),
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            false,
            null,
            false,
            null,
            null,
            null,
            null);

    assertEquals(Order.OrderStatus.FILLED, BittrexAdapters.adaptOrder(order).getStatus());
  }

  @Test
  public void testAdaptLimitOrder() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        BittrexAdaptersTest.class.getResourceAsStream(
            "/org/knowm/xchange/bittrex/dto/trade/order/example-limit-buy-order.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    JavaType responseType =
        mapper
            .getTypeFactory()
            .constructParametricType(BittrexBaseResponse.class, BittrexOrder.class);
    BittrexBaseResponse<BittrexOrder> bittrexOrderResponse = mapper.readValue(is, responseType);

    Order order = BittrexAdapters.adaptOrder(bittrexOrderResponse.getResult());

    assertThat(order.getId()).isEqualTo("0cb4c4e4-bdc7-4e13-8c13-430e587d2cc1");
    assertThat(order.getAveragePrice()).isNull();
    assertThat(order.getCumulativeAmount()).isEqualTo(new BigDecimal("0.00000000"));
    assertThat(order.getCurrencyPair()).isEqualTo(CurrencyPair.LTC_BTC);
    assertThat(order.getStatus()).isEqualTo(Order.OrderStatus.NEW);
    assertThat(LimitOrder.class.isAssignableFrom(order.getClass()));
  }
}
