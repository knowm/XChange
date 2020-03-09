package info.bitrich.xchangestream.cexio;

import static org.junit.Assert.*;

import info.bitrich.xchangestream.cexio.dto.CexioWebSocketOrderBookSubscribeResponse;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.trade.LimitOrder;

public class CexioAdaptersTest {

  @Test
  public void testAdaptOrderBookIncremental() {
    OrderBook orderBookSoFar =
        new OrderBook(new Date(), new ArrayList<LimitOrder>(), new ArrayList<LimitOrder>());

    List<List<BigDecimal>> askOrders = new ArrayList<List<BigDecimal>>();
    askOrders.add(
        new ArrayList<BigDecimal>(
            Arrays.asList(new BigDecimal[] {new BigDecimal(10), new BigDecimal(500)})));
    askOrders.add(
        new ArrayList<BigDecimal>(
            Arrays.asList(new BigDecimal[] {new BigDecimal(11), new BigDecimal(400)})));
    askOrders.add(
        new ArrayList<BigDecimal>(
            Arrays.asList(new BigDecimal[] {new BigDecimal(13), new BigDecimal(200)})));

    List<List<BigDecimal>> bidOrders = new ArrayList<List<BigDecimal>>();
    bidOrders.add(
        new ArrayList<BigDecimal>(
            Arrays.asList(new BigDecimal[] {new BigDecimal(10), new BigDecimal(200)})));
    bidOrders.add(
        new ArrayList<BigDecimal>(
            Arrays.asList(new BigDecimal[] {new BigDecimal(9), new BigDecimal(100)})));
    bidOrders.add(
        new ArrayList<BigDecimal>(
            Arrays.asList(new BigDecimal[] {new BigDecimal(7), new BigDecimal(50)})));

    List<LimitOrder> expectedAsks = new ArrayList<LimitOrder>();
    List<LimitOrder> expectedBids = new ArrayList<LimitOrder>();
    for (int i = 0; i < askOrders.size(); i++) {
      LimitOrder expectedAsk =
          new LimitOrder(
              OrderType.ASK,
              askOrders.get(i).get(1),
              CurrencyPair.LTC_USD,
              "1",
              new Date(1234567),
              askOrders.get(i).get(0));
      LimitOrder expectedBid =
          new LimitOrder(
              OrderType.BID,
              bidOrders.get(i).get(1),
              CurrencyPair.LTC_USD,
              "1",
              new Date(1234567),
              bidOrders.get(i).get(0));

      expectedAsks.add(expectedAsk);
      expectedBids.add(expectedBid);
    }

    CexioWebSocketOrderBookSubscribeResponse subResp1 =
        new CexioWebSocketOrderBookSubscribeResponse(
            new Date(1234567), null, bidOrders, askOrders, "LTC:USD", BigInteger.ONE);
    OrderBook orderBookV1 = CexioAdapters.adaptOrderBookIncremental(orderBookSoFar, subResp1);
    OrderBook expectedOrderBookV1 =
        new OrderBook(new Date(1234567), expectedAsks, expectedBids, true /* sort */);
    assertEquals(expectedOrderBookV1.getBids(), orderBookV1.getBids());
    assertEquals(expectedOrderBookV1.getAsks(), orderBookV1.getAsks());

    List<List<BigDecimal>> askOrders2 = new ArrayList<List<BigDecimal>>();
    askOrders2.add(
        new ArrayList<BigDecimal>(
            Arrays.asList(new BigDecimal[] {new BigDecimal(10), new BigDecimal(400)})));
    askOrders2.add(
        new ArrayList<BigDecimal>(
            Arrays.asList(new BigDecimal[] {new BigDecimal(11), new BigDecimal(600)})));
    askOrders2.add(
        new ArrayList<BigDecimal>(
            Arrays.asList(new BigDecimal[] {new BigDecimal(12), new BigDecimal(100)})));
    askOrders2.add(
        new ArrayList<BigDecimal>(
            Arrays.asList(new BigDecimal[] {new BigDecimal(15), new BigDecimal(50)})));

    List<List<BigDecimal>> bidOrders2 = new ArrayList<List<BigDecimal>>();
    bidOrders2.add(
        new ArrayList<BigDecimal>(
            Arrays.asList(new BigDecimal[] {new BigDecimal(9), new BigDecimal(150)})));
    bidOrders2.add(
        new ArrayList<BigDecimal>(
            Arrays.asList(new BigDecimal[] {new BigDecimal(8), new BigDecimal(70)})));
    bidOrders2.add(
        new ArrayList<BigDecimal>(
            Arrays.asList(new BigDecimal[] {new BigDecimal(6), new BigDecimal(30)})));

    CexioWebSocketOrderBookSubscribeResponse subResp2 =
        new CexioWebSocketOrderBookSubscribeResponse(
            new Date(1235567),
            null,
            bidOrders2,
            askOrders2,
            "LTC:USD",
            BigInteger.ONE.add(BigInteger.ONE));

    LimitOrder expectedAsk1 =
        new LimitOrder(
            OrderType.ASK,
            new BigDecimal(400),
            CurrencyPair.LTC_USD,
            "2",
            new Date(1235567),
            new BigDecimal(10));
    LimitOrder expectedAsk2 =
        new LimitOrder(
            OrderType.ASK,
            new BigDecimal(600),
            CurrencyPair.LTC_USD,
            "2",
            new Date(1235567),
            new BigDecimal(11));
    LimitOrder expectedAsk3 =
        new LimitOrder(
            OrderType.ASK,
            new BigDecimal(100),
            CurrencyPair.LTC_USD,
            "2",
            new Date(1235567),
            new BigDecimal(12));
    LimitOrder expectedAsk4 =
        new LimitOrder(
            OrderType.ASK,
            new BigDecimal(50),
            CurrencyPair.LTC_USD,
            "2",
            new Date(1235567),
            new BigDecimal(15));
    LimitOrder expectedAsk5 =
        new LimitOrder(
            OrderType.ASK,
            new BigDecimal(200),
            CurrencyPair.LTC_USD,
            "1",
            new Date(1234567),
            new BigDecimal(13));

    LimitOrder expectedBid1 =
        new LimitOrder(
            OrderType.BID,
            new BigDecimal(150),
            CurrencyPair.LTC_USD,
            "2",
            new Date(1235567),
            new BigDecimal(9));
    LimitOrder expectedBid2 =
        new LimitOrder(
            OrderType.BID,
            new BigDecimal(70),
            CurrencyPair.LTC_USD,
            "2",
            new Date(1235567),
            new BigDecimal(8));
    LimitOrder expectedBid3 =
        new LimitOrder(
            OrderType.BID,
            new BigDecimal(30),
            CurrencyPair.LTC_USD,
            "2",
            new Date(1235567),
            new BigDecimal(6));
    LimitOrder expectedBid4 =
        new LimitOrder(
            OrderType.BID,
            new BigDecimal(200),
            CurrencyPair.LTC_USD,
            "1",
            new Date(1234567),
            new BigDecimal(10));
    LimitOrder expectedBid5 =
        new LimitOrder(
            OrderType.BID,
            new BigDecimal(50),
            CurrencyPair.LTC_USD,
            "1",
            new Date(1234567),
            new BigDecimal(7));

    List<LimitOrder> expectedAsks2 =
        new ArrayList<LimitOrder>(
            Arrays.asList(
                new LimitOrder[] {
                  expectedAsk1, expectedAsk2, expectedAsk3, expectedAsk4, expectedAsk5
                }));
    List<LimitOrder> expectedBids2 =
        new ArrayList<LimitOrder>(
            Arrays.asList(
                new LimitOrder[] {
                  expectedBid1, expectedBid2, expectedBid3, expectedBid4, expectedBid5
                }));

    OrderBook orderBookV2 = CexioAdapters.adaptOrderBookIncremental(orderBookV1, subResp2);
    OrderBook expectedOrderBookV2 =
        new OrderBook(new Date(1235567), expectedAsks2, expectedBids2, true /* sort */);
    assertEquals(expectedOrderBookV2.getBids(), orderBookV2.getBids());
    assertEquals(expectedOrderBookV2.getAsks(), orderBookV2.getAsks());
  }
}
