package org.knowm.xchange.bittrex;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexLevel;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexTrade;
import org.knowm.xchange.bittrex.dto.trade.BittrexOrder;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;

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
            null, null, null, null, null, null, null, null, null, null, null, null, null, null,
            null, null, null);
    Assert.assertEquals(
        Order.OrderStatus.PARTIALLY_FILLED, BittrexAdapters.adaptOrderStatus(orderPartiallyFilled));
    Assert.assertEquals(Order.OrderStatus.FILLED, BittrexAdapters.adaptOrderStatus(orderFilled));
    Assert.assertEquals(Order.OrderStatus.NEW, BittrexAdapters.adaptOrderStatus(orderNew));
    Assert.assertEquals(Order.OrderStatus.UNKNOWN, BittrexAdapters.adaptOrderStatus(orderUnknown));
  }

  public void testAdaptTrades() {
    CurrencyPair pair = CurrencyPair.ETH_BTC;

    BittrexTrade trade1 = new BittrexTrade();
    trade1.setExecutedAt(new Date());
    trade1.setId("123");
    trade1.setQuantity(new BigDecimal("1"));
    trade1.setRate(new BigDecimal("2"));
    trade1.setTakerSide(BittrexConstants.BUY);

    BittrexTrade trade2 = new BittrexTrade();
    trade2.setExecutedAt(new Date());
    trade2.setId("456");
    trade2.setQuantity(new BigDecimal("3"));
    trade2.setRate(new BigDecimal("4"));
    trade2.setTakerSide(BittrexConstants.SELL);

    Trade adaptedTrade1 = BittrexAdapters.adaptTrade(trade1, pair);
    Trade adaptedTrade2 = BittrexAdapters.adaptTrade(trade2, pair);

    List<BittrexTrade> bittrexTradesList = Arrays.asList(trade1, trade2);
    List<Trade> tradesList = Arrays.asList(adaptedTrade1, adaptedTrade2);

    Trades adaptedTrades = BittrexAdapters.adaptTrades(bittrexTradesList, pair);
    Trades trades =
        new Trades(
            tradesList,
            Math.max(Long.parseLong(trade1.getId()), Long.parseLong(trade2.getId())),
            Trades.TradeSortType.SortByID);
    Assert.assertEquals(trades.getlastID(), adaptedTrades.getlastID());
    Assert.assertEquals(trades.getNextPageCursor(), adaptedTrades.getNextPageCursor());
    Assert.assertEquals(trades.getTrades(), adaptedTrades.getTrades());
    Assert.assertEquals(trades.getTradeSortType(), adaptedTrades.getTradeSortType());
  }
}
