package com.xeiam.xchange.service.marketdata.streaming;

import org.junit.Ignore;
import org.junit.Test;

import java.net.MalformedURLException;

@Ignore
public class DefaultStreamingMarketDataServiceTest {

  // TODO Use a mock now that the demo is in place
  @Test
  public void testRegisterMarketDataListener_expectSuccess() throws MalformedURLException, InterruptedException {

    // Arrange
    DefaultStreamingMarketDataService testObject = new DefaultStreamingMarketDataService("intersango.com", 1337);
    RunnableMarketDataListener marketDataListener = new RunnableMarketDataListener() {
      @Override
      public void handleEvent(MarketDataEvent event) {
        System.out.println(event.getRawData());
      }

    };

    // Act
    testObject.registerMarketDataListener(marketDataListener);

    // Assert
    Thread.sleep(10000L);
  }

}
