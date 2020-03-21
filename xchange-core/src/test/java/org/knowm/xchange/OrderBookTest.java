package org.knowm.xchange;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.OrderBookUpdate;
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
}
