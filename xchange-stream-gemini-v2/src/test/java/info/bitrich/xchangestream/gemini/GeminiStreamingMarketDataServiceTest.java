package info.bitrich.xchangestream.gemini;

import static org.junit.Assert.assertEquals;
import static org.knowm.xchange.currency.CurrencyPair.LTC_USD;
import static org.mockito.Mockito.when;

import info.bitrich.xchangestream.core.ProductSubscription;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class GeminiStreamingMarketDataServiceTest {

  @InjectMocks GeminiStreamingMarketDataService geminiStreamingMarketDataService;

  @Mock GeminiStreamingService geminiStreamingService;
  @Mock ProductSubscription mockProductSubscription;

  @Before
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  /**
   * Simulates the case in which an attempt to subscribe to an Flowable of a currency pair not
   * defined in the product subscription passed to
   * GeminiStreamingMarketDataService.connect(productSubscription) throws an
   * UnsupportedOperationException.
   */
  @Test(expected = UnsupportedOperationException.class)
  public void getOrderBook_InvalidPair() {
    when(geminiStreamingService.getProduct()).thenReturn(mockProductSubscription);
    when(mockProductSubscription.getOrderBook()).thenReturn(Arrays.asList(CurrencyPair.BTC_USD));

    try {
      geminiStreamingMarketDataService.getOrderBook(LTC_USD).subscribe(orderBook -> {});
    } catch (Exception e) {
      System.out.println(e.getMessage());
      assertEquals(
          String.format("The currency pair %s is not subscribed for orderbook", LTC_USD),
          e.getMessage());
      throw e;
    }
  }
}
