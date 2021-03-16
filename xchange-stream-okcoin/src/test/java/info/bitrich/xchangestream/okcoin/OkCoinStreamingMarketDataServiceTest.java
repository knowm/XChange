package info.bitrich.xchangestream.okcoin;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.subscribers.TestSubscriber;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class OkCoinStreamingMarketDataServiceTest {

  @Mock private OkCoinStreamingService okCoinStreamingService;
  private OkCoinStreamingMarketDataService marketDataService;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    marketDataService = new OkCoinStreamingMarketDataService(okCoinStreamingService);
  }

  @Test
  public void testGetOrderBook() throws Exception {
    // Given order book in JSON
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode jsonNode =
        objectMapper.readTree(
            ClassLoader.getSystemClassLoader().getResourceAsStream("order-book.json"));

    when(okCoinStreamingService.subscribeChannel(any())).thenReturn(Flowable.just(jsonNode));

    Date timestamp = new Date(1484602135246L);

    List<LimitOrder> bids = new ArrayList<>();
    bids.add(
        new LimitOrder(
            Order.OrderType.BID,
            new BigDecimal("0.922"),
            CurrencyPair.BTC_USD,
            null,
            timestamp,
            new BigDecimal("819.9")));
    bids.add(
        new LimitOrder(
            Order.OrderType.BID,
            new BigDecimal("0.085"),
            CurrencyPair.BTC_USD,
            null,
            timestamp,
            new BigDecimal("818.63")));

    List<LimitOrder> asks = new ArrayList<>();
    asks.add(
        new LimitOrder(
            Order.OrderType.ASK,
            new BigDecimal("0.035"),
            CurrencyPair.BTC_USD,
            null,
            timestamp,
            new BigDecimal("821.6")));
    asks.add(
        new LimitOrder(
            Order.OrderType.ASK,
            new BigDecimal("5.18"),
            CurrencyPair.BTC_USD,
            null,
            timestamp,
            new BigDecimal("821.65")));
    asks.add(
        new LimitOrder(
            Order.OrderType.ASK,
            new BigDecimal("2.89"),
            CurrencyPair.BTC_USD,
            null,
            timestamp,
            new BigDecimal("821.7")));

    OrderBook expected = new OrderBook(timestamp, asks, bids);

    // Call get order book Flowable
    TestSubscriber<OrderBook> test = marketDataService.getOrderBook(CurrencyPair.BTC_USD).test();

    // Get order book object in correct order
    test.assertResult(expected);
  }
}
