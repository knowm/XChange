package org.knowm.xchange.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.utils.ObjectMapperHelper;

public class OrderBookTest {

  private OrderBook orderBook;

  @Before
  public void setUp() throws Exception {

    LimitOrder askOrder =
        new LimitOrder(
            OrderType.ASK,
            BigDecimal.ONE,
            CurrencyPair.BTC_USD,
            "",
            null,
            BigDecimal.TEN.add(BigDecimal.ONE));
    LimitOrder bidOrder =
        new LimitOrder(
            OrderType.BID, BigDecimal.ONE, CurrencyPair.BTC_USD, "", null, BigDecimal.TEN);

    List<LimitOrder> asks = new ArrayList<>(Arrays.asList(askOrder));
    List<LimitOrder> bids = new ArrayList<>(Arrays.asList(bidOrder));
    Date timeStamp = new Date();
    orderBook = new OrderBook(timeStamp, asks, bids);
  }

  @Test
  public void testSerializeDeserialize() throws IOException {
    OrderBook jsonCopy = ObjectMapperHelper.viaJSON(orderBook);
    assertThat(jsonCopy.getTimeStamp()).isEqualTo(orderBook.getTimeStamp());
  }

  @Test
  public void testUpdateAddOrder() {

    Date timeStamp = new Date(0);
    OrderBookUpdate lowerBidUpdate =
        new OrderBookUpdate(
            OrderType.BID,
            BigDecimal.ONE,
            CurrencyPair.BTC_USD,
            BigDecimal.TEN.subtract(BigDecimal.ONE),
            timeStamp,
            BigDecimal.ONE);
    orderBook.update(lowerBidUpdate);
    assertThat(orderBook.getBids().size()).isEqualTo(2);
  }

  @Test
  public void testUpdateRemoveOrder() {

    Date timeStamp = new Date(0);
    OrderBookUpdate lowerBidUpdate =
        new OrderBookUpdate(
            OrderType.BID,
            BigDecimal.ONE,
            CurrencyPair.BTC_USD,
            BigDecimal.TEN,
            timeStamp,
            BigDecimal.ZERO);
    orderBook.update(lowerBidUpdate);
    assertThat(orderBook.getBids().size()).isEqualTo(0);
  }

  @Test
  public void testUpdateRemoveSingleOrder() {

    Date timeStamp = new Date(0);
    LimitOrder limitOrder =
        new LimitOrder.Builder(OrderType.BID, CurrencyPair.BTC_USD)
            .originalAmount(BigDecimal.ONE)
            .limitPrice(BigDecimal.TEN)
            .timestamp(timeStamp)
            .cumulativeAmount(BigDecimal.ONE) // remaining amount is now 0
            .build();
    orderBook.update(limitOrder);
    assertThat(orderBook.getBids().size()).isEqualTo(0);
  }

  @Test
  public void testUpdateAddVolume() {

    Date timeStamp = new Date(0);
    OrderBookUpdate lowerBidUpdate =
        new OrderBookUpdate(
            OrderType.BID,
            BigDecimal.ONE,
            CurrencyPair.BTC_USD,
            BigDecimal.TEN,
            timeStamp,
            BigDecimal.TEN);
    orderBook.update(lowerBidUpdate);
    assertThat(orderBook.getBids().size()).isEqualTo(1);
    assertThat(orderBook.getBids().get(0).getOriginalAmount()).isEqualTo(BigDecimal.TEN);
  }

  @Test
  public void testDateSame() {

    Date timeStamp = new Date(0);
    OrderBookUpdate lowerBidUpdate =
        new OrderBookUpdate(
            OrderType.BID,
            BigDecimal.ONE,
            CurrencyPair.BTC_USD,
            BigDecimal.TEN,
            timeStamp,
            BigDecimal.TEN);
    Date oldDate = orderBook.getTimeStamp();
    orderBook.update(lowerBidUpdate);
    assertThat(orderBook.getTimeStamp()).isEqualTo(oldDate);
  }

