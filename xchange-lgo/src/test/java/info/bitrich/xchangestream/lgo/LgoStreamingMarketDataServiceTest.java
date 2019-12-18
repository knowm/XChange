package info.bitrich.xchangestream.lgo;

import com.fasterxml.jackson.databind.JsonNode;
import io.reactivex.Observable;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.*;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.*;
import org.knowm.xchange.dto.trade.LimitOrder;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.*;
import java.util.TimeZone;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LgoStreamingMarketDataServiceTest {

    private final RecursiveComparisonConfiguration orderComparator = new RecursiveComparisonConfiguration();
    private SimpleDateFormat dateFormat;

    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        orderComparator.ignoreFields("userReference", "id");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    @Test
    public void it_reads_trades() throws IOException, ParseException {
        LgoStreamingService streamingService = mock(LgoStreamingService.class);
        LgoStreamingMarketDataService service = new LgoStreamingMarketDataService(streamingService);
        JsonNode message = TestUtils.getJsonContent("/marketdata/trades-update.json");
        when(streamingService.subscribeChannel(anyString())).thenReturn(Observable.just(message));

        Observable<Trade> trades = service.getTrades(CurrencyPair.BTC_USD);

        verify(streamingService).subscribeChannel("trades-BTC-USD");
        assertThat(trades.blockingFirst())
                .isEqualToComparingFieldByField(
                        new Trade(
                                Order.OrderType.ASK,
                                new BigDecimal("4.36920000"),
                                CurrencyPair.BTC_USD,
                                new BigDecimal("428.5000"),
                                dateFormat.parse("2019-07-19T12:25:01.596Z"),
                                "3128770"));
        assertThat(trades.blockingLast())
                .isEqualToComparingFieldByField(
                        new Trade(
                                Order.OrderType.BID,
                                new BigDecimal("1.85390000"),
                                CurrencyPair.BTC_USD,
                                new BigDecimal("420.3000"),
                                dateFormat.parse("2019-07-19T12:25:05.860Z"),
                                "3128771"));
    }

    @Test
    public void it_reads_level2() throws IOException {
        LgoStreamingService streamingService = mock(LgoStreamingService.class);
        LgoStreamingMarketDataService service = new LgoStreamingMarketDataService(streamingService);
        JsonNode snapshot = TestUtils.getJsonContent("/marketdata/level2-snapshot.json");
        JsonNode update = TestUtils.getJsonContent("/marketdata/level2-update.json");
        when(streamingService.subscribeChannel(anyString()))
                .thenReturn(Observable.just(snapshot, update));

        Observable<OrderBook> orderBook = service.getOrderBook(CurrencyPair.BTC_USD);

        verify(streamingService).subscribeChannel("level2-BTC-USD");
        OrderBook firstBook = orderBook.blockingFirst();
        assertThat(firstBook.getAsks())
                .usingElementComparatorIgnoringFields("id", "userReference")
                .contains(sellOrder("1111.1000", "9.39370000"), sellOrder("1115.9000", "0.88420000"));
        assertThat(firstBook.getBids())
                .usingElementComparatorIgnoringFields("id", "userReference")
                .contains(buyOrder("1089.1000", "0.10000000"));
        OrderBook lastBook = orderBook.blockingLast();
        assertThat(lastBook.getBids()).isEmpty();
        assertThat(lastBook.getAsks()).usingElementComparatorIgnoringFields("id", "userReference")
                .contains(
                        sellOrder("1111.1000", "0.10000000"),
                        sellOrder("1115.9000", "0.88420000"),
                        sellOrder("1116.9000", "0.20000000"));

    }

    private LimitOrder sellOrder(String price, String quantity) {
        return new LimitOrder(
                Order.OrderType.ASK,
                new BigDecimal(quantity),
                CurrencyPair.BTC_USD,
                null,
                null,
                new BigDecimal(price));
    }

    private LimitOrder buyOrder(String price, String quantity) {
        return new LimitOrder(
                Order.OrderType.BID,
                new BigDecimal(quantity),
                CurrencyPair.BTC_USD,
                "0",
                null,
                new BigDecimal(price));
    }

}
