package com.xeiam.xchange.service.marketdata.streaming;

import java.net.MalformedURLException;
import java.util.concurrent.BlockingQueue;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class DefaultStreamingMarketDataServiceTest {

  @Test
  public void testRegisterMarketDataListener_expectSuccess() throws MalformedURLException, InterruptedException {

    // Arrange
    DefaultStreamingMarketDataService testObject = new DefaultStreamingMarketDataService("intersango.com", 1337);
    RunnableMarketDataListener marketDataListener = new RunnableMarketDataListener() {
      @Override
      public void handleEvent(MarketDataEvent event) {
        System.out.println(event.getRawData());
      }

      @Override
      public BlockingQueue<MarketDataEvent> getMarketDataEventQueue() {
        return null;
      }
    };

    // Act
    testObject.registerMarketDataListener(marketDataListener);

    // Assert
    Thread.sleep(100000L);
  }

}