  @Test
  public void testDateOther() {

    Date timeStamp = Date.from(orderBook.getTimeStamp().toInstant().plus(Duration.ofDays(10)));
    OrderBookUpdate lowerBidUpdate =
        new OrderBookUpdate(
            OrderType.BID,
            BigDecimal.ONE,
            CurrencyPair.BTC_USD,
            BigDecimal.TEN,
            timeStamp,
            BigDecimal.TEN);
    Date oldDate = orderBook.getTimeStamp();
    orderBook.update(lowerBidUpdate);
    assertThat(orderBook.getTimeStamp()).isAfter(oldDate);
    assertThat(orderBook.getTimeStamp()).isEqualTo(timeStamp);
  }

  @Test
  public void testOrderSorting() {
    List<LimitOrder> asks =
        Arrays.asList(
            new LimitOrder.Builder(OrderType.ASK, CurrencyPair.BTC_USD)
                .limitPrice(new BigDecimal("30"))
                .build(),
            new LimitOrder.Builder(OrderType.ASK, CurrencyPair.BTC_USD)
                .limitPrice(new BigDecimal("10"))
                .build(),
            new LimitOrder.Builder(OrderType.ASK, CurrencyPair.BTC_USD)
                .limitPrice(new BigDecimal("20"))
                .build());
    List<LimitOrder> bids =
        Arrays.asList(
            new LimitOrder.Builder(OrderType.BID, CurrencyPair.BTC_USD)
                .limitPrice(new BigDecimal("2"))
                .build(),
            new LimitOrder.Builder(OrderType.BID, CurrencyPair.BTC_USD)
                .limitPrice(new BigDecimal("3"))
                .build(),
            new LimitOrder.Builder(OrderType.BID, CurrencyPair.BTC_USD)
                .limitPrice(new BigDecimal("1"))
                .build());
    OrderBook book = new OrderBook(null, asks, bids, true);
    assertThat(book.getAsks().get(0).getLimitPrice()).isEqualTo(new BigDecimal("10"));
    assertThat(book.getBids().get(0).getLimitPrice()).isEqualTo(new BigDecimal("3"));
  }

  @Test
  public void testRecheckIdx()
      throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    Class[] cArg = new Class[3];
    cArg[0] = List.class;
    cArg[1] = LimitOrder.class;
    cArg[2] = int.class;
    Method method = OrderBook.class.getDeclaredMethod("recheckIdx", cArg);
    method.setAccessible(true);
    LimitOrder sameBidOrder =
        new LimitOrder(
            OrderType.BID, BigDecimal.ONE, CurrencyPair.BTC_USD, "", null, BigDecimal.TEN);
    LimitOrder smallerBidOrder =
        new LimitOrder(
            OrderType.BID, BigDecimal.ONE, CurrencyPair.BTC_USD, "", null, BigDecimal.ONE);
    LimitOrder higherBidOrder =
        new LimitOrder(
            OrderType.BID, BigDecimal.ONE, CurrencyPair.BTC_USD, "", null, new BigDecimal("10.5"));
    //idx!= -1,0
    assertThat(method.invoke(orderBook, new ArrayList<LimitOrder>(),sameBidOrder, 1)).isEqualTo(true);
    //idx=0, empty List
    assertThat(method.invoke(orderBook, new ArrayList<LimitOrder>(),sameBidOrder, 0)).isEqualTo(true);
    //idx=0, order equals
    assertThat(method.invoke(orderBook, orderBook.getBids(),sameBidOrder, 0)).isEqualTo(false);
    //idx=0, smallerBidOrder
    assertThat(method.invoke(orderBook, orderBook.getBids(),smallerBidOrder, 0)).isEqualTo(true);
    //idx=-1, empty List
    assertThat(method.invoke(orderBook, new ArrayList<LimitOrder>(),sameBidOrder, -1)).isEqualTo(false);
    //idx=-1, order equals
    assertThat(method.invoke(orderBook, orderBook.getBids(),sameBidOrder, -1)).isEqualTo(true);
    //idx=-1, smaller order
    assertThat(method.invoke(orderBook, orderBook.getBids(),smallerBidOrder, -1)).isEqualTo(true);
    //idx=-1, higher order
    assertThat(method.invoke(orderBook, orderBook.getBids(),higherBidOrder, -1)).isEqualTo(false);
  }

}
