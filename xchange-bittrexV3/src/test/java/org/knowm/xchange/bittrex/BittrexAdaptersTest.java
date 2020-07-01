package org.knowm.xchange.bittrex;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexLevel;
import org.knowm.xchange.bittrex.dto.trade.BittrexOrder;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;

import junit.framework.TestCase;

public class BittrexAdaptersTest extends TestCase {

  @Test
  public void testAdaptOrders() {
    BittrexLevel ask1 = new BittrexLevel(new BigDecimal("10"), new BigDecimal("1"));
    BittrexLevel ask2 = new BittrexLevel(new BigDecimal("11"), new BigDecimal("2"));
    BittrexLevel ask3 = new BittrexLevel(new BigDecimal("12"), new BigDecimal("3"));

    BittrexLevel bid1 = new BittrexLevel(new BigDecimal("9"), new BigDecimal("4"));
    BittrexLevel bid2 = new BittrexLevel(new BigDecimal("8"), new BigDecimal("5"));
    BittrexLevel bid3 = new BittrexLevel(new BigDecimal("7"), new BigDecimal("6"));

    BittrexLevel[] asks = {ask1, ask2, ask3};
    BittrexLevel[] bids = {bid1, bid2, bid3};
    CurrencyPair currencyPair = CurrencyPair.ETH_BTC;
    Order.OrderType askType = Order.OrderType.ASK;
    Order.OrderType bidType = Order.OrderType.BID;
    int depth = 2;

    List<LimitOrder> expectedAsks =
        Arrays.asList(
            new LimitOrder(askType, ask1.getAmount(), currencyPair, null, null, ask1.getPrice()),
            new LimitOrder(askType, ask2.getAmount(), currencyPair, null, null, ask2.getPrice()));

    List<LimitOrder> expectedBids =
        Arrays.asList(
            new LimitOrder(bidType, bid1.getAmount(), currencyPair, null, null, bid1.getPrice()),
            new LimitOrder(bidType, bid2.getAmount(), currencyPair, null, null, bid2.getPrice()));

    List<LimitOrder> adaptedAsks = BittrexAdapters.adaptOrders(asks, currencyPair, askType, depth);
    List<LimitOrder> adaptedBids = BittrexAdapters.adaptOrders(bids, currencyPair, bidType, depth);

    Assert.assertEquals(expectedAsks, adaptedAsks);
    Assert.assertEquals(expectedBids, adaptedBids);
  }

  public void testAdaptOrderStatus() {
    BittrexOrder orderPartiallyFilled =
        new BittrexOrder(
            null,
            null,
            null,
            null,
            new BigDecimal("10"),
            null,
            null,
            null,
            null,
            new BigDecimal("5"),
            null,
            null,
            null,
            null,
            null,
            null,
            null);
    BittrexOrder orderFilled =
        new BittrexOrder(
            null,
            null,
            null,
            null,
            new BigDecimal("10"),
            null,
            null,
            null,
            null,
            new BigDecimal("10"),
            null,
            null,
            null,
            null,
            null,
            null,
            null);
    BittrexOrder orderNew =
        new BittrexOrder(
            null,
            null,
            null,
            null,
            new BigDecimal("10"),
            null,
            null,
            null,
            null,
            new BigDecimal("0"),
            null,
            null,
            null,
            null,
            null,
            null,
            null);
    BittrexOrder orderUnknown =
        new BittrexOrder(
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
            null,
            null,
            null,
            null,
            null,
            null,
            null);
    Assert.assertEquals(
        Order.OrderStatus.PARTIALLY_FILLED, BittrexAdapters.adaptOrderStatus(orderPartiallyFilled));
    Assert.assertEquals(Order.OrderStatus.FILLED, BittrexAdapters.adaptOrderStatus(orderFilled));
    Assert.assertEquals(Order.OrderStatus.NEW, BittrexAdapters.adaptOrderStatus(orderNew));
    Assert.assertEquals(Order.OrderStatus.UNKNOWN, BittrexAdapters.adaptOrderStatus(orderUnknown));
  }
}
