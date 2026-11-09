package info.bitrich.xchangestream.gemini;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.knowm.xchange.currency.CurrencyPair.LTC_USD;
import static org.mockito.Mockito.when;

import info.bitrich.xchangestream.core.ProductSubscription;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class GeminiStreamingMarketDataServiceTest {

  @InjectMocks GeminiStreamingMarketDataService geminiStreamingMarketDataService;

  @Mock GeminiStreamingService geminiStreamingService;
  @Mock ProductSubscription mockProductSubscription;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
  }

  /**
   * Simulates the case in which an attempt to subscribe to an observable of a currency pair not
   * defined in the product subscription passed to
   * GeminiStreamingMarketDataService.connect(productSubscription) throws an
   * UnsupportedOperationException.
   */
  @Test
  void getOrderBook_InvalidPair() {
    when(geminiStreamingService.getProduct()).thenReturn(mockProductSubscription);
    when(mockProductSubscription.getOrderBook()).thenReturn(Arrays.asList(CurrencyPair.BTC_USD));
    assertThatExceptionOfType(UnsupportedOperationException.class)
        .isThrownBy(
            () -> {
              try {
                geminiStreamingMarketDataService.getOrderBook(LTC_USD).subscribe(orderBook -> {});
              } catch (Exception e) {
                //      System.out.println(e.getMessage());
                assertThat(e.getMessage())
                    .isEqualTo(
                        String.format(
                            "The currency pair %s is not subscribed for orderbook", LTC_USD));
                throw e;
              }
            });
  }
}
