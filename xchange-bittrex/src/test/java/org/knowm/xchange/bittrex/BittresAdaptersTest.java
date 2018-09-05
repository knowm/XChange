package org.knowm.xchange.bittrex;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;
import org.knowm.xchange.bittrex.dto.BittrexBaseReponse;
import org.knowm.xchange.bittrex.dto.account.BittrexOrder;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;

public class BittresAdaptersTest {

  @Test
  public void testAdaptBittresOrderIsNew() {
    BittrexOrder order = new BittrexOrder();

    order.setType("LIMIT_SELL");
    order.setExchange("USDT-ETH");
    order.setQuantity(new BigDecimal("100.32"));
    order.setQuantityRemaining(new BigDecimal("100.32"));
    order.setIsOpen(true);
    order.setCancelInitiated(false);
    assertEquals(Order.OrderStatus.NEW, BittrexAdapters.adaptOrder(order).getStatus());
  }

  @Test
  public void testAdaptBittresOrderIsPartiallyFilled() {
    BittrexOrder order = new BittrexOrder();

    order.setType("LIMIT_SELL");
    order.setExchange("USDT-ETH");
    order.setQuantity(new BigDecimal("100.32"));
    order.setQuantityRemaining(new BigDecimal("100.00"));
    order.setIsOpen(true);
    order.setCancelInitiated(false);
    assertEquals(Order.OrderStatus.PARTIALLY_FILLED, BittrexAdapters.adaptOrder(order).getStatus());
  }

  @Test
  public void testAdaptBittresOrderIsCancelPending() {
    BittrexOrder order = new BittrexOrder();

    order.setType("LIMIT_SELL");
    order.setExchange("USDT-ETH");
    order.setQuantity(new BigDecimal("100.32"));
    order.setQuantityRemaining(new BigDecimal("100.00"));
    order.setIsOpen(true);
    order.setCancelInitiated(true);
    assertEquals(Order.OrderStatus.PENDING_CANCEL, BittrexAdapters.adaptOrder(order).getStatus());
  }

  @Test
  public void testAdaptBittresOrderIsCanceled() {
    BittrexOrder order = new BittrexOrder();

    order.setType("LIMIT_SELL");
    order.setExchange("USDT-ETH");
    order.setQuantity(new BigDecimal("100.32"));
    order.setQuantityRemaining(new BigDecimal("100.00"));
    order.setIsOpen(false);
    order.setCancelInitiated(true);
    assertEquals(Order.OrderStatus.CANCELED, BittrexAdapters.adaptOrder(order).getStatus());
  }

  @Test
  public void testAdaptBittresOrderIsFilled() {
    BittrexOrder order = new BittrexOrder();

    order.setType("LIMIT_SELL");
    order.setExchange("USDT-ETH");
    order.setQuantity(new BigDecimal("100.32"));
    order.setQuantityRemaining(new BigDecimal("0E-8"));
    order.setIsOpen(false);
    order.setCancelInitiated(false);
    assertEquals(Order.OrderStatus.FILLED, BittrexAdapters.adaptOrder(order).getStatus());
  }

  @Test
  public void testAdaptLimitOrder() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        BittresAdaptersTest.class.getResourceAsStream(
            "/org/knowm/xchange/bittrex/dto/trade/order/example-limit-buy-order.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    JavaType responseType =
        mapper
            .getTypeFactory()
            .constructParametricType(BittrexBaseReponse.class, BittrexOrder.class);
    BittrexBaseReponse<BittrexOrder> bittrexOrderResponse = mapper.readValue(is, responseType);

    Order order = BittrexAdapters.adaptOrder(bittrexOrderResponse.getResult());

    assertThat(order.getId()).isEqualTo("0cb4c4e4-bdc7-4e13-8c13-430e587d2cc1");
    assertThat(order.getAveragePrice()).isNull();
    assertThat(order.getCumulativeAmount()).isEqualTo(new BigDecimal("0.00000000"));
    assertThat(order.getCurrencyPair()).isEqualTo(CurrencyPair.LTC_BTC);
    assertThat(order.getStatus()).isEqualTo(Order.OrderStatus.NEW);
    assertThat(LimitOrder.class.isAssignableFrom(order.getClass()));
  }
}
