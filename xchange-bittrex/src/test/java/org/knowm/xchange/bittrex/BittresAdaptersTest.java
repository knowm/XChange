package org.knowm.xchange.bittrex;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;
import org.knowm.xchange.bittrex.dto.account.BittrexOrder;
import org.knowm.xchange.dto.Order;

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
}
